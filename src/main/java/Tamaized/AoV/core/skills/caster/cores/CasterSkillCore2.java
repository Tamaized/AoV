package Tamaized.AoV.core.skills.caster.cores;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.CureLightWounds;
import Tamaized.AoV.core.skills.AoVSkill;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillCore2 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/test.png");

	public CasterSkillCore2() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillCore1.getUnlocalizedName()), 1, 3, 0, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Caster Core 2",
				ChatFormatting.RED+"Requires: Class Core Caster",
				ChatFormatting.RED+"Requires: Level 3",
				"",
				ChatFormatting.GREEN+"+15 Spell Power"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(0, 15, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "CasterSkillCore2";
	}

}
