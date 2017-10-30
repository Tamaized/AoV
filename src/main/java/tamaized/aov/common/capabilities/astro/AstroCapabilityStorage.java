package tamaized.aov.common.capabilities.astro;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AstroCapabilityStorage implements IStorage<IAstroCapability> {

	@Override
	public NBTBase writeNBT(Capability<IAstroCapability> capability, IAstroCapability instance, EnumFacing side) {
		return new NBTTagCompound();
	}

	@Override
	public void readNBT(Capability<IAstroCapability> capability, IAstroCapability instance, EnumFacing side, NBTBase nbt) {

	}

}
