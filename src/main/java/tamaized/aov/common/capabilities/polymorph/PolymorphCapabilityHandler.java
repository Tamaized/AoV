package tamaized.aov.common.capabilities.polymorph;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import tamaized.aov.AoV;
import tamaized.aov.client.DebugHelper;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.helper.UtilHelper;
import tamaized.aov.network.client.ClientPacketHandlerPolymorphDogAttack;
import tamaized.aov.network.server.ServerPacketHandlerPolymorphDogAttack;
import tamaized.tammodized.common.helper.CapabilityHelper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public class PolymorphCapabilityHandler implements IPolymorphCapability {
	private static final Set<StateWrapper> WATER_ELEMENTAL_STATES = ImmutableSet.of(

			new StateWrapper(Blocks.WATER).matchAnyState().dealsDamage(), new StateWrapper(Blocks.FLOWING_WATER).matchAnyState().dealsDamage()

	);
	private static final Set<StateWrapper> FIRE_ELEMENTAL_STATES = ImmutableSet.of(

			new StateWrapper(Blocks.FIRE).matchAnyState(), new StateWrapper(Blocks.LAVA).matchAnyState(), new StateWrapper(Blocks.FLOWING_LAVA).matchAnyState()

	);
	private static Field ENTITY_isImmuneToFire;
	private boolean unsetter_ENTITY_isImmuneToFire = false;
	private Morph morph;
	private boolean morphSize = false;
	private int attackCooldown;
	private int initalAttackCooldown;
	private int attackCooldownMax = 20 * 5;
	private boolean attacking = false;
	// 1XXX - unused
	// X1XX - unused
	// XX1X - Dangerous Biome Elemental (0 = Water; 1 = Fire)
	// XXX1 - Dangerous Biome Temperature
	private byte renderBits = 0b0000;

	@Override
	public Morph getMorph() {
		return morph;
	}

	@Override
	public void morph(Morph type) {
		morph = type;
	}

	@Override
	public boolean localMorphSize() {
		return morphSize;
	}

	@Override
	public void setLocalMorphSize(boolean val) {
		morphSize = val;
	}

	@Override
	public float getAttackCooldown() {
		return attackCooldown;
	}

	@Override
	public void doAttack(EntityPlayer player) {
		doAttack(player, false);
	}

	@Override
	public void doAttack(EntityPlayer player, boolean fromPacket) {
		doAttack(player, fromPacket, attackCooldownMax);
	}

	@Override
	public void doAttack(EntityPlayer player, boolean fromPacket, int cooldown) {
		if (getMorph() != Morph.Wolf)
			return;
		if (player.world.isRemote) {
			if (fromPacket && player.onGround) {
				initalAttackCooldown = attackCooldown = cooldown;
				Vec3d lookVector = player.getLook(Minecraft.getMinecraft().getRenderPartialTicks());
				player.addVelocity(0.9F * lookVector.x, 0.5F, 0.9F * lookVector.z);
			} else if (attackCooldown <= 0 && player.onGround)
				AoV.network.sendToServer(new ServerPacketHandlerPolymorphDogAttack.Packet());
		} else {
			if (attackCooldown <= 0 && player instanceof EntityPlayerMP && player.onGround) {
				initalAttackCooldown = attackCooldown = cooldown;
				attacking = true;
				AoV.network.sendTo(new ClientPacketHandlerPolymorphDogAttack.Packet(), (EntityPlayerMP) player);
			}
		}
	}

	@Override
	public float getInitalAttackCooldown() {
		return initalAttackCooldown;
	}

	@Override
	public void update(EntityPlayer player) {
		if (ENTITY_isImmuneToFire == null)
			ENTITY_isImmuneToFire = ReflectionHelper.findField(Entity.class, "field_70178_ae", "isImmuneToFire");
		if (attackCooldown > 0)
			attackCooldown--;
		if (attacking && attackCooldown < attackCooldownMax - 10 && (attackCooldown <= 0 || player.onGround))
			attacking = false;
		if (getMorph() == Morph.Wolf) {
			player.ticksSinceLastSwing = 9000;
			UtilHelper.setSize(player, 0.6F, 0.85F);
			player.eyeHeight = player.height * 0.8F;
			setLocalMorphSize(true);
			if (attacking) {
				IAoVCapability aov = CapabilityHelper.getCap(player, CapabilityList.AOV, null);
				List<Entity> targets = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(0.75D));
				for (Entity target : targets) {
					target.attackEntityFrom(DamageSource.causePlayerDamage(player), 5F * (aov == null ? 1 : (aov.getSpellPower() + 1F)));
				}
			}
		} else if (localMorphSize()) {
			UtilHelper.setSize(player, 0.6F, 1.8F);
			player.eyeHeight = player.getDefaultEyeHeight();
			setLocalMorphSize(false);
		}
		if (getMorph() == Morph.WaterElemental || getMorph() == Morph.FireElemental) {
			if (!player.world.isRemote) {
				List<IBlockState> states = Lists.newArrayList();
				AxisAlignedBB bounding = player.getEntityBoundingBox();
				double x, y, z;
				for (x = bounding.minX; x <= bounding.maxX; x += 0.5D)
					for (z = bounding.minZ; z <= bounding.maxZ; z += 0.5D)
						for (y = bounding.minY; y <= bounding.maxY; y += 0.5D) {
							IBlockState state = player.world.getBlockState(new BlockPos(x, y, z));
							if (!states.contains(state))
								states.add(state);
						}
				boolean flag = false;
				for (IBlockState state : states) {
					if (getMorph() == Morph.WaterElemental) {
						if (StateWrapper.compare(FIRE_ELEMENTAL_STATES, state, true))
							player.attackEntityFrom(DamageSource.STARVE, 4F);
						if (StateWrapper.compare(WATER_ELEMENTAL_STATES, state, false)) {
							player.setAir(300);
							flag = true;
							break;
						}
					} else if (getMorph() == Morph.FireElemental) {
						if (StateWrapper.compare(WATER_ELEMENTAL_STATES, state, true))
							player.attackEntityFrom(DamageSource.STARVE, 4F);
						if (StateWrapper.compare(FIRE_ELEMENTAL_STATES, state, false)) {
							flag = true;
							break;
						}
					}
				}
				if (flag && (player.getActivePotionEffect(MobEffects.REGENERATION) == null || player.ticksExisted % 60 == 0)) {
					player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100));
				}
				if ((player.ticksExisted % (20 * 6)) == 0) {
					byte flags = 0b00; // 1X = heal; X1 = damage
					float temp = player.world.getBiome(player.getPosition()).getTemperature(player.getPosition());
					if (temp <= 0.2F) { // Cold
						if (getMorph() == Morph.WaterElemental)
							flags |= 0b10;
						if (getMorph() == Morph.FireElemental)
							flags |= 0b01;
					} else if (temp >= 1.0F) { // Hot
						if (getMorph() == Morph.WaterElemental)
							flags |= 0b01;
						if (getMorph() == Morph.FireElemental)
							flags |= 0b10;
					}
					byte oldBits = renderBits;
					if ((flags & 0b01) == 0b01) {
						player.attackEntityFrom(DamageSource.STARVE, 1F);
						renderBits |= 0b0001;
						if (getMorph() == Morph.WaterElemental)
							renderBits &= 0b1101;
						else if (getMorph() == Morph.FireElemental)
							renderBits |= 0b0010;
					} else {
						renderBits &= 0b1110;
					}
					if (oldBits != renderBits) {
						IAoVCapability aov = CapabilityHelper.getCap(player, CapabilityList.AOV, null);
						if (aov != null)
							aov.markDirty();
					}
					if ((flags & 0b10) == 0b10) {
						player.heal(1F);
					}
				}
			}
			try {
				if (getMorph() == Morph.FireElemental && !player.isImmuneToFire()) {
					ENTITY_isImmuneToFire.setBoolean(player, true);
					unsetter_ENTITY_isImmuneToFire = true;
				} else if (getMorph() != Morph.FireElemental && player.isImmuneToFire() && unsetter_ENTITY_isImmuneToFire) {
					ENTITY_isImmuneToFire.setBoolean(player, false);
					unsetter_ENTITY_isImmuneToFire = false;
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public byte getRenderBits() {
		return renderBits;
	}

	@Override
	public void setRenderBits(byte bits) {
		renderBits = bits;
	}

	private static class StateWrapper {

		private final Block block;
		private final Set<IBlockState> states;
		private boolean match;
		private boolean doesDamage;

		StateWrapper(Block block) {
			this(block.getDefaultState());
		}

		StateWrapper(IBlockState... states) {
			this.states = ImmutableSet.copyOf(states);
			block = states[0].getBlock();
		}

		public static boolean compare(Set<StateWrapper> set, Object obj, boolean damage) {
			for (StateWrapper wrapper : set)
				if (wrapper.doesDamage == damage && wrapper.equals(obj))
					return true;
			return false;
		}

		public StateWrapper matchAnyState() {
			match = true;
			return this;
		}

		public StateWrapper dealsDamage() {
			doesDamage = true;
			return this;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof IBlockState) {
				if (match)
					return ((IBlockState) obj).getBlock() == block;
				else
					return states.contains(obj);
			}
			return super.equals(obj);
		}
	}
}
