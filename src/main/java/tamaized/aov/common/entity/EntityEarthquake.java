package tamaized.aov.common.entity;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.config.ConfigHandler;
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.tammodized.common.helper.CapabilityHelper;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EntityEarthquake extends Entity {

	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	public List<Quake> quakes = Lists.newArrayList(new Quake());
	private float damage = 1F;
	private Entity caster;
	private UUID casterID;

	public EntityEarthquake(World worldIn) {
		super(worldIn);
		setSize(6F, 0.1F);
	}

	public EntityEarthquake(World worldIn, Entity caster, float damage) {
		this(worldIn);
		this.caster = caster;
		this.damage = damage;
	}

	@SuppressWarnings("deprecation")
	private static IBlockState checkDestruction(String check) {
		int index = 0;
		for (String compare : ConfigHandler.EARTHQUAKE.destruction) {
			index++;
			for (String c : compare.split("\\|"))
				if (c.equalsIgnoreCase(check)) {
					if (index >= ConfigHandler.EARTHQUAKE.destruction.length)
						return Blocks.AIR.getDefaultState();
					String[] next = ConfigHandler.EARTHQUAKE.destruction[index].split("\\|")[0].split(":");
					if (next.length < 2)
						next = new String[]{"minecraft", next[0]};
					Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(next[0], next[1]));
					if (block == null)
						return null;
					try {
						if (next.length > 2)
							return block.getStateFromMeta(Integer.parseInt(next[2]));
					} catch (NumberFormatException e) {
						// NO-OP
					}
					return block.getDefaultState();
				}
		}
		return null;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (world.isRemote) {
			if (ticksExisted % 35 == 0)
				quakes.add(new Quake());
			if (ticksExisted % 40 == 0 && rand.nextBoolean())
				quakes.add(new Quake());
		} else {
			if (world.isAirBlock(getPosition().down())) {
				for (int i = getPosition().down().getY(); i >= 0; i--) {
					if (i <= 0) {
						setDead();
						return;
					}
					if (!world.isAirBlock(new BlockPos(getPosition().getX(), i, getPosition().getZ()))) {
						setPosition(posX, i + 1, posZ);
						break;
					}
				}
			}
			if ((ticksExisted % 3 == 0 || ticksExisted % 15 == 0 || ticksExisted % 35 == 0) && rand.nextBoolean())
				world.playSound(null, posX, posY, posZ, SoundEvents.BLOCK_STONE_BREAK, SoundCategory.PLAYERS, 2.0F, rand.nextFloat() * 0.65F + (rand.nextBoolean() ? 0.75F : 0.1F));
			if (ConfigHandler.EARTHQUAKE.enable && ticksExisted % ConfigHandler.EARTHQUAKE.ticks == 0 && rand.nextInt(ConfigHandler.EARTHQUAKE.chance) == 0) {
				final int radius = 2;
				List<BlockPos> positions = Lists.newArrayList(BlockPos.getAllInBox(new BlockPos(posX - radius, posY - 1, posZ - radius), new BlockPos(posX + radius, posY, posZ + radius)));
				IBlockState newState = null;
				IBlockState state = null;
				BlockPos pos = null;
				int tries = positions.size();
				while (newState == null && tries-- > 0) {
					pos = positions.get(rand.nextInt(positions.size()));
					state = world.getBlockState(pos);
					Block block = state.getBlock();
					ResourceLocation regname = block.getRegistryName();
					if (regname == null) {
						newState = null;
						continue;
					}
					StringBuilder check = new StringBuilder(regname.getNamespace()).append(":").append(regname.getPath());
					if ((newState = checkDestruction(check.toString())) != null)
						break;
					newState = checkDestruction(check.append(":").append(block.getMetaFromState(state)).toString());
				}
				if (newState != null) {
					world.setBlockState(pos, newState);
					world.playEvent(2001, pos, Block.getStateId(state));
				}
			}
			if (ticksExisted % 20 == 0) {
				if (caster == null && casterID != null)
					for (Entity e : world.loadedEntityList)
						if (e.getUniqueID().equals(casterID))
							caster = e;
				IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
				for (EntityLivingBase entity : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(posX - width, posY - 1F, posZ - width, posX + width, posY + 3F, posZ + width))) {
					if (entity != caster && IAoVCapability.selectiveTarget(caster, cap, entity) && entity.attackEntityFrom(AoVDamageSource.createEntityDamageSource(DamageSource.MAGIC, caster), damage)) {
						entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * 8));
						if (cap != null)
							cap.addExp(caster, 20, Abilities.earthquake);
					}
				}
			}
		}
		if (ticksExisted % 400 == 0)
			setDead();
	}

	@Override
	protected void entityInit() {

	}

	@Override
	protected void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
		damage = Math.max(1F, compound.getFloat("damage"));
		if (compound.hasUniqueId("casterID"))
			casterID = compound.getUniqueId("casterID");
	}

	@Override
	protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
		if (caster != null)
			compound.setUniqueId("casterID", caster.getUniqueID());
		compound.setFloat("damage", damage);
	}

	public static class Quake {

		static final float WIDTH = 2.5F;
		static final float HEIGHT = 2.5F;
		private static final Random RAND = new Random();
		final int lifespan = RAND.nextInt(20 * 4) + 20 * 4;
		final Vec3d offset = new Vec3d(nextAxisOffset() - 1D, 0.01F, nextAxisOffset() - 1D);
		final float alphaspeed = RAND.nextFloat() * 0.024F + 0.001F;
		final float rot = RAND.nextFloat() * 360F;
		float life = lifespan;
		float alpha = 0F;

		static double nextAxisOffset() {
			return RAND.nextDouble() * 3.0D - 1.5D;
		}

		@OnlyIn(Dist.CLIENT)
		public boolean render(@Nonnull EntityEarthquake entity, double ox, double oy, double oz, float partialTicks) {
			life -= partialTicks;
			if (life <= 0)
				return true;

			float r = 0.2F;
			float g = 0.1F;
			float b = 0F;

			if (alpha < 1F)
				alpha += (alphaspeed * partialTicks);
			if (alpha > 1F)
				alpha = 1F;

			double x = ox + offset.x;
			double y = oy + offset.y;
			double z = oz + offset.z;

			BufferBuilder buffer = Tessellator.getInstance().getBuffer();
			buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

			buffer.pos(x, y, z + HEIGHT).tex(0, 1).color(r, g, b, alpha).endVertex();
			buffer.pos(x + WIDTH, y, z + HEIGHT).tex(1, 1).color(r, g, b, alpha).endVertex();
			buffer.pos(x + WIDTH, y, z).tex(1, 0).color(r, g, b, alpha).endVertex();
			buffer.pos(x, y, z).tex(0, 0).color(r, g, b, alpha).endVertex();

			GlStateManager.pushMatrix();
			GlStateManager.translate(ox, oy, oz);
			GlStateManager.rotate(rot, 0, 1, 0);
			GlStateManager.translate(-ox, -oy, -oz);
			Tessellator.getInstance().draw();
			GlStateManager.popMatrix();

			return false;
		}

	}
}
