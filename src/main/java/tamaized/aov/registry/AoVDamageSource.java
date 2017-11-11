package tamaized.aov.registry;

import net.minecraft.util.DamageSource;
import tamaized.aov.AoV;

public class AoVDamageSource {

	public static final DamageSource nimbus = new DamageSource(AoV.modid + ".nimbusRay").setDamageBypassesArmor().setMagicDamage();
	public static final DamageSource destruction = new DamageSource(AoV.modid + ".destruction").setDamageBypassesArmor().setMagicDamage();
	public static final DamageSource cosmic = new DamageSource(AoV.modid + ".cosmic").setDamageBypassesArmor().setMagicDamage();

}
