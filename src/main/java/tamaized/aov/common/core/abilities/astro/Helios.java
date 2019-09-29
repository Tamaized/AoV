package tamaized.aov.common.core.abilities.astro;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.client.ParticleHelper;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntitySpellAoVParticles;
import tamaized.aov.registry.SoundEvents;

import java.util.List;

public class Helios extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.MODID, "textures/spells/helios.png");

	private static final int charges = 4;
	private static final int distance = 20;
	private static final int heal = 2;

	public Helios() {
		super(

				new TranslationTextComponent(getStaticName()),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", charges),

				new TranslationTextComponent("aov.spells.global.range", distance),

				new TranslationTextComponent("aov.spells.global.healing", heal),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.helios.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.helios.name";
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
		return 4;
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
		LivingEntity e = target != null && IAoVCapability.canBenefit(caster, cap, target) ? target : caster;
		List<LivingEntity> list = e.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(e.getPosition().add(-distance, -distance, -distance), e.getPosition().add(distance, distance, distance)));
		for (LivingEntity entity : list) {
			if (entity == caster || IAoVCapability.canBenefit(caster, cap, entity)) {
				entity.heal(heal);
				entity.world.addEntity(new EntitySpellAoVParticles(entity.world, entity, ParticleHelper.ParticleType.Heart, 2, 0x3FFF6AFF));
			}
			cap.addExp(caster, 12, this);
		}
		SoundEvents.playMovingSoundOnServer(SoundEvents.helios, e);
		return true;
	}

}
