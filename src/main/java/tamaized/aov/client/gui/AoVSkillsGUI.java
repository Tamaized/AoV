package tamaized.aov.client.gui;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import tamaized.aov.AoV;
import tamaized.aov.client.gui.buttonlist.AstroSkillRegisterButtons;
import tamaized.aov.client.gui.buttonlist.ClericSkillRegisterButtons;
import tamaized.aov.client.gui.buttonlist.DruidSkillRegisterButtons;
import tamaized.aov.client.gui.buttonlist.FavoredSoulSkillRegisterButtons;
import tamaized.aov.client.gui.buttonlist.IClassButtons;
import tamaized.aov.client.gui.buttonlist.PaladinSkillRegisterButtons;
import tamaized.aov.client.gui.buttons.SkillButton;
import tamaized.aov.common.blocks.BlockAngelicBlock;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.gui.GuiHandler;
import tamaized.aov.network.server.ServerPacketHandlerSpellSkill;

import java.util.ArrayList;
import java.util.List;

public class AoVSkillsGUI extends GuiScreenClose {

	private static final int BUTTON_CLOSE = 0;
	private static final int BUTTON_SKILL_CHECK = 1;
	private static final int BUTTON_SPELLBOOK = 2;
	private static final int BUTTON_RESET = 3;
	private static final int BUTTON_CHECKSTATS = 4;
	private static final int BUTTON_PAGE_PREV = 5;
	private static final int BUTTON_PAGE_NEXT = 6;
	private static List<IClassButtons> CLASS_BUTTON_REGISTRY = Lists.newArrayList(

			new FavoredSoulSkillRegisterButtons(),

			new ClericSkillRegisterButtons(),

			new PaladinSkillRegisterButtons(),

			new AstroSkillRegisterButtons(),

			new DruidSkillRegisterButtons()

	);
	private final Container inventory;
	private final BlockAngelicBlock.ClassType classType;
	private int page;
	private List<SkillButton> skillbuttons = new ArrayList<>();
	private int lastMx = 0;
	private int lastMy = 0;
	private IAoVCapability cap;

	public AoVSkillsGUI(BlockAngelicBlock.ClassType classType) {
		inventory = new GuiHandler.FakeContainer();
		this.classType = classType;
	}

	public static int getSkillButtonID() {
		return BUTTON_SKILL_CHECK;
	}

	@Override
	public void initGui() {
		super.initGui();
		mc.player.openContainer = inventory;
		cap = CapabilityList.getCap(mc.player, CapabilityList.AOV);
		if (cap != null) {
			if (classType == BlockAngelicBlock.ClassType.ALL) {
				for (IClassButtons b : CLASS_BUTTON_REGISTRY)
					if (b.active(cap)) {
						page = CLASS_BUTTON_REGISTRY.indexOf(b);
						break;
					}
			} else
				page = classType.ordinal() - 1;
			initButtons();
		} else {
			mc.player.closeScreen();
		}
	}

