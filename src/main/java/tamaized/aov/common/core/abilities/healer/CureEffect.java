package tamaized.aov.common.core.abilities.healer;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.helper.ParticleHelper;
import tamaized.aov.proxy.CommonProxy;
import tamaized.aov.registry.SoundEvents;

import java.util.List;

public abstract class CureEffect extends AbilityBase {

	private final String name;
	private final int charges;
	private final double range;
	private final Potion effect;

	public CureEffect(String n, int c, double r, Potion effect) {
		super(

				new TextComponentTranslation(n),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", c),

				new TextComponentTranslation("aov.spells.global.range", r),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.cure.desc")

		);
		name = n;
		charges = c;
		range = r;
		this.effect = effect;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName() {
		return I18n.format(name);
	}

	@Override
	public double getMaxDistance() {
		return range;
	}

	@Override
	public int getMaxCharges() {
		return charges;
	}

	@Override
	public boolean usesInvoke() {
		return true;
	}

	protected abstract int getParticleColor();

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase e) {
		IAoVCapability cap = caster.hasCapability(CapabilityList.AOV, null) ? caster.getCapability(CapabilityList.AOV, null) : null;
		if (cap == null)
			return false;
		if (cap.getInvokeMass())
			castAsMass(caster, cap);
		else if (e == null) {
			caster.removePotionEffect(effect);
			SoundEvents.playMovingSoundOnServer(SoundEvents.restore, caster);
		} else {
			if (!IAoVCapability.selectiveTarget(caster, cap, e)) {
				e.removePotionEffect(effect);
				SoundEvents.playMovingSoundOnServer(SoundEvents.restore, e);
			}
		}
		cap.addExp(caster, 12, this);
		return true;
	}

	private void castAsMass(EntityLivingBase caster, IAoVCapability cap) {
		int range = (int) (getMaxDistance() * 2);
		ParticleHelper.spawnParticleMesh(ParticleHelper.MeshType.BURST, CommonProxy.ParticleType.Heart, caster.world, caster.getPositionVector(), range, getParticleColor());
		List<EntityLivingBase> list = caster.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(caster.getPosition().add(-range, -range, -range), caster.getPosition().add(range, range, range)));
		for (EntityLivingBase entity : list) {
			if (!IAoVCapability.selectiveTarget(caster, cap, entity)) {
				entity.removePotionEffect(effect);
				SoundEvents.playMovingSoundOnServer(SoundEvents.restore, entity);
				cap.addExp(caster, 12, this);
			}
		}
	}

}
