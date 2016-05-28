package Tamaized.AoV.core.skills.caster.tier3;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.caster.cores.CasterSkillCore1;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillT3S4 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/CasterT3S4.png");

	public CasterSkillT3S4() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillCore1.getUnlocalizedName()), 1, 0, 8, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"null",
				ChatFormatting.RED+"Requires: 8 Points Spent in Tree"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "CasterSkillT3S4";
	}

}
