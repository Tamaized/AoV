package tamaized.aov.client;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tamaized.aov.AoV;

// TODO: TamModized
@Mod.EventBusSubscriber(modid = AoV.MODID, value = Side.CLIENT)
public class DebugHelper {

	private static String textClient = "";
	private static String textServer = "";
	private static DebugHelper INSTANCE = new DebugHelper();
	private static boolean persist;
	private static boolean clientDirty;
	private static boolean serverDirty;
	private static boolean remote = true;

	private DebugHelper() {

	}

	public static DebugHelper begin(boolean remote) {
		DebugHelper.remote = remote;
		return INSTANCE;
	}

	public static void reset() {
		textClient = "";
		textServer = "";
		clientDirty = false;
		serverDirty = false;
		persist = false;
	}

	public static void clear() {
		if (remote) {
			textClient = "";
			clientDirty = false;
			persist = false;
		} else {
			textServer = "";
			serverDirty = false;
		}
	}

	@SubscribeEvent
	public static void draw(RenderGameOverlayEvent.Text e) {
		if (!textClient.isEmpty())
			for (String t : textClient.split("\n"))
				e.getRight().add(t);
		if (!textServer.isEmpty())
			for (String t : textServer.split("\n"))
				e.getLeft().add(t);
		if (!persist)
			clear();
	}

	@SubscribeEvent
	public static void tick(TickEvent.ClientTickEvent e) {
		if (persist && e.phase == TickEvent.Phase.END)
			clientDirty = true;
	}

	@SubscribeEvent
	public static void tick(TickEvent.ServerTickEvent e) {
		if (e.phase == TickEvent.Phase.END)
			serverDirty = true;
	}

	public void setText(String text) {
		if (remote)
			textClient = text;
		else
			textServer = text;
	}

	public void persist() {
		persist = true;
	}

	public DebugHelper addText(String t) {
		boolean dirty = remote ? clientDirty : serverDirty;
		if (dirty)
			clear();
		String text = remote ? textClient : textServer;
		if (text.isEmpty())
			text += remote ? "Client" : "Server";
		text += ("\n" + t);
		if (remote)
			textClient = text;
		else
			textServer = text;
		return this;
	}

}
