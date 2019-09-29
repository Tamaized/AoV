package tamaized.aov.common.capabilities.astro;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class AstroCapabilityStorage implements IStorage<IAstroCapability> {

	@Override
	public INBT writeNBT(Capability<IAstroCapability> capability, IAstroCapability instance, Direction side) {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("draw", IAstroCapability.ICard.getCardID(instance.getDraw()));
		nbt.putInt("spread", IAstroCapability.ICard.getCardID(instance.getSpread()));
		nbt.putInt("burn", IAstroCapability.ICard.getCardID(instance.getBurn()));
		nbt.putInt("drawtime", instance.getDrawTime());
		return nbt;
	}

	@Override
	public void readNBT(Capability<IAstroCapability> capability, IAstroCapability instance, Direction side, INBT nbt) {
		if (!(nbt instanceof CompoundNBT))
			return;
		CompoundNBT data = (CompoundNBT) nbt;
		instance.setDraw(IAstroCapability.ICard.getCardFromID(data.getInt("draw")));
		instance.setSpread(IAstroCapability.ICard.getCardFromID(data.getInt("spread")));
		instance.setBurn(IAstroCapability.ICard.getCardFromID(data.getInt("burn")));
		instance.setDrawTime(data.getInt("drawtime"));
		instance.markDirty();
	}

}
