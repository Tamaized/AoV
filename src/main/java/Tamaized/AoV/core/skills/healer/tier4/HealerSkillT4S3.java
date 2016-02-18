package Tamaized.AoV.core.skills.healer.tier4;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S3;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillT4S3 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/HealerT4S3.png");

	public HealerSkillT4S3() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillT3S3.getUnlocalizedName()), 1, 0, 12, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Spell Power IV",
				ChatFormatting.RED+"Requires: 12 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Spell Power III",
				"",
				ChatFormatting.GREEN+"+20 Spell Power"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(0, 20, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "HealerSkillT4S3";
	}

}
