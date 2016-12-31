package Tamaized.AoV.core.abilities;

import java.io.DataOutputStream;
import java.io.IOException;

import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

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
		Ability ability = new Ability(AbilityBase.getAbilityFromID(stream.readInt()), cap);
		ability.decode(stream);
		return null;
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

	public void reset(IAoVCapability cap) {
		cooldown = 0;
		charges = ability.getMaxCharges() + cap.getExtraCharges();
		decay = 0;
	}

	public void cast(EntityPlayer caster, EntityLivingBase target) {
		IAoVCapability cap = caster.getCapability(CapabilityList.AOV, null);
		if (cap != null) {
			if (canUse(cap)) {
				int cost = ability.getCost(cap);
				ability.cast(caster, target);
				charges -= cost;
			}
		}
	}

	public boolean canUse(IAoVCapability cap) {
		return cooldown <= 0 && charges >= ability.getCost(cap);
	}

	public AbilityBase getAbility() {
		return ability;
	}

	public int getCooldown() {
		return cooldown;
	}

	public int getCharges() {
		return charges;
	}

	public int getDecay() {
		return decay;
	}

	public void update() {
		tick++;
		if (cooldown > 0) cooldown--;
		if (decay > 0 && tick % (20 * 20) == 0) decay--;
	}

}
