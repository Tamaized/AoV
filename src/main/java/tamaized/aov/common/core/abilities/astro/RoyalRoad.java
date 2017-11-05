package tamaized.aov.common.core.abilities.astro;

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
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;

public class RoyalRoad extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid, "textures/spells/royalroad.png");

	private static final int charges = -1;
	private static final int distance = 10;

	public RoyalRoad() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.range", distance),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.royalroad.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.royalroad.name";
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
		return 0;
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
	public void cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		if (!caster.hasCapability(CapabilityList.ASTRO, null) || !caster.hasCapability(CapabilityList.AOV, null))
			return;
		IAstroCapability astro = caster.getCapability(CapabilityList.ASTRO, null);
		IAoVCapability aov = caster.getCapability(CapabilityList.AOV, null);
		if (astro == null || aov == null)
			return;
		if (astro.getDraw() != null && astro.getBurn() == null) {
			astro.burnCard(caster);
			for (Ability a : aov.getSlots()) {
				if (a != null && a.getAbility() == AbilityBase.draw)
					a.setTimer(0);
			}
			ability.setNextCooldown(1);
			aov.addExp(caster, 15, this);
		}
		ability.setNextCooldown(1);
		astro.sendPacketUpdates(caster);
	}

}
