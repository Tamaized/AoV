package tamaized.aov.client.events;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import tamaized.aov.AoV;
import tamaized.aov.client.gui.AoVUIBar;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.proxy.ClientProxy;

import java.util.List;

@Mod.EventBusSubscriber(modid = AoV.MODID, value = Dist.CLIENT)
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
	private static GLFWCursorPosCallback delegate_cursosPos;
	private static GLFWMouseButtonCallback delegate_mouseButton;
	private static GLFWScrollCallback delegate_scroll;
	private static GLFWKeyCallback delegate_key;
	private static float ryh, ry, rp, yaw, pitch, roll, rcy, rcp;
	private static double mx, my;

	public static void register() {
		key_target = new KeyBinding("key.aov.target", GLFW.GLFW_KEY_Z, CATEGORY_AOV);
		key_bar = new KeyBinding("key.aov.bartoggle", GLFW.GLFW_KEY_LEFT_ALT, CATEGORY_AOV);
		key_bar_slot_0 = new KeyBinding("key.aov.bar.0", -1, CATEGORY_AOV);
		key_bar_slot_1 = new KeyBinding("key.aov.bar.1", -1, CATEGORY_AOV);
		key_bar_slot_2 = new KeyBinding("key.aov.bar.2", -1, CATEGORY_AOV);
		key_bar_slot_3 = new KeyBinding("key.aov.bar.3", -1, CATEGORY_AOV);
		key_bar_slot_4 = new KeyBinding("key.aov.bar.4", -1, CATEGORY_AOV);
		key_bar_slot_5 = new KeyBinding("key.aov.bar.5", -1, CATEGORY_AOV);
		key_bar_slot_6 = new KeyBinding("key.aov.bar.6", -1, CATEGORY_AOV);
		key_bar_slot_7 = new KeyBinding("key.aov.bar.7", -1, CATEGORY_AOV);
		key_bar_slot_8 = new KeyBinding("key.aov.bar.8", -1, CATEGORY_AOV);

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

		setupCallbacks();
	}

	@SubscribeEvent
	public static void handle(TickEvent.ClientTickEvent e) {
		if (e.phase == TickEvent.Phase.START)
			return;
		PlayerEntity player = Minecraft.getInstance().player;
		IAoVCapability cap = CapabilityList.getCap(player, CapabilityList.AOV);
		if (player == null || cap == null) {
			ClientProxy.setTarget(null);
			ClientProxy.barToggle = false;
			return;
		}
		if (key_bar.isPressed() || !cap.hasCoreSkill())
			ClientProxy.barToggle = cap.hasCoreSkill() && !ClientProxy.barToggle;
		if (key_target.isPressed())
			ClientProxy.setTarget();
		for (KeyBinding slotKey : SLOT_KEYS)
			if (slotKey.isPressed())
				cap.cast(SLOT_KEYS.indexOf(slotKey));
	}

	public static void setupCallbacks() {
		if (delegate_cursosPos == null)
			delegate_cursosPos = GLFW.glfwSetCursorPosCallback(Minecraft.getInstance().mainWindow.getHandle(), KeyHandler::callbackCursorPos);
		if (delegate_mouseButton == null)
			delegate_mouseButton = GLFW.glfwSetMouseButtonCallback(Minecraft.getInstance().mainWindow.getHandle(), KeyHandler::callbackMouseButton);
		if (delegate_scroll == null)
			delegate_scroll = GLFW.glfwSetScrollCallback(Minecraft.getInstance().mainWindow.getHandle(), KeyHandler::callbackScroll);
		if (delegate_key == null)
			delegate_key = GLFW.glfwSetKeyCallback(Minecraft.getInstance().mainWindow.getHandle(), KeyHandler::callbackKey);
	}

	private static void callbackCursorPos(long id, double x, double y) {
		PlayerEntity player = Minecraft.getInstance().player;
		if (player != null) {
			if (!player.canUpdate()) {
				GLFW.glfwSetCursorPos(Minecraft.getInstance().mainWindow.getHandle(), mx, my);
				return;
			}
			mx = x;
			my = y;
		}
		delegate_cursosPos.invoke(id, x, y);
	}

	private static void callbackMouseButton(long id, int button, int action, int mods) {
		PlayerEntity player = Minecraft.getInstance().player;
		IPolymorphCapability poly = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
		if (ClientProxy.barToggle || (poly != null && poly.getMorph() == IPolymorphCapability.Morph.Wolf)) {
			KeyBinding itemUse = Minecraft.getInstance().gameSettings.keyBindUseItem;
			IAoVCapability cap = CapabilityList.getCap(player, CapabilityList.AOV);
			if (button == itemUse.getKey().getKeyCode()) {
				// Cancel item use on the main hotbar if we're a mouse button
				if (action == GLFW.GLFW_RELEASE) {
					if (ClientProxy.barToggle && cap != null)
						cap.cast(AoVUIBar.slotLoc);
					if (!ClientProxy.barToggle && poly != null && poly.getMorph() == IPolymorphCapability.Morph.Wolf) {
						poly.doAttack(player, false);
					}
					KeyBinding.setKeyBindState(itemUse.getKey(), false);
				}
				return;
			}
		}
		delegate_mouseButton.invoke(id, button, action, mods);
	}

	private static void callbackScroll(long id, double xoffset, double yoffset) {
		if (ClientProxy.barToggle) {
			if (yoffset > 0)
				AoVUIBar.slotLoc--;
			if (yoffset < 0)
				AoVUIBar.slotLoc++;
			if (AoVUIBar.slotLoc < 0)
				AoVUIBar.slotLoc = 8;
			if (AoVUIBar.slotLoc > 8)
				AoVUIBar.slotLoc = 0;
		} else
			delegate_scroll.invoke(id, xoffset, yoffset);
	}

	private static void callbackKey(long windowPointer, int key, int scanCode, int action, int modifiers) {
		delegate_key.invoke(windowPointer, key, scanCode, action, modifiers);
		PlayerEntity player = Minecraft.getInstance().player;
		if (player == null)
			return;
		if (!player.canUpdate()) {
			KeyBinding chat = Minecraft.getInstance().gameSettings.keyBindChat;
			KeyBinding perspective = Minecraft.getInstance().gameSettings.keyBindTogglePerspective;
			boolean chatpress = chat.isPressed();
			boolean perspectivepress = perspective.isPressed();
			KeyBinding.unPressAllKeys();
			if (chatpress) {
				KeyBinding.setKeyBindState(chat.getKey(), true);
				KeyBinding.onTick(chat.getKey());
			}
			if (perspectivepress) {
				KeyBinding.setKeyBindState(perspective.getKey(), true);
				KeyBinding.onTick(perspective.getKey());
			}
			return;
		}
		if (ClientProxy.barToggle) {
			for (int i = 0; i <= 8; i++) {
				KeyBinding keyBind = Minecraft.getInstance().gameSettings.keyBindsHotbar[i];
				if (keyBind.isPressed()) {
					KeyBinding.setKeyBindState(keyBind.getKey(), false);
					AoVUIBar.slotLoc = i;
					break;
				}
			}
		}
		KeyBinding itemUse = Minecraft.getInstance().gameSettings.keyBindUseItem;
		if (itemUse.isPressed()) {
			if (ClientProxy.barToggle) {
				IAoVCapability cap = CapabilityList.getCap(player, CapabilityList.AOV);
				if (cap != null) {
					cap.cast(AoVUIBar.slotLoc);
					KeyBinding.setKeyBindState(itemUse.getKey(), false);
				}
			} else {
				IPolymorphCapability cap = CapabilityList.getCap(player, CapabilityList.POLYMORPH);
				if (cap != null && cap.getMorph() == IPolymorphCapability.Morph.Wolf) {
					cap.doAttack(player, false);
					KeyBinding.setKeyBindState(itemUse.getKey(), false);
				}
			}
		}
	}

	//TODO: EVERYTHING BELOW HERE NEEDS TO BE DOUBLECHECKED

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void handleCamera(EntityViewRenderEvent.CameraSetup e) {
		PlayerEntity player = Minecraft.getInstance().player;
		if (player == null)
			return;
		if (!player.canUpdate()) {
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
		if (!e.getEntity().canUpdate())
			e.setCanceled(true);
	}

	@SubscribeEvent
	public static void handleDelta(TickEvent.RenderTickEvent e) {
		PlayerEntity player = Minecraft.getInstance().player;
		if (player == null || e.phase == TickEvent.Phase.END)
			return;
		if (!player.canUpdate()) {
			//			Mouse.getDX();
			//			Mouse.getDY(); TODO
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
}
