package Tamaized.AoV.core.abilities.healer;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;

public class CureLightWounds extends AbilityBase {

	public CureLightWounds(int c, double d) {
		super(c, d);
	}

	@Override
	protected void doAction(EntityPlayer player, AoVData data, Entity e) {
		if(player.worldObj.isRemote){
			MovingObjectPosition obj = Minecraft.getMinecraft().objectMouseOver;
			if(obj != null && obj.entityHit != null){
				sendPacketTypeTarget(getName(), obj.entityHit.getEntityId());
			}else{
				sendPacketTypeSelf(getName());
			}
		}else{
			int a = (int) (8*(1f+(data.getSpellPower()/100f)));
			if(e == null){
				player.heal(a);
			}else{
				if(e != null && e instanceof EntityLivingBase) ((EntityLivingBase)e).heal(a);
			}
		}
	}

	@Override
	public String getName() {
		return "Cure Light Wounds";
	}

}
