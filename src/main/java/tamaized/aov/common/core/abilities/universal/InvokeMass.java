package tamaized.aov.common.core.abilities.universal;

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

public class InvokeMass extends AbilityBase {

	private final static int charges = -1;

	public InvokeMass() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.invokemass.desc"),

				new TextComponentTranslation("aov.spells.global.double")

		);
	}

	public static String getStaticName() {
		return "aov.spells.invokemass.name";
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer player, EntityLivingBase e) {
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap == null)
			return false;
		cap.toggleInvokeMass();
		return true;
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.MODID, "textures/spells/invokemass.png");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName() {
		return I18n.format(getStaticName());
	}

	@Override
	public int getCoolDown() {
		return 1;
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
		return 0;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
		return false;
	}

}
