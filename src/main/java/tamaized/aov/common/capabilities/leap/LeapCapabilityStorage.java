package tamaized.aov.common.capabilities.leap;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class LeapCapabilityStorage implements IStorage<ILeapCapability> {

	@Override
	public NBTBase writeNBT(Capability<ILeapCapability> capability, ILeapCapability instance, EnumFacing side) {
		return new NBTTagCompound();
	}

	@Override
	public void readNBT(Capability<ILeapCapability> capability, ILeapCapability instance, EnumFacing side, NBTBase nbt) {
	}

}
