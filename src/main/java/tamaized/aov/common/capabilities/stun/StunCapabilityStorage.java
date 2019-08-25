package tamaized.aov.common.capabilities.stun;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StunCapabilityStorage implements IStorage<IStunCapability> {

	@Override
	public INBT writeNBT(Capability<IStunCapability> capability, IStunCapability instance, Direction side) {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("stunTicks", instance.getStunTicks());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IStunCapability> capability, IStunCapability instance, Direction side, INBT nbt) {
		instance.setStunTicks(((CompoundNBT) nbt).getInt("stunTicks"));
	}

}
