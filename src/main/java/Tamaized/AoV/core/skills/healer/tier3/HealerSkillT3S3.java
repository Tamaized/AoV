package Tamaized.AoV.core.skills.healer.tier3;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.healer.tier2.HealerSkillT2S3;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillT3S3 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/HealerT3S3.png");

	public HealerSkillT3S3() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillT2S3.getUnlocalizedName()), 1, 0, 8, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Spell Power III",
				ChatFormatting.RED+"Requires: 8 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Spell Power II",
				"",
				ChatFormatting.GREEN+"+10 Spell Power"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(0, 10, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "HealerSkillT3S3";
	}

}
