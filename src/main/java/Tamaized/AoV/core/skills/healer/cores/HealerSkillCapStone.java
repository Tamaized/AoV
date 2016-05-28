package Tamaized.AoV.core.skills.healer.cores;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.CureLightWounds;
import Tamaized.AoV.core.skills.AoVSkill;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillCapStone extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/test.png");

	public HealerSkillCapStone() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillCore4.getUnlocalizedName()), 1, 15, 0, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Capstone: Healer",
				ChatFormatting.RED+"Requires: Healer Core 4",
				ChatFormatting.RED+"Requires: Level 15",
				"",
				ChatFormatting.GREEN+"+50 Spell Power",
				ChatFormatting.GREEN+"+3 Charges"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(3, 50, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "HealerSkillCapStone";
	}

}
