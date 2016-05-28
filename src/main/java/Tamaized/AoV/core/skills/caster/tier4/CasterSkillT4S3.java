package Tamaized.AoV.core.skills.caster.tier4;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S3;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillT4S3 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/CasterT4S3.png");

	public CasterSkillT4S3() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillT3S3.getUnlocalizedName()), 1, 0, 12, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Spell Power IV",
				ChatFormatting.RED+"Requires: 12 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Spell Power III",
				"",
				ChatFormatting.GREEN+"+25 Spell Power"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(0, 25, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "CasterSkillT4S3";
	}

}
