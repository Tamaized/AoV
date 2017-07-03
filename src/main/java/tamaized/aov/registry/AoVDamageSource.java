package tamaized.aov.registry;

import net.minecraft.util.DamageSource;

public class AoVDamageSource {

	public static DamageSource damageSource_caster_NimbusRay;
	public static DamageSource destruction;

	static {
		damageSource_caster_NimbusRay = new DamageSource("nimbusRay").setDamageBypassesArmor().setMagicDamage();
		destruction = new DamageSource("destruction").setDamageBypassesArmor().setMagicDamage();
	}

}
