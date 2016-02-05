package Tamaized.AoV.gui;

import Tamaized.AoV.gui.client.AoVSkillsGUI;
import Tamaized.AoV.gui.server.AoVSkillsContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	public final static int GUI_SKILLS = 0;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == GUI_SKILLS){
			//return new AoVSkillsContainer();
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == GUI_SKILLS){
			return new AoVSkillsGUI();
		}
		return null;
	}

}
