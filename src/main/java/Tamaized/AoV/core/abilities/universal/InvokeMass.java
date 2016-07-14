package Tamaized.AoV.core.abilities.universal;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;

import com.mojang.realmsclient.gui.ChatFormatting;

public class InvokeMass extends AbilityBase {

	private final static int charges = -1;
	private final static int range = 0;
	private final static int dmg = 0;

	public InvokeMass() {
		super(getStaticName(), charges, dmg, false,
				ChatFormatting.YELLOW+getStaticName(),
				"",
				ChatFormatting.DARK_PURPLE+"While Active, certain spells",
				ChatFormatting.DARK_PURPLE+"and abilities have double",
				ChatFormatting.DARK_PURPLE+"range and are cast as",
				ChatFormatting.DARK_PURPLE+"an AoE (Area of Effect).",
				ChatFormatting.RED+"This also doubles the cost and cooldown."
				);
	}

	@Override
	protected void doAction(EntityPlayer player, AoVData data, EntityLivingBase e) {
		if(player.worldObj.isRemote){
			sendPacketTypeSelf(getName());
		}else{
			data.invokeMass = !data.invokeMass;
			data.forceSync = true;
		}
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid+":textures/spells/InvokeMass.png");
	}

	@Override
	public String getName() {
		return getStaticName();
	}
	
	public static String getStaticName() {
		return "Invoke Mass";
	}

	@Override
	public int getCoolDown() {
		return 1;
	}

}
