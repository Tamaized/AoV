package tamaized.aov.common.capabilities.polymorph;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import tamaized.aov.AoV;

public interface IPolymorphCapability {

	ResourceLocation ID = new ResourceLocation(AoV.modid, "PolymorphCapabilityHandler");

	Morph getMorph();

	void morph(Morph type);

	boolean localMorphSize();

	void setLocalMorphSize(boolean val);

	float getAttackCooldown();

	void doAttack(EntityPlayer player);

	void doAttack(EntityPlayer player, boolean fromPacket);

	void doAttack(EntityPlayer player, boolean fromPacket, int cooldown);

	float getInitalAttackCooldown();

	void update(EntityPlayer player);

	enum Morph {

		Wolf, FireElemental, WaterElemental;

		public static final Morph[] values = values();

		public static Morph getMorph(int ordinal) {
			return (ordinal < 0 || ordinal >= values.length) ? null : values[ordinal];
		}
	}

}
