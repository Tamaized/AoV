package tamaized.aov.common.core.abilities;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.config.ConfigHandler;
import tamaized.tammodized.common.helper.RayTraceHelper;

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
		reset(cap, astro);
	}

	public static Ability construct(ByteBuf stream) {
		int id = stream.readInt();
		if (id < 0)
			return null;
		Ability ability = new Ability(AbilityBase.getAbilityFromID(id));
		ability.decode(stream);
		return ability;
	}

	public static Ability construct(IAoVCapability cap, @Nullable IAstroCapability astro, NBTTagCompound nbt) {
		int id = nbt.getInteger("id");
		if (id < 0)
			return null;
		Ability ability = new Ability(AbilityBase.getAbilityFromID(id), cap, astro);
		ability.decode(nbt);
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
	public NBTTagCompound encode(NBTTagCompound nbt) {
		nbt.setInteger("id", ability.getID());
		nbt.setInteger("cooldown", cooldown);
		nbt.setInteger("charges", charges);
		nbt.setInteger("decay", decay);
		nbt.setInteger("timer", timer);
		nbt.setBoolean("disabled", disabled);
		return nbt;
	}

	public void decode(NBTTagCompound nbt) {
		cooldown = nbt.getInteger("cooldown");
		charges = nbt.getInteger("charges");
		decay = nbt.getInteger("decay");
		timer = nbt.getInteger("timer");
		disabled = nbt.getBoolean("disabled");
	}

	public void reset(IAoVCapability cap, @Nullable IAstroCapability astro) {
		cooldown = 0;
		nextCooldown = -1;
		charges = ability.getMaxCharges() < 0 ? -1 : ability.getMaxCharges() + cap.getExtraCharges();
		decay = 0;
		timer = -1;
		disabled = getAbility().shouldDisable(cap, astro);
	}

	public void restoreCharge(IAoVCapability cap, int amount) {
		charges += (ability.getMaxCharges() > -1 && charges < (ability.getMaxCharges() + cap.getExtraCharges())) ? amount : 0;
	}

	public void setNextCooldown(int cd) {
		nextCooldown = cd;
	}

	public void setTimer(int t) {
		timer = t;
	}

	@SuppressWarnings("unused")
	public void setDisabled() {
		disabled = true;
	}

	@SuppressWarnings("unused")
	public void setEnabled() {
		disabled = false;
	}

	public final void cast(EntityPlayer caster) {
		if (disabled)
			return;
		HashSet<Entity> set = new HashSet<>();
		set.add(caster);
		RayTraceResult ray = RayTraceHelper.tracePath(caster.world, caster, (int) getAbility().getMaxDistance(), 1, set);
		cast(caster, (ray == null || ray.entityHit == null || !(ray.entityHit instanceof EntityLivingBase)) ? null : (EntityLivingBase) ray.entityHit);
	}

	public void cast(EntityPlayer caster, EntityLivingBase target) {
		if (disabled)
			return;
		IAoVCapability cap = caster.getCapability(CapabilityList.AOV, null);
		if (cap != null) {
			if (cap.canUseAbility(this)) {
				if (ability.cast(this, caster, target))
					charges -= ability.getCost(cap);
				cooldown = (nextCooldown < 0 ? ability.getCoolDown() : nextCooldown) * ((ability.usesInvoke() && cap.getInvokeMass()) ? 2 : 1);
				nextCooldown = -1;
			}
		}
	}

	public void castAsAura(EntityPlayer caster, IAoVCapability cap, int life) {
		if (!disabled && ability instanceof IAura)
			((IAura) ability).castAsAura(caster, cap, life);
	}

	public boolean canUse(IAoVCapability cap) {
		return !disabled && cooldown <= 0 && (charges == -1 || charges >= ability.getCost(cap)) && cap.slotsContain(getAbility());
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

	@SuppressWarnings("unused")
	public int getDecay() {
		return decay;
	}

	public void update(IAoVCapability cap) {
		tick++;
		if (cooldown > 0)
			cooldown--;
		if (decay > 0 && tick % (20) == 0)
			decay--;
		if (ability.getMaxCharges() >= 0 && ConfigHandler.recharge >= 0 && charges < (ability.getMaxCharges() + cap.getExtraCharges()) && tick % ConfigHandler.recharge == 0)
			charges++;
		if (timer > 0)
			timer--;
		else if (timer == 0) {
			timer--;
			cooldown = (nextCooldown < 0 ? ability.getCoolDown() : nextCooldown) * ((ability.usesInvoke() && cap.getInvokeMass()) ? 2 : 1);
			nextCooldown = -1;
			cap.markDirty();
		}

	}

	public boolean compare(Ability check) {
		return check != null && ability == check.ability;
	}

}
