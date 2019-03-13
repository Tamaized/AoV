package tamaized.aov.common.capabilities.aov;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
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
	public INBTBase writeNBT(Capability<IAoVCapability> capability, IAoVCapability instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		for (AoVSkill skill : instance.getObtainedSkills())
			list.add(new NBTTagInt(skill.getID()));
		nbt.setTag("obtainedSkills", list);
		list = new NBTTagList();
		for (Entry<AbilityBase, DecayWrapper> entry : instance.getDecayMap().entrySet()) {
			NBTTagCompound comp = new NBTTagCompound();
			comp.setInt("id", AbilityBase.getID(entry.getKey()));
			comp.setInt("decay", entry.getValue().getDecay());
			list.add(comp);
		}
		nbt.setTag("decay", list);
		nbt.setInt("skillPoints", instance.getSkillPoints());
		nbt.setInt("exp", instance.getExp());
		nbt.setInt("maxLevel", instance.getMaxLevel());
		nbt.setBoolean("invokeMass", instance.getInvokeMass());
		list = new NBTTagList();
		for (int index = 0; index < 9; index++) {
			NBTTagCompound ct = new NBTTagCompound();
			Ability ability = instance.getSlot(index);
			ct.setInt("slot", index);
			ct.setInt("id", ability == null ? -1 : ability.getAbility().getID());
			list.add(ct);
		}
		nbt.setTag("slots", list);
		list = new NBTTagList();
		for (Ability ability : instance.getAbilities())
			list.add(ability.encode(new NBTTagCompound(), instance));
		nbt.setTag("abilities", list);
		nbt.setInt("currentSlot", instance.getCurrentSlot());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IAoVCapability> capability, IAoVCapability instance, EnumFacing side, INBTBase nbt) {
		NBTTagCompound compound = (NBTTagCompound) nbt;
		INBTBase tag = compound.getTag("obtainedSkills");
		if (tag instanceof NBTTagList) {
			NBTTagList list = (NBTTagList) tag;
			for (int index = 0; index < list.size(); index++) {
				instance.addObtainedSkill(AoVSkills.getSkillFromID(list.getInt(index)));
			}
		}
		Map<AbilityBase, DecayWrapper> decay = new HashMap<>();
		tag = compound.getTag("decay");
		if (tag instanceof NBTTagList) {
			NBTTagList list = (NBTTagList) tag;
			for (int index = 0; index < list.size(); index++) {
				NBTTagCompound comp = list.getCompound(index);
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
		if (tag instanceof NBTTagList) {
			for (INBTBase nt : (NBTTagList) tag) {
				if (nt instanceof NBTTagCompound) {
					instance.addAbility(Ability.construct(instance, null, (NBTTagCompound) nt));
				}
			}
		}
		tag = compound.getTag("slots");
		if (tag instanceof NBTTagList) {
			for (INBTBase nt : (NBTTagList) tag) {
				if (nt instanceof NBTTagCompound) {
					NBTTagCompound ct = ((NBTTagCompound) nt);
					instance.setSlot(new Ability(AbilityBase.getAbilityFromID(ct.getInt("id"))), ct.getInt("slot"), false);
				}
			}
		}
		instance.setCurrentSlot(compound.getInt("currentSlot"));
		instance.markDirty();
		instance.setLoaded();
	}

}
