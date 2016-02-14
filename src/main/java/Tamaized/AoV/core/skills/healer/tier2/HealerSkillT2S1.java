package Tamaized.AoV.core.skills.healer.tier2;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.CureSeriousWounds;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.healer.tier1.HealerSkillT1S1;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillT2S1 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/HealerT2S1.png");

	public HealerSkillT2S1() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillT1S1.getUnlocalizedName()), 1, 0, 4, false,
				new AbilityBase[]{
					AbilityBase.fromName(CureSeriousWounds.getStaticName())
				},
				ChatFormatting.AQUA+"Cure Serious Wounds",
				ChatFormatting.RED+"Requires: 4 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Cure Moderate Wounds",
				"",
				ChatFormatting.YELLOW+"Added Spell: Cure Serious Wounds"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(0, 0, 0, 0);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "HealerSkillT2S1";
	}

}
