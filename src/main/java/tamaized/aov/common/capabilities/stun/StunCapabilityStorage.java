package tamaized.aov.common.capabilities.stun;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StunCapabilityStorage implements IStorage<IStunCapability> {

	@Override
	public INBTBase writeNBT(Capability<IStunCapability> capability, IStunCapability instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInt("stunTicks", instance.getStunTicks());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IStunCapability> capability, IStunCapability instance, EnumFacing side, INBTBase nbt) {
		instance.setStunTicks(((NBTTagCompound) nbt).getInt("stunTicks"));
	}

}
