package Tamaized.AoV.registry;

import net.minecraft.stats.Achievement;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AoVAchievements extends RegistryBase {
	
	public static Achievement placeholder;

	@Override
	public void preInit() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		//voidCraftAchMainLine_1 = new Achievement("achievement.achM_1", "achM_1", 0, 0, voidCraft.items.voidcrystal, (Achievement) null);
		//voidCraftAchMainLine_1.setSpecial().initIndependentStat().registerStat();
	}

	@Override
	public void postInit() {
		Achievement[] achArray = {
				placeholder
		};
		//AchievementPage.registerAchievementPage(new AchievementPage("VoidCraft", achArray));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void setupRender() {
		
	}

}
