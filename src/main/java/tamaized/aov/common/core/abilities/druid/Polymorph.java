package tamaized.aov.common.core.abilities.druid;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.SkillIcons;

import javax.annotation.Nullable;

public class Polymorph extends AbilityBase {

	private String name;
	private IPolymorphCapability.Morph type;

	public Polymorph(String name, IPolymorphCapability.Morph type) {
		super(

				new TextComponentTranslation((name = "aov.spells.polymorph.".concat(name)).concat(".name")),

				new TextComponentTranslation(""),

				new TextComponentTranslation(name.concat(".desc"))

		);
		this.name = name.concat(".name");
		this.type = type;
	}

	@Override
	@SideOnly(Side.CLIENT)
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
		return 60;
	}

	@Override
	public boolean usesInvoke() {
		return false;
	}

	@Override
	public boolean shouldDisable(@Nullable EntityPlayer caster, IAoVCapability cap) {
		return false;
	}

	@Override
	public boolean isCastOnTarget(EntityPlayer caster, IAoVCapability cap, EntityLivingBase target) {
		return false;
	}

	@Override
	public boolean cast(Ability ability, EntityPlayer caster, EntityLivingBase target) {
		if (caster.hasCapability(CapabilityList.POLYMORPH, null)) {
			IPolymorphCapability cap = caster.getCapability(CapabilityList.POLYMORPH, null);
			if (cap != null) {
				if (cap.getMorph() != type)
					cap.morph(type);
				else
					cap.morph(null);
			}
		}
		return true;
	}

	@Override
	public ResourceLocation getIcon() {
		return SkillIcons.vitality;
	}
}
