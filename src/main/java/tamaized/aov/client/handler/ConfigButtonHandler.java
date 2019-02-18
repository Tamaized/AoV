package tamaized.aov.client.handler;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tamaized.aov.AoV;
import tamaized.aov.client.gui.AdjustElementsGUI;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = AoV.MODID)
public class ConfigButtonHandler {

	private static final IConfigElement CONFIG_ELEMENT = new DummyConfigElement.DummyCategoryElement(I18n.format("aov.config.repositionelements.name"), "aov.config.repositionelements", Lists.newArrayList());

	@SubscribeEvent
	public static void hook(GuiScreenEvent.InitGuiEvent.Post e) {
		if (e.getGui() instanceof GuiConfig && ((GuiConfig) e.getGui()).modID.equalsIgnoreCase(AoV.MODID)) {
			GuiConfig gui = (GuiConfig) e.getGui();
			if (gui.parentScreen instanceof GuiConfig || (gui.entryList.listEntries.size() > 0 && gui.entryList.listEntries.get(0).getConfigElement() == CONFIG_ELEMENT))
				return;
			gui.entryList.listEntries.add(0, new GuiConfigEntries.CategoryEntry(gui, gui.entryList, CONFIG_ELEMENT) {
				@Override
				public boolean mousePressed(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
					boolean requiresMcRestart = gui.entryList.saveConfigElements();
					if (Loader.isModLoaded(gui.modID)) {
						ConfigChangedEvent event = new ConfigChangedEvent.OnConfigChangedEvent(gui.modID, gui.configID, gui.isWorldRunning, requiresMcRestart);
						MinecraftForge.EVENT_BUS.post(event);
						if (!event.getResult().equals(Event.Result.DENY))
							MinecraftForge.EVENT_BUS.post(new ConfigChangedEvent.PostConfigChangedEvent(gui.modID, gui.configID, gui.isWorldRunning, requiresMcRestart));
					}
					Minecraft.getMinecraft().displayGuiScreen(new AdjustElementsGUI());
					return true;
				}
			});
		}
	}

}
