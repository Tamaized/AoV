package Tamaized.AoV.core.skills.healer.cores;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.CureLightWounds;
import Tamaized.AoV.core.skills.AoVSkill;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillCore2 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/test.png");

	public HealerSkillCore2() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillCore1.getUnlocalizedName()), 1, 3, 0, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Healer Core 2",
				ChatFormatting.RED+"Requires: Class Core Healer",
				ChatFormatting.RED+"Requires: Level 3",
				"",
				ChatFormatting.GREEN+"+10 Spell Power"
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
		return "HealerSkillCore2";
	}

}
