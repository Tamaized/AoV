package tamaized.aov.common.capabilities.polymorph;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.core.skills.AoVSkills;
import tamaized.aov.common.entity.EntityDruidicWolf;
import tamaized.aov.common.helper.UtilHelper;
import tamaized.aov.network.client.ClientPacketHandlerPolymorphDogAttack;
import tamaized.aov.network.server.ServerPacketHandlerPolymorphDogAttack;
import tamaized.tammodized.common.helper.CapabilityHelper;
import tamaized.tammodized.common.particles.ParticleHelper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public class PolymorphCapabilityHandler implements IPolymorphCapability {
	private static final Set<StateWrapper> WATER_ELEMENTAL_STATES = ImmutableSet.of(

			new StateWrapper(Blocks.WATER).matchAnyState(), new StateWrapper(Blocks.FLOWING_WATER).matchAnyState()

	);
	private static final Set<StateWrapper> FIRE_ELEMENTAL_STATES = ImmutableSet.of(

			new StateWrapper(Blocks.FIRE).matchAnyState(), new StateWrapper(Blocks.LAVA).matchAnyState(), new StateWrapper(Blocks.FLOWING_LAVA).matchAnyState()

	);
	private static final byte FLAG_BIT_LENGTH = 0b1111;
	private static Field ENTITY_isImmuneToFire;
	List<EntityDruidicWolf> wolves = Lists.newArrayList();
	private boolean unsetter_ENTITY_isImmuneToFire = false;
	private Morph morph;
	private boolean morphSize = false;
	private int attackCooldown;
	private int initalAttackCooldown;
	private int attackCooldownMax = 20 * 5;
	private boolean attacking = false;
	// 1XXX - Furious Claw
	// X1XX - Furious Fang
	// XX1X - Dangerous Biome Elemental (0 = Water; 1 = Fire)
	// XXX1 - Dangerous Biome Temperature
	private byte flagBits = 0b0000;

	private static boolean isTeleportFriendlyBlock(World world, Entity entity, int x, int z, int y, int xOffset, int zOffset) {
		BlockPos blockpos = new BlockPos(x + xOffset, y - 1, z + zOffset);
		IBlockState iblockstate = world.getBlockState(blockpos);
		return iblockstate.getBlockFaceShape(world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && iblockstate.canEntitySpawn(entity) && world.isAirBlock(blockpos.up()) && world.isAirBlock(blockpos.up(2));
	}

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
				IAoVCapability cap = CapabilityHelper.getCap(player, CapabilityList.AOV, null);
				Vec3d vel = new Vec3d(0.9F * lookVector.x, 0.5F, 0.9F * lookVector.z);
				if (cap != null && cap.hasSkill(AoVSkills.druid_core_4) && IAoVCapability.isCentered(player, cap))
					vel = new Vec3d(1.8F * lookVector.x, 0.65F, 1.8F * lookVector.z);
				player.addVelocity(vel.x, vel.y, vel.z);
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
	public void callWolves(World world, EntityPlayer caster, float damage) {
		wolves.removeIf(w -> w.isDead);
		for (EntityDruidicWolf wolf : wolves) {
			wolf.heal(wolf.getMaxHealth());
			wolf.extendLife();
		}
		loop:
		for (int index = 0; index < 5 - wolves.size(); index++) {
			EntityDruidicWolf wolf = new EntityDruidicWolf(world, caster, damage);

			int x = MathHelper.floor(caster.posX) - 2;
			int z = MathHelper.floor(caster.posZ) - 2;
			int y = MathHelper.floor(caster.getEntityBoundingBox().minY);

			for (int l = wolf.getRNG().nextInt(6); l <= 8; ++l) {
				for (int i1 = wolf.getRNG().nextInt(6); i1 <= 8; ++i1) {
					if (/*(l < 1 || i1 < 1 || l > 3 || i1 > 3) && */isTeleportFriendlyBlock(world, wolf, x, z, y, l, i1)) {
						double posx = (double) ((float) (x + l) + 0.5F);
						double posy = (double) y;
						double posz = (double) ((float) (z + i1) + 0.5F);
						wolf.setLocationAndAngles(posx, posy, posz, wolf.rotationYaw, wolf.rotationPitch);
						for (int i = 0; i < 25; i++) {
							Vec3d result = wolf.getLook(1F).rotateYaw(wolf.getRNG().nextFloat() * 360F).rotatePitch(wolf.getRNG().nextFloat() * 360F);
							ParticleHelper.spawnVanillaParticleOnServer(world, EnumParticleTypes.END_ROD, wolf.posX + result.x, wolf.posY + wolf.height / 2F + result.y, wolf.posZ + result.z, 0, 0, 0);
						}
						wolves.add(wolf);
						world.spawnEntity(wolf);
						world.playSound(null, wolf.getPosition(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1.0F, wolf.getRNG().nextFloat() + 0.5F);
						continue loop;
					}
				}
			}
		}
	}

	@Override
	public void update(EntityPlayer player) {
		if (ENTITY_isImmuneToFire == null)
			ENTITY_isImmuneToFire = ReflectionHelper.findField(Entity.class, "field_70178_ae", "isImmuneToFire");
		if (attackCooldown > 0)
			attackCooldown--;
		if (attacking && attackCooldown < attackCooldownMax - 10 && (attackCooldown <= 0 || player.onGround))
			attacking = false;
		if (getMorph() != null && !IAoVCapability.isCentered(player, CapabilityHelper.getCap(player, CapabilityList.AOV, null)))
			morph(null);
		if (getMorph() == Morph.Wolf) {
			player.ticksSinceLastSwing = 9000;
			UtilHelper.setSize(player, 0.6F, 0.85F);
			player.eyeHeight = player.height * 0.8F;
			setLocalMorphSize(true);
			if (attacking) {
				IAoVCapability aov = CapabilityHelper.getCap(player, CapabilityList.AOV, null);
				List<Entity> targets = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(0.75D));
				for (Entity target : targets) {
					target.attackEntityFrom(DamageSource.causePlayerDamage(player), 5F * (aov == null ? 1 : (aov.getSpellPower() / 100F + 1F)));
					if (aov != null)
						aov.addExp(player, 10, Abilities.wildshapeWolf);
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
						if (StateWrapper.compare(FIRE_ELEMENTAL_STATES, state))
							player.attackEntityFrom(DamageSource.STARVE, 4F);
						if (StateWrapper.compare(WATER_ELEMENTAL_STATES, state)) {
							player.setAir(300);
							flag = true;
							break;
						}
					} else if (getMorph() == Morph.FireElemental) {
						if (StateWrapper.compare(WATER_ELEMENTAL_STATES, state))
							player.attackEntityFrom(DamageSource.STARVE, 4F);
						if (StateWrapper.compare(FIRE_ELEMENTAL_STATES, state)) {
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
					byte oldBits = flagBits;
					if ((flags & 0b01) == 0b01) {
						player.attackEntityFrom(DamageSource.STARVE, 1F);
						flagBits |= 0b0001;
						if (getMorph() == Morph.WaterElemental)
							flagBits &= 0b1101;
						else if (getMorph() == Morph.FireElemental)
							flagBits |= 0b0010;
					} else {
						flagBits &= 0b1110;
					}
					if (oldBits != flagBits) {
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
	public byte getFlagBits() {
		return flagBits;
	}

	@Override
	public void setFlagBits(byte bits) {
		flagBits = bits;
	}

	@Override
	public boolean isFlagBitActive(byte bit) {
		return (flagBits & bit) == bit;
	}

	@Override
	public void addFlagBits(byte add) {
		flagBits |= add;
	}

	@Override
	public void subtractFlagBits(byte sub) {
		flagBits &= (~sub & FLAG_BIT_LENGTH);
	}

	private static class StateWrapper {

		private final Block block;
		private final Set<IBlockState> states;
		private boolean match;

		StateWrapper(Block block) {
			this(block.getDefaultState());
		}

		StateWrapper(IBlockState... states) {
			this.states = ImmutableSet.copyOf(states);
			block = states[0].getBlock();
		}

		public static boolean compare(Set<StateWrapper> set, Object obj) {
			for (StateWrapper wrapper : set)
				if (wrapper.equals(obj))
					return true;
			return false;
		}

		public StateWrapper matchAnyState() {
			match = true;
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
