package tamaized.aov.common.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import tamaized.aov.AoV;
import tamaized.aov.client.gui.AoVSkillsGUI;
import tamaized.aov.client.gui.ResetSkillsGUI;
import tamaized.aov.client.gui.ShowStatsGUI;
import tamaized.aov.client.gui.SpellBookGUI;
import tamaized.aov.common.blocks.BlockAngelicBlock;
import tamaized.aov.network.client.ClientPacketHandlerOpenGui;

public class GuiHandler {

	private static final int GUI_BITS = 0b1111;
	public static final int GUI_BIT_SHIFT = Integer.highestOneBit(GUI_BITS);

	public static void openGui(GUI gui, BlockAngelicBlock.ClassType classType) {
		openGui((classType.ordinal() << GUI_BIT_SHIFT) + gui.ordinal());
	}

	public static void openGui(GUI gui) {
		openGui(gui.ordinal());
	}

	@OnlyIn(Dist.CLIENT)
	private static void openGui(int gui) {
		Minecraft.getInstance().displayGuiScreen(getGui(gui));
	}

	public static void openGui(GUI gui, BlockAngelicBlock.ClassType classType, ServerPlayerEntity player) {
		openGui((classType.ordinal() << GUI_BIT_SHIFT) + gui.ordinal(), player);
	}

	public static void openGui(GUI gui, ServerPlayerEntity player) {
		openGui(gui.ordinal(), player);
	}

	private static void openGui(int gui, ServerPlayerEntity player) {
		AoV.network.send(PacketDistributor.PLAYER.with(() -> player), new ClientPacketHandlerOpenGui(gui));
	}

	@OnlyIn(Dist.CLIENT)
	public static Screen getGui(int id) {
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

}
