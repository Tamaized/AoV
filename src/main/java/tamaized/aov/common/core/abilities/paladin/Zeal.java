package tamaized.aov.common.core.abilities.paladin;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.registry.AoVPotions;
import tamaized.aov.registry.SoundEvents;

public class Zeal extends AbilityBase {

	private final static String name = "aov.spells.zeal.name";
	private final static int charges = 5;

	public Zeal() {
		super(

				new TranslationTextComponent(name),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", charges),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.zeal.desc")

		);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return I18n.format(name);
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

	@Override
	public boolean isCastOnTarget(PlayerEntity caster, IAoVCapability cap, LivingEntity target) {
		return false;
	}

	protected int getParticleColor() {
		return 0xFFFFFFFF;
	}

	@Override
	public boolean cast(Ability ability, PlayerEntity player, LivingEntity e) {
		IAoVCapability cap = CapabilityList.getCap(player, CapabilityList.AOV);
		if (cap == null)
			return false;
		addPotionEffects(player);
		SoundEvents.playMovingSoundOnServer(SoundEvents.cast_2, player);
		cap.addExp(player, 8, this);
		return true;
	}

	private void addPotionEffects(LivingEntity entity) {
		entity.addPotionEffect(new EffectInstance(AoVPotions.zeal, 20 * (int) (60F * 2.5F)));
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
		return new ResourceLocation(AoV.MODID, "textures/spells/zeal.png");
	}
}
