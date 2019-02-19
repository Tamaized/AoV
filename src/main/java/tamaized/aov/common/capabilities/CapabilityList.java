package tamaized.aov.common.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.capabilities.leap.ILeapCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.capabilities.stun.IStunCapability;
import tamaized.tammodized.common.helper.CapabilityHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityList {

	@CapabilityInject(IAoVCapability.class)
	public static final Capability<IAoVCapability> AOV;
	@CapabilityInject(IAstroCapability.class)
	public static final Capability<IAstroCapability> ASTRO;
	@CapabilityInject(IStunCapability.class)
	public static final Capability<IStunCapability> STUN;
	@CapabilityInject(ILeapCapability.class)
	public static final Capability<ILeapCapability> LEAP;
	@CapabilityInject(IPolymorphCapability.class)
	public static final Capability<IPolymorphCapability> POLYMORPH;

	// Tricks Intellij
	static {
		AOV = null;
		ASTRO = null;
		STUN = null;
		LEAP = null;
		POLYMORPH = null;
	}

	public static <T> T getCap(@Nullable ICapabilityProvider provider, Capability<T> cap) {
		return getCap(provider, cap, null);
	}

	public static <T> T getCap(@Nullable ICapabilityProvider provider, Capability<T> cap, @Nullable EnumFacing face) {
		LazyOptional<T> data = provider != null ? provider.getCapability(cap, face) : null;
		return data != null && data.isPresent() ? data.orElseThrow(IllegalStateException::new) : null;
	}

	@SubscribeEvent
	public void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> e) {
		if (e.getObject() instanceof EntityPlayer) {
			e.addCapability(IAoVCapability.ID, new ICapabilitySerializable<NBTTagCompound>() {

				IAoVCapability inst = AOV.getDefaultInstance();

				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
					return capability == AOV;
				}

				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
					return capability == AOV ? AOV.<T>cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) AOV.getStorage().writeNBT(AOV, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					AOV.getStorage().readNBT(AOV, inst, null, nbt);
				}

			});
			e.addCapability(IAstroCapability.ID, new ICapabilitySerializable<NBTTagCompound>() {

				IAstroCapability inst = ASTRO.getDefaultInstance();

				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
					return capability == ASTRO;
				}

				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
					return capability == ASTRO ? ASTRO.<T>cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) ASTRO.getStorage().writeNBT(ASTRO, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					ASTRO.getStorage().readNBT(ASTRO, inst, null, nbt);
				}

			});
			e.addCapability(IPolymorphCapability.ID, new ICapabilitySerializable<NBTTagCompound>() {

				IPolymorphCapability inst = POLYMORPH.getDefaultInstance();

				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
					return capability == POLYMORPH;
				}

				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
					return capability == POLYMORPH ? POLYMORPH.<T>cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) POLYMORPH.getStorage().writeNBT(POLYMORPH, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					POLYMORPH.getStorage().readNBT(POLYMORPH, inst, null, nbt);
				}

			});
		}
		if (e.getObject() instanceof EntityLivingBase) {
			e.addCapability(IStunCapability.ID, new ICapabilitySerializable<NBTTagCompound>() {

				IStunCapability inst = STUN.getDefaultInstance();

				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
					return capability == STUN;
				}

				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
					return capability == STUN ? STUN.<T>cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) STUN.getStorage().writeNBT(STUN, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					STUN.getStorage().readNBT(STUN, inst, null, nbt);
				}

			});
			e.addCapability(ILeapCapability.ID, new ICapabilitySerializable<NBTTagCompound>() {

				ILeapCapability inst = LEAP.getDefaultInstance();

				@Override
				public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
					return capability == LEAP;
				}

				@Override
				public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
					return capability == LEAP ? LEAP.<T>cast(inst) : null;
				}

				@Override
				public NBTTagCompound serializeNBT() {
					return (NBTTagCompound) LEAP.getStorage().writeNBT(LEAP, inst, null);
				}

				@Override
				public void deserializeNBT(NBTTagCompound nbt) {
					LEAP.getStorage().readNBT(LEAP, inst, null, nbt);
				}

			});
		}
	}

	@SubscribeEvent
	public void updateClone(PlayerEvent.Clone e) {
		IAoVCapability newcap = CapabilityHelper.getCap(e.getEntityPlayer(), AOV, null);
		IAoVCapability oldcap = CapabilityHelper.getCap(e.getOriginal(), AOV, null);
		if (newcap != null && oldcap != null)
			newcap.copyFrom(oldcap);
		IPolymorphCapability newpoly = CapabilityHelper.getCap(e.getEntityPlayer(), POLYMORPH, null);
		IPolymorphCapability oldpoly = CapabilityHelper.getCap(e.getOriginal(), POLYMORPH, null);
		if (newpoly != null && oldpoly != null)
			newpoly.morph(oldpoly.getMorph());
	}

	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent e) {
		IAoVCapability cap = CapabilityHelper.getCap(e.getEntity(), AOV, null);
		if (cap != null) {
			cap.markDirty();
			cap.setLoaded();
		}
		IAstroCapability astro = CapabilityHelper.getCap(e.getEntity(), ASTRO, null);
		if (astro != null)
			astro.markDirty();
	}

}
