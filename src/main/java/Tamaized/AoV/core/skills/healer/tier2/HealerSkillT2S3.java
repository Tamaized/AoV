package Tamaized.AoV.core.skills.healer.tier2;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S3;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillT2S3 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/HealerT2S3.png");

	public HealerSkillT2S3() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillT1S3.getUnlocalizedName()), 1, 0, 4, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Spell Power II",
				ChatFormatting.RED+"Requires: 4 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Spell Power I",
				"",
				ChatFormatting.YELLOW+"+10 Spell Power"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(0, 10, 0, 0);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "HealerSkillT2S3";
	}

}
