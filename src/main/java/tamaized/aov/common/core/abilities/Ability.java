package tamaized.aov.common.core.abilities;

import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.config.ConfigHandler;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.RayTraceResult;
import tamaized.tammodized.common.helper.RayTraceHelper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;

public final class Ability {

	private AbilityBase ability;

	private int cooldown;
	private int charges;
	private int decay;

	private int tick = 0;

	public Ability(AbilityBase ability, IAoVCapability cap) {
		this.ability = ability;
		reset(cap);
	}

	public static Ability construct(IAoVCapability cap, ByteBufInputStream stream) throws IOException {
		int id = stream.readInt();
		if (id < 0)
			return null;
		Ability ability = new Ability(AbilityBase.getAbilityFromID(id), cap);
		ability.decode(stream);
		return ability;
	}

	public static Ability construct(IAoVCapability cap, NBTTagCompound nbt) {
		int id = nbt.getInteger("id");
		if (id < 0)
			return null;
		Ability ability = new Ability(AbilityBase.getAbilityFromID(id), cap);
		ability.decode(nbt);
		return ability;
	}

	public void encode(DataOutputStream stream) throws IOException {
		stream.writeInt(ability.getID());
		stream.writeInt(cooldown);
		stream.writeInt(charges);
		stream.writeInt(decay);
	}

	public void decode(ByteBufInputStream stream) throws IOException {
		cooldown = stream.readInt();
		charges = stream.readInt();
		decay = stream.readInt();
	}

	public NBTTagCompound encode(NBTTagCompound nbt) {
		nbt.setInteger("id", ability.getID());
		nbt.setInteger("cooldown", cooldown);
		nbt.setInteger("charges", charges);
		nbt.setInteger("decay", decay);
		return nbt;
	}

	public void decode(NBTTagCompound nbt) {
		cooldown = nbt.getInteger("cooldown");
		charges = nbt.getInteger("charges");
		decay = nbt.getInteger("decay");
	}

	public void reset(IAoVCapability cap) {
		cooldown = 0;
		charges = ability.getMaxCharges() < 0 ? -1 : ability.getMaxCharges() + cap.getExtraCharges();
		decay = 0;
	}

	public final void cast(EntityPlayer caster) {
		HashSet<Entity> set = new HashSet<Entity>();
		set.add(caster);
		RayTraceResult ray = RayTraceHelper.tracePath(caster.world, caster, (int) getAbility().getMaxDistance(), 1, set);
		cast(caster, (ray == null || ray.entityHit == null || !(ray.entityHit instanceof EntityLivingBase)) ? null : (EntityLivingBase) ray.entityHit);
	}

	public void cast(EntityPlayer caster, EntityLivingBase target) {
		IAoVCapability cap = caster.getCapability(CapabilityList.AOV, null);
		if (cap != null) {
			if (cap.canUseAbility(this)) {
				ability.cast(this, caster, target);
				charges -= ability.getCost(cap);
				cooldown = ability.getCoolDown() * ((ability.usesInvoke() && cap.getInvokeMass()) ? 2 : 1);
			}
		}
	}

	public void castAsAura(EntityPlayer caster, IAoVCapability cap, int life) {
		if (ability instanceof IAura)
			((IAura) ability).castAsAura(caster, cap, life);
	}

	public boolean canUse(IAoVCapability cap) {
		return cooldown <= 0 && (charges == -1 || charges >= ability.getCost(cap)) && cap.slotsContain(this);
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

	public void update(IAoVCapability cap) {
		tick++;
		if (cooldown > 0)
			cooldown--;
		if (decay > 0 && tick % (20) == 0)
			decay--;
		if (ability.getMaxCharges() >= 0 && ConfigHandler.recharge >= 0 && charges < (ability.getMaxCharges() + cap.getExtraCharges()) && tick % ConfigHandler.recharge == 0)
			charges++;

	}

	public boolean compare(Ability check) {
		return check != null && ability == check.ability;
	}

}
