package tamaized.aov.client.gui;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
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
import java.util.Objects;
import java.util.function.Function;

public class AoVSkillsGUI extends GuiScreenClose {

	private static List<IClassButtons> CLASS_BUTTON_REGISTRY = Lists.newArrayList(

			new FavoredSoulSkillRegisterButtons(),

			new ClericSkillRegisterButtons(),

			new PaladinSkillRegisterButtons(),

			new AstroSkillRegisterButtons(),

			new DruidSkillRegisterButtons()

	);
	private final BlockAngelicBlock.ClassType classType;
	private int page;
	private List<SkillButton> skillbuttons = new ArrayList<>();
	private int lastMx = 0;
	private int lastMy = 0;
	private IAoVCapability cap;

	public AoVSkillsGUI(BlockAngelicBlock.ClassType classType) {
		super(makeTranslationKey("skills"));
		this.classType = classType;
	}

	@Override
	public void init() {
		super.init();
		cap = CapabilityList.getCap(Objects.requireNonNull(minecraft).player, CapabilityList.AOV);
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
			minecraft.player.closeScreen();
		}
	}

	public void initButtons() {
		buttons.clear();
		skillbuttons.clear();
		buttons.add(new Button(10, height - 25, 80, 20, I18n.format("aov.gui.button.close"), button -> Objects.requireNonNull(minecraft).player.closeScreen()));
		buttons.add(new Button(110, height - 25, 80, 20, I18n.format("aov.gui.button.spellbook"), button -> GuiHandler.openGui(GuiHandler.GUI.SPELLBOOK, classType)));
		buttons.add(new Button(width - 190, height - 25, 80, 20, I18n.format("aov.gui.button.stats"), button -> GuiHandler.openGui(GuiHandler.GUI.CHECKSTATS, classType)));
		buttons.add(new Button(width - 90, height - 25, 80, 20, I18n.format("aov.gui.button.reset"), button -> GuiHandler.openGui(GuiHandler.GUI.RESET, classType)));
		if (classType == BlockAngelicBlock.ClassType.ALL) {
			buttons.add(new ArrowButton(width / 2 - 95, 39, 20, height - 70, "<", button -> {
				if (page > 0)
					page--;
				initButtons();
			}, page -> page > 0));
			buttons.add(new ArrowButton(width / 2 + 69, 39, 20, height - 70, ">", button -> {
				if (page < CLASS_BUTTON_REGISTRY.size() - 1)
					page++;
				initButtons();
			}, page -> page < CLASS_BUTTON_REGISTRY.size() - 1));
		}
		CLASS_BUTTON_REGISTRY.get(MathHelper.clamp(page, 0, CLASS_BUTTON_REGISTRY.size() - 1)).register(this);
	}

	public void addNewButton(SkillButton button) {
		buttons.add(button);
		skillbuttons.add(button);
	}

	public void doSkillButton(SkillButton button) {
		button.active = !beginChecks(button);
	}

	private boolean beginChecks(SkillButton button) {
		if ((button.getSkill() == null || !cap.hasSkill(button.getSkill())) && button.canObtain(cap))
			AoV.network.sendToServer(new ServerPacketHandlerSpellSkill(ServerPacketHandlerSpellSkill.PacketType.SKILLEDIT_CHECK_CANOBTAIN, null, button.getSkill().getID()));
		return false;
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		for (Widget button : buttons) {
			if (button instanceof SkillButton)
				((SkillButton) button).update(cap);
			else if (button instanceof ArrowButton)
				((ArrowButton) button).update(page);
		}
		if (!Objects.requireNonNull(minecraft).player.isAlive() || !minecraft.player.isAlive())
			minecraft.player.closeScreen();
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderBackground();
		drawCenteredString(font, I18n.format("aov.gui.title.skills"), width / 2, 15, 16777215);
		drawString(font, I18n.format("aov.gui.skills.points", cap == null ? "null" : cap.getSkillPoints()), 5, 5, 0xFFFFFF00);
		drawString(font, I18n.format("aov.gui.skills.spent", cap == null ? "null" : cap.getSpentSkillPoints(), cap == null ? "null" : cap.getLevel()), 5, 15, 0xFFFFFF00);
		drawString(font, I18n.format("aov.gui.skills.level"), width - 40, 5, 0xFFFFFF00);
		drawString(font, "" + (cap == null ? "null" : cap.getLevel()), width - 40, 15, 0xFFFFFF00);

		RenderSystem.enableBlend();
		RenderUtils.setup(blitOffset);
		RenderUtils.renderRect(width / 2F - 66, height - 215, 126, 188, false, 0x00000088);
		super.render(mouseX, mouseY, partialTicks);
		RenderSystem.disableBlend();

		if (mouseX != lastMx || mouseY != lastMy) {
			boolean flag = true;
			for (SkillButton b : skillbuttons) {
				if (!b.visible || mouseX < b.x || mouseX > b.x + b.getWidth() || mouseY < b.y || mouseY > b.y + b.getHeight())
					continue;
				if (b.getSkill() != null && b.getSkill().getDescriptionAsTextComponent() != null) {
					renderComponentHoverEffect(b.getSkill().getDescriptionAsTextComponent(), mouseX, mouseY);
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

	static class ArrowButton extends Button {

		private static final ResourceLocation TEXTURE = new ResourceLocation(AoV.MODID, "textures/gui/buttons.png");

		private final Function<Integer, Boolean> func;

		public ArrowButton(int x, int y, int widthIn, int heightIn, String buttonText, IPressable func, Function<Integer, Boolean> active) {
			super(x, y, widthIn, heightIn, buttonText, func);
			this.func = active;
		}

		public void update(int data) {
			active = func.apply(data);
		}

		@Override
		public void render(int mouseX, int mouseY, float partialTicks) {
			Minecraft mc = Minecraft.getInstance();
			if (this.visible) {
				FontRenderer font = mc.fontRenderer;
				mc.getTextureManager().bindTexture(TEXTURE);
				RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
				int i = this.getYImage(isHovered());
				RenderSystem.enableBlend();
				RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				Tessellator tessellator = Tessellator.getInstance();
				BufferBuilder bufferbuilder = tessellator.getBuffer();
				bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
				bufferbuilder.pos(x, y, 0.0D).tex(i / 3F, 0).endVertex();
				bufferbuilder.pos(x, y + height, 0.0D).tex(i / 3F, 1).endVertex();
				bufferbuilder.pos(x + 20, y + height, 0.0D).tex((1F + i) / 3F, 1).endVertex();
				bufferbuilder.pos(x + 20, y, 0.0D).tex((1F + i) / 3F, 0).endVertex();
				tessellator.draw();
				this.mouseDragged(mouseX, mouseY, 0, mouseX, mouseY);
				int j = getFGColor();

				this.drawCenteredString(font, getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
			}
		}
	}
}
