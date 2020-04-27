package tamaized.aov.common.core.abilities.druid;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.registry.AoVPotions;

import javax.annotation.Nullable;
import java.util.Objects;

public class Polymorph extends AbilityBase {

	private final ResourceLocation icon;
	private String name;
	private IPolymorphCapability.Morph type;

	public Polymorph(String name, IPolymorphCapability.Morph type) {
		super(

				new TranslationTextComponent((name = "aov.spells.polymorph.".concat(name)).concat(".name")),

				new TranslationTextComponent(""),

				new TranslationTextComponent(name.concat(".desc"))

		);
		this.name = name.concat(".name");
		this.type = type;
		icon = new ResourceLocation(AoV.MODID, "textures/spells/polymorph" + type.name().toLowerCase() + ".png");
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return I18n.format(name);
	}

	@Override
	public int getMaxCharges() {
		return -1;
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
	public int getCoolDown() {
		return type == IPolymorphCapability.Morph.ArchAngel ? 600 : 60;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public boolean shouldDisable(@Nullable PlayerEntity caster, IAoVCapability cap) {
		return false;
	}

	@Override
	public boolean isCastOnTarget(PlayerEntity caster, IAoVCapability cap, LivingEntity target) {
		return false;
	}

	@Override
	public boolean canUseOnCooldown(IAoVCapability cap, PlayerEntity caster) {
		IPolymorphCapability poly = CapabilityList.getCap(caster, CapabilityList.POLYMORPH);
		return poly != null && poly.getMorph() == type && type != IPolymorphCapability.Morph.ArchAngel;
	}

	@Override
	public void onCooldownCast(Ability ability, PlayerEntity caster, LivingEntity target, int cooldown) {
		IPolymorphCapability cap = CapabilityList.getCap(caster, CapabilityList.POLYMORPH);
		if (cap != null && cap.getMorph() == type)
			cap.morph(null);

	}

	@Override
	public boolean cast(Ability ability, PlayerEntity caster, LivingEntity target) {
		boolean cooldown = true;
		IPolymorphCapability cap = CapabilityList.getCap(caster, CapabilityList.POLYMORPH);
		if (cap != null) {
			if (cap.getMorph() != type || type == IPolymorphCapability.Morph.ArchAngel) {
				cap.morph(type);
				if (type == IPolymorphCapability.Morph.ArchAngel)
					caster.addPotionEffect(new EffectInstance(Objects.requireNonNull(AoVPotions.slowFall.get()), 120 * 20));
			} else {
				cap.morph(null);
				cooldown = false;
			}
			IAoVCapability aov = CapabilityList.getCap(caster, CapabilityList.AOV);
			if (aov != null)
				aov.markDirty();
		}
		float pitch = type == IPolymorphCapability.Morph.ArchAngel ?

				caster.getRNG().nextFloat() * 0.20F + 0.95F :

				caster.getRNG().nextFloat() * 0.75F + 0.25F;
		caster.world.playSound(null, caster.getPosX(), caster.getPosY(), caster.getPosZ(), type.sound, SoundCategory.PLAYERS, type == IPolymorphCapability.Morph.ArchAngel ? 1F : 0.5F, pitch);
		return cooldown;
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
}
