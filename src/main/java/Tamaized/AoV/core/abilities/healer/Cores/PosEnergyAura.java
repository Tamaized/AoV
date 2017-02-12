package Tamaized.AoV.core.abilities.healer.Cores;

import java.util.List;

import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.AuraBase;
import Tamaized.AoV.core.abilities.IAura;
import Tamaized.AoV.helper.ParticleHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;

public class PosEnergyAura extends AbilityBase implements IAura {

	private final static int charges = 6;
	private final static int range = 10;
	private final static int dmg = 2;
	private final static int life = 45;

	public PosEnergyAura() {
		super(

				TextFormatting.YELLOW + getStaticName(),

				"",

				TextFormatting.AQUA + "Charges: " + charges,

				TextFormatting.AQUA + "Range: " + range,

				TextFormatting.AQUA + "Base Healing: " + dmg,

				TextFormatting.AQUA + "Lasts: " + life + " Seconds",

				"",

				TextFormatting.DARK_PURPLE + "Creates an aura around yourself",

				TextFormatting.DARK_PURPLE + "to heal you and everything",

				TextFormatting.DARK_PURPLE + "around you for a period of time."

		);
	}

	@Override
	public void cast(Ability ability, EntityPlayer player, EntityLivingBase e) {
		if (player.world.isRemote) {
			sendPacketTypeSelf(ability);
		} else {
			IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
			if (cap == null) return;
			cap.addAura(this);
			cap.addExp(20, this);
		}
	}

	@Override
	public ResourceLocation getIcon() {
		return null;
	}

	@Override
	public String getName() {
		return getStaticName();
	}

	public static String getStaticName() {
		return "Positive Energy Aura";
	}

	@Override
	public AuraBase createAura() {
		return new PosAura();
	}

	@Override
	public int getLife() {
		return life;
	}

	@Override
	public int getCoolDown() {
		return 60;
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
		return range;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	private class PosAura extends AuraBase {

		private int currLife = 0;

		@Override
		public void update(IAoVCapability cap, EntityPlayer player) {
			ParticleHelper.spawnParticleMesh(ParticleHelper.Type.BURST, player.world, player.getPositionVector(), range);
			int a = (int) (dmg * (1f + (cap.getSpellPower() / 100f)));
			List<EntityLivingBase> list = player.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(player.getPosition().add(-range, -range, -range), player.getPosition().add(range, range, range)));
			for (EntityLivingBase entity : list) {
				if (entity.isEntityUndead()) entity.attackEntityFrom(DamageSource.MAGIC, a);
				else if (cap.hasSelectiveFocus() && !(entity instanceof IMob)) continue;
				else entity.heal(a);
			}
			currLife++;
		}

		@Override
		public int getCurrentLife() {
			return currLife;
		}

	}

}
