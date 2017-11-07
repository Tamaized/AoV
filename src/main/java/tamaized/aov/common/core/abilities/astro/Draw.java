package tamaized.aov.common.core.abilities.astro;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
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
import tamaized.aov.registry.AoVPotions;

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

	public static void doDrawEffects(EntityLivingBase entity, @Nonnull IAstroCapability.ICard card, int potency, @Nullable IAstroCapability.ICard burn) {
		int ticks = 300;
		boolean aoe = false;
		if (burn != null)
			switch (burn) {
				default:
				case Balance:
				case Bole:
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
					potency *= 1.5F;
					if (potency <= 0)
						potency = 1;
					break;
			}
		if (aoe) {
			IAoVCapability cap = entity.hasCapability(CapabilityList.AOV, null) ? entity.getCapability(CapabilityList.AOV, null) : null;
			int range = 16;
			for (EntityLivingBase e : entity.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(entity.posX - range, entity.posY - range, entity.posZ - range, entity.posX + range, entity.posY + range, entity.posZ + range))) {
				if (cap != null && cap.hasSelectiveFocus()) {
					if (entity.getTeam() == null) {
						if (e instanceof EntityPlayer || (e instanceof IEntityOwnable && ((IEntityOwnable) e).getOwner() == entity))
							doDrawEffects(e, card, potency, null);
					} else {
						if (entity.isOnSameTeam(e) || (e instanceof IEntityOwnable && ((IEntityOwnable) e).getOwner() == entity))
							doDrawEffects(e, card, potency, null);
					}
				} else {
					doDrawEffects(e, card, potency, null);
				}
			}
			return;
		}
		switch (card) {
			default:
			case Balance:
				entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, ticks, potency));
				break;
			case Bole:
				entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, ticks, potency));
				entity.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, ticks, (int) Math.floor(potency / 2F)));
				entity.addPotionEffect(new PotionEffect(MobEffects.SATURATION, ticks, (int) Math.floor(potency / 2F)));
				break;
			case Spear:
				entity.addPotionEffect(new PotionEffect(AoVPotions.spear, ticks, potency));
				break;
			case Arrow:
				entity.addPotionEffect(new PotionEffect(MobEffects.HASTE, ticks, potency));
				break;
			case Ewer:
				entity.addPotionEffect(new PotionEffect(AoVPotions.ewer, ticks, 0));
				break;
			case Spire:
				entity.addPotionEffect(new PotionEffect(AoVPotions.spire, ticks, potency));
				break;
		}
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
		if (astro.getDraw() == null) {
			astro.drawCard(caster);
			ability.setNextCooldown(1);
			ability.setTimer(30);
		} else {
			IAstroCapability.ICard card = astro.getDraw();
			EntityLivingBase entity = target == null ? caster : target;
			int potency = (int) Math.floor(aov.getSpellPower() / 10F);
			IAstroCapability.ICard burn = astro.getBurn();
			astro.useDraw(caster);
			doDrawEffects(entity, card, potency, burn);
			aov.addExp(caster, 15, this);
		}
		astro.sendPacketUpdates(caster);
	}

}
