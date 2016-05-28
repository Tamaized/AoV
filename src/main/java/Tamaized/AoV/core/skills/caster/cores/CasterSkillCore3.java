package Tamaized.AoV.core.skills.caster.cores;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillCore3 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/test.png");

	public CasterSkillCore3() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillCore2.getUnlocalizedName()), 1, 6, 0, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Caster Core 3",
				ChatFormatting.RED+"Requires: Caster Core 2",
				ChatFormatting.RED+"Requires: Level 6",
				"",
				ChatFormatting.GREEN+"+15 Spell Power",
				ChatFormatting.GREEN+"+1 Charge",
				"",
				ChatFormatting.YELLOW+"Added Spell: Flame Strike"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(1, 15, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "CasterSkillCore3";
	}

}
