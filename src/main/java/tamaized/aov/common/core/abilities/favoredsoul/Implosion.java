package tamaized.aov.common.core.abilities.favoredsoul;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntitySpellImplosion;
import tamaized.tammodized.common.helper.CapabilityHelper;

public class Implosion extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.MODID, "textures/spells/impolsion.png");

	private static final int charges = 1;
	private static final int distance = 10;

	public Implosion() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", charges),

				new TextComponentTranslation("aov.spells.global.range", distance),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.impolsion.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.impolsion.name";
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName() {
		return I18n.format(getStaticName());
	}

	@Override
	public int getCoolDown() {
		return 30;
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
	public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
		return false;
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		boolean flag = false;
		for (EntityLivingBase entity : caster.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(caster.getPosition().add(-distance, -1, -distance), caster.getPosition().add(distance, 5, distance)))) {
			IAoVCapability cap = CapabilityHelper.getCap(caster, CapabilityList.AOV, null);
			if (entity == caster || (cap != null && !IAoVCapability.selectiveTarget(caster, cap, entity)))
				continue;
			caster.world.spawnEntity(new EntitySpellImplosion(caster.world, caster, entity));
			flag = true;
		}
		return flag;
	}

}
