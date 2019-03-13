package tamaized.aov.common.capabilities.astro;

import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AstroCapabilityStorage implements IStorage<IAstroCapability> {

	@Override
	public INBTBase writeNBT(Capability<IAstroCapability> capability, IAstroCapability instance, EnumFacing side) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInt("draw", IAstroCapability.ICard.getCardID(instance.getDraw()));
		nbt.setInt("spread", IAstroCapability.ICard.getCardID(instance.getSpread()));
		nbt.setInt("burn", IAstroCapability.ICard.getCardID(instance.getBurn()));
		nbt.setInt("drawtime", instance.getDrawTime());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IAstroCapability> capability, IAstroCapability instance, EnumFacing side, INBTBase nbt) {
		if (!(nbt instanceof NBTTagCompound))
			return;
		NBTTagCompound data = (NBTTagCompound) nbt;
		instance.setDraw(IAstroCapability.ICard.getCardFromID(data.getInt("draw")));
		instance.setSpread(IAstroCapability.ICard.getCardFromID(data.getInt("spread")));
		instance.setBurn(IAstroCapability.ICard.getCardFromID(data.getInt("burn")));
		instance.setDrawTime(data.getInt("drawtime"));
		instance.markDirty();
	}

}
