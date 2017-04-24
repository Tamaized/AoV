package Tamaized.AoV.events;

import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyHandler {

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		if (ClientProxy.key.isPressed()) {
			EntityPlayer player = Minecraft.getMinecraft().player;
			if (player == null || !player.hasCapability(CapabilityList.AOV, null)) return;
			IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
			if (cap.hasCoreSkill()) ClientProxy.barToggle = !ClientProxy.barToggle;
			else ClientProxy.barToggle = false;
		} else if (ClientProxy.barToggle) {
			for (int i = 0; i <= 8; i++) {
				KeyBinding keyBind = Minecraft.getMinecraft().gameSettings.keyBindsHotbar[i];
				if (keyBind.isPressed()) {
					keyBind.setKeyBindState(keyBind.getKeyCode(), false);
					ClientProxy.bar.slotLoc = i;
					break;
				}
			}
		}

	}

	@SubscribeEvent
	public void handleMouse(MouseEvent e) {
		if (!ClientProxy.barToggle) return;

		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null || !player.hasCapability(CapabilityList.AOV, null)) return;
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);

		if (e.getDwheel() > 0) ClientProxy.bar.slotLoc--;
		if (e.getDwheel() < 0) ClientProxy.bar.slotLoc++;
		if (ClientProxy.bar.slotLoc < 0) ClientProxy.bar.slotLoc = 8;
		if (ClientProxy.bar.slotLoc > 8) ClientProxy.bar.slotLoc = 0;

		if (e.getButton() != 0) {
			e.setCanceled(true);
			KeyBinding.setKeyBindState(e.getButton() - 100, false);
		}
		if (e.getButton() == 1) {
			Ability spell = cap.getSlot(ClientProxy.bar.slotLoc);
			if (spell != null) {
				if (e.isButtonstate()) {
					spell.cast(player);
				}
			}
		}
	}
}
