package Tamaized.AoV.core.skills.caster.tier2;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.CureSeriousWounds;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.caster.tier1.CasterSkillT1S1;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillT2S1 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/CasterT2S1.png");

	public CasterSkillT2S1() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillT1S1.getUnlocalizedName()), 1, 0, 4, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Slay Living",
				ChatFormatting.RED+"Requires: 4 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Searing Light",
				"",
				ChatFormatting.YELLOW+"Added Spell: Slay Living"
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
		return "CasterSkillT2S1";
	}

}
