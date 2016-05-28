package Tamaized.AoV.core.skills.caster.tier2;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S3;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillT2S3 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/CasterT2S3.png");

	public CasterSkillT2S3() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillT1S3.getUnlocalizedName()), 1, 0, 4, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Spell Power II",
				ChatFormatting.RED+"Requires: 4 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Spell Power I",
				"",
				ChatFormatting.YELLOW+"+15 Spell Power"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(0, 15, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "CasterSkillT2S3";
	}

}
