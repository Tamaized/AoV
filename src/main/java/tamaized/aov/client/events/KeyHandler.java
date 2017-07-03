package tamaized.aov.client.events;

import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.client.gui.AoVUIBar;
import tamaized.aov.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyHandler {

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (ClientProxy.key.isPressed()) {
			EntityPlayer player = Minecraft.getMinecraft().player;
			if (player == null || !player.hasCapability(CapabilityList.AOV, null))
				return;
			IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
			if (cap.hasCoreSkill())
				ClientProxy.barToggle = !ClientProxy.barToggle;
			else
				ClientProxy.barToggle = false;
		} else if (ClientProxy.barToggle) {
			for (int i = 0; i <= 8; i++) {
				KeyBinding keyBind = Minecraft.getMinecraft().gameSettings.keyBindsHotbar[i];
				if (keyBind.isPressed()) {
					keyBind.setKeyBindState(keyBind.getKeyCode(), false);
					AoVUIBar.slotLoc = i;
					break;
				}
			}
		}
	}

	@SubscribeEvent
	public void handleMouse(MouseEvent e) {
		if (!ClientProxy.barToggle)
			return;

		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null || !player.hasCapability(CapabilityList.AOV, null))
			return;
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);

		if (e.getDwheel() > 0)
			AoVUIBar.slotLoc--;
		if (e.getDwheel() < 0)
			AoVUIBar.slotLoc++;
		if (AoVUIBar.slotLoc < 0)
			AoVUIBar.slotLoc = 8;
		if (AoVUIBar.slotLoc > 8)
			AoVUIBar.slotLoc = 0;

		if (e.getButton() != 0) {
			e.setCanceled(true);
			KeyBinding.setKeyBindState(e.getButton() - 100, false);
		}
		if (e.getButton() == 1 && e.isButtonstate()) {
			cap.cast(AoVUIBar.slotLoc);
		}
	}
}
