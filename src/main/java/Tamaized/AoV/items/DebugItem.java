package Tamaized.AoV.items;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import Tamaized.AoV.registry.IBasicAoV;

public class DebugItem extends Item implements IBasicAoV{

	private final String name;

	public DebugItem(String n) {
		super();
		name = n;
		setUnlocalizedName(name);
		GameRegistry.registerItem(this, "items/"+n);
	}

	@Override
	public String getName() {
		return name;
	}

}
