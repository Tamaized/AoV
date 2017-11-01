package tamaized.aov.common.capabilities.astro;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import tamaized.aov.AoV;
import tamaized.aov.network.client.ClientPacketHandlerAstroAnimation;
import tamaized.aov.registry.SoundEvents;

import javax.annotation.Nullable;

public class AstroCapabilityHandler implements IAstroCapability {

	private int tick;
	private float[] frameData = {0, 0, 0, 0, 0, 0};
	private IAnimation animation;
	private ICard draw;
	private int drawTime;
	private ICard burn;
	private ICard spread;

	private static void spawnParticle(EntityLivingBase entity, double x, double y, double z) {
		entity.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, x, y, z, 0.0D, 0.0D, 0.0D);
	}

	@Nullable
	@Override
	public IAnimation getAnimation() {
		return animation;
	}

	@Override
	public void setAnimation(EntityLivingBase entity, IAnimation animation) {
		this.animation = animation;
		if (animation == null)
			return;
		switch (animation) {
			default:
			case Draw:
				frameData[0] = 80;
				frameData[1] = 80;
				frameData[2] = 180;
				frameData[3] = 100;
				frameData[4] = 0;
				frameData[5] = 0;
				if (!entity.world.isRemote)
					SoundEvents.playMovingSoundOnServer(SoundEvents.draw1, entity);
				break;
			case Burn:
				frameData[0] = 0;
				frameData[1] = 0;
				frameData[2] = 0;
				frameData[3] = 0;
				frameData[4] = 0;
				frameData[5] = 0;
				break;
			case Spread:
				frameData[0] = 80;
				frameData[1] = 80;
				frameData[2] = 180;
				frameData[3] = 100;
				frameData[4] = 0;
				frameData[5] = 0;
				if (!entity.world.isRemote)
					SoundEvents.playMovingSoundOnServer(SoundEvents.spread, entity);
				break;
			case Activate:
				frameData[0] = 0;
				frameData[1] = 0;
				frameData[2] = 0;
				frameData[3] = 0;
				frameData[4] = 0;
				frameData[5] = 0;
				break;
		}
	}

	@Override
	public void updateFrameData(float[] data) {
		frameData = data;
	}

	@Override
	public float[] getFrameData() {
		return frameData;
	}

	@Override
	public void drawCard(EntityLivingBase entity) {
		if (getDraw() == null) {
			draw = ICard.getRandomCard();
			drawTime = 30;
			setAnimation(entity, IAnimation.Draw);
		}
	}

	@Override
	public void burnCard(EntityLivingBase entity) {
		if (getDraw() != null) {
			burn = getDraw();
			draw = null;
			drawTime = 0;
			setAnimation(entity, IAnimation.Burn);
		}
	}

	@Override
	public void spreadCard(EntityLivingBase entity) {
		if (getDraw() != null) {
			spread = getDraw();
			draw = null;
			drawTime = 0;
			setAnimation(entity, IAnimation.Spread);
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
	public void useDraw(EntityLivingBase entity) {
		setAnimation(entity, IAnimation.Activate);
		draw = null;
		burn = null;
		drawTime = 0;
	}

	@Override
	public void useSpread(EntityLivingBase entity) {
		setAnimation(entity, IAnimation.Activate);
		spread = null;
		burn = null;
	}

	@Override
	public void update(EntityLivingBase entity) {
		if (frameData[3] > 0)
			frameData[3]--;
		else if (animation != null)
			animation = null;
		if (animation != null)
			switch (animation) {
				case Spread:
				case Draw:
					if (frameData[3] < 90 && frameData[1] > 0)
						for (int i = 0; i < 15; i++)
							spawnParticle(entity,

									entity.posX + (entity.getRNG().nextDouble() * 0.75D) - 0.375D,

									entity.posY + 2.7D + entity.getRNG().nextGaussian() * 0.25D,

									entity.posZ + (entity.getRNG().nextDouble() * 0.75D) - 0.375D);
					if (frameData[0] > 0 && frameData[3] < 25)
						for (int x = 0; x < 7; x++)
							for (int z = 0; z < 7; z++)
								spawnParticle(entity,

										entity.posX - 0.375D + 0.13 * x,

										entity.posY + 2.95D - (0.75D * ((80F - frameData[0]) / 80F)),

										entity.posZ - 0.375D + 0.13 * z);
					if (frameData[3] == 60 && !entity.world.isRemote)
						SoundEvents.playMovingSoundOnServer(SoundEvents.draw2, entity);
					break;
				case Burn:
					break;
				case Activate:
					break;
			}
		if (++tick % 20 == 0) {
			if (drawTime > 0) {
				drawTime--;
			} else if (getDraw() != null) {
				draw = null;
			}
			tick = 0;
		}
	}

	@Override
	public void sendPacketUpdates(EntityPlayer player) {
		AoV.network.sendToAllAround(new ClientPacketHandlerAstroAnimation.Packet(player), new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 256));
	}
}
