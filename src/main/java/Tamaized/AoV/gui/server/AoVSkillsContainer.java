package Tamaized.AoV.gui.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class AoVSkillsContainer extends Container {
	
	public AoVSkillsContainer() {
		
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
	}

}
