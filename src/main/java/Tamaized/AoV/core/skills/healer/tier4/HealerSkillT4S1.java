package Tamaized.AoV.core.skills.healer.tier4;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.Heal;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.healer.tier3.HealerSkillT3S1;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillT4S1 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/HealerT4S1.png");

	public HealerSkillT4S1() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillT3S1.getUnlocalizedName()), 1, 0, 12, false,
				new AbilityBase[]{
					AbilityBase.fromName(Heal.getStaticName())	
				},
				ChatFormatting.AQUA+"Heal",
				ChatFormatting.RED+"Requires: 12 Points Spent in Tree",
				ChatFormatting.RED+"Requires: Cure Critical Wounds",
				"",
				ChatFormatting.YELLOW+"Added Spell: Heal"
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
		return "HealerSkillT4S1";
	}

}
