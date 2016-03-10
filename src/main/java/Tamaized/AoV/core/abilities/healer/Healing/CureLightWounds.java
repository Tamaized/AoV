package Tamaized.AoV.core.abilities.healer.Healing;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.healer.CureWounds;

public class CureLightWounds extends CureWounds{

	public CureLightWounds() {
		super(CureLightWounds.getStaticName(), 4, 2, 4);
	}

	public static String getStaticName() {
		return "Cure Light Wounds";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid+":textures/spells/cureLightWounds.png");
	}

	@Override
	public int getCoolDown() {
		return 2;
	}

}
