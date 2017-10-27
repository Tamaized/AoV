package tamaized.aov.registry;

import net.minecraft.util.DamageSource;
import tamaized.aov.AoV;

public class AoVDamageSource {

	public static DamageSource damageSource_caster_NimbusRay;
	public static DamageSource destruction;

	static {
		damageSource_caster_NimbusRay = new DamageSource(AoV.modid + ".nimbusRay").setDamageBypassesArmor().setMagicDamage();
		destruction = new DamageSource(AoV.modid + ".destruction").setDamageBypassesArmor().setMagicDamage();
	}

}
