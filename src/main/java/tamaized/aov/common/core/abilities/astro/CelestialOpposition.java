package tamaized.aov.common.core.abilities.astro;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.stun.IStunCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntityCelestialOpposition;
import tamaized.aov.registry.SoundEvents;
import tamaized.tammodized.common.helper.CapabilityHelper;

public class CelestialOpposition extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid, "textures/spells/celestialopposition.png");

	private static final int charges = -1;
	private static final int distance = 8;

	public CelestialOpposition() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", "aov.gui.infinite"),

				new TextComponentTranslation("aov.spells.global.range", distance),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.celestialopposition.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.celestialopposition.name";
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
		return 120;
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
		if (!caster.hasCapability(CapabilityList.AOV, null))
			return false;
		IAoVCapability aov = caster.getCapability(CapabilityList.AOV, null);
		if (!caster.world.isRemote && aov != null) {
			for (EntityLivingBase e : caster.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(caster.posX - distance, caster.posY - distance, caster.posY - distance, caster.posX + distance, caster.posY + distance, caster.posZ + distance))) {
				if (e != caster && IAoVCapability.selectiveTarget(caster, aov, e)) {
					IStunCapability stun = CapabilityHelper.getCap(e, CapabilityList.STUN, null);
					if (stun != null) {
						stun.setStunTicks(20 * 8);
						aov.addExp(caster, 25, ability.getAbility());
					}
				}
				if(e == caster || IAoVCapability.canBenefit(caster, aov, e)){
					for (PotionEffect effect : e.getActivePotionEffects())
						if (!effect.getPotion().isBadEffect())
							e.addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration() + 400, effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles()));
				}
			}
			EntityCelestialOpposition spell = new EntityCelestialOpposition(caster.world);
			spell.setPosition(caster.posX, caster.posY, caster.posZ);
			caster.world.spawnEntity(spell);
			SoundEvents.playMovingSoundOnServer(SoundEvents.celestialopposition, spell, 0.5F, 1F);
			return true;
		}
		return false;
	}

}
