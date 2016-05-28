package Tamaized.AoV.core.skills.healer.tier3;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Healing.CureCriticalWounds;
import Tamaized.AoV.core.abilities.universal.InvokeMass;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.core.skills.healer.cores.HealerSkillCore1;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillT3S5 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/HealerT3S5.png");

	public HealerSkillT3S5() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillCore1.getUnlocalizedName()), 1, 0, 8, false,
				new AbilityBase[]{
					AbilityBase.fromName(InvokeMass.getStaticName())
				},
				ChatFormatting.AQUA+"Invoke Mass",
				ChatFormatting.RED+"Requires: 8 Points Spent in Tree",
				"",
				ChatFormatting.YELLOW+"Added Ability: Invoke Mass",
				ChatFormatting.YELLOW+" While this toggle is active",
				ChatFormatting.YELLOW+" your spells cost double but",
				ChatFormatting.YELLOW+" can be cast as an AoE (Area of Effect)"
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
		return "HealerSkillT3S5";
	}

}
