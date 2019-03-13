package tamaized.aov.common.core.abilities.cleric.Cores;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.helper.ParticleHelper;
import tamaized.aov.proxy.CommonProxy;
import tamaized.aov.registry.AoVDamageSource;
import tamaized.aov.registry.SoundEvents;

import java.util.Iterator;
import java.util.List;

public class Burst extends AbilityBase {

	private final static int charges = 6;
	private final static int range = 20;
	private final static int dmg = 10;

	public Burst() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", charges),

				new TextComponentTranslation("aov.spells.global.range", range),

				new TextComponentTranslation("aov.spells.global.healing", dmg),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.burst.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.burst.name";
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase e) {
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap == null)
			return false;
		ParticleHelper.spawnParticleMesh(ParticleHelper.MeshType.BURST, CommonProxy.ParticleType.Heart, caster.world, caster.getPositionVector(), range, 0xFFFF00FF);
		SoundEvents.playMovingSoundOnServer(SoundEvents.burst, caster);
		int a = (int) (dmg * (1f + (cap.getSpellPower() / 100f)));
		List<EntityLivingBase> list = caster.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(caster.getPosition().add(-range, -range, -range), caster.getPosition().add(range, range, range)));
		for (EntityLivingBase entity : list) {
			if (entity.isEntityUndead())
				entity.attackEntityFrom(AoVDamageSource.createEntityDamageSource(DamageSource.MAGIC, caster), a);
			else if (IAoVCapability.canBenefit(caster, cap, entity)) {
				entity.heal(a);
				//noinspection ForLoopReplaceableByForEach
				for (Iterator<PotionEffect> iter = entity.getActivePotionEffects().iterator(); iter.hasNext(); ) {
					PotionEffect effect = iter.next();
					if (effect.getPotion().isBadEffect())
						entity.removePotionEffect(effect.getPotion());
				}
				cap.addExp(caster, 20, this);
			}
		}
		return true;
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.MODID, "textures/spells/burst.png");
	}

	@Override
	@OnlyIn(Dist.CLIENT)
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
		return 1;
	}

	@Override
	public double getMaxDistance() {
		return range;
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
