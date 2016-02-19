package Tamaized.AoV.core.skills.caster.tier4;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.Heal;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.caster.tier3.CasterSkillT3S1;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillT4S1 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/CasterT4S1.png");

	public CasterSkillT4S1() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillT3S1.getUnlocalizedName()), 1, 0, 12, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Implosion",
				ChatFormatting.RED+"Requires: 12 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Destruction",
				"",
				ChatFormatting.YELLOW+"Added Spell: Implosion"
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
		return "CasterSkillT4S1";
	}

}
