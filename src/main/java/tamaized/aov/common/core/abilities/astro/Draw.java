package tamaized.aov.common.core.abilities.astro;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntitySpellVanillaParticles;
import tamaized.aov.registry.AoVPotions;
import tamaized.tammodized.common.helper.CapabilityHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Draw extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid, "textures/spells/draw.png");

	private static final int charges = -1;
	private static final int distance = 10;

	public Draw() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.range", distance),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.draw.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.draw.name";
	}

	public static void doDrawEffects(EntityLivingBase caster, @Nonnull IAstroCapability.ICard card, int potency, @Nullable IAstroCapability.ICard burn, boolean fromAoe) {
		int ticks = 300;
		int hardcodedBalancePotency = fromAoe ? potency : 0;
		boolean aoe = false;
		if (burn != null)
			switch (burn) {
				default:
				case Balance:
				case Bole:
					hardcodedBalancePotency = 2;
					potency *= 2.5F;
					if (potency <= 0)
						potency = 2;
					break;
				case Spear:
				case Arrow:
					ticks *= 3;
					break;
				case Ewer:
				case Spire:
					aoe = true;
					hardcodedBalancePotency = 1;
					potency *= 1.5F;
					if (potency <= 0)
						potency = 1;
					break;
			}
		if (aoe) {
			IAoVCapability cap = CapabilityHelper.getCap(caster, CapabilityList.AOV, null);
			int range = 16;
			for (EntityLivingBase e : caster.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(caster.posX - range, caster.posY - range, caster.posZ - range, caster.posX + range, caster.posY + range, caster.posZ + range))) {
				if (cap == null || IAoVCapability.canBenefit(caster, cap, e))
					doDrawEffects(e, card, potency, null, true);
			}
			return;
		}
		switch (card) {
			default:
			case Balance:
				caster.addPotionEffect(new PotionEffect(AoVPotions.balance, ticks, hardcodedBalancePotency));
				break;
			case Bole:
				caster.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, ticks, potency));
				caster.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, ticks, (int) Math.floor(potency / 2F)));
				caster.addPotionEffect(new PotionEffect(MobEffects.SATURATION, 20 * 3, (int) Math.floor(potency / 2F)));
				break;
			case Spear:
				caster.addPotionEffect(new PotionEffect(AoVPotions.spear, ticks, potency));
				break;
			case Arrow:
				caster.addPotionEffect(new PotionEffect(MobEffects.HASTE, ticks, potency));
				break;
			case Ewer:
				caster.addPotionEffect(new PotionEffect(AoVPotions.ewer, ticks, 0));
				break;
			case Spire:
				caster.addPotionEffect(new PotionEffect(AoVPotions.spire, ticks, potency));
				break;
		}
		caster.world.spawnEntity(new EntitySpellVanillaParticles(caster.world, caster, EnumParticleTypes.CRIT_MAGIC, 5));
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
		return IAoVCapability.canBenefit(caster, cap, target);
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		if (!caster.hasCapability(CapabilityList.ASTRO, null) || !caster.hasCapability(CapabilityList.AOV, null))
			return false;
		IAstroCapability astro = caster.getCapability(CapabilityList.ASTRO, null);
		IAoVCapability aov = caster.getCapability(CapabilityList.AOV, null);
		if (astro == null || aov == null)
			return false;
		if (astro.getDraw() == null) {
			astro.drawCard(caster);
			ability.setNextCooldown(1);
			ability.setTimer(30);
		} else {
			IAstroCapability.ICard card = astro.getDraw();
			EntityLivingBase entity = target == null || caster.getDistance(target) >= getMaxDistance() ? caster : target;
			int potency = (int) Math.floor(aov.getSpellPower() / 10F);
			IAstroCapability.ICard burn = astro.getBurn();
			astro.useDraw(caster);
			doDrawEffects(entity, card, potency, burn, false);
			aov.addExp(caster, 15, this);
			ability.setTimer(-1);
		}
		astro.sendPacketUpdates(caster);
		return true;
	}

}
