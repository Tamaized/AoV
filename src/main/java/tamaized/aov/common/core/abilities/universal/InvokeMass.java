package tamaized.aov.common.core.abilities.universal;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;

public class InvokeMass extends AbilityBase {

	private final static int charges = -1;

	public InvokeMass() {
		super(

				new TranslationTextComponent(getStaticName()),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.invokemass.desc"),

				new TranslationTextComponent("aov.spells.global.double")

		);
	}

	public static String getStaticName() {
		return "aov.spells.invokemass.name";
	}

	@Override
	public boolean cast(Ability ability, PlayerEntity player, LivingEntity e) {
		IAoVCapability cap = CapabilityList.getCap(player, CapabilityList.AOV);
		if (cap == null)
			return false;
		cap.toggleInvokeMass();
		return true;
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.MODID, "textures/spells/invokemass.png");
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return I18n.format(getStaticName());
	}

	@Override
	public int getCoolDown() {
		return 1;
	}

	@Override
	public int getMaxCharges() {
		return charges;
	}

	@Override
	public int getChargeCost() {
		return 0;
	}

	@Override
	public double getMaxDistance() {
		return 0;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public boolean isCastOnTarget(PlayerEntity caster, IAoVCapability cap, LivingEntity target) {
		return false;
	}

}
