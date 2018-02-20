package tamaized.aov.common.core.abilities.astro;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.entity.EntitySpellVanillaParticles;
import tamaized.aov.registry.SoundEvents;

public class EssentialDignity extends AbilityBase {

	private static final ResourceLocation icon = new ResourceLocation(AoV.modid, "textures/spells/essentialdignity.png");

	private static final int charges = -1;
	private static final int distance = 10;

	public EssentialDignity() {
		super(

				new TextComponentTranslation(getStaticName()),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.global.charges", "aov.gui.infinite"),

				new TextComponentTranslation("aov.spells.global.range", distance),

				new TextComponentTranslation(""),

				new TextComponentTranslation("aov.spells.essentialdignity.desc")

		);
	}

	public static String getStaticName() {
		return "aov.spells.essentialdignity.name";
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getName() {
		return I18n.format(getStaticName());
	}

	@Override
	public int getCoolDown() {
		return 40;
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
		return distance;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		IAoVCapability cap = caster.hasCapability(CapabilityList.AOV, null) ? caster.getCapability(CapabilityList.AOV, null) : null;
		if (cap == null)
			return false;
		EntityLivingBase entity = target != null && IAoVCapability.selectiveTarget(cap, target) ? target : caster;
		entity.heal(entity.getMaxHealth());
		SoundEvents.playMovingSoundOnServer(SoundEvents.essentialdignity, entity);
		entity.world.spawnEntity(new EntitySpellVanillaParticles(entity.world, entity, EnumParticleTypes.END_ROD, 10));
		cap.addExp(caster, 12, this);
		return true;
	}

}
