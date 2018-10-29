package tamaized.aov.common.entity;

import com.google.common.collect.Lists;
import com.sun.javafx.geom.Vec2d;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class EntityEarthquake extends Entity {

	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	public List<Quake> quakes = Lists.newArrayList(new Quake());
	private float damage = 1F;
	private Entity caster;

	public EntityEarthquake(World worldIn) {
		super(worldIn);
		setSize(6F, 0.1F);
	}

	public EntityEarthquake(World worldIn, Entity caster, float damage) {
		super(worldIn);
		this.caster = caster;
		this.damage = damage;
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
						setPosition(posX, i, posZ);
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

	}

	@Override
	protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {

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

		@SideOnly(Side.CLIENT)
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
