package tamaized.aov.common.capabilities.aov;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.aov.AoVCapabilityHandler.DecayWrapper;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.common.core.skills.AoVSkills;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AoVCapabilityStorage implements IStorage<IAoVCapability> {

	@Override
	public INBT writeNBT(Capability<IAoVCapability> capability, IAoVCapability instance, Direction side) {
		CompoundNBT nbt = new CompoundNBT();
		ListNBT list = new ListNBT();
		for (AoVSkill skill : instance.getObtainedSkills())
			list.add(new IntNBT(skill.getID()));
		nbt.setTag("obtainedSkills", list);
		list = new ListNBT();
		for (Entry<AbilityBase, DecayWrapper> entry : instance.getDecayMap().entrySet()) {
			CompoundNBT comp = new CompoundNBT();
			comp.setInt("id", AbilityBase.getID(entry.getKey()));
			comp.setInt("decay", entry.getValue().getDecay());
			list.add(comp);
		}
		nbt.setTag("decay", list);
		nbt.setInt("skillPoints", instance.getSkillPoints());
		nbt.setInt("exp", instance.getExp());
		nbt.setInt("maxLevel", instance.getMaxLevel());
		nbt.setBoolean("invokeMass", instance.getInvokeMass());
		list = new ListNBT();
		for (int index = 0; index < 9; index++) {
			CompoundNBT ct = new CompoundNBT();
			Ability ability = instance.getSlot(index);
			ct.setInt("slot", index);
			ct.setInt("id", ability == null ? -1 : ability.getAbility().getID());
			list.add(ct);
		}
		nbt.setTag("slots", list);
		list = new ListNBT();
		for (Ability ability : instance.getAbilities())
			list.add(ability.encode(new CompoundNBT(), instance));
		nbt.setTag("abilities", list);
		nbt.setInt("currentSlot", instance.getCurrentSlot());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IAoVCapability> capability, IAoVCapability instance, Direction side, INBT nbt) {
		CompoundNBT compound = (CompoundNBT) nbt;
		INBT tag = compound.getTag("obtainedSkills");
		if (tag instanceof ListNBT) {
			ListNBT list = (ListNBT) tag;
			for (int index = 0; index < list.size(); index++) {
				instance.addObtainedSkill(AoVSkills.getSkillFromID(list.getInt(index)));
			}
		}
		Map<AbilityBase, DecayWrapper> decay = new HashMap<>();
		tag = compound.getTag("decay");
		if (tag instanceof ListNBT) {
			ListNBT list = (ListNBT) tag;
			for (int index = 0; index < list.size(); index++) {
				CompoundNBT comp = list.getCompound(index);
				decay.put(AbilityBase.getAbilityFromID(comp.getInt("id")), ((AoVCapabilityHandler) instance).new DecayWrapper(comp.getInt("decay")));
			}
		}
		if (!decay.isEmpty())
			instance.setDecayMap(decay);
		instance.setSkillPoints(compound.getInt("skillPoints"));
		instance.setExp(compound.getInt("exp"));
		instance.setMaxLevel(AoV.config.maxlevel.get());//compound.getInteger("maxLevel")); TODO
		instance.toggleInvokeMass(compound.getBoolean("invokeMass"));
		instance.update(null);
		tag = compound.getTag("abilities");
		if (tag instanceof ListNBT) {
			for (INBT nt : (ListNBT) tag) {
				if (nt instanceof CompoundNBT) {
					instance.addAbility(Ability.construct(instance, null, (CompoundNBT) nt));
				}
			}
		}
		tag = compound.getTag("slots");
		if (tag instanceof ListNBT) {
			for (INBT nt : (ListNBT) tag) {
				if (nt instanceof CompoundNBT) {
					CompoundNBT ct = ((CompoundNBT) nt);
					instance.setSlot(new Ability(AbilityBase.getAbilityFromID(ct.getInt("id"))), ct.getInt("slot"), false);
				}
			}
		}
		instance.setCurrentSlot(compound.getInt("currentSlot"));
		instance.markDirty();
		instance.setLoaded();
	}

}
