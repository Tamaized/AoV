package tamaized.aov.common.capabilities.leap;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class LeapCapabilityStorage implements IStorage<ILeapCapability> {

	@Override
	public INBT writeNBT(Capability<ILeapCapability> capability, ILeapCapability instance, Direction side) {
		return new CompoundNBT();
	}

	@Override
	public void readNBT(Capability<ILeapCapability> capability, ILeapCapability instance, Direction side, INBT nbt) {
	}

}
