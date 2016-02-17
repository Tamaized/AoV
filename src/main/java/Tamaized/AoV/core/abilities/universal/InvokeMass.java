package Tamaized.AoV.core.abilities.universal;

import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.helper.ParticleHelper;

public class InvokeMass extends AbilityBase {

	private final static int cost = 0;
	private final static int range = 0;
	private final static int dmg = 0;

	public InvokeMass() {
		super(getStaticName(), cost, dmg, false,
				ChatFormatting.YELLOW+getStaticName(),
				"",
				ChatFormatting.DARK_PURPLE+"While Active your spells",
				ChatFormatting.DARK_PURPLE+"and abilities have double",
				ChatFormatting.DARK_PURPLE+"range and are cast on",
				ChatFormatting.DARK_PURPLE+"everything around you.",
				ChatFormatting.RED+"This also doubles the cost."
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

}
