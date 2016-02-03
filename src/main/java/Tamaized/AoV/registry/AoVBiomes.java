package Tamaized.AoV.registry;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.Height;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AoVBiomes extends RegistryBase {

	public static BiomeGenBase placeholder;

	@Override
	public void preInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		Height bvoidmm = new Height(-1F, 0.1F);
		//biomeVoid = new BiomeGenVoid(251).setBiomeName("The Void").setHeight(bvoidmm).setTemperatureRainfall(0.21F, 0.0F).setDisableRain();
		//BiomeManager.coolBiomes.add(new BiomeEntry(biomeVoid, 100));
	}

	@Override
	public void postInit() {

	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void setupRender() {
		
	}

}
