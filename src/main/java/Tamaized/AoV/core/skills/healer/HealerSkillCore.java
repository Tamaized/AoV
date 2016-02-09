package Tamaized.AoV.core.skills.healer;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.CureLightWounds;
import Tamaized.AoV.core.skills.AoVSkill;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillCore extends AoVSkill{

	public HealerSkillCore() {
		super("HealerSkillCore", null, 1, true,
				new AbilityBase[]{
					AbilityBase.fromName(CureLightWounds.getStaticName())
				},
				ChatFormatting.AQUA+"Class Core: Healer",
				ChatFormatting.RED+"Obtaining this Skill", 
				ChatFormatting.RED+"prevents you from taking",
				ChatFormatting.RED+"skills from any other class!",
				"",
				ChatFormatting.GREEN+"+10 Divine Power",
				"",
				ChatFormatting.YELLOW+"Added Spell: Cure Light Wounds"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(10, 0, 0, 0);
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid+":textures/skills/HealerCore.png");
	}

}
