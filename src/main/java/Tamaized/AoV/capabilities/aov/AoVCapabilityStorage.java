package Tamaized.AoV.capabilities.aov;

import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AoVCapabilityStorage implements IStorage<IAoVCapability> {

	@Override
	public NBTBase writeNBT(Capability<IAoVCapability> capability, IAoVCapability instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		for (AoVSkill skill : instance.getObtainedSkills())
			list.appendTag(new NBTTagInt(skill.getID()));
		nbt.setTag("obtainedSkills", list);
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
		instance.setSkillPoints(compound.getInteger("skillPoints"));
		instance.setExp(compound.getInteger("exp"));
		instance.setMaxLevel(compound.getInteger("maxLevel"));
		instance.toggleInvokeMass(compound.getBoolean("invokeMass"));
		instance.update(null);
		tag = compound.getTag("slots");
		if (tag instanceof NBTTagList) {
			NBTTagList list = (NBTTagList) tag;
			for (int index = 0; index < 9; index++) {
				instance.setSlot(Ability.construct(instance, list.getCompoundTagAt(index)), index);
			}
		}
		instance.setCurrentSlot(compound.getInteger("currentSlot"));
	}

}
