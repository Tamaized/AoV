package tamaized.aov.common.core.abilities.druid;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntitySpellLightningBolt;
import tamaized.aov.common.helper.UtilHelper;
import tamaized.tammodized.common.helper.CapabilityHelper;

public class LightningStrike extends AbilityBase {

	private static final String UNLOC = "aov.spells.litstrike";
	private static final float DAMAGE = 5F;
	private static final int RANGE = 40;
	private static final int CHARGES = 10;

	private static final ResourceLocation ICON = new ResourceLocation(AoV.MODID, "textures/spells/lightningstrike.png");

	public LightningStrike() {
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
	@SideOnly(Side.CLIENT)
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
		return 15;
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
		EntitySpellLightningBolt strike = new EntitySpellLightningBolt(caster.world, caster, damage, this);
		Vec3d pos = UtilHelper.getSpellLocation(caster, RANGE, target);
		strike.setPosition(pos.x, pos.y, pos.z);
		caster.world.spawnEntity(strike);
		//		strike.world.playSound(null, strike.posX, strike.posY, strike.posZ, SoundEvents.firestrike, SoundCategory.NEUTRAL, 1.0F, 1.0F);
		return true;
	}

	@Override
	public ResourceLocation getIcon() {
		return ICON;
	}
}
