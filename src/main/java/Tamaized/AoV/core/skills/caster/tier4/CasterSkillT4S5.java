package Tamaized.AoV.core.skills.caster.tier4;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore1;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillT4S5 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/CasterT4S5.png");

	public CasterSkillT4S5() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillCore1.getUnlocalizedName()), 1, 0, 12, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Selective Focus",
				ChatFormatting.RED+"Requires: 12 Points Spent in Tree",
				"",
				ChatFormatting.DARK_PURPLE+"Your Spells no longer",
				ChatFormatting.DARK_PURPLE+"affect Monsters if they",
				ChatFormatting.DARK_PURPLE+"benefit from the Spell",
				ChatFormatting.DARK_PURPLE+"or Friendly Players/Creatures",
				ChatFormatting.DARK_PURPLE+"if they would be harmed by the spell."
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(0, 0, 0, 0, true);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "CasterSkillT4S5";
	}

}