	public void initButtons() {
		buttons.clear();
		skillbuttons.clear();
		buttons.add(new Button(BUTTON_CLOSE, 10, height - 25, 80, 20, I18n.format("aov.gui.button.close")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);
				mc.player.closeScreen();
			}
		});
		buttons.add(new Button(BUTTON_SPELLBOOK, 110, height - 25, 80, 20, I18n.format("aov.gui.button.spellbook")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);
				GuiHandler.openGui(GuiHandler.GUI.SPELLBOOK, classType);
			}
		});
		buttons.add(new Button(BUTTON_CHECKSTATS, width - 190, height - 25, 80, 20, I18n.format("aov.gui.button.stats")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);
				GuiHandler.openGui(GuiHandler.GUI.CHECKSTATS, classType);
			}
		});
		buttons.add(new Button(BUTTON_RESET, width - 90, height - 25, 80, 20, I18n.format("aov.gui.button.reset")) {
			@Override
			public void onClick(double mouseX, double mouseY) {
				super.onClick(mouseX, mouseY);
				GuiHandler.openGui(GuiHandler.GUI.RESET, classType);
			}
		});
		if (classType == BlockAngelicBlock.ClassType.ALL) {
			buttons.add(new ArrowButton(BUTTON_PAGE_PREV, width / 2 - 95, 39, 20, height - 70, "<") {
				@Override
				public void onClick(double mouseX, double mouseY) {
					super.onClick(mouseX, mouseY);
					if (page > 0)
						page--;
					initButtons();
				}
			});
			buttons.add(new ArrowButton(BUTTON_PAGE_NEXT, width / 2 + 69, 39, 20, height - 70, ">") {
				@Override
				public void onClick(double mouseX, double mouseY) {
					super.onClick(mouseX, mouseY);
					if (page < CLASS_BUTTON_REGISTRY.size() - 1)
						page++;
					initButtons();
				}
			});
		}
		CLASS_BUTTON_REGISTRY.get(MathHelper.clamp(page, 0, CLASS_BUTTON_REGISTRY.size() - 1)).register(this);
	}

	public void addNewButton(SkillButton button) {
		buttons.add(button);
		skillbuttons.add(button);
	}

	public void doSkillButton(SkillButton button) {
		button.enabled = !beginChecks(button);
	}

	private boolean beginChecks(SkillButton button) {
		if ((button.getSkill() == null || !cap.hasSkill(button.getSkill())) && button.canObtain(cap))
			AoV.network.sendToServer(new ServerPacketHandlerSpellSkill(ServerPacketHandlerSpellSkill.PacketType.SKILLEDIT_CHECK_CANOBTAIN, null, button.getSkill().getID()));
		return false;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		for (Button button : buttons) {
			if (button instanceof SkillButton)
				((SkillButton) button).update(cap);
			if (button.id == BUTTON_PAGE_PREV)
				button.enabled = page > 0;
			if (button.id == BUTTON_PAGE_NEXT)
				button.enabled = page < CLASS_BUTTON_REGISTRY.size() - 1;
		}
		if (!this.mc.player.isAlive() || this.mc.player.removed) {
			this.mc.player.closeScreen();
		}
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(fontRenderer, I18n.format("aov.gui.title.skills"), width / 2, 15, 16777215);
		drawString(fontRenderer, I18n.format("aov.gui.skills.points", cap == null ? "null" : cap.getSkillPoints()), 5, 5, 0xFFFFFF00);
		drawString(fontRenderer, I18n.format("aov.gui.skills.spent", cap == null ? "null" : cap.getSpentSkillPoints(), cap == null ? "null" : cap.getLevel()), 5, 15, 0xFFFFFF00);
		drawString(fontRenderer, I18n.format("aov.gui.skills.level"), width - 40, 5, 0xFFFFFF00);
		drawString(fontRenderer, "" + (cap == null ? "null" : cap.getLevel()), width - 40, 15, 0xFFFFFF00);

		// drawCenteredString(fontRenderer, "Tier 4", width / 2 - 135, height - 222, 0xFFFFFF00);
		// drawCenteredString(fontRenderer, "Tier 3", width / 2 - 135, height - 182, 0xFFFFFF00);
		// drawCenteredString(fontRenderer, "Tier 2", width / 2 - 135, height - 142, 0xFFFFFF00);
		// drawCenteredString(fontRenderer, "Tier 1", width / 2 - 135, height - 102, 0xFFFFFF00);
		// drawCenteredString(fontRenderer, "Core", width / 2 - 135, height - 62, 0xFFFFFF00);

		// drawCenteredString(fontRenderer, "Tier 4", width / 2, height - 222, 0xFFFFFF00);
		// drawCenteredString(fontRenderer, "Tier 3", width / 2, height - 182, 0xFFFFFF00);
		// drawCenteredString(fontRenderer, "Tier 2", width / 2, height - 142, 0xFFFFFF00);
		// drawCenteredString(fontRenderer, "Tier 1", width / 2, height - 102, 0xFFFFFF00);
		// drawCenteredString(fontRenderer, "Core", width / 2, height - 62, 0xFFFFFF00);

		// drawCenteredString(fontRenderer, "Tier 4", width / 2 + 135, height - 222, 0xFFFFFF00);
		// drawCenteredString(fontRenderer, "Tier 3", width / 2 + 135, height - 182, 0xFFFFFF00);
		// drawCenteredString(fontRenderer, "Tier 2", width / 2 + 135, height - 142, 0xFFFFFF00);
		// drawCenteredString(fontRenderer, "Tier 1", width / 2 + 135, height - 102, 0xFFFFFF00);
		// drawCenteredString(fontRenderer, "Core", width / 2 + 135, height - 62, 0xFFFFFF00);

		//		drawRect(width / 2 - 200, height - 215, width / 2 - 200 + 126, height - 27, 0x88000000);
		drawRect(width / 2 - 66, height - 215, width / 2 - 66 + 126, height - 27, 0x88000000);
		//		drawRect(width / 2 + 68, height - 215, width / 2 + 68 + 126, height - 27, 0x88000000);
		super.render(mouseX, mouseY, partialTicks);
		if (mouseX != lastMx || mouseY != lastMy) {
			boolean flag = true;
			for (SkillButton b : skillbuttons) {
				if (!b.isMouseOver())
					continue;
				if (b.getSkill() != null && b.getSkill().getDescription() != null) {
					drawHoveringText(b.getSkill().getDescription(), mouseX, mouseY);
					flag = false;
					break;
				}
			}
			if (flag) {
				lastMy = mouseY;
				lastMx = mouseX;
			}
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		if (this.mc.player != null) {
			this.inventory.onContainerClosed(this.mc.player);
		}
	}

	static class ArrowButton extends Button {

		private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.MODID, "textures/gui/buttons.png");

		public ArrowButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
			super(buttonId, x, y, widthIn, heightIn, buttonText);
		}

		@Override
		public void render(int mouseX, int mouseY, float partialTicks) {
			Minecraft mc = Minecraft.getInstance();
			if (this.visible) {
				FontRenderer fontrenderer = mc.fontRenderer;
				mc.getTextureManager().bindTexture(TEXTURE);
				GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
				int i = this.getHoverState(this.hovered);
				GlStateManager.enableBlend();
				GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder bufferbuilder = tessellator.getBuffer();
				bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
				bufferbuilder.pos(x, y, 0.0D).tex(i / 3F, 0).endVertex();
				bufferbuilder.pos(x, y + height, 0.0D).tex(i / 3F, 1).endVertex();
				bufferbuilder.pos(x + 20, y + height, 0.0D).tex((1F + i) / 3F, 1).endVertex();
				bufferbuilder.pos(x + 20, y, 0.0D).tex((1F + i) / 3F, 0).endVertex();
				tessellator.draw();
				this.mouseDragged(mouseX, mouseY, 0, mouseX, mouseY);
				int j = 14737632;

				if (packedFGColor != 0) {
					j = packedFGColor;
				} else if (!this.enabled) {
					j = 10526880;
				} else if (this.hovered) {
					j = 16777120;
				}

				this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
			}
		}
	}
}
