package Tamaized.AoV.gui;

import Tamaized.AoV.AoV;
import Tamaized.AoV.gui.client.AoVSkillsGUI;
import Tamaized.AoV.gui.client.ResetSkillsGUI;
import Tamaized.AoV.gui.client.ShowStatsGUI;
import Tamaized.AoV.gui.client.SpellBookGUI;
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
		switch(id){
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch(id){
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
	
	public static void openGUI(int id){
		FMLNetworkHandler.openGui(null, AoV.instance, id, null, 0, 0, 0);
	}

}
