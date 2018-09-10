package tamaized.aov.common.capabilities.polymorph;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PolymorphCapabilityStorage implements IStorage<IPolymorphCapability> {

	@Override
	public NBTBase writeNBT(Capability<IPolymorphCapability> capability, IPolymorphCapability instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("morph", instance.getMorph() == null ? -1 : instance.getMorph().ordinal());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IPolymorphCapability> capability, IPolymorphCapability instance, EnumFacing side, NBTBase nbt) {
		instance.morph(IPolymorphCapability.Morph.getMorph(((NBTTagCompound) nbt).getInteger("morph")));
	}

}
