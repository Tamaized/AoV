package Tamaized.AoV.core.skills.healer.tier2;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S2;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillT2S2 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/HealerT2S2.png");

	public HealerSkillT2S2() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillT1S2.getUnlocalizedName()), 1, 0, 4, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Cure Wither",
				ChatFormatting.RED+"Requires: 4 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Cure Poison",
				"",
				ChatFormatting.YELLOW+"Added Spell: Cure Wither"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "HealerSkillT2S2";
	}

}
