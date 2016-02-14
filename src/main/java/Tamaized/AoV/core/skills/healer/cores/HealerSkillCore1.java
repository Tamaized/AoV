package Tamaized.AoV.core.skills.healer.cores;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.CureLightWounds;
import Tamaized.AoV.core.skills.AoVSkill;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillCore1 extends AoVSkill{

	public HealerSkillCore1() {
		super(getUnlocalizedName(), null, 1, 0, 0, true,
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
	
	public static String getUnlocalizedName(){
		return "HealerSkillCore1";
	}

}
