package Tamaized.AoV.gui.client;

import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;

public class AoVUIBar {
	
	private AbilityBase[] slots = new AbilityBase[10];
	
	public void setSlot(AbilityBase spell, int slot){
		if(slot < 0 || slot > 9){
			AoV.logger.error("ISSUE! An Ability was attempted to be set out of bounds on AoVUIBar!");
			return;
		}
		slots[slot] = spell;
	}
	
	public AbilityBase getSlot(int slot){
		return slots[slot];
	}
	
	public void render(){
		
	}

}
