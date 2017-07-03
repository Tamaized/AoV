package tamaized.aov.common.gui;

import tamaized.aov.AoV;
import tamaized.aov.client.gui.AoVSkillsGUI;
import tamaized.aov.client.gui.ResetSkillsGUI;
import tamaized.aov.client.gui.ShowStatsGUI;
import tamaized.aov.client.gui.SpellBookGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class GuiHandler implements IGuiHandler {

	public final static int GUI_SKILLS = 0;
	public final static int GUI_SPELLBOOK = 1;
	public final static int GUI_CHECKSTATS = 2;
	public final static int GUI_RESET = 3;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case GUI_SKILLS:
				return new AoVSkillsGUI();
			case GUI_SPELLBOOK:
				return new SpellBookGUI();
			case GUI_CHECKSTATS:
				return new ShowStatsGUI();
			case GUI_RESET:
				return new ResetSkillsGUI();
			default:
				return null;
		}
	}

	public static void openGUI(int id) {
		FMLNetworkHandler.openGui(null, AoV.instance, id, null, 0, 0, 0);
	}

}
