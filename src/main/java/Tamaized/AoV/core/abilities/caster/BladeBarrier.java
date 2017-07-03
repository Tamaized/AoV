package Tamaized.AoV.core.abilities.caster;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.entity.EntitySpellBladeBarrier;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class BladeBarrier extends AbilityBase {

	private static final int damage = 5;
	private static final int charges = 5;
	private static final int distance = 4;

	public BladeBarrier() {
		super(

				TextFormatting.YELLOW + getStaticName(),

				"",

				TextFormatting.AQUA + "Charges: " + charges,

				TextFormatting.AQUA + "Range: " + distance,

				TextFormatting.AQUA + "Base Damage: " + damage,

				"",

				TextFormatting.DARK_PURPLE + "Summons a ring of spinning",

				TextFormatting.DARK_PURPLE + " blades to deal damage to",

				TextFormatting.DARK_PURPLE + " entities that enter or",

				TextFormatting.DARK_PURPLE + " exit the ring."

		);
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid, "textures/spells/bladebarrier.png");
	}

	@Override
	public String getName() {
		return getStaticName();
	}

	public static String getStaticName() {
		return "Blade Barrier";
	}

	@Override
	public int getCoolDown() {
		return 8;
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
	public void cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		IAoVCapability cap = caster.getCapability(CapabilityList.AOV, null);
		if (cap == null) return;
		int a = (int) (damage * (1f + (cap.getSpellPower() / 100f)));
		caster.world.spawnEntity(new EntitySpellBladeBarrier(caster.world, caster, a, distance));
	}

}
