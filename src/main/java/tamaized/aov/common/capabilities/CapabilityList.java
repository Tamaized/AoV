package tamaized.aov.common.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.capabilities.stun.IStunCapability;

import javax.annotation.Nonnull;

public class CapabilityList {

	@CapabilityInject(IAoVCapability.class)
	public static final Capability<IAoVCapability> AOV;
	@CapabilityInject(IAstroCapability.class)
	public static final Capability<IAstroCapability> ASTRO;
	@CapabilityInject(IStunCapability.class)
	public static final Capability<IStunCapability> STUN;

	// Tricks Intellij
	static {
		AOV = null;
		ASTRO = null;
		STUN = null;
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
		if (e.getObject() instanceof EntityLivingBase) {
			e.addCapability(IStunCapability.ID, new ICapabilitySerializable<NBTTagCompound>() {

				IStunCapability inst = CapabilityList.STUN.getDefaultInstance();

				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
					return capability == CapabilityList.STUN;
				}

				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
					return capability == CapabilityList.STUN ? CapabilityList.STUN.<T>cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) CapabilityList.STUN.getStorage().writeNBT(CapabilityList.STUN, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					CapabilityList.STUN.getStorage().readNBT(CapabilityList.STUN, inst, null, nbt);
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

	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent e) {
		IAoVCapability cap = e.getEntity().hasCapability(CapabilityList.AOV, null) ? e.getEntity().getCapability(CapabilityList.AOV, null) : null;
		if (cap != null)
			cap.markDirty();
	}

}
