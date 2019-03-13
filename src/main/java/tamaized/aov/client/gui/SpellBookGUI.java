package tamaized.aov.client.gui;

import net.minecraft.client.MainWindow;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import tamaized.aov.AoV;
import tamaized.aov.client.gui.buttons.BlankButton;
import tamaized.aov.client.gui.buttons.SpellButton;
import tamaized.aov.common.blocks.BlockAngelicBlock;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.Ability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.abilities.universal.InvokeMass;
import tamaized.aov.common.gui.GuiHandler;
import tamaized.aov.network.server.ServerPacketHandlerSpellSkill;

import java.util.List;

public class SpellBookGUI extends GuiScreenClose {

	public static final int BUTTON_SPELL = 2;
	public static final int BUTTON_BAR_SLOT_0 = 3;
	public static final int BUTTON_BAR_SLOT_1 = 4;
	public static final int BUTTON_BAR_SLOT_2 = 5;
	public static final int BUTTON_BAR_SLOT_3 = 6;
	public static final int BUTTON_BAR_SLOT_4 = 7;
	public static final int BUTTON_BAR_SLOT_5 = 8;
	public static final int BUTTON_BAR_SLOT_6 = 9;
	public static final int BUTTON_BAR_SLOT_7 = 10;
	public static final int BUTTON_BAR_SLOT_8 = 11;
	private static final int BUTTON_CLOSE = 0;
	private static final int BUTTON_BACK = 1;

	private final BlockAngelicBlock.ClassType parent;

	public SpellBookGUI(BlockAngelicBlock.ClassType parent) {
		this.parent = parent;
	}

