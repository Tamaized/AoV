package tamaized.aov.common.core.abilities.druid;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.helper.ParticleHelper;
import tamaized.aov.proxy.CommonProxy;

import java.util.List;

public class DruidicRegenerate extends AbilityBase {

	private static final String UNLOC = "aov.spells.druidregenerate";
	private static final int CHARGES = 10;
	private static final float RANGE = 6;

	private static final ResourceLocation ICON = new ResourceLocation(AoV.MODID, "textures/spells/druidicregenerate.png");

	public DruidicRegenerate() {
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
		return 3;
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
		if (cap != null) {
			int range = (int) (getMaxDistance() * 2);
			List<LivingEntity> list = caster.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(caster.getPosition().add(-range, -range, -range), caster.getPosition().add(range, range, range)));
			for (LivingEntity entity : list) {
				entity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 200, IAoVCapability.isImprovedCentered(caster, cap) ? 1 : 0));
				cap.addExp(caster, 15, this);
			}
			ParticleHelper.spawnParticleMesh(ParticleHelper.MeshType.BURST, CommonProxy.ParticleType.Heart, caster.world, caster.getPositionVector(), range, 0x00FFAAFF);
			caster.world.playSound(null, caster.posX, caster.posY, caster.posZ, SoundEvents.ENTITY_EVOKER_CAST_SPELL, SoundCategory.PLAYERS, 1.0F, caster.getRNG().nextFloat() * 0.50F + 0.50F);
			return true;
		}
		return false;
	}

	@Override
	public ResourceLocation getIcon() {
		return ICON;
	}
}
