package Tamaized.AoV.core.skills.caster.cores;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.CureLightWounds;
import Tamaized.AoV.core.skills.AoVSkill;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillCore4 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/test.png");

	public CasterSkillCore4() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillCore3.getUnlocalizedName()), 1, 12, 0, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Caster Core 4",
				ChatFormatting.RED+"Requires: Healer Core 3",
				ChatFormatting.RED+"Requires: Level 12",
				"",
				ChatFormatting.GREEN+"+15 Spell Power",
				ChatFormatting.GREEN+"+15 Divine Power",
				"",
				ChatFormatting.YELLOW+"Added Spell: Blade Barrier"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(15, 15, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "CasterSkillCore4";
	}

}
