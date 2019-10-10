package tamaized.aov.registry;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.eventbus.api.IEventBus;
import tamaized.aov.AoV;

import javax.annotation.Nullable;

public class AoVDamageSource {

	public static final DamageSource NIMBUS = new DamageSource(AoV.MODID + ".nimbusray").setDamageBypassesArmor().setMagicDamage();
	public static final DamageSource DESTRUCTION = new DamageSource(AoV.MODID + ".destruction").setDamageBypassesArmor().setMagicDamage();
	public static final DamageSource COSMIC = new DamageSource(AoV.MODID + ".cosmic").setDamageBypassesArmor().setMagicDamage();

	public static void register(IEventBus mod) {
	}

	public static DamageSource createEntityDamageSource(DamageSource orign, @Nullable Entity caster) {
		DamageSource source = new EntityDamageSource(orign.getDamageType(), caster);
		if (orign.isUnblockable())
			source.setDamageBypassesArmor();
		if (orign.isProjectile())
			source.setProjectile();
		if (orign.isDamageAbsolute())
			source.setDamageIsAbsolute();
		if (orign.isFireDamage())
			source.setFireDamage();
		if (orign.isProjectile())
			source.setProjectile();
		if (orign.isDifficultyScaled())
			source.setDifficultyScaled();
		if (orign.isMagicDamage())
			source.setMagicDamage();
		if (orign.isExplosion())
			source.setExplosion();
		return source;
	}

}
