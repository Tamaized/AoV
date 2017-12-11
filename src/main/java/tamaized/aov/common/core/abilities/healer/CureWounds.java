package tamaized.aov.common.core.abilities.healer;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
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

public abstract class CureWounds extends AbilityBase {

	private final String name;
	private final int damage;
	private final int charges;
	private final double range;

	public CureWounds(String n, int c, double r, int dmg) {
		super(

				new TextComponentTranslation(n),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", c),

				new TextComponentTranslation("aov.spells.global.range", r),

				new TextComponentTranslation("aov.spells.global.healing", dmg),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.curewounds.desc")

		);
		name = n;
		damage = dmg;
		charges = c;
		range = r;
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
	public boolean cast(Ability ability, EntityPlayer player, EntityLivingBase e) {
		IAoVCapability cap = player.hasCapability(CapabilityList.AOV, null) ? player.getCapability(CapabilityList.AOV, null) : null;
		if (cap == null)
			return false;
		int a = (int) (damage * (1f + (cap.getSpellPower() / 100f)));
		if (cap.getInvokeMass())
			castAsMass(player, a, cap);
		else if (e == null) {
			player.heal(a);
			SoundEvents.playMovingSoundOnServer(SoundEvents.heal, player);
		} else {
			if (e.isEntityUndead()) {
				e.attackEntityFrom(DamageSource.MAGIC, a);
				SoundEvents.playMovingSoundOnServer(SoundEvents.heal, e);
			} else if (IAoVCapability.selectiveTarget(cap, e)) {
				e.heal(a);
				SoundEvents.playMovingSoundOnServer(SoundEvents.heal, e);
			}
		}
		cap.addExp(player, 24, this);
		return true;
	}

	private void castAsMass(EntityLivingBase target, int dmg, IAoVCapability cap) {
		int range = (int) (getMaxDistance() * 2);
		ParticleHelper.spawnParticleMesh(ParticleHelper.MeshType.BURST, CommonProxy.ParticleType.Heart, target.world, target.getPositionVector(), range, getParticleColor());
		List<EntityLivingBase> list = target.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(target.getPosition().add(-range, -range, -range), target.getPosition().add(range, range, range)));
		for (EntityLivingBase entity : list) {
			if (entity.isEntityUndead()) {
				entity.attackEntityFrom(DamageSource.MAGIC, dmg);
				SoundEvents.playMovingSoundOnServer(SoundEvents.heal, entity);
			} else if (IAoVCapability.selectiveTarget(cap, entity)) {
				entity.heal(dmg);
				SoundEvents.playMovingSoundOnServer(SoundEvents.heal, entity);
			}
			cap.addExp(target, 24, this);
		}
	}

}
