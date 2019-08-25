package tamaized.aov.common.core.abilities.astro;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntitySpellAoVParticles;
import tamaized.aov.proxy.CommonProxy;
import tamaized.aov.registry.SoundEvents;

public class AspectedBenefic extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.MODID, "textures/spells/aspectedbenefic.png");

	private static final int charges = 2;
	private static final int distance = 10;
	private static final int heal = 8;

	public AspectedBenefic() {
		super(

				new TranslationTextComponent(getStaticName()),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", charges),

				new TranslationTextComponent("aov.spells.global.range", distance),

				new TranslationTextComponent("aov.spells.global.healing", heal),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.aspectedbenefic.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.aspectedbenefic.name";
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return I18n.format(getStaticName());
	}

	@Override
	public int getCoolDown() {
		return 3;
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
		return IAoVCapability.canBenefit(caster, cap, target);
	}

	@Override
	public boolean cast(Ability ability, PlayerEntity caster, LivingEntity target) {
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap == null)
			return false;
		LivingEntity entity = target != null && IAoVCapability.canBenefit(caster, cap, target) ? target : caster;
		int a = (int) (heal * (1f + (cap.getSpellPower() / 100f)));
		entity.heal(a);
		SoundEvents.playMovingSoundOnServer(SoundEvents.aspectedbenefic, entity);
		entity.world.addEntity(new EntitySpellAoVParticles(entity.world, entity, CommonProxy.ParticleType.Heart, 0x00FFD8FF, 5));
		entity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 600));
		cap.addExp(caster, 15, this);
		return true;
	}

}
