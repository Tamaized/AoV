package tamaized.aov.common.core.abilities.cleric;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.helper.ParticleHelper;
import tamaized.aov.proxy.CommonProxy;
import tamaized.aov.registry.AoVDamageSource;
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
	@OnlyIn(Dist.CLIENT)
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

	@Override
	public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
		return IAoVCapability.canBenefit(caster, cap, target);
	}

	protected abstract int getParticleColor();

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase e) {
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap == null)
			return false;
		int a = (int) (damage * (1f + (cap.getSpellPower() / 100f)));
		if (cap.getInvokeMass())
			castAsMass(caster, a, cap);
		else if (e == null) {
			caster.heal(a);
			SoundEvents.playMovingSoundOnServer(SoundEvents.heal, caster);
		} else {
			heal(e, caster, a, cap);
		}
		cap.addExp(caster, 24, this);
		return true;
	}

	private void castAsMass(EntityLivingBase caster, int dmg, IAoVCapability cap) {
		int range = (int) (getMaxDistance() * 2);
		ParticleHelper.spawnParticleMesh(ParticleHelper.MeshType.BURST, CommonProxy.ParticleType.Heart, caster.world, caster.getPositionVector(), range, getParticleColor());
		List<EntityLivingBase> list = caster.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(caster.getPosition().add(-range, -range, -range), caster.getPosition().add(range, range, range)));
		for (EntityLivingBase entity : list) {
			heal(entity, caster, dmg, cap);
			cap.addExp(caster, 24, this);
		}
	}

	private void heal(EntityLivingBase entity, EntityLivingBase caster, int dmg, IAoVCapability cap) {
		if (entity.isEntityUndead()) {
			entity.attackEntityFrom(AoVDamageSource.createEntityDamageSource(DamageSource.MAGIC, caster), dmg);
			SoundEvents.playMovingSoundOnServer(SoundEvents.heal, entity);
		} else if (IAoVCapability.canBenefit(caster, cap, entity)) {
			entity.heal(dmg);
			SoundEvents.playMovingSoundOnServer(SoundEvents.heal, entity);
		}
	}

}
