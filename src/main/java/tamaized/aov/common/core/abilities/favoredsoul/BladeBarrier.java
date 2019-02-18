package tamaized.aov.common.core.abilities.favoredsoul;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntitySpellBladeBarrier;
import tamaized.tammodized.common.helper.CapabilityHelper;

public class BladeBarrier extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.MODID, "textures/spells/bladebarrier.png");

	private static final int damage = 5;
	private static final int charges = 5;
	private static final int distance = 4;

	public BladeBarrier() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", charges),

				new TextComponentTranslation("aov.spells.global.range", distance),

				new TextComponentTranslation("aov.spells.global.damage", damage),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.bladebarrier.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.bladebarrier.name";
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
		return 8;
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
		IAoVCapability cap = CapabilityHelper.getCap(caster, CapabilityList.AOV, null);
		if (cap == null)
			return false;
		int a = (int) (damage * (1f + (cap.getSpellPower() / 100f)));
		caster.world.spawnEntity(new EntitySpellBladeBarrier(caster.world, caster, a, distance));
		return true;
	}

}
