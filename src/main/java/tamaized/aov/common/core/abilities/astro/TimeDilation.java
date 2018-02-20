package tamaized.aov.common.core.abilities.astro;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntitySpellVanillaParticles;
import tamaized.aov.registry.SoundEvents;

public class TimeDilation extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid, "textures/spells/timedilation.png");

	private static final int charges = -1;
	private static final int distance = 10;

	public TimeDilation() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.range", distance),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.timedilation.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.timedilation.name";
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
		return 90;
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
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		if (!caster.hasCapability(CapabilityList.AOV, null))
			return true;
		IAoVCapability aov = caster.getCapability(CapabilityList.AOV, null);
		EntityLivingBase entity = target != null && aov != null && !IAoVCapability.selectiveTarget(caster, aov, target) ? target : caster;
		for (PotionEffect effect : entity.getActivePotionEffects())
			if (!effect.getPotion().isBadEffect())
				entity.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration() * 2, effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));
		if (!entity.world.isRemote) {
			SoundEvents.playMovingSoundOnServer(SoundEvents.timedilation, entity);
			entity.world.spawnEntity(new EntitySpellVanillaParticles(entity.world, entity, EnumParticleTypes.VILLAGER_HAPPY, 3));
			if (aov != null)
				aov.addExp(caster, 20, ability.getAbility());
		}
		return true;
	}

}
