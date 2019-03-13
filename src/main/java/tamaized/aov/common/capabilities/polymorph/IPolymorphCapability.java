package tamaized.aov.common.capabilities.polymorph;

import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import tamaized.aov.AoV;

import java.util.Set;

public interface IPolymorphCapability {

	Set<Potion> ELEMENTAL_IMMUNITY_EFFECTS = ImmutableSet.of(

			MobEffects.SLOWNESS, MobEffects.POISON, MobEffects.WITHER

	);

	ResourceLocation ID = new ResourceLocation(AoV.MODID, "polymorphcapabilityhandler");

	Morph getMorph();

	void morph(Morph type);

	boolean localMorphSize();

	void setLocalMorphSize(boolean val);

	float getAttackCooldown();

	void doAttack(EntityPlayer player);

	void doAttack(EntityPlayer player, boolean fromPacket);

	void doAttack(EntityPlayer player, boolean fromPacket, int cooldown);

	float getInitalAttackCooldown();

	void callWolves(World world, EntityPlayer caster, float damage);

	void update(EntityPlayer player);

	byte getFlagBits();

	void setFlagBits(byte bits);

	boolean isFlagBitActive(byte bit);

	void addFlagBits(byte add);

	void subtractFlagBits(byte sub);

	enum Morph {

		Wolf, FireElemental, WaterElemental, ArchAngel(tamaized.aov.registry.SoundEvents.aura, false);

		public static final Morph[] values = values();

		public final boolean requiresCentered;
		public final SoundEvent sound;

		Morph() {
			this(true);
		}

		Morph(SoundEvent sound) {
			this(sound, true);
		}


		Morph(boolean centered) {
			this(SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, centered);
		}

		Morph(SoundEvent sound, boolean centered) {
			requiresCentered = centered;
			this.sound = sound;
		}

		public static Morph getMorph(int ordinal) {
			return (ordinal < 0 || ordinal >= values.length) ? null : values[ordinal];
		}
	}

}
