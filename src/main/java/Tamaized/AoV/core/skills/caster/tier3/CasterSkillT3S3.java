package Tamaized.AoV.core.skills.caster.tier3;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S3;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillT3S3 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/CasterT3S3.png");

	public CasterSkillT3S3() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillT2S3.getUnlocalizedName()), 1, 0, 8, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Spell Power III",
				ChatFormatting.RED+"Requires: 8 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Spell Power II",
				"",
				ChatFormatting.GREEN+"+15 Spell Power"
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
		return "CasterSkillT3S3";
	}

}
