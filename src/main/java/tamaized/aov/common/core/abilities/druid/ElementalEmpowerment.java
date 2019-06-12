package tamaized.aov.common.core.abilities.druid;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.init.Particles;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.abilities.IAura;
import tamaized.aov.common.helper.ParticleHelper;
import tamaized.aov.registry.AoVParticles;
import tamaized.aov.registry.ParticleRegistry;
import tamaized.aov.registry.SoundEvents;

import javax.annotation.Nullable;
import java.util.List;

public class ElementalEmpowerment extends AbilityBase implements IAura {

	private static final String UNLOC = "aov.spells.elementalempowerment";
	private static final float DAMAGE = 1F;
	private static final float RANGE = 4F;
	private static final int CHARGES = 2;

	private static final ResourceLocation ICON = new ResourceLocation(AoV.MODID, "textures/spells/elementalempowerment.png");

	public ElementalEmpowerment() {
		super(

				new TranslationTextComponent(UNLOC.concat(".name")),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", CHARGES),

				new TranslationTextComponent("aov.spells.global.range", RANGE),

				new TranslationTextComponent("aov.spells.global.damage", DAMAGE),

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
	public int getChargeCost() {
		return 1;
	}

	@Override
	public double getMaxDistance() {
		return RANGE;
	}

	@Override
	public int getCoolDown() {
		return 90;
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
	public boolean shouldDisable(@Nullable PlayerEntity caster, IAoVCapability cap) {
		IPolymorphCapability poly = CapabilityList.getCap(caster, CapabilityList.POLYMORPH);
		return poly == null || (poly.getMorph() != IPolymorphCapability.Morph.FireElemental && poly.getMorph() != IPolymorphCapability.Morph.WaterElemental);
	}

	@Override
	public boolean cast(Ability ability, PlayerEntity caster, LivingEntity target) {
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap == null)
			return false;
		IPolymorphCapability poly = CapabilityList.getCap(caster, CapabilityList.POLYMORPH);
		if (poly != null) {
			switch (poly.getMorph()) {
				case WaterElemental:
				case FireElemental:
					cap.addAura(createAura(ability));
					SoundEvents.playMovingSoundOnServer(SoundEvents.aura, caster);
					return true;
				default:
					return false;
			}
		}
		return false;
	}

	@Override
	public ResourceLocation getIcon() {
		return ICON;
	}

	@Override
	public void castAsAura(PlayerEntity caster, IAoVCapability cap, int life) {
		IPolymorphCapability poly = CapabilityList.getCap(caster, CapabilityList.POLYMORPH);
		if (poly != null) {
			switch (poly.getMorph()) {
				case WaterElemental:
					ParticleRegistry.spawnFromServer(caster.world, AoVParticles.SNOW, caster.posX + caster.world.rand.nextDouble() * 1.5F - 0.75F, caster.posY + caster.getEyeHeight() - 0.25F + caster.world.rand.nextDouble() * 1.5F - 0.75F, caster.posZ + caster.world.rand.nextDouble() * 1.5F - 0.75F, 0, -0.03F, 0);
					break;
				case FireElemental:
					ParticleHelper.spawnVanillaParticleOnServer(caster.world, Particles.FLAME, caster.posX + caster.world.rand.nextDouble() * 1.5F - 0.75F, caster.posY + caster.getEyeHeight() - 0.25F + caster.world.rand.nextDouble() * 1.5F - 0.75F, caster.posZ + caster.world.rand.nextDouble() * 1.5F - 0.75F, 0, 0, 0);
					float damage = DAMAGE * (1F + (cap.getSpellPower() / 100F));
					if (life > 0 && life % (3 * 20) == 0) {
						List<LivingEntity> list = caster.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(caster.getPosition().add(-RANGE, -RANGE, -RANGE), caster.getPosition().add(RANGE, RANGE, RANGE)));
						for (LivingEntity entity : list) {
							if (IAoVCapability.selectiveTarget(caster, cap, entity) && entity.attackEntityFrom(DamageSource.IN_FIRE, damage)) {
								entity.setFire(5);
								cap.addExp(caster, 20, this);
							}
						}
					}
					break;
				default:
					break;
			}
		}
	}

	@Override
	public int getLife() {
		return 45;
	}
}
