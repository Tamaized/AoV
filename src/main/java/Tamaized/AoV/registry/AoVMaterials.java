package Tamaized.AoV.registry;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AoVMaterials extends RegistryBase{
	
	public static ToolMaterial voidTools;
	
	public static ArmorMaterial voidArmor;

	@Override
	public void preInit() {
		//voidTools = EnumHelper.addToolMaterial("voidcrystal", 3, 2000, 12.0F, 8.0F, 30);
		
		//voidArmor = EnumHelper.addArmorMaterial("Void", "", 120, new int[] {4, 8, 6, 4}, 30); //22
	}

	@Override
	public void init() {
		
	}

	@Override
	public void postInit() {
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void setupRender() {
		
	}

}
