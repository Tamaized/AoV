package tamaized.aov.common.core.abilities.astro;

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
import tamaized.aov.common.entity.EntityGravity;
import tamaized.aov.registry.SoundEvents;

public class Gravity extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.MODID, "textures/spells/gravity.png");

	private static final int charges = 8;
	private static final int distance = 25;
	private static final int damage = 5;

	public Gravity() {
		super(

				new TranslationTextComponent(getStaticName()),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.global.charges", charges),

				new TranslationTextComponent("aov.spells.global.range", distance),

				new TranslationTextComponent("aov.spells.global.damage", damage),

				new TranslationTextComponent(""),

				new TranslationTextComponent("aov.spells.gravity.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.gravity.name";
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
		return 6;
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
		IAoVCapability aov = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (!caster.world.isRemote && aov != null && (target == null || IAoVCapability.selectiveTarget(caster, aov, target))) {
			int dmg = (int) (damage * (1f + (aov.getSpellPower() / 100f)));
			EntityGravity spell = target == null ? new EntityGravity(caster.world, caster, dmg, distance) : new EntityGravity(caster.world, caster, dmg, target.getPositionVector());
			caster.world.spawnEntity(spell);
			SoundEvents.playMovingSoundOnServer(SoundEvents.gravity, spell, 2F, 1F);
			return true;
		}
		return false;
	}

}
