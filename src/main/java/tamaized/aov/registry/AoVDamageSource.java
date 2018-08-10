package tamaized.aov.registry;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import tamaized.aov.AoV;

import javax.annotation.Nullable;

public class AoVDamageSource {

	public static final DamageSource NIMBUS = new DamageSource(AoV.modid + ".nimbusray").setDamageBypassesArmor().setMagicDamage();
	public static final DamageSource DESTRUCTION = new DamageSource(AoV.modid + ".destruction").setDamageBypassesArmor().setMagicDamage();
	public static final DamageSource COSMIC = new DamageSource(AoV.modid + ".cosmic").setDamageBypassesArmor().setMagicDamage();

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
