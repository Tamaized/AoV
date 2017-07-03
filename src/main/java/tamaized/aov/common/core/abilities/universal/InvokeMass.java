package tamaized.aov.common.core.abilities.universal;

import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class InvokeMass extends AbilityBase {

	private final static int charges = -1;

	public InvokeMass() {
		super(

				TextFormatting.YELLOW + getStaticName(),

				"",

				TextFormatting.DARK_PURPLE + "While Active, certain spells",

				TextFormatting.DARK_PURPLE + "and abilities have double",

				TextFormatting.DARK_PURPLE + "range and are cast as",

				TextFormatting.DARK_PURPLE + "an AoE (Area of Effect).",

				TextFormatting.RED + "This also doubles the cost and cooldown."

		);
	}

	@Override
	public void cast(Ability ability, EntityPlayer player, EntityLivingBase e) {
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap == null)
			return;
		cap.toggleInvokeMass();
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid, "textures/spells/invokemass.png");
	}

	@Override
	public String getName() {
		return getStaticName();
	}

	public static String getStaticName() {
		return "Invoke Mass";
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

}
