package tamaized.aov.common.core.abilities.druid;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntitySpellLightningStorm;
import tamaized.aov.common.helper.UtilHelper;
import tamaized.tammodized.common.helper.CapabilityHelper;

public class LightningStorm extends AbilityBase {

	private static final String UNLOC = "aov.spells.litstorm";
	private static final float DAMAGE = 3F;
	private static final int RANGE = 25;
	private static final int CHARGES = 2;

	private static final ResourceLocation ICON = new ResourceLocation(AoV.MODID, "textures/spells/lightningstorm.png");

	public LightningStorm() {
		super(

				new TextComponentTranslation(UNLOC.concat(".name")),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", CHARGES),

				new TextComponentTranslation("aov.spells.global.range", RANGE),

				new TextComponentTranslation("aov.spells.global.damage", DAMAGE),


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
	public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
		return IAoVCapability.selectiveTarget(caster, cap, target);
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap == null)
			return false;
		float damage = DAMAGE * (1F + (cap.getSpellPower() / 100F));
		EntitySpellLightningStorm storm = new EntitySpellLightningStorm(caster.world, caster, damage);
		Vec3d pos = UtilHelper.getSpellLocation(caster, RANGE, target);
		if (pos.equals(caster.getPositionVector()))
			return false;
		storm.setPosition(pos.x, pos.y + 10F, pos.z);
		caster.world.spawnEntity(storm);
		caster.world.playSound(null, storm.posX, storm.posY, storm.posZ, SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON, SoundCategory.PLAYERS, 4.0F, caster.getRNG().nextFloat() * 0.5F + 0.5F);
		return true;
	}

	@Override
	public ResourceLocation getIcon() {
		return ICON;
	}
}
