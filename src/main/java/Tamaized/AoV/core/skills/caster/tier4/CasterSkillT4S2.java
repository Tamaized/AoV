package Tamaized.AoV.core.skills.caster.tier4;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S2;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillT4S2 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/CasterT4S2.png");

	public CasterSkillT4S2() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillT3S2.getUnlocalizedName()), 1, 0, 12, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Spell Cost Reduction II",
				ChatFormatting.RED+"Requires: 12 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Spell Cost Reduction I",
				"",
				ChatFormatting.GREEN+"+10% Spell Cost Reduction"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(0, 0, 10, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "CasterSkillT4S2";
	}

}
