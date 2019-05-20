package tamaized.aov.common.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;
import tamaized.aov.AoV;
import tamaized.aov.client.gui.AoVSkillsGUI;
import tamaized.aov.client.gui.ResetSkillsGUI;
import tamaized.aov.client.gui.ShowStatsGUI;
import tamaized.aov.client.gui.SpellBookGUI;
import tamaized.aov.common.blocks.BlockAngelicBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GuiHandler {

	private static final int GUI_BITS = 0b1111;
	public static final int GUI_BIT_SHIFT = Integer.highestOneBit(GUI_BITS);
	private static IInteractionObject FAKE_CONTAINER = new IInteractionObject() {

		@Nonnull
		@Override
		public Container createContainer(@Nonnull InventoryPlayer inventoryPlayer, @Nonnull EntityPlayer entityPlayer) {
			return new FakeContainer();
		}

		@Nonnull
		@Override
		public String getGuiID() {
			return AoV.MODID + ":fake";
		}

		@Nonnull
		@Override
		public ITextComponent getName() {
			return new TextComponentTranslation("fake");
		}

		@Override
		public boolean hasCustomName() {
			return false;
		}

		@Nullable
		@Override
		public ITextComponent getCustomName() {
			return null;
		}
	};

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

	public static void openGui(GUI gui, BlockAngelicBlock.ClassType classType, EntityPlayerMP player) {
		openGui((classType.ordinal() << GUI_BIT_SHIFT) + gui.ordinal(), player);
	}

	public static void openGui(GUI gui, EntityPlayerMP player) {
		openGui(gui.ordinal(), player);
	}

	private static void openGui(int gui, EntityPlayerMP player) {
		IInteractionObject container = FAKE_CONTAINER;
		NetworkHooks.openGui(player, container, packetBuffer -> packetBuffer.writeInt(gui));
	}

	@OnlyIn(Dist.CLIENT)
	public static GuiScreen getGui(FMLPlayMessages.OpenContainer packet) {
		int id = packet.getAdditionalData().readInt();
		return getGui(id);
	}

	@OnlyIn(Dist.CLIENT)
	public static GuiScreen getGui(int id) {
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
