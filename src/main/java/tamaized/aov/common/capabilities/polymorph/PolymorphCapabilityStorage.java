package tamaized.aov.common.capabilities.polymorph;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PolymorphCapabilityStorage implements IStorage<IPolymorphCapability> {

	@Override
	public INBT writeNBT(Capability<IPolymorphCapability> capability, IPolymorphCapability instance, Direction side) {
		CompoundNBT nbt = new CompoundNBT();
		nbt.setInt("morph", instance.getMorph() == null ? -1 : instance.getMorph().ordinal());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IPolymorphCapability> capability, IPolymorphCapability instance, Direction side, INBT nbt) {
		instance.morph(IPolymorphCapability.Morph.getMorph(((CompoundNBT) nbt).getInt("morph")));
	}

}
