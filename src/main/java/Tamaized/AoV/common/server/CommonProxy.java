package Tamaized.AoV.common.server;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVCore;


public class CommonProxy {

	public void registerRenderInformation() {}

	public void registerTiles() {}

	public void registerBlocks() {}

	public void registerItems() {}

	public void registerRenders() {}

	public void registerMISC() {
		//NetworkRegistry.INSTANCE.registerGuiHandler(AoV.instance, new GuiHandler());
	}

	public void registerNetwork() {}

	public void registerAchievements() {}

	public void registerInventoryRender() {
		
	}

	public void beginCore() {
		AoV.aovCore = new AoVCore();
	}

}