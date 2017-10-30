package tamaized.aov.common.capabilities.astro;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tamaized.aov.AoV;

import javax.annotation.Nullable;
import java.util.Random;

public interface IAstroCapability {

	ResourceLocation ID = new ResourceLocation(AoV.modid, "AstroCapabilityHandler");

	@Nullable
	IAnimation getAnimation();

	void setAnimation(EntityLivingBase entity, IAnimation animation);

	void updateFrameData(float[] data);

	float[] getFrameData();

	void drawCard(EntityLivingBase entity);

	void burnCard();

	void spreadCard();

	int getDrawTime();

	void setDrawTime(int time);

	void setDraw(ICard card);

	void setBurn(ICard card);

	void setSpread(ICard card);

	@Nullable
	ICard getDraw();

	@Nullable
	ICard getBurn();

	@Nullable
	ICard getSpread();

	void useDraw(EntityLivingBase entity);

	void useSpread(EntityLivingBase entity);

	void update(EntityLivingBase entity);

	void sendPacketUpdates(EntityPlayer player);

	enum IAnimation {
		Draw, Burn, Spread, Activate;

		public static final IAnimation[] animations = IAnimation.values();

		public static int getAnimationID(IAnimation animation) {
			return animation == null ? -1 : animation.ordinal();
		}

		public static IAnimation getAnimationFromID(int id) {
			return id < 0 || id >= animations.length ? null : animations[id];
		}
	}

	enum ICard {
		Balance, Bole, Arrow, Spear, Ewer, Spire;

		public static final ICard[] cards = ICard.values();
		private static final Random rand = new Random();

		public static ICard getRandomCard() {
			return cards[rand.nextInt(cards.length)];
		}

		public static int getCardID(ICard card) {
			return card == null ? -1 : card.ordinal();
		}

		public static ICard getCardFromID(int id) {
			return id < 0 || id >= cards.length ? null : cards[id];
		}
	}
}