	@Override
	public void initGui() {
		int margin = 20;
		int padding = 100;
		float workW = width - padding;
		int loc1 = (int) (workW * .25) + margin;
		int loc2 = (int) (workW * .75) + margin;
		buttons.add(new GuiButton(BUTTON_BACK, loc1, height - 25, 80, 20, I18n.format("aov.gui.button.back")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);
				GuiHandler.openGui(GuiHandler.GUI.SKILLS, parent);
			}
		});
		buttons.add(new GuiButton(BUTTON_CLOSE, loc2, height - 25, 80, 20, I18n.format("aov.gui.button.close")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);
				mc.player.closeScreen();
			}
		});

		int xLoc = 50;
		int yLoc = 50;

		{
			int y = height - 47;
			int x = width / 2;
			buttons.add(new BlankButton(BUTTON_BAR_SLOT_0, x - 88, y, 16, 16, false) {
				@Override
				public void onClick(double mouseX, double mouseY) {
					super.onClick(mouseX, mouseY);
					sendPacketTypeRemoveSlot(0);
				}
			});
			buttons.add(new BlankButton(BUTTON_BAR_SLOT_1, x - 68, y, 16, 16, false) {
				@Override
				public void onClick(double mouseX, double mouseY) {
					super.onClick(mouseX, mouseY);
					sendPacketTypeRemoveSlot(1);
				}
			});
			buttons.add(new BlankButton(BUTTON_BAR_SLOT_2, x - 48, y, 16, 16, false) {
				@Override
				public void onClick(double mouseX, double mouseY) {
					super.onClick(mouseX, mouseY);
					sendPacketTypeRemoveSlot(2);
				}
			});
			buttons.add(new BlankButton(BUTTON_BAR_SLOT_3, x - 28, y, 16, 16, false) {
				@Override
				public void onClick(double mouseX, double mouseY) {
					super.onClick(mouseX, mouseY);
					sendPacketTypeRemoveSlot(3);
				}
			});
			buttons.add(new BlankButton(BUTTON_BAR_SLOT_4, x - 8, y, 16, 16, false) {
				@Override
				public void onClick(double mouseX, double mouseY) {
					super.onClick(mouseX, mouseY);
					sendPacketTypeRemoveSlot(4);
				}
			});
			buttons.add(new BlankButton(BUTTON_BAR_SLOT_5, x + 12, y, 16, 16, false) {
				@Override
				public void onClick(double mouseX, double mouseY) {
					super.onClick(mouseX, mouseY);
					sendPacketTypeRemoveSlot(5);
				}
			});
			buttons.add(new BlankButton(BUTTON_BAR_SLOT_6, x + 32, y, 16, 16, false) {
				@Override
				public void onClick(double mouseX, double mouseY) {
					super.onClick(mouseX, mouseY);
					sendPacketTypeRemoveSlot(6);
				}
			});
			buttons.add(new BlankButton(BUTTON_BAR_SLOT_7, x + 52, y, 16, 16, false) {
				@Override
				public void onClick(double mouseX, double mouseY) {
					super.onClick(mouseX, mouseY);
					sendPacketTypeRemoveSlot(7);
				}
			});
			buttons.add(new BlankButton(BUTTON_BAR_SLOT_8, x + 72, y, 16, 16, false) {
				@Override
				public void onClick(double mouseX, double mouseY) {
					super.onClick(mouseX, mouseY);
					sendPacketTypeRemoveSlot(8);
				}
			});
		}
		if (mc == null || mc.player == null)
			return;
		IAoVCapability cap = CapabilityList.getCap(mc.player, CapabilityList.AOV);
		if (cap == null)
			return;
		int index = 0;
		for (Ability ability : cap.getAbilities()) {
			buttons.add(new SpellButton(BUTTON_SPELL, xLoc + (100 * ((int) Math.floor(index / 6))), yLoc + (25 * (index % 6)), ability.getAbility()) {
				@Override
				public void onClick(double mouseX, double mouseY) {
					super.onClick(mouseX, mouseY);
					sendPacketTypeAddNearestSlot(getSpell());
				}
			});
			index++;
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onGuiClosed() {

	}

	@Override
	public void tick() {
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, I18n.format("aov.gui.title.spellbook"), width / 2, 15, 16777215);
		super.render(mouseX, mouseY, partialTicks);
		renderBar();
		for (GuiButton b : buttons) {
			if (!b.isMouseOver())
				continue;
			if (b instanceof SpellButton) {
				SpellButton sb = (SpellButton) b;
				List<String> desc;
				if (sb.getSpell() != null && (desc = sb.getSpell().getDescription()) != null)
					drawHoveringText(desc, mouseX, mouseY);
			}
		}
	}

	private void renderBar() {
		if (mc == null)
			return;
		IAoVCapability cap = CapabilityList.getCap(mc.player, CapabilityList.AOV);
		MainWindow sr = mc.mainWindow;
		float alpha = 1.0f;
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, alpha);
		mc.getTextureManager().bindTexture(AoVUIBar.widgetsTexPath);
		int i = sr.getScaledWidth() / 2;
		drawTexturedModalRect(i - 91, sr.getScaledHeight() - 50, 0, 0, 182, 22);
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(770, 771, 1, 0);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translated(0.01f, 0, 0);
		GlStateManager.translated(-20.01f, 0, 0);
		for (int j = 0; j < 9; ++j) {
			GlStateManager.translated(20.01f, 0, 0);
			if (cap == null || cap.getSlot(j) == null)
				continue;
			int k = sr.getScaledWidth() / 2 - 90 + 2;
			int l = sr.getScaledHeight() - 47;
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, alpha);
			AoVUIBar.renderHotbarIcon(this, null, j, k, l, cap.getSlot(j).getAbility().getIcon(), (cap.getSlot(j).getAbility() instanceof InvokeMass) && cap.getInvokeMass());
		}
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableBlend();
	}

	private void sendPacketTypeRemoveSlot(int slot) {
		AoV.network.sendToServer(new ServerPacketHandlerSpellSkill(ServerPacketHandlerSpellSkill.PacketType.SPELLBAR_REMOVE, null, slot));
	}

	private void sendPacketTypeAddNearestSlot(AbilityBase ability) {
		AoV.network.sendToServer(new ServerPacketHandlerSpellSkill(ServerPacketHandlerSpellSkill.PacketType.SPELLBAR_ADDNEAR, ability, 0));
	}
}
