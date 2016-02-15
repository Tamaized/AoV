package Tamaized.AoV.core.skills.healer.cores;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Cores.Burst;
import Tamaized.AoV.core.skills.AoVSkill;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillCore3 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/test.png");

	public HealerSkillCore3() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillCore2.getUnlocalizedName()), 1, 6, 0, false,
				new AbilityBase[]{
					AbilityBase.fromName(Burst.getStaticName())
				},
				ChatFormatting.AQUA+"Healer Core 3",
				ChatFormatting.RED+"Requires: Healer Core 2",
				ChatFormatting.RED+"Requires: Level 6",
				"",
				ChatFormatting.GREEN+"+10 Spell Power",
				ChatFormatting.GREEN+"+10 Divine Power",
				"",
				ChatFormatting.YELLOW+"Added Spell: Positive Energy Burst"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(10, 10, 0, 0);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "HealerSkillCore3";
	}

}
