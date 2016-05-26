package Tamaized.AoV.registry;

import net.minecraft.util.DamageSource;

public class AoVDamageSource extends RegistryBase {
	
	public static DamageSource damageSource_caster_NimbusRay;

	@Override
	public void preInit() {
		damageSource_caster_NimbusRay = new DamageSource("nimbusRay").setDamageBypassesArmor().setMagicDamage();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void postInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setupRender() {
		// TODO Auto-generated method stub

	}

}
