package Tamaized.AoV.core.abilities.caster;

import java.util.HashSet;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.entity.projectile.caster.ProjectileFlameStrike;
import Tamaized.TamModized.helper.RayTraceHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;

public class FlameStrike extends AbilityBase {

	private static final int damage = 6;
	private static final int charges = 5;
	private static final int distance = 45;

	public FlameStrike() {
		super(

				TextFormatting.YELLOW + getStaticName(),

				"",

				TextFormatting.AQUA + "Charges: " + charges,

				TextFormatting.AQUA + "Range: " + distance,

				TextFormatting.AQUA + "Base Damage: " + damage,

				"",

				TextFormatting.DARK_PURPLE + "Summons a ball of fire",

				TextFormatting.DARK_PURPLE + "to strike the ground",

				TextFormatting.DARK_PURPLE + "dealing damage to entities",

				TextFormatting.DARK_PURPLE + "around and setting them on fire"

		);
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid + ":textures/spells/test.png");
	}

	@Override
	public String getName() {
		return getStaticName();
	}

	public static String getStaticName() {
		return "Flame Strike";
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
		ProjectileFlameStrike strike = new ProjectileFlameStrike(caster.world, caster, a);
		HashSet<Entity> exclude = new HashSet<Entity>();
		exclude.add(caster);
		RayTraceResult result = RayTraceHelper.tracePath(caster.world, caster, distance, 1, exclude);
		if (result != null && result.typeOfHit != null) {
			switch (result.typeOfHit) {
				case BLOCK:
					BlockPos pos = result.getBlockPos();
					strike.setPosition(pos.getX(), pos.getY() + 20, pos.getZ());
					break;
				case ENTITY:
					pos = result.entityHit.getPosition();
					strike.setPosition(pos.getX(), pos.getY() + 20, pos.getZ());
					break;
				default:
					pos = caster.getPosition();
					strike.setPosition(pos.getX(), pos.getY() + 20, pos.getZ());
					break;
			}
		}
		caster.world.spawnEntity(strike);
	}

}
