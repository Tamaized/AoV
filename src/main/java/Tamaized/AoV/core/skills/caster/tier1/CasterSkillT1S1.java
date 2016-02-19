package Tamaized.AoV.core.skills.caster.tier1;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore1;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillT1S1 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/CasterT1S1.png");

	public CasterSkillT1S1() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillCore1.getUnlocalizedName()), 1, 0, 1, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Searing Light",
				ChatFormatting.RED+"Requires: 1 Point Spent in Tree",
				"",
				ChatFormatting.YELLOW+"Added Spell: Searing Light"
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
		return "CasterSkillT1S1";
	}

}
