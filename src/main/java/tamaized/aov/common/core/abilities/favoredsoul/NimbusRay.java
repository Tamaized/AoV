package tamaized.aov.common.core.abilities.favoredsoul;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.ProjectileNimbusRay;

public class NimbusRay extends AbilityBase {

	private static final int damage = 4;
	private static final int charges = 6;
	private static final int distance = 20;

	public NimbusRay() {
		super(

				new TranslationTextComponent(getStaticName()),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", charges),

				new TranslationTextComponent("aov.spells.global.range", distance),

				new TranslationTextComponent("aov.spells.global.damage", damage),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.nimbus.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.nimbus.name";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.MODID, "textures/spells/nimbus.png");
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return I18n.format(getStaticName());
	}

	@Override
	public int getCoolDown() {
		return 2;
	}

	@Override
	public int getMaxCharges() {
		return charges;
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	public double getMaxDistance() {
		return distance;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public boolean isCastOnTarget(PlayerEntity caster, IAoVCapability cap, LivingEntity target) {
		return false;
	}

	@Override
	public boolean cast(Ability ability, PlayerEntity caster, LivingEntity target) {
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap == null)
			return false;
		int a = (int) (damage * (1f + (cap.getSpellPower() / 100f)));
		ProjectileNimbusRay ray = new ProjectileNimbusRay(caster.world, caster, caster.posX, caster.posY, caster.posZ);
		ray.setSpell(this);
		ray.setColor(0xFFFF66FF);
		ray.setDamage(a);
		ray.setMaxRange(distance);
		caster.world.addEntity(ray);
		return true;
	}

}
