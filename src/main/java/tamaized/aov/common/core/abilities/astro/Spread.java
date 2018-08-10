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
import tamaized.aov.common.core.abilities.Abilities;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;

public class Spread extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid, "textures/spells/spread.png");

	private static final int charges = -1;
	private static final int distance = 10;

	public Spread() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.range", distance),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.spread.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.spread.name";
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
	public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
		return false;
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		if (!caster.hasCapability(CapabilityList.ASTRO, null) || !caster.hasCapability(CapabilityList.AOV, null))
			return false;
		IAstroCapability astro = caster.getCapability(CapabilityList.ASTRO, null);
		IAoVCapability aov = caster.getCapability(CapabilityList.AOV, null);
		if (astro == null || aov == null)
			return false;
		if (astro.getSpread() == null) {
			ability.setNextCooldown(1);
			if (astro.getDraw() != null) {
				astro.spreadCard(caster);
				for (Ability a : aov.getSlots()) {
					if (a != null && a.getAbility() == Abilities.draw)
						a.setTimer(0);
				}
			}
		} else {
			IAstroCapability.ICard card = astro.getSpread();
			EntityLivingBase entity = target == null || caster.getDistanceToEntity(target) < getMaxDistance() ? caster : target;
			int potency = (int) Math.floor(aov.getSpellPower() / 10F);
			IAstroCapability.ICard burn = astro.getBurn();
			astro.useSpread(caster);
			Draw.doDrawEffects(entity, card, potency, burn);
			aov.addExp(caster, 15, this);
		}
		astro.sendPacketUpdates(caster);
		return true;
	}

}
