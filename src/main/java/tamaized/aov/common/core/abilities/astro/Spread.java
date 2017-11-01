package tamaized.aov.common.core.abilities.astro;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
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
import tamaized.aov.registry.AoVPotions;

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
	public void cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		if (!caster.hasCapability(CapabilityList.ASTRO, null) || !caster.hasCapability(CapabilityList.AOV, null))
			return;
		IAstroCapability astro = caster.getCapability(CapabilityList.ASTRO, null);
		IAoVCapability aov = caster.getCapability(CapabilityList.AOV, null);
		if (astro == null || aov == null)
			return;
		if (astro.getSpread() == null) {
			ability.setNextCooldown(1);
			if (astro.getDraw() != null) {
				astro.spreadCard(caster);
				for (Ability a : aov.getSlots()) {
					if (a != null && a.getAbility() == AbilityBase.draw)
						a.setTimer(0);
				}
			}
		} else {
			IAstroCapability.ICard card = astro.getSpread();
			astro.useSpread(caster);
			EntityLivingBase entity = target == null ? caster : target;
			int potency = (int) Math.floor(aov.getSpellPower() / 10F);
			switch (card) {
				default:
				case Balance:
					entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 600, potency));
					break;
				case Bole:
					entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 600, potency));
					break;
				case Spear:
					entity.addPotionEffect(new PotionEffect(AoVPotions.spear, 600, 0));
					break;
				case Arrow:
					entity.addPotionEffect(new PotionEffect(MobEffects.HASTE, 600, potency));
					break;
				case Ewer:
					entity.addPotionEffect(new PotionEffect(AoVPotions.ewer, 600, 0));
					break;
				case Spire:
					entity.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, 600, potency));
					entity.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 600, potency));
					break;
			}
			aov.addExp(caster, 15, this);
		}
		astro.sendPacketUpdates(caster);
	}

}
