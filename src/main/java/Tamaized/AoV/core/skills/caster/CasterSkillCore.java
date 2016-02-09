package Tamaized.AoV.core.skills.caster;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.caster.NimbusRay;
import Tamaized.AoV.core.abilities.healer.CureLightWounds;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.AoVSkill.Buffs;

public class CasterSkillCore extends AoVSkill {

	public CasterSkillCore() {
		super("CasterSkillCore", null, 1, true,
				new AbilityBase[]{
					AbilityBase.fromName(NimbusRay.getStaticName())
				},
				ChatFormatting.AQUA+"Class Core: Offensive Caster",
				ChatFormatting.RED+"Obtaining this Skill", 
				ChatFormatting.RED+"prevents you from taking",
				ChatFormatting.RED+"skills from any other class!",
				"",
				ChatFormatting.GREEN+"+15 Divine Power",
				ChatFormatting.GREEN+"+10 Spell Power",
				"",
				ChatFormatting.YELLOW+"Added Spell: Nimbus Ray"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(15, 10, 0, 0);
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid+":textures/skills/test.png");
	}

}