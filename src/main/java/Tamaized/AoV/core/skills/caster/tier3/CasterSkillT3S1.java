package Tamaized.AoV.core.skills.caster.tier3;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.CureCriticalWounds;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.caster.tier2.CasterSkillT2S1;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillT3S1 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/CasterT3S1.png");

	public CasterSkillT3S1() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillT2S1.getUnlocalizedName()), 1, 0, 8, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Destruction",
				ChatFormatting.RED+"Requires: 8 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Slay Living",
				"",
				ChatFormatting.YELLOW+"Added Spell: Destruction"
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
		return "CasterSkillT3S1";
	}

}
