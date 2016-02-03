package Tamaized.AoV.registry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AoVFluids extends RegistryBase {

	public static final int idFluidVoid = 198;
	public static Fluid fluidVoid;
	public static Block fluidVoidz;
	public static Material materialFluidVoid;

	public static Item voidBucket;
	public static Block blockVoidFluid;

	@Override
	public void preInit() {
		// Fluid
		//fluidVoid = new Fluid("void", new ResourceLocation("VoidCraft:textures/blocks/voidFluid_still.png"), new ResourceLocation("VoidCraft:textures/blocks/voidFluid_flow.png"));
		//materialFluidVoid = new MaterialLiquid(MapColor.purpleColor);
		// This has to be here for Fluids
		//FluidRegistry.registerFluid(fluidVoid);
		// This must be last
		//blockVoidFluid = new BlockVoidFluid(fluidVoid, Material.water).setDensity(-400).setUnlocalizedName("blockVoidFluid");
		//voidBucket = new ItemVoidCraftBucket(blockVoidFluid).setUnlocalizedName("voidBucket").setMaxStackSize(1).setContainerItem(Items.bucket).setCreativeTab(voidCraft.tabs.tabVoid);
		//FluidContainerRegistry.registerFluidContainer(new FluidContainerData(FluidRegistry.getFluidStack(fluidVoid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(voidBucket), new ItemStack(Items.bucket)));
		//BucketHandler.INSTANCE.buckets.put(blockVoidFluid, voidBucket);
		//MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
	}

	@Override
	public void init() {
		//GameRegistry.registerBlock(blockVoidFluid, "blockVoidFluid");
		//GameRegistry.registerItem(voidBucket, voidBucket.getUnlocalizedName());
	}

	@Override
	public void postInit() {

	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void setupRender() {
		
	}

}
