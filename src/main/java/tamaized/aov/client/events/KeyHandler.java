package tamaized.aov.client.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import tamaized.aov.client.gui.AoVUIBar;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.proxy.ClientProxy;

public class KeyHandler {

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null || !player.hasCapability(CapabilityList.AOV, null))
			return;
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap == null)
			return;
		if (ClientProxy.key.isPressed()) {
			ClientProxy.barToggle = cap.hasCoreSkill() && !ClientProxy.barToggle;
		} else if (ClientProxy.barToggle) {
			for (int i = 0; i <= 8; i++) {
				KeyBinding keyBind = Minecraft.getMinecraft().gameSettings.keyBindsHotbar[i];
				if (keyBind.isPressed()) {
					KeyBinding.setKeyBindState(keyBind.getKeyCode(), false);
					AoVUIBar.slotLoc = i;
					break;
				}
			}
			KeyBinding itemUse = Minecraft.getMinecraft().gameSettings.keyBindUseItem;
			if (itemUse.isPressed()) {
				cap.cast(AoVUIBar.slotLoc);
				KeyBinding.setKeyBindState(itemUse.getKeyCode(), false);
			}
		}
	}

	@SubscribeEvent
	public void handleMouse(MouseEvent e) {
		if (!ClientProxy.barToggle)
			return;
		if (e.getDwheel() > 0)
			AoVUIBar.slotLoc--;
		if (e.getDwheel() < 0)
			AoVUIBar.slotLoc++;
		if (AoVUIBar.slotLoc < 0)
			AoVUIBar.slotLoc = 8;
		if (AoVUIBar.slotLoc > 8)
			AoVUIBar.slotLoc = 0;
		if (e.getDwheel() != 0)
			e.setCanceled(true);
		KeyBinding itemUse = Minecraft.getMinecraft().gameSettings.keyBindUseItem;
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null || !player.hasCapability(CapabilityList.AOV, null))
			return;
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap == null)
			return;
		if (e.getButton() - 100 == itemUse.getKeyCode() && e.isButtonstate()) {
			cap.cast(AoVUIBar.slotLoc);
			KeyBinding.setKeyBindState(itemUse.getKeyCode(), false);
			e.setCanceled(true);
		}
	}
}
