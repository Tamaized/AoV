package Tamaized.AoV.core.skills.healer.tier1;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.CureSeriousWounds;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore1;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillT1S2 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/HealerT1S2.png");

	public HealerSkillT1S2() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillCore1.getUnlocalizedName()), 1, 0, 1, false,
				new AbilityBase[]{
			
				},
				ChatFormatting.AQUA+"Cure Poison",
				ChatFormatting.RED+"Requires: 1 Point Spent in Tree",
				"",
				ChatFormatting.YELLOW+"Added Spell: Cure Poison"
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
		return "HealerSkillT1S2";
	}

}
