package tamaized.aov.common.capabilities.aov;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import tamaized.aov.common.capabilities.aov.AoVCapabilityHandler.DecayWrapper;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;

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
		nbt.setInteger("skillPoints", instance.getSkillPoints());
		nbt.setInteger("exp", instance.getExp());
		nbt.setInteger("maxLevel", instance.getMaxLevel());
		nbt.setBoolean("invokeMass", instance.getInvokeMass());
		list = new NBTTagList();
		for (int index = 0; index < 9; index++) {
			NBTTagCompound ct = new NBTTagCompound();
			Ability ability = instance.getSlot(index);
			ct.setInteger("slot", index);
			ct.setInteger("id", ability == null ? -1 : ability.getAbility().getID());
			list.appendTag(ct);
		}
		nbt.setTag("slots", list);
		list = new NBTTagList();
		for (Ability ability : instance.getAbilities())
			list.appendTag(ability.encode(new NBTTagCompound()));

		nbt.setTag("abilities", list);
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
		tag = compound.getTag("abilities");
		if (tag instanceof NBTTagList) {
			for (NBTBase nt : (NBTTagList) tag) {
				if (nt instanceof NBTTagCompound) {
					instance.addAbility(Ability.construct(instance, null, (NBTTagCompound) nt));
				}
			}
		}
		tag = compound.getTag("slots");
		if (tag instanceof NBTTagList) {
			for (NBTBase nt : (NBTTagList) tag) {
				if (nt instanceof NBTTagCompound) {
					NBTTagCompound ct = ((NBTTagCompound) nt);
					instance.setSlot(new Ability(AbilityBase.getAbilityFromID(ct.getInteger("id"))), ct.getInteger("slot"), false);
				}
			}
		}
		instance.setCurrentSlot(compound.getInteger("currentSlot"));
		instance.markDirty();
		instance.setLoaded();
	}

}
