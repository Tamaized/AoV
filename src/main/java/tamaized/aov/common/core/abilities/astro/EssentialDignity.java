package tamaized.aov.common.core.abilities.astro;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntitySpellVanillaParticles;
import tamaized.aov.registry.SoundEvents;

public class EssentialDignity extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.MODID, "textures/spells/essentialdignity.png");

	private static final int charges = -1;
	private static final int distance = 10;

	public EssentialDignity() {
		super(

				new TranslationTextComponent(getStaticName()),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", "aov.gui.infinite"),

				new TranslationTextComponent("aov.spells.global.range", distance),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.essentialdignity.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.essentialdignity.name";
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
		return 40;
	}

	@Override
	public int getMaxCharges() {
		return charges;
	}

	@Override
	public int getChargeCost() {
		return 0;
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
		entity.heal(entity.getMaxHealth());
		SoundEvents.playMovingSoundOnServer(SoundEvents.essentialdignity, entity);
		entity.world.addEntity(new EntitySpellVanillaParticles(entity.world, entity, ParticleTypes.END_ROD, 10));
		cap.addExp(caster, 12, this);
		return true;
	}

}
