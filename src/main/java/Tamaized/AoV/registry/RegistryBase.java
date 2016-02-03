package Tamaized.AoV.registry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class RegistryBase {
	
	public abstract void preInit();
	
	public abstract void init();
	
	public abstract void postInit();
	
	@SideOnly(Side.CLIENT)
	public abstract void setupRender();

}
