package Tamaized.AoV.core.abilities.defender;

import java.util.List;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.helper.ParticleHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;

public class Aid extends AbilityBase {

	private final static String name = "Aid";
	private final static int charges = 5;
	private final static double range = 3;

	public Aid() {
		super(

				TextFormatting.YELLOW + name,

				"",

				TextFormatting.AQUA + "Charges: " + charges,

				TextFormatting.AQUA + "Range: " + range,

				"",

				TextFormatting.DARK_PURPLE + "Grants absorption and a +5 bonus to dodge."

		);
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

	protected int getParticleColor() {
		return 0xFFFFFFFF;
	}

	@Override
	public void cast(Ability ability, EntityPlayer player, EntityLivingBase e) {
		if (player.world.isRemote) {
			if (e != null) {
				sendPacketTypeTarget(ability, e.getEntityId());
			} else {
				sendPacketTypeSelf(ability);
			}
		} else {
			IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
			if (cap == null) return;
			if (cap.getInvokeMass()) castAsMass(player, cap);
			else if (e == null) {
				player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 20 * 90));
				player.addPotionEffect(new PotionEffect(AoV.potions.aid, 20 * 90));
			} else {
				if (cap.hasSelectiveFocus() && (e instanceof IMob)) return;
				else {
					e.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 20 * 90));
					e.addPotionEffect(new PotionEffect(AoV.potions.aid, 20 * 90));
				}
			}
			cap.addExp(20, this);
		}

	}

	private void castAsMass(EntityLivingBase target, IAoVCapability cap) {
		int range = (int) (getMaxDistance() * 2);
		ParticleHelper.spawnParticleMesh(ParticleHelper.Type.BURST, target.world, target.getPositionVector(), range, getParticleColor());
		List<EntityLivingBase> list = target.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(target.getPosition().add(-range, -range, -range), target.getPosition().add(range, range, range)));
		for (EntityLivingBase entity : list) {
			if (cap.hasSelectiveFocus() && (entity instanceof IMob)) continue;
			entity.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 20 * 90));
			entity.addPotionEffect(new PotionEffect(AoV.potions.aid, 20 * 90));
			cap.addExp(20, this);
		}
	}

	@Override
	public int getChargeCost() {
		return 1;
	}

	@Override
	public int getCoolDown() {
		return 12;
	}

	@Override
	public ResourceLocation getIcon() {
		return null;
	}
}
