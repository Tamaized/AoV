package tamaized.aov.common.core.abilities.favoredsoul;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.ProjectileFlameStrike;
import tamaized.aov.common.helper.UtilHelper;
import tamaized.aov.registry.SoundEvents;

public class FlameStrike extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.MODID, "textures/spells/flamestrike.png");

	private static final int damage = 6;
	private static final int charges = 5;
	private static final int distance = 45;

	public FlameStrike() {
		super(

				new TranslationTextComponent(getStaticName()),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", charges),

				new TranslationTextComponent("aov.spells.global.range", distance),

				new TranslationTextComponent("aov.spells.global.damage", damage),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.flamestrike.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.flamestrike.name";
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return I18n.format(getStaticName());
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
	public boolean isCastOnTarget(PlayerEntity caster, IAoVCapability cap, LivingEntity target) {
		return IAoVCapability.selectiveTarget(caster, cap, target);
	}

	@Override
	public boolean cast(Ability ability, PlayerEntity caster, LivingEntity target) {
		IAoVCapability cap = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (cap == null)
			return false;
		int a = (int) (damage * (1f + (cap.getSpellPower() / 100f)));
		ProjectileFlameStrike strike = new ProjectileFlameStrike(caster.world, caster, a);
		Vec3d pos = UtilHelper.getSpellLocation(caster, distance, target);
		strike.setPosition(pos.x, pos.y + 15, pos.z);
		caster.world.addEntity(strike);
		strike.world.playSound(null, strike.posX, strike.posY - 20, strike.posZ, SoundEvents.firestrike, SoundCategory.NEUTRAL, 1.0F, 1.0F);
		return true;
	}

}
