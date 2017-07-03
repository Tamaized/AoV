package tamaized.aov.common.core.abilities.defender;

import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.registry.SoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class Zeal extends AbilityBase {

	private final static String name = "Zeal";
	private final static int charges = 5;

	public Zeal() {
		super(

				TextFormatting.YELLOW + name,

				"",

				TextFormatting.AQUA + "Charges: " + charges,

				"",

				TextFormatting.DARK_PURPLE + "Grants +25 DoubleStrike",

				TextFormatting.DARK_PURPLE + " of yourself only."

		);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public double getMaxDistance() {
		return 1;
	}

	@Override
	public int getMaxCharges() {
		return charges;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	protected int getParticleColor() {
		return 0xFFFFFFFF;
	}

	@Override
	public void cast(Ability ability, EntityPlayer player, EntityLivingBase e) {
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap == null)
			return;
		addPotionEffects(player);
		SoundEvents.playMovingSoundOnServer(SoundEvents.cast_2, player);
		cap.addExp(player, 20, this);
	}

	private void addPotionEffects(EntityLivingBase entity) {
		entity.addPotionEffect(new PotionEffect(AoV.potions.zeal, 20 * (int) (60F * 2.5F)));
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
		return new ResourceLocation(AoV.modid, "textures/spells/zeal.png");
	}
}
