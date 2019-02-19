package tamaized.aov.common.core.abilities.astro;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntitySpellAoVParticles;
import tamaized.aov.proxy.CommonProxy;
import tamaized.aov.registry.SoundEvents;
import tamaized.tammodized.common.helper.CapabilityHelper;

import java.util.List;

public class Helios extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.MODID, "textures/spells/helios.png");

	private static final int charges = 4;
	private static final int distance = 20;
	private static final int heal = 2;

	public Helios() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", charges),

				new TextComponentTranslation("aov.spells.global.range", distance),

				new TextComponentTranslation("aov.spells.global.healing", heal),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.helios.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.helios.name";
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return I18n.format(getStaticName());
	}

	@Override
	public int getCoolDown() {
		return 4;
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
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap == null)
			return false;
		EntityLivingBase e = target != null && IAoVCapability.canBenefit(caster, cap, target) ? target : caster;
		List<EntityLivingBase> list = e.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(e.getPosition().add(-distance, -distance, -distance), e.getPosition().add(distance, distance, distance)));
		for (EntityLivingBase entity : list) {
			if (entity == caster || IAoVCapability.canBenefit(caster, cap, entity)) {
				entity.heal(heal);
				entity.world.spawnEntity(new EntitySpellAoVParticles(entity.world, entity, CommonProxy.ParticleType.Heart, 0x3FFF6AFF, 2));
			}
			cap.addExp(caster, 12, this);
		}
		SoundEvents.playMovingSoundOnServer(SoundEvents.helios, e);
		return true;
	}

}
