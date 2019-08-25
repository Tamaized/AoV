package tamaized.aov.common.core.abilities.druid;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
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
import tamaized.aov.registry.AoVPotions;

public class NaturesBounty extends AbilityBase {

	private static final String UNLOC = "aov.spells.naturesbounty";
	private static final int CHARGES = 5;
	private static final float RANGE = 10F;

	private static final ResourceLocation ICON = new ResourceLocation(AoV.MODID, "textures/spells/naturesbounty.png");

	public NaturesBounty() {
		super(

				new TranslationTextComponent(UNLOC.concat(".name")),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", CHARGES),

				new TranslationTextComponent("aov.spells.global.range", RANGE),

				new TranslationTextComponent(""),

				new TranslationTextComponent(UNLOC.concat(".desc"))

		);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return I18n.format(UNLOC.concat(".name"));
	}

	@Override
	public int getMaxCharges() {
		return CHARGES;
	}

	@Override
	public int getExtraCharges(LivingEntity entity, IAoVCapability cap) {
		return IAoVCapability.isImprovedCentered(entity, cap) ? getMaxCharges() : super.getExtraCharges(entity, cap);
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	public double getMaxDistance() {
		return RANGE;
	}

	@Override
	public int getCoolDown() {
		return 45;
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
		if (target != null)
			addPotionEffects(target);
		else
			addPotionEffects(caster);
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap != null)
			cap.addExp(caster, 15, this);
		return true;
	}

	private void addPotionEffects(LivingEntity entity) {
		entity.addPotionEffect(new EffectInstance(AoVPotions.naturesBounty, 20 * (60 * 15)));
		entity.world.addEntity(new EntitySpellAoVParticles(entity.world, entity, CommonProxy.ParticleType.Heart, 0x00FFAAFF, 1));
		entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, SoundCategory.PLAYERS, 1.0F, entity.getRNG().nextFloat() * 0.5F + 0.75F);
	}

	@Override
	public ResourceLocation getIcon() {
		return ICON;
	}
}
