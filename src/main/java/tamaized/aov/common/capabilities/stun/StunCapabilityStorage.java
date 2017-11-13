package tamaized.aov.common.capabilities.stun;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StunCapabilityStorage implements IStorage<IStunCapability> {

	@Override
	public NBTBase writeNBT(Capability<IStunCapability> capability, IStunCapability instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("stunTicks", instance.getStunTicks());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IStunCapability> capability, IStunCapability instance, EnumFacing side, NBTBase nbt) {
		instance.setStunTicks(((NBTTagCompound) nbt).getInteger("stunTicks"));
	}

}
