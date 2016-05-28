package Tamaized.AoV.core.skills.caster.cores;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.CureLightWounds;
import Tamaized.AoV.core.skills.AoVSkill;

import com.mojang.realmsclient.gui.ChatFormatting;

public class CasterSkillCapStone extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/test.png");

	public CasterSkillCapStone() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(CasterSkillCore4.getUnlocalizedName()), 1, 15, 0, false,
				new AbilityBase[]{
					
				},
				ChatFormatting.AQUA+"Capstone: Caster",
				ChatFormatting.RED+"Requires: Caster Core 4",
				ChatFormatting.RED+"Requires: Level 15",
				"",
				ChatFormatting.GREEN+"+50 Spell Power",
				ChatFormatting.GREEN+"+4 Charges"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(4, 50, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "CasterSkillCapStone";
	}

}
