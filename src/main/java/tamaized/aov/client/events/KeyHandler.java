package tamaized.aov.client.events;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
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
	public static KeyBinding key_target;
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
	private static float ryh, ry, rp, yaw, pitch, roll, rcy, rcp;
	private static int mx, my;

	public static void register() {
		key_target = new KeyBinding("key.aov.target", Keyboard.KEY_Z, CATEGORY_AOV);
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

		ClientRegistry.registerKeyBinding(key_target);
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
		if (player == null || !player.hasCapability(CapabilityList.AOV, null)) {
			ClientProxy.setTarget(null);
			return;
		}
		IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
		if (cap == null)
			return;
		if (key_bar.isPressed())
			ClientProxy.barToggle = cap.hasCoreSkill() && !ClientProxy.barToggle;
		if (key_target.isPressed())
			ClientProxy.setTarget();
		for (KeyBinding slotKey : SLOT_KEYS)
			if (slotKey.isPressed())
				cap.cast(SLOT_KEYS.indexOf(slotKey));
	}

	@SubscribeEvent
	public static void handleKeys(InputEvent.KeyInputEvent e) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null)
			return;
		if (player.updateBlocked) {
			KeyBinding chat = Minecraft.getMinecraft().gameSettings.keyBindChat;
			KeyBinding perspective = Minecraft.getMinecraft().gameSettings.keyBindTogglePerspective;
			boolean chatpress = chat.isPressed();
			boolean perspectivepress = perspective.isPressed();
			KeyBinding.unPressAllKeys();
			if (chatpress) {
				KeyBinding.setKeyBindState(chat.getKeyCode(), true);
				KeyBinding.onTick(chat.getKeyCode());
			}
			if (perspectivepress) {
				KeyBinding.setKeyBindState(perspective.getKeyCode(), true);
				KeyBinding.onTick(perspective.getKeyCode());
			}
			return;
		}
		IAoVCapability cap = player.hasCapability(CapabilityList.AOV, null) ? player.getCapability(CapabilityList.AOV, null) : null;
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

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void handleCamera(EntityViewRenderEvent.CameraSetup e) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null)
			return;
		if (player.updateBlocked) {
			e.setYaw(yaw);
			e.setPitch(pitch);
			e.setRoll(roll);
		} else {
			yaw = e.getYaw();
			pitch = e.getPitch();
			roll = e.getRoll();
		}
	}

	@SubscribeEvent
	public static void handleUpdate(LivingEvent.LivingUpdateEvent e) {
		if (e.getEntity().updateBlocked)
			e.setCanceled(true);
	}

	@SubscribeEvent
	public static void handleDelta(TickEvent.RenderTickEvent e) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null || e.phase == TickEvent.Phase.END)
			return;
		if (player.updateBlocked) {
			Mouse.getDX();
			Mouse.getDY();
			player.prevRotationYawHead = player.rotationYawHead = ryh;
			player.prevRotationYaw = player.rotationYaw = ry;
			player.prevRotationPitch = player.rotationPitch = rp;
			player.prevCameraPitch = player.cameraPitch = rcp;
			player.prevCameraYaw = player.cameraYaw = rcy;
		} else {
			ry = player.rotationYaw;
			rp = player.rotationPitch;
			ryh = player.rotationYawHead;
			rcp = player.cameraPitch;
			rcy = player.cameraYaw;
		}
	}

	@SubscribeEvent
	public static void handleMouse(MouseEvent e) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null)
			return;
		if (player.updateBlocked) {
			Mouse.setCursorPosition(mx, my);
			e.setCanceled(true);
			return;
		}
		mx = Mouse.getX();
		my = Mouse.getY();
		if (!ClientProxy.barToggle)
			return;
		KeyBinding itemUse = Minecraft.getMinecraft().gameSettings.keyBindUseItem;
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
		if (!player.hasCapability(CapabilityList.AOV, null))
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
