package tamaized.aov.common.capabilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.capabilities.leap.ILeapCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.capabilities.stun.IStunCapability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = AoV.MODID)
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

	private static volatile MethodHandle FIELD_capabilityProvider_valid;

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

	public static <T> T getCap(@Nullable ICapabilityProvider provider, Capability<T> cap, @Nullable Direction face) {
		LazyOptional<T> data = provider != null ? provider.getCapability(cap, face) : null;
		return data != null && data.isPresent() ? data.orElseThrow(IllegalStateException::new) : null;
	}

	@SubscribeEvent
	public static void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> e) {
		if (e.getObject() instanceof PlayerEntity) {
			e.addCapability(IAoVCapability.ID, new ICapabilitySerializable<CompoundNBT>() {

				IAoVCapability inst = AOV.getDefaultInstance();

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
					return AOV.orEmpty(capability, LazyOptional.of(() -> inst)).cast();
				}

				@Override
				public CompoundNBT serializeNBT() {
					return (CompoundNBT) AOV.getStorage().writeNBT(AOV, inst, null);
				}

				@Override
				public void deserializeNBT(CompoundNBT nbt) {
					AOV.getStorage().readNBT(AOV, inst, null, nbt);
				}

			});
			e.addCapability(IAstroCapability.ID, new ICapabilitySerializable<CompoundNBT>() {

				IAstroCapability inst = ASTRO.getDefaultInstance();

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
					return ASTRO.orEmpty(capability, LazyOptional.of(() -> inst)).cast();
				}

				@Override
				public CompoundNBT serializeNBT() {
					return (CompoundNBT) ASTRO.getStorage().writeNBT(ASTRO, inst, null);
				}

				@Override
				public void deserializeNBT(CompoundNBT nbt) {
					ASTRO.getStorage().readNBT(ASTRO, inst, null, nbt);
				}

			});
			e.addCapability(IPolymorphCapability.ID, new ICapabilitySerializable<CompoundNBT>() {

				IPolymorphCapability inst = POLYMORPH.getDefaultInstance();

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
					return POLYMORPH.orEmpty(capability, LazyOptional.of(() -> inst)).cast();
				}

				@Override
				public CompoundNBT serializeNBT() {
					return (CompoundNBT) POLYMORPH.getStorage().writeNBT(POLYMORPH, inst, null);
				}

				@Override
				public void deserializeNBT(CompoundNBT nbt) {
					POLYMORPH.getStorage().readNBT(POLYMORPH, inst, null, nbt);
				}

			});
		}
		if (e.getObject() instanceof LivingEntity) {
			e.addCapability(IStunCapability.ID, new ICapabilitySerializable<CompoundNBT>() {

				IStunCapability inst = STUN.getDefaultInstance();

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
					return STUN.orEmpty(capability, LazyOptional.of(() -> inst)).cast();
				}

				@Override
				public CompoundNBT serializeNBT() {
					return (CompoundNBT) STUN.getStorage().writeNBT(STUN, inst, null);
				}

				@Override
				public void deserializeNBT(CompoundNBT nbt) {
					STUN.getStorage().readNBT(STUN, inst, null, nbt);
				}

			});
			e.addCapability(ILeapCapability.ID, new ICapabilitySerializable<CompoundNBT>() {

				ILeapCapability inst = LEAP.getDefaultInstance();

				@Nonnull
				@Override
				public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, Direction facing) {
					return LEAP.orEmpty(capability, LazyOptional.of(() -> inst)).cast();
				}

				@Override
				public CompoundNBT serializeNBT() {
					return (CompoundNBT) LEAP.getStorage().writeNBT(LEAP, inst, null);
				}

				@Override
				public void deserializeNBT(CompoundNBT nbt) {
					LEAP.getStorage().readNBT(LEAP, inst, null, nbt);
				}

			});
		}
	}

	@SubscribeEvent
	public static void updateClone(PlayerEvent.Clone event) {
		if (FIELD_capabilityProvider_valid == null) {
			try {
				Field f = CapabilityProvider.class.getDeclaredField("valid");
				f.setAccessible(true);
				FIELD_capabilityProvider_valid = MethodHandles.lookup().unreflectSetter(f);
				f.setAccessible(false);
			} catch (IllegalAccessException | NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		if (FIELD_capabilityProvider_valid != null) {
			try {
				FIELD_capabilityProvider_valid.invoke(event.getOriginal(), true);
				{
					handlePlayerCopy(event.getEntityPlayer(), event.getOriginal(), AOV);
					handlePlayerCopy(event.getEntityPlayer(), event.getOriginal(), POLYMORPH);
				}
				FIELD_capabilityProvider_valid.invoke(event.getOriginal(), false);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	public static <C extends IPlayerCapabilityHandler<C>> void handlePlayerCopy(PlayerEntity player, PlayerEntity old, Capability<C> cap) {
		C newCap = getCap(player, cap);
		C oldCap = getCap(old, cap);
		if (newCap != null && oldCap != null) {
			newCap.handleClone(oldCap);
		}
	}

	@SubscribeEvent
	public static void onJoin(EntityJoinWorldEvent e) {
		IAoVCapability cap = getCap(e.getEntity(), AOV);
		if (cap != null) {
			cap.markDirty();
			cap.setLoaded();
		}
		IAstroCapability astro = getCap(e.getEntity(), ASTRO);
		if (astro != null)
			astro.markDirty();
	}

}
