package tamaized.aov.common.capabilities.astro;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AstroCapabilityStorage implements IStorage<IAstroCapability> {

	@Override
	public NBTBase writeNBT(Capability<IAstroCapability> capability, IAstroCapability instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("draw", IAstroCapability.ICard.getCardID(instance.getDraw()));
		nbt.setInteger("spread", IAstroCapability.ICard.getCardID(instance.getSpread()));
		nbt.setInteger("burn", IAstroCapability.ICard.getCardID(instance.getBurn()));
		nbt.setInteger("drawtime", instance.getDrawTime());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IAstroCapability> capability, IAstroCapability instance, EnumFacing side, NBTBase nbt) {
		if (!(nbt instanceof NBTTagCompound))
			return;
		NBTTagCompound data = (NBTTagCompound) nbt;
		instance.setDraw(IAstroCapability.ICard.getCardFromID(data.getInteger("draw")));
		instance.setSpread(IAstroCapability.ICard.getCardFromID(data.getInteger("spread")));
		instance.setBurn(IAstroCapability.ICard.getCardFromID(data.getInteger("burn")));
		instance.setDrawTime(data.getInteger("drawtime"));
		instance.markDirty();
	}

}
