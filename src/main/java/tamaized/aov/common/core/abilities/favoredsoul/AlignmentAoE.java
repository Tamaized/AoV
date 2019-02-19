package tamaized.aov.common.core.abilities.favoredsoul;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntityAlignmentAoE;
import tamaized.aov.registry.SoundEvents;

import java.util.Locale;

public class AlignmentAoE extends AbilityBase {

	private static final int charges = 4;
	private static final int distance = 25;
	private static final int damage = 5;

	private final Type type;

	public AlignmentAoE(Type type) {
		super(

				new TextComponentTranslation("aov.spells." + type.name().toLowerCase(Locale.US) + ".name"),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", charges),

				new TextComponentTranslation("aov.spells.global.range", distance),

				new TextComponentTranslation("aov.spells.global.damage", damage),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells." + type.name().toLowerCase(Locale.US) + ".desc")

		);
		this.type = type;
	}

	@Override
	public ResourceLocation getIcon() {
		return type.icon;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public String getName() {
		return I18n.format("aov.spells." + type.name().toLowerCase(Locale.US) + ".name");
	}

	@Override
	public int getCoolDown() {
		return 25;
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
	public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
		return IAoVCapability.selectiveTarget(caster, cap, target);
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		if (!caster.hasCapability(CapabilityList.AOV, null))
			return false;
		IAoVCapability aov = CapabilityList.getCap(caster, CapabilityList.AOV);
		if (!caster.world.isRemote && aov != null && (target == null || IAoVCapability.selectiveTarget(caster, aov, target))) {
			int dmg = (int) (damage * (1f + (aov.getSpellPower() / 100f)));
			EntityAlignmentAoE spell = target == null ? new EntityAlignmentAoE(caster.world, type, caster, dmg, distance) : new EntityAlignmentAoE(caster.world, type, caster, dmg, target.getPositionVector());
			caster.world.spawnEntity(spell);
			SoundEvents.playMovingSoundOnServer(type.sound, spell, 2F, type.minPitch + caster.getRNG().nextFloat() * 0.5F);
			return true;
		}
		return false;
	}

	public enum Type {

		ChaosHammer(new ResourceLocation(AoV.MODID, "textures/spells/chaoshammer.png"), SoundEvents.chaosHammer, 1F),

		OrdersWrath(new ResourceLocation(AoV.MODID, "textures/spells/orderswrath.png"), SoundEvents.ordersWrath, 0.5F);

		public static final Type[] values = values();
		final ResourceLocation icon;
		final SoundEvent sound;
		final float minPitch;

		Type(ResourceLocation resourceLocation, SoundEvent sound, float pitch) {
			icon = resourceLocation;
			this.sound = sound;
			minPitch = pitch;
		}

		public static Type getTypeFromID(int id) {
			return id >= values.length || id < 0 ? null : values[id];
		}
	}

}
