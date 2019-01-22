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
import tamaized.aov.common.blocks.BlockAngelicBlock;

import javax.annotation.Nonnull;

public class GuiHandler implements IGuiHandler {

	private static final int GUI_BITS = 0b1111;
	public static final int GUI_BIT_SHIFT = Integer.highestOneBit(GUI_BITS);

	public static void openGUI(GUI gui, BlockAngelicBlock.ClassType classType, @Nonnull EntityPlayer player, @Nonnull World world) {
		BlockPos pos = player.getPosition();
		FMLNetworkHandler.openGui(player, AoV.instance, (classType.ordinal() << GUI_BIT_SHIFT) + gui.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (GUI.values[id & GUI_BITS]) {
			case SKILLS:
				return new FakeContainer();
			default:
				break;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		BlockAngelicBlock.ClassType data = BlockAngelicBlock.ClassType.values[id >>> GUI_BIT_SHIFT];
		switch (GUI.values[id & GUI_BITS]) {
			case SKILLS:
				return new AoVSkillsGUI(data);
			case SPELLBOOK:
				return new SpellBookGUI(data);
			case CHECKSTATS:
				return new ShowStatsGUI(data);
			case RESET:
				return new ResetSkillsGUI(data);
			default:
				break;
		}
		return null;
	}

	public enum GUI {

		SKILLS,

		SPELLBOOK,

		CHECKSTATS,

		RESET;

		public static final GUI[] values = values();

	}

	public static class FakeContainer extends Container {

		@Override
		public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
			return true;
		}
	}

}
