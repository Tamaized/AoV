package Tamaized.AoV.core.abilities.healer;

import java.util.List;

import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.helper.ParticleHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;

public abstract class CureWounds extends AbilityBase {

	private final String name;
	private final int damage;
	private final int charges;
	private final double range;

	public CureWounds(String n, int c, double r, int dmg) {
		super(n,

				TextFormatting.YELLOW + n,

				"",

				TextFormatting.AQUA + "Charges: " + c,

				TextFormatting.AQUA + "Range: " + r,

				TextFormatting.AQUA + "Base Healing: " + dmg,

				"",

				TextFormatting.DARK_PURPLE + "Heals yourself or an entity if",

				TextFormatting.DARK_PURPLE + "your crosshair is over the entity."

		);
		name = n;
		damage = dmg;
		charges = c;
		range = r;
	}

	@Override
	public String getName() {
		return name;
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
	public void cast(EntityPlayer player, EntityLivingBase e) {
		if (player.world.isRemote) {
			if (e != null) {
				sendPacketTypeTarget(this, e.getEntityId());
			} else {
				sendPacketTypeSelf(this);
			}
		} else {
			IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
			if (cap == null) return;
			int a = (int) (damage * (1f + (cap.getSpellPower() / 100f)));
			if (cap.getInvokeMass()) castAsMass(player, a, cap);
			else if (e == null) {
				player.heal(a);
			} else {
				if (e.isEntityUndead()) e.attackEntityFrom(DamageSource.MAGIC, a);
				else if (cap.hasSelectiveFocus() && (e instanceof IMob)) return;
				else e.heal(a);
			}
			cap.addExp(20, this);
		}

	}

	private void castAsMass(EntityLivingBase target, int dmg, IAoVCapability cap) {
		int range = (int) (getMaxDistance() * 2);
		ParticleHelper.spawnParticleMesh(ParticleHelper.Type.BURST, target.world, target.getPositionVector(), range);
		List<EntityLivingBase> list = target.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(target.getPosition().add(-range, -range, -range), target.getPosition().add(range, range, range)));
		for (EntityLivingBase entity : list) {
			if (entity.isEntityUndead()) entity.attackEntityFrom(DamageSource.MAGIC, dmg);
			else if (cap.hasSelectiveFocus() && (entity instanceof IMob)) continue;
			else entity.heal(dmg);
			cap.addExp(20, this);
		}
	}

}
