package Tamaized.AoV.core.abilities.caster;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;

public class NimbusRay extends AbilityBase {

	private static final int cost = 6;
	private static final int range = 20;
	private static final int dmg = 4;

	public NimbusRay() {
		super(getStaticName(), cost, dmg, false,
				ChatFormatting.YELLOW+getStaticName(),
				"",
				ChatFormatting.AQUA+"Cost: "+cost,
				ChatFormatting.AQUA+"Range: "+range,
				ChatFormatting.AQUA+"Base Damage: "+dmg,
				"",
				ChatFormatting.DARK_PURPLE+"Shoots a small ray of light",
				ChatFormatting.DARK_PURPLE+"to deal damage.",
				ChatFormatting.DARK_PURPLE+"This damage is doubled on",
				ChatFormatting.DARK_PURPLE+"Undead targets."
				);
	}

	@Override
	protected void doAction(EntityPlayer player, AoVData data,
			EntityLivingBase e) {
		// TODO Auto-generated method stub

	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid+":textures/spells/test.png");
	}

	@Override
	public String getName() {
		return getStaticName();
	}
	
	public static String getStaticName(){
		return "Nimbus Ray";
	}
	
	@Override
	public int getCoolDown() {
		return 2;
	}

}
