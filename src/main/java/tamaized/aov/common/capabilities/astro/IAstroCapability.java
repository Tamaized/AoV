package tamaized.aov.common.capabilities.astro;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;

import javax.annotation.Nullable;
import java.util.Random;

public interface IAstroCapability {

	ResourceLocation ID = new ResourceLocation(AoV.modid, "AstroCapabilityHandler");

	IAnimation[] getAnimations();

	void playAnimation(EntityLivingBase entity, IAnimation animation);

	void updateFrameData(float[][] data);

	float[][] getFrameData();

	void drawCard(EntityLivingBase entity);

	void burnCard(EntityLivingBase entity);

	void spreadCard(EntityLivingBase entity);

	int getDrawTime();

	void setDrawTime(int time);

	@Nullable
	ICard getDraw();

	void setDraw(ICard card);

	@Nullable
	ICard getBurn();

	void setBurn(ICard card);

	@Nullable
	ICard getSpread();

	void setSpread(ICard card);

	void useDraw(EntityLivingBase entity);

	void useSpread(EntityLivingBase entity);

	void update(EntityLivingBase entity);

	void sendPacketUpdates(EntityPlayer player);

	enum IAnimation {

		Draw, Burn, Spread, Activate;

		public static final IAnimation[] values = IAnimation.values();

		public static IAnimation getAnimationFromID(int id) {
			if (id >= 0 && id < values.length)
				return values()[id];
			return null;
		}

		public static int getAnimationID(IAnimation animation) {
			return animation == null ? -1 : animation.ordinal();
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
