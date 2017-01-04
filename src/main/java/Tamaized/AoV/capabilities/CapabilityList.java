package Tamaized.AoV.capabilities;

import Tamaized.AoV.capabilities.aov.IAoVCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityList {

	@CapabilityInject(IAoVCapability.class)
	public static final Capability<IAoVCapability> AOV = null;

	@SubscribeEvent
	public void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> e) { // TODO: move capability stuff into TamModized
		if (e.getObject() instanceof EntityPlayer) {
			e.addCapability(IAoVCapability.ID, new ICapabilitySerializable<NBTTagCompound>() {

				IAoVCapability inst = CapabilityList.AOV.getDefaultInstance();

				@Override
				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
					return capability == CapabilityList.AOV;
				}

				@Override
				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
					return capability == CapabilityList.AOV ? CapabilityList.AOV.<T> cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) CapabilityList.AOV.getStorage().writeNBT(CapabilityList.AOV, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					CapabilityList.AOV.getStorage().readNBT(CapabilityList.AOV, inst, null, nbt);
				}

			});
		}
	}

	@SubscribeEvent
	public void updateClone(PlayerEvent.Clone e) {
		EntityPlayer oldPlayer = e.getOriginal();
		EntityPlayer newPlayer = e.getEntityPlayer();
		newPlayer.getCapability(CapabilityList.AOV, null).copyFrom(oldPlayer.getCapability(CapabilityList.AOV, null));
	}

}
