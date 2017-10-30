package tamaized.aov.common.capabilities;

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
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;

import javax.annotation.Nonnull;

public class CapabilityList {

	@CapabilityInject(IAoVCapability.class)
	public static final Capability<IAoVCapability> AOV;
	@CapabilityInject(IAstroCapability.class)
	public static final Capability<IAstroCapability> ASTRO;

	// Tricks Intellij
	static {
		AOV = null;
		ASTRO = null;
	}

	@SubscribeEvent
	public void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> e) {
		if (e.getObject() instanceof EntityPlayer) {
			e.addCapability(IAoVCapability.ID, new ICapabilitySerializable<NBTTagCompound>() {

				IAoVCapability inst = CapabilityList.AOV.getDefaultInstance();

				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
					return capability == CapabilityList.AOV;
				}

				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
					return capability == CapabilityList.AOV ? CapabilityList.AOV.<T>cast(inst) : null;
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
			e.addCapability(IAstroCapability.ID, new ICapabilitySerializable<NBTTagCompound>() {

				IAstroCapability inst = CapabilityList.ASTRO.getDefaultInstance();

				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
					return capability == CapabilityList.ASTRO;
				}

				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
					return capability == CapabilityList.ASTRO ? CapabilityList.ASTRO.<T>cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) CapabilityList.ASTRO.getStorage().writeNBT(CapabilityList.ASTRO, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					CapabilityList.ASTRO.getStorage().readNBT(CapabilityList.ASTRO, inst, null, nbt);
				}

			});
		}
	}

	@SubscribeEvent
	public void updateClone(PlayerEvent.Clone e) {
		EntityPlayer oldPlayer = e.getOriginal();
		EntityPlayer newPlayer = e.getEntityPlayer();
		IAoVCapability cap = newPlayer.hasCapability(CapabilityList.AOV, null) ? newPlayer.getCapability(CapabilityList.AOV, null) : null;
		if (cap != null)
			cap.copyFrom(oldPlayer.getCapability(CapabilityList.AOV, null));
	}

}
