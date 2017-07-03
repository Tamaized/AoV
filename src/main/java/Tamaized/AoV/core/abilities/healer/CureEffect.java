package Tamaized.AoV.core.abilities.healer;

import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.helper.ParticleHelper;
import Tamaized.AoV.sound.SoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public abstract class CureEffect extends AbilityBase {

	private final String name;
	private final int charges;
	private final double range;
	private final Potion effect;

	public CureEffect(String n, int c, double r, Potion effect) {
		super(

				TextFormatting.YELLOW + n,

				"",

				TextFormatting.AQUA + "Charges: " + c,

				TextFormatting.AQUA + "Range: " + r,

				"",

				TextFormatting.DARK_PURPLE + "Heals yourself or an entity if",

				TextFormatting.DARK_PURPLE + "your crosshair is over the entity."

		);
		name = n;
		charges = c;
		range = r;
		this.effect = effect;
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

	protected abstract int getParticleColor();

	@Override
	public void cast(Ability ability, EntityPlayer player, EntityLivingBase e) {
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap == null) return;
		if (cap.getInvokeMass()) castAsMass(player, cap);
		else if (e == null) {
			player.removePotionEffect(effect);
			SoundEvents.playMovingSoundOnServer(SoundEvents.restore, player);
		} else {
			if (cap.hasSelectiveFocus() && (e instanceof IMob)) return;
			else {
				e.removePotionEffect(effect);
				SoundEvents.playMovingSoundOnServer(SoundEvents.restore, e);
			}
		}
		cap.addExp(player, 20, this);

	}

	private void castAsMass(EntityLivingBase target, IAoVCapability cap) {
		int range = (int) (getMaxDistance() * 2);
		ParticleHelper.spawnParticleMesh(ParticleHelper.Type.BURST, target.world, target.getPositionVector(), range, getParticleColor());
		List<EntityLivingBase> list = target.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(target.getPosition().add(-range, -range, -range), target.getPosition().add(range, range, range)));
		for (EntityLivingBase entity : list) {
			if (cap.hasSelectiveFocus() && (entity instanceof IMob)) continue;
			else {
				entity.removePotionEffect(effect);
				SoundEvents.playMovingSoundOnServer(SoundEvents.restore, entity);
				cap.addExp(target, 20, this);
			}
		}
	}

}
