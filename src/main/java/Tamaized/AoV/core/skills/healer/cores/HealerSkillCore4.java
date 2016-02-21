package Tamaized.AoV.core.skills.healer.cores;

import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.Cores.PosEnergyAura;
import Tamaized.AoV.core.abilities.healer.Healing.CureLightWounds;
import Tamaized.AoV.core.skills.AoVSkill;

import com.mojang.realmsclient.gui.ChatFormatting;

public class HealerSkillCore4 extends AoVSkill{
	
	private static final ResourceLocation icon = new ResourceLocation(AoV.modid+":textures/skills/test.png");

	public HealerSkillCore4() {
		super(getUnlocalizedName(), AoVSkill.getSkillFromName(HealerSkillCore3.getUnlocalizedName()), 1, 12, 0, false,
				new AbilityBase[]{
					AbilityBase.fromName(PosEnergyAura.getStaticName())
				},
				ChatFormatting.AQUA+"Healer Core 4",
				ChatFormatting.RED+"Requires: Healer Core 3",
				ChatFormatting.RED+"Requires: Level 12",
				"",
				ChatFormatting.GREEN+"+10 Spell Power",
				ChatFormatting.GREEN+"+10 Divine Power",
				"",
				ChatFormatting.YELLOW+"Added Spell: Positive Energy Aura"
				);
	}

	@Override
	protected void setupBuffs() {
		buffs = new Buffs(10, 10, 0, 0, false);
	}

	@Override
	public ResourceLocation getIcon() {
		return icon;
	}
	
	public static String getUnlocalizedName(){
		return "HealerSkillCore4";
	}

}
