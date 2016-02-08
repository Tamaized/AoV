package Tamaized.AoV.core.abilities.healer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;

public class CureLightWounds extends AbilityBase {

	public CureLightWounds(int c, double d) {
		super(c, d);
	}

	@Override
	protected void doAction(EntityPlayer player, AoVData data, Entity e) {
    	System.out.println("test");
		if(player.worldObj.isRemote){
			if(e != null){
				sendPacketTypeTarget(getName(), e.getEntityId());
			}else{
				sendPacketTypeSelf(getName());
			}
		}else{
			int a = (int) (4*(1f+(data.getSpellPower()/100f)));
			if(e == null){
				player.heal(a);
			}else{
				if(e != null && e instanceof EntityLivingBase) ((EntityLivingBase)e).heal(a);
			}
		}
	}

	public static String getName() {
		return "Cure Light Wounds";
	}

	@Override
	public ResourceLocation getIcon() {
		return new ResourceLocation(AoV.modid+":textures/spells/test.png");
	}

}
