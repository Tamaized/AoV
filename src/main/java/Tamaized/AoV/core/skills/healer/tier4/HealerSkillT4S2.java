package Tamaized.AoV.core.skills.healer.tier4;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S2;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillT4S2 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/HealerT4S2.png");

	public HealerSkillT4S2() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillT3S2.getUnlocalizedName()), 1, 0, 12, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Charges II",
				ChatFormatting.RED+"Requires: 12 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Charges I",
				"",
				ChatFormatting.GREEN+"+2 Charges"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(2, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "HealerSkillT4S2";
	}

}
