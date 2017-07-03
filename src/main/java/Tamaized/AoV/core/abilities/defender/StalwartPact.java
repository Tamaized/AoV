package Tamaized.AoV.core.abilities.defender;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.helper.ParticleHelper;
import Tamaized.AoV.sound.SoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class StalwartPact extends AbilityBase {

	private final static String name = "Stalwart Pact";
	private final static int charges = 3;
	private final static double range = 3;

	public StalwartPact() {
		super(

				TextFormatting.YELLOW + name,

				"",

				TextFormatting.AQUA + "Charges: " + charges,

				TextFormatting.AQUA + "Range: " + range,

				"",

				TextFormatting.DARK_PURPLE + "Upon reaching half or less health",

				TextFormatting.DARK_PURPLE + " you are granted with Absorption III",

				TextFormatting.DARK_PURPLE + " and Regeneration III, can be cast on",

				TextFormatting.DARK_PURPLE + " yourself or a target."

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
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap == null) return;
		if (cap.getInvokeMass()) castAsMass(player, cap);
		else if (e == null) {
			addPotionEffects(player);
		} else {
			if (cap.hasSelectiveFocus() && (e instanceof IMob)) return;
			else {
				addPotionEffects(e);
			}
		}
		SoundEvents.playMovingSoundOnServer(SoundEvents.cast_2, player);
		cap.addExp(player, 20, this);
	}

	private void addPotionEffects(EntityLivingBase entity) {
		entity.addPotionEffect(new PotionEffect(AoV.potions.stalwartPact, 20 * (60 * 5)));
	}

	private void castAsMass(EntityLivingBase target, IAoVCapability cap) {
		int range = (int) (getMaxDistance() * 2);
		ParticleHelper.spawnParticleMesh(ParticleHelper.Type.BURST, target.world, target.getPositionVector(), range, getParticleColor());
		List<EntityLivingBase> list = target.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(target.getPosition().add(-range, -range, -range), target.getPosition().add(range, range, range)));
		for (EntityLivingBase entity : list) {
			if (cap.hasSelectiveFocus() && (entity instanceof IMob)) continue;
			addPotionEffects(entity);
			cap.addExp(target, 20, this);
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
		return new ResourceLocation(AoV.modid, "textures/spells/stalwart.png");
	}
}
