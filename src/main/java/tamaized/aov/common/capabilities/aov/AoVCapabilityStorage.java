package tamaized.aov.common.capabilities.aov;

import tamaized.aov.common.capabilities.aov.AoVCapabilityHandler.DecayWrapper;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AoVCapabilityStorage implements IStorage<IAoVCapability> {

	@Override
	public NBTBase writeNBT(Capability<IAoVCapability> capability, IAoVCapability instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		for (AoVSkill skill : instance.getObtainedSkills())
			list.appendTag(new NBTTagInt(skill.getID()));
		nbt.setTag("obtainedSkills", list);
		list = new NBTTagList();
		for (Entry<AbilityBase, DecayWrapper> entry : instance.getDecayMap().entrySet()) {
			NBTTagCompound comp = new NBTTagCompound();
			comp.setInteger("id", AbilityBase.getID(entry.getKey()));
			comp.setInteger("decay", entry.getValue().getDecay());
			list.appendTag(comp);
		}
		nbt.setTag("decay", list);
		// TODO: figure out auras
		nbt.setInteger("skillPoints", instance.getSkillPoints());
		nbt.setInteger("exp", instance.getExp());
		nbt.setInteger("maxLevel", instance.getMaxLevel());
		nbt.setBoolean("invokeMass", instance.getInvokeMass());
		list = new NBTTagList();
		for (int index = 0; index < 9; index++) {
			Ability ability = instance.getSlot(index);
			NBTTagCompound ct = new NBTTagCompound();
			if (ability == null) {
				ct.setInteger("id", -1);
			} else {
				ability.encode(ct);
			}
			list.appendTag(ct);
		}
		nbt.setTag("slots", list);
		nbt.setInteger("currentSlot", instance.getCurrentSlot());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IAoVCapability> capability, IAoVCapability instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound compound = (NBTTagCompound) nbt;
		NBTBase tag = compound.getTag("obtainedSkills");
		if (tag instanceof NBTTagList) {
			NBTTagList list = (NBTTagList) tag;
			for (int index = 0; index < list.tagCount(); index++) {
				instance.addObtainedSkill(AoVSkill.getSkillFromID(list.getIntAt(index)));
			}
		}
		Map<AbilityBase, DecayWrapper> decay = new HashMap<>();
		tag = compound.getTag("decay");
		if (tag instanceof NBTTagList) {
			NBTTagList list = (NBTTagList) tag;
			for (int index = 0; index < list.tagCount(); index++) {
				NBTTagCompound comp = list.getCompoundTagAt(index);
				decay.put(AbilityBase.getAbilityFromID(comp.getInteger("id")), ((AoVCapabilityHandler) instance).new DecayWrapper(comp.getInteger("decay")));
			}
		}
		if (!decay.isEmpty())
			instance.setDecayMap(decay);
		instance.setSkillPoints(compound.getInteger("skillPoints"));
		instance.setExp(compound.getInteger("exp"));
		instance.setMaxLevel(compound.getInteger("maxLevel"));
		instance.toggleInvokeMass(compound.getBoolean("invokeMass"));
		instance.update(null);
		tag = compound.getTag("slots");
		if (tag instanceof NBTTagList) {
			NBTTagList list = (NBTTagList) tag;
			for (int index = 0; index < 9; index++) {
				instance.setSlot(Ability.construct(instance, null, list.getCompoundTagAt(index)), index);
			}
		}
		instance.setCurrentSlot(compound.getInteger("currentSlot"));
		instance.markDirty();
	}

}
