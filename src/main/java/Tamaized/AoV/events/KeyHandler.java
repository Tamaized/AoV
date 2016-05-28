package Tamaized.AoV.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import Tamaized.AoV.AoV;
import Tamaized.AoV.common.client.ClientProxy;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;

public class KeyHandler {
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if(ClientProxy.key.isPressed()){
			if(AoV.clientAoVCore != null && AoV.clientAoVCore.getPlayer(null) != null && AoV.clientAoVCore.getPlayer(null).hasSkillCore()) ClientProxy.barToggle = !ClientProxy.barToggle;
			else ClientProxy.barToggle = false;
		}
				
	}
	
	@SubscribeEvent
	public void handleMouse(MouseEvent e){
		if(!ClientProxy.barToggle) return;
		
		AoVData data = AoV.clientAoVCore.getPlayer(null);
		
		if(e.dwheel > 0) ClientProxy.bar.slotLoc--;
		if(e.dwheel < 0) ClientProxy.bar.slotLoc++;
		if(ClientProxy.bar.slotLoc < 0) ClientProxy.bar.slotLoc = 8;
		if(ClientProxy.bar.slotLoc > 8) ClientProxy.bar.slotLoc = 0;
		
		if(e.button != 0){
			e.setCanceled(true);    		
  	     	KeyBinding.setKeyBindState(e.button - 100, false);
		}
		if(e.button == 1){
			AbilityBase spell = data.getCurrentSlot();
			if(spell != null){
				if(e.buttonstate){
					MovingObjectPosition obj = Minecraft.getMinecraft().objectMouseOver;
					EntityLivingBase entity = null;
					if(obj != null && obj.entityHit != null && obj.entityHit instanceof EntityLivingBase) entity = (EntityLivingBase) obj.entityHit;
					spell.activate(Minecraft.getMinecraft().thePlayer, data, entity);
				}else{
					//System.out.println(e.buttonstate);
				}
			}
		}
	}
}
