package tamaized.aov.common.capabilities.astro;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;

import javax.annotation.Nullable;
import java.util.Random;

public interface IAstroCapability {

	ResourceLocation ID = new ResourceLocation(AoV.MODID, "astrocapabilityhandler");

	void markDirty();

	IAnimation[] getAnimations();

	void playAnimation(LivingEntity entity, IAnimation animation);

	void updateFrameData(float[][] data);

	float[][] getFrameData();

	void drawCard(LivingEntity entity);

	void redrawCard(LivingEntity entity);

	void burnCard(LivingEntity entity);

	void spreadCard(LivingEntity entity);

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

	void useDraw(LivingEntity entity);

	void useSpread(LivingEntity entity);

	void update(LivingEntity entity);

	void sendPacketUpdates(PlayerEntity player);

	enum IAnimation {

		Draw, Burn, Spread, Activate, Redraw;

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
