package tamaized.aov.common.core.abilities;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.helper.RayTraceHelper;

import javax.annotation.Nullable;
import java.util.HashSet;

public final class Ability {

	private AbilityBase ability;

	private int nextCooldown = -1;
	private int cooldown;
	private int charges;
	private int decay;
	private int timer = -1;
	private boolean disabled = false;

	private int tick = 0;

	public Ability(AbilityBase ability) {
		this.ability = ability;
	}

	public Ability(AbilityBase ability, IAoVCapability cap, @Nullable IAstroCapability astro) {
		this.ability = ability;
		reset(null, cap);
	}

	public static Ability construct(ByteBuf stream) {
		int id = stream.readInt();
		if (id < 0)
			return null;
		Ability ability = new Ability(AbilityBase.getAbilityFromID(id));
		ability.decode(stream);
		return ability;
	}

	public static Ability construct(IAoVCapability cap, @Nullable IAstroCapability astro, CompoundNBT nbt) {
		int id = nbt.getInt("id");
		if (id < 0)
			return null;
		Ability ability = new Ability(AbilityBase.getAbilityFromID(id), cap, astro);
		ability.decode(nbt, cap);
		return ability;
	}

	public void encode(ByteBuf stream) {
		stream.writeInt(ability.getID());
		stream.writeInt(cooldown);
		stream.writeInt(charges);
		stream.writeInt(decay);
		stream.writeInt(timer);
		stream.writeBoolean(disabled);
	}

	public void decode(ByteBuf stream) {
		cooldown = stream.readInt();
		charges = stream.readInt();
		decay = stream.readInt();
		timer = stream.readInt();
		disabled = stream.readBoolean();
	}

	@SuppressWarnings("UnusedReturnValue")
	public CompoundNBT encode(CompoundNBT nbt, IAoVCapability cap) {
		nbt.putInt("id", ability.getID());
		nbt.putInt("cooldown", cooldown);
		nbt.putInt("cooldownfailsafe", cap.getCooldown(ability));
		nbt.putInt("charges", charges);
		nbt.putInt("decay", decay);
		nbt.putInt("timer", timer);
		nbt.putBoolean("disabled", disabled);
		return nbt;
	}

	public void decode(CompoundNBT nbt, IAoVCapability cap) {
		cooldown = nbt.getInt("cooldown");
		int cooldownfailsafe = nbt.getInt("cooldownfailsafe");
		if (cooldownfailsafe > 0)
			cap.setCooldown(ability, cooldownfailsafe);
		charges = nbt.getInt("charges");
		decay = nbt.getInt("decay");
		timer = nbt.getInt("timer");
		disabled = nbt.getBoolean("disabled");
	}

	public void reset(PlayerEntity caster, IAoVCapability cap) {
		cooldown = cap.getCooldown(ability);
		nextCooldown = -1;
		charges = ability.getMaxCharges() < 0 ? -1 : ability.getMaxCharges() + cap.getExtraCharges(caster, this);
		decay = 0;
		timer = -1;
		disabled = getAbility().shouldDisable(caster, cap);
	}

	public void restoreCharge(LivingEntity caster, IAoVCapability cap, int amount) {
		charges += (ability.getMaxCharges() > -1 && charges < (ability.getMaxCharges() + cap.getExtraCharges(caster, this))) ? amount : 0;
	}

	public void setNextCooldown(int cd) {
		nextCooldown = cd;
	}

	public void setTimer(int t) {
		timer = t;
	}

	public final void cast(PlayerEntity caster) {
		if (disabled)
			return;
		HashSet<Entity> set = new HashSet<>();
		set.add(caster);
		RayTraceResult ray = RayTraceHelper.tracePath(caster, caster.world, caster, (int) getAbility().getMaxDistance(), 1, set);
		Entity target = ray instanceof EntityRayTraceResult ? ((EntityRayTraceResult) ray).getEntity() : null;
		cast(caster, target instanceof LivingEntity ? (LivingEntity) target : null);
	}

	public void cast(PlayerEntity caster, LivingEntity target) {
		if (disabled)
			return;
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap != null) {
			if (target != null && !ability.isCastOnTarget(caster, cap, target))
				target = null;
			if (cap.canUseAbility(this) && ((ability.usesInvoke() && cap.getInvokeMass()) || target == null || ability.getMaxDistance() >= caster.getDistance(target))) {
				if (!isOnCooldown(cap)) {
					if (ability.cast(this, caster, target)) {
						charges -= caster.isCreative() ? 0 : ability.getCost(cap);
						cooldown = (nextCooldown < 0 ? ability.getCoolDown() : nextCooldown) * ((ability.usesInvoke() && cap.getInvokeMass()) ? 2 : 1);
					} else
						cooldown = 1;
					cap.setCooldown(ability, cooldown);
					nextCooldown = -1;
				} else if (ability.canUseOnCooldown(cap, caster)) {
					ability.onCooldownCast(this, caster, target, cooldown);
				}
			}
		}
	}

	public void castAsAura(Aura aura, PlayerEntity caster, IAoVCapability cap, int life) {
		if (disabled)
			aura.kill();
		else if (ability instanceof IAura)
			((IAura) ability).castAsAura(caster, cap, life);
	}

	public boolean canUse(IAoVCapability cap) {
		return !disabled && (charges == -1 || charges >= ability.getCost(cap)) && cap.slotsContain(getAbility());
	}

	public boolean isOnCooldown(IAoVCapability cap) {
		return cooldown > 0 && cap.getCooldown(ability) > 0;
	}

	public AbilityBase getAbility() {
		return ability;
	}

	public int getCooldown() {
		return cooldown;
	}

	public float getCooldownPerc() {
		return (float) cooldown / (float) ability.getCoolDown();
	}

	public int getCharges() {
		return charges;
	}

	public int getDecay() {
		return decay;
	}

	public void update(PlayerEntity caster, IAoVCapability cap) {
		tick++;
		disabled = ability.shouldDisable(caster, cap);
		if (tick % 20 == 0 && cooldown > 0)
			cooldown--;
		if (decay > 0 && tick % (20 * 20) == 0)
			decay--;
		if (ability.getMaxCharges() >= 0 && AoV.config.recharge.get() >= 0 && charges < (ability.getMaxCharges() + cap.getExtraCharges(caster, this)) && tick % AoV.config.recharge.get() == 0)
			charges++;
		if (tick % 20 == 0 && timer > 0)
			timer--;
		else if (timer == 0) {
			timer--;
			cooldown = (nextCooldown < 0 ? ability.getCoolDown() : nextCooldown) * ((ability.usesInvoke() && cap.getInvokeMass()) ? 2 : 1);
			cap.setCooldown(ability, cooldown);
			nextCooldown = -1;
			cap.markDirty();
		}

	}

	public boolean compare(Ability check) {
		return check != null && ability == check.ability;
	}

}
