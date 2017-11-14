package tamaized.aov.client.events;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;
import tamaized.aov.AoV;
import tamaized.aov.client.gui.AoVUIBar;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.proxy.ClientProxy;

import java.util.List;

@Mod.EventBusSubscriber(modid = AoV.modid, value = Side.CLIENT)
public class KeyHandler {

	public static final String CATEGORY_AOV = "key.categories.aov";
	public static final List<KeyBinding> SLOT_KEYS = Lists.newArrayList();
	public static KeyBinding key_bar;
	public static KeyBinding key_bar_slot_0;
	public static KeyBinding key_bar_slot_1;
	public static KeyBinding key_bar_slot_2;
	public static KeyBinding key_bar_slot_3;
	public static KeyBinding key_bar_slot_4;
	public static KeyBinding key_bar_slot_5;
	public static KeyBinding key_bar_slot_6;
	public static KeyBinding key_bar_slot_7;
	public static KeyBinding key_bar_slot_8;

	public static void register() {
		key_bar = new KeyBinding("key.aov.bartoggle", Keyboard.KEY_LMENU, CATEGORY_AOV);
		key_bar_slot_0 = new KeyBinding("key.aov.bar.0", Keyboard.KEY_NONE, CATEGORY_AOV);
		key_bar_slot_1 = new KeyBinding("key.aov.bar.1", Keyboard.KEY_NONE, CATEGORY_AOV);
		key_bar_slot_2 = new KeyBinding("key.aov.bar.2", Keyboard.KEY_NONE, CATEGORY_AOV);
		key_bar_slot_3 = new KeyBinding("key.aov.bar.3", Keyboard.KEY_NONE, CATEGORY_AOV);
		key_bar_slot_4 = new KeyBinding("key.aov.bar.4", Keyboard.KEY_NONE, CATEGORY_AOV);
		key_bar_slot_5 = new KeyBinding("key.aov.bar.5", Keyboard.KEY_NONE, CATEGORY_AOV);
		key_bar_slot_6 = new KeyBinding("key.aov.bar.6", Keyboard.KEY_NONE, CATEGORY_AOV);
		key_bar_slot_7 = new KeyBinding("key.aov.bar.7", Keyboard.KEY_NONE, CATEGORY_AOV);
		key_bar_slot_8 = new KeyBinding("key.aov.bar.8", Keyboard.KEY_NONE, CATEGORY_AOV);

		ClientRegistry.registerKeyBinding(key_bar);
		ClientRegistry.registerKeyBinding(key_bar_slot_0);
		ClientRegistry.registerKeyBinding(key_bar_slot_1);
		ClientRegistry.registerKeyBinding(key_bar_slot_2);
		ClientRegistry.registerKeyBinding(key_bar_slot_3);
		ClientRegistry.registerKeyBinding(key_bar_slot_4);
		ClientRegistry.registerKeyBinding(key_bar_slot_5);
		ClientRegistry.registerKeyBinding(key_bar_slot_6);
		ClientRegistry.registerKeyBinding(key_bar_slot_7);
		ClientRegistry.registerKeyBinding(key_bar_slot_8);

		SLOT_KEYS.add(key_bar_slot_0);
		SLOT_KEYS.add(key_bar_slot_1);
		SLOT_KEYS.add(key_bar_slot_2);
		SLOT_KEYS.add(key_bar_slot_3);
		SLOT_KEYS.add(key_bar_slot_4);
		SLOT_KEYS.add(key_bar_slot_5);
		SLOT_KEYS.add(key_bar_slot_6);
		SLOT_KEYS.add(key_bar_slot_7);
		SLOT_KEYS.add(key_bar_slot_8);
	}

	@SubscribeEvent
	public static void handle(TickEvent.ClientTickEvent e) {
		if (e.phase == TickEvent.Phase.START)
			return;
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null || !player.hasCapability(CapabilityList.AOV, null))
			return;
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap == null)
			return;
		if (key_bar.isPressed())
			ClientProxy.barToggle = cap.hasCoreSkill() && !ClientProxy.barToggle;
		for (KeyBinding slotKey : SLOT_KEYS)
			if (slotKey.isPressed())
				cap.cast(SLOT_KEYS.indexOf(slotKey));
	}

	@SubscribeEvent
	public static void handleKeys(InputEvent.KeyInputEvent e) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null || !player.hasCapability(CapabilityList.AOV, null))
			return;
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap == null)
			return;
		if (ClientProxy.barToggle) {
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
	public static void handleMouse(MouseEvent e) {
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
			// Cancel item use on the main hotbar if we're a mouse button
			cap.cast(AoVUIBar.slotLoc);
			KeyBinding.setKeyBindState(itemUse.getKeyCode(), false);
			e.setCanceled(true);
		}
	}
}
