package tamaized.aov.common.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import tamaized.aov.AoV;
import tamaized.aov.client.gui.AoVSkillsGUI;
import tamaized.aov.client.gui.ResetSkillsGUI;
import tamaized.aov.client.gui.ShowStatsGUI;
import tamaized.aov.client.gui.SpellBookGUI;

import javax.annotation.Nonnull;

public class GuiHandler implements IGuiHandler {

	public final static int GUI_SKILLS = 0;
	public final static int GUI_SPELLBOOK = 1;
	public final static int GUI_CHECKSTATS = 2;
	public final static int GUI_RESET = 3;

	public static void openGUI(int id, @Nonnull EntityPlayer player, @Nonnull World world) {
		BlockPos pos = player.getPosition();
		FMLNetworkHandler.openGui(player, AoV.instance, id, world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case GUI_SKILLS:
				return new FakeContainer();
			default:
				break;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case GUI_SKILLS:
				return new AoVSkillsGUI();
			case GUI_SPELLBOOK:
				return new SpellBookGUI();
			case GUI_CHECKSTATS:
				return new ShowStatsGUI();
			case GUI_RESET:
				return new ResetSkillsGUI();
			default:
				break;
		}
		return null;
	}

	static class FakeContainer extends Container {

		@Override
		public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
			return true;
		}
	}

}
