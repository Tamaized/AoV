package Tamaized.AoV.core.skills.healer.tier3;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.CureCriticalWounds;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.healer.tier2.HealerSkillT2S1;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillT3S1 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/HealerT3S1.png");

	public HealerSkillT3S1() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillT2S1.getUnlocalizedName()), 1, 0, 8, false,
				new AbilityBase[]{
					AbilityBase.fromName(CureCriticalWounds.getStaticName())	
				},
				ChatFormatting.AQUA+"Cure Critical Wounds",
				ChatFormatting.RED+"Requires: 8 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Cure Serious Wounds",
				"",
				ChatFormatting.YELLOW+"Added Spell: Cure Critical Wounds"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(0, 0, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "HealerSkillT3S1";
	}

}
