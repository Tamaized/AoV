package tamaized.aov.client.handler;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tamaized.aov.AoV;
import tamaized.aov.client.gui.AdjustElementsGUI;

import java.util.List;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = AoV.modid)
public class ConfigButtonHandler {

	@SubscribeEvent
	public static void hook(GuiScreenEvent.InitGuiEvent.Post e) {
		if (e.getGui() instanceof GuiConfig && ((GuiConfig) e.getGui()).modID.equalsIgnoreCase(AoV.modid)) {
			GuiConfig gui = (GuiConfig) e.getGui();
			gui.entryList.listEntries.add(0, new GuiConfigEntries.CategoryEntry(gui, gui.entryList, new DummyConfigElement.DummyCategoryElement(I18n.format("aov.config.repositionelements.name"), "aov.config.repositionelements", Lists.newArrayList())) {
				@Override
				public boolean mousePressed(int index, int x, int y, int mouseEvent, int relativeX, int relativeY) {
					Minecraft.getMinecraft().displayGuiScreen(new AdjustElementsGUI());
					return true;
				}
			});
		}
	}

}
