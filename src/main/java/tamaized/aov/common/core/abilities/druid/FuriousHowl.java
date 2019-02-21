package tamaized.aov.common.core.abilities.druid;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.registry.AoVDamageSource;

import javax.annotation.Nullable;

public class FuriousHowl extends AbilityBase {

	private static final String UNLOC = "aov.spells.furioushowl";
	private static final int RANGE = 6;
	private static final float DAMAGE = 2F;
	private static final int CHARGES = 5;

	private static final ResourceLocation ICON = new ResourceLocation(AoV.MODID, "textures/spells/furioushowl.png");

	public FuriousHowl() {
		super(

				new TextComponentTranslation(UNLOC.concat(".name")),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", CHARGES),

				new TextComponentTranslation("aov.spells.global.range", RANGE),

				new TextComponentTranslation("aov.spells.global.damage", DAMAGE),

				new TextComponentTranslation(""),

				new TextComponentTranslation(UNLOC.concat(".desc"))

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
	public int getExtraCharges(EntityLivingBase entity, IAoVCapability cap) {
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
		return 30;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
		return false;
	}

	@Override
	public boolean shouldDisable(@Nullable EntityPlayer caster, IAoVCapability cap) {
		IPolymorphCapability poly = CapabilityList.getCap(caster, CapabilityList.POLYMORPH);
		return poly == null || poly.getMorph() != IPolymorphCapability.Morph.Wolf;
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		IAoVCapability aov = CapabilityList.getCap(caster, CapabilityList.AOV);
		IPolymorphCapability cap = CapabilityList.getCap(caster, CapabilityList.POLYMORPH);
		if (aov == null || cap == null || cap.getMorph() != IPolymorphCapability.Morph.Wolf)
			return false;
		float damage = DAMAGE * (1F + ((aov.getSpellPower() * (IAoVCapability.isImprovedCentered(caster, aov) ? 2F : 1F)) / 100F));
		for (EntityLivingBase entity : caster.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(caster.getPosition().add(-RANGE, -RANGE, -RANGE), caster.getPosition().add(RANGE, RANGE, RANGE)), e -> e != caster)) {
			if (IAoVCapability.selectiveTarget(caster, aov, entity) && entity.attackEntityFrom(AoVDamageSource.createEntityDamageSource(DamageSource.MAGIC, caster), damage))
				aov.addExp(caster, 20, this);
		}
		for (int i = 0; i < 3; i++)
			caster.world.playSound(null, caster.posX, caster.posY, caster.posZ, SoundEvents.ENTITY_WOLF_GROWL, SoundCategory.PLAYERS, 0.5F, caster.getRNG().nextFloat() * 0.75F + 0.25F);
		return true;
	}

	@Override
	public ResourceLocation getIcon() {
		return ICON;
	}
}
