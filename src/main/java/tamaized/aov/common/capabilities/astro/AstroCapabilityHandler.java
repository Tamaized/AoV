package tamaized.aov.common.capabilities.astro;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.network.PacketDistributor;
import tamaized.aov.AoV;
import tamaized.aov.network.client.ClientPacketHandlerAstroAnimation;
import tamaized.aov.network.client.ClientPacketHandlerAstroData;
import tamaized.aov.registry.SoundEvents;

import javax.annotation.Nullable;

public class AstroCapabilityHandler implements IAstroCapability {

	public ICard lastDraw;
	public ICard lastSpread;
	private int tick;
	private float[][] frameData = new float[IAnimation.values.length][6];
	private IAnimation[] animations = new IAnimation[IAnimation.values.length];
	private ICard draw;
	private int drawTime;
	private ICard burn;
	private ICard spread;
	private boolean dirty = true;

	private static void spawnParticle(LivingEntity entity, double x, double y, double z) {
		entity.world.addParticle(ParticleTypes.WITCH, x, y, z, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void markDirty() {
		dirty = true;
	}

	@Override
	public IAnimation[] getAnimations() {
		return animations;
	}

	@Override
	public void playAnimation(LivingEntity entity, IAnimation animation) {
		if (animation == null)
			return;
		//		for (IAnimation a : animations)
		//			if (a == animation)
		//				return;
		switch (animation) {
			default:
			case Draw:
				animations[0] = animation;
				frameData[0][0] = 80;
				frameData[0][1] = 80;
				frameData[0][2] = 180;
				frameData[0][3] = 100;
				frameData[0][4] = 0;
				frameData[0][5] = 0;
				if (!entity.world.isRemote)
					SoundEvents.playMovingSoundOnServer(SoundEvents.draw1, entity);
				break;
			case Burn:
				animations[1] = animation;
				frameData[1][0] = 80;
				frameData[1][1] = 90;
				frameData[1][2] = 0;
				frameData[1][3] = 35;
				frameData[1][4] = 0;
				frameData[1][5] = 0;
				if (!entity.world.isRemote)
					SoundEvents.playMovingSoundOnServer(SoundEvents.burn, entity);
				break;
			case Spread:
				animations[2] = animation;
				frameData[2][0] = 80;
				frameData[2][1] = 80;
				frameData[2][2] = 180;
				frameData[2][3] = 100;
				frameData[2][4] = 0;
				frameData[2][5] = 0;
				if (!entity.world.isRemote)
					SoundEvents.playMovingSoundOnServer(SoundEvents.spread, entity);
				break;
			case Activate:
				animations[3] = animation;
				frameData[3][0] = 80;
				frameData[3][1] = 90;
				frameData[3][2] = 0;
				frameData[3][3] = 35;
				frameData[3][4] = 0;
				frameData[3][5] = 0;
				if (!entity.world.isRemote)
					SoundEvents.playMovingSoundOnServer(SoundEvents.activate, entity);
				break;
			case Redraw:
				animations[4] = animation;
				frameData[4][0] = 400;
				frameData[4][1] = 80;
				frameData[4][2] = 200;
				frameData[4][3] = 100;
				frameData[4][4] = 110;
				frameData[4][5] = 0;
				animations[0] = IAnimation.Draw;
				frameData[0][0] = 80;
				frameData[0][1] = 80;
				frameData[0][2] = 180;
				frameData[0][3] = 100;
				frameData[0][4] = 0;
				frameData[0][5] = 0;
				if (!entity.world.isRemote)
					SoundEvents.playMovingSoundOnServer(SoundEvents.redraw, entity);
				break;
		}
		if (!entity.world.isRemote)
			AoV.network.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new ClientPacketHandlerAstroAnimation(entity, animation));
	}

	@Override
	public void updateFrameData(float[][] data) {
		frameData = data;
	}

	@Override
	public float[][] getFrameData() {
		return frameData;
	}

	@Override
	public void drawCard(LivingEntity entity) {
		if (getDraw() == null) {
			draw = ICard.getRandomCard();
			drawTime = 30;
			playAnimation(entity, IAnimation.Draw);
		}
	}

	@Override
	public void redrawCard(LivingEntity entity) {
		if (getDraw() != null) {
			ICard newDraw = draw;
			while (newDraw == draw)
				newDraw = ICard.getRandomCard();
			draw = newDraw;
			playAnimation(entity, IAnimation.Redraw);
			drawTime = 30;
		}
	}

	@Override
	public void burnCard(LivingEntity entity) {
		if (getDraw() != null) {
			burn = getDraw();
			draw = null;
			drawTime = 0;
			playAnimation(entity, IAnimation.Burn);
		}
	}

	@Override
	public void spreadCard(LivingEntity entity) {
		if (getDraw() != null) {
			spread = getDraw();
			draw = null;
			drawTime = 0;
			playAnimation(entity, IAnimation.Spread);
		}
	}

	@Override
	public int getDrawTime() {
		return drawTime;
	}

	@Override
	public void setDrawTime(int drawTime) {
		this.drawTime = drawTime;
	}

	@Nullable
	@Override
	public ICard getDraw() {
		return draw;
	}

	@Override
	public void setDraw(ICard card) {
		draw = card;
	}

	@Nullable
	@Override
	public ICard getBurn() {
		return burn;
	}

	@Override
	public void setBurn(ICard card) {
		burn = card;
	}

	@Nullable
	@Override
	public ICard getSpread() {
		return spread;
	}

	@Override
	public void setSpread(ICard card) {
		spread = card;
	}

	@Override
	public void useDraw(LivingEntity entity) {
		playAnimation(entity, IAnimation.Activate);
		draw = null;
		burn = null;
		drawTime = 0;
	}

	@Override
	public void useSpread(LivingEntity entity) {
		playAnimation(entity, IAnimation.Activate);
		spread = null;
		burn = null;
	}

	@Override
	public void update(LivingEntity entity) {
		if (lastDraw != draw && draw != null)
			lastDraw = draw;
		if (lastSpread != spread && spread != null)
			lastSpread = spread;
		int index = 0;
		for (float[] dat : frameData) {
			IAnimation animation = animations[index];
			if (dat[3] > 0)
				frameData[index][3]--;
			else if (animation != null)
				animation = animations[index] = null;
			if (animation != null)
				switch (animation) {
					case Spread:
					case Draw:
						if (dat[3] < 90 && dat[1] > 0)
							for (int i = 0; i < 15; i++)
								spawnParticle(entity,

										entity.posX + (entity.getRNG().nextDouble() * 0.75D) - 0.375D,

										entity.posY + 2.7D + entity.getRNG().nextGaussian() * 0.25D,

										entity.posZ + (entity.getRNG().nextDouble() * 0.75D) - 0.375D);
						if (dat[0] > 0 && dat[3] < 25)
							for (int x = 0; x < 7; x++)
								for (int z = 0; z < 7; z++)
									spawnParticle(entity,

											entity.posX - 0.375D + 0.13 * x,

											entity.posY + 2.95D - (0.75D * ((80F - dat[0]) / 80F)),

											entity.posZ - 0.375D + 0.13 * z);
						if (dat[3] == 60 && !entity.world.isRemote)
							SoundEvents.playMovingSoundOnServer(SoundEvents.draw2, entity);
						break;
					case Burn:
						float theta = (float) Math.toRadians(dat[3] * 16);
						entity.world.addParticle(ParticleTypes.FLAME, entity.posX + MathHelper.cos(theta), entity.posY + 2F + MathHelper.sin(theta), entity.posZ + MathHelper.sin(theta), 0.0D, 0.0D, 0.0D);
						entity.world.addParticle(ParticleTypes.FLAME, entity.posX + MathHelper.sin(theta), entity.posY + 2F + MathHelper.sin(theta), entity.posZ + MathHelper.cos(theta), 0.0D, 0.0D, 0.0D);
						if (dat[3] <= 20 && dat[3] > 0)
							entity.world.addParticle(ParticleTypes.FLAME, entity.posX + entity.getRNG().nextDouble() * 0.125D - 0.0625D, entity.posY + 2.9F - (0.125D * ((80F - dat[0]) / 80F)), entity.posZ + entity.getRNG().nextDouble() * 0.125D - 0.0625D, 0.0D, 0.0D, 0.0D);
						break;
					case Activate:
						if (dat[3] <= 20 && dat[3] > 0)
							spawnParticle(entity, entity.posX + entity.getRNG().nextDouble() * 0.125D - 0.0625D, entity.posY + 2.7F - (0.125D * ((80F - dat[0]) / 80F)), entity.posZ + entity.getRNG().nextDouble() * 0.125D - 0.0625D);
						break;
					default:
						break;
				}
			index++;
		}
		if (++tick % 20 == 0) {
			if (drawTime > 0) {
				drawTime--;
				dirty = true;
			} else if (getDraw() != null) {
				draw = null;
				dirty = true;
			}
			tick = 0;
		}
		if (dirty) {
			if (entity instanceof ServerPlayerEntity)
				sendPacketUpdates((ServerPlayerEntity) entity);
			dirty = false;
		}
	}

	@Override
	public void sendPacketUpdates(PlayerEntity player) {
		AoV.network.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), new ClientPacketHandlerAstroData(player));
	}
}
