package tamaized.aov.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MainWindow;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
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

import java.util.Objects;

public class SpellBookGUI extends GuiScreenClose {

	private final BlockAngelicBlock.ClassType parent;

	public SpellBookGUI(BlockAngelicBlock.ClassType parent) {
		super(makeTranslationKey("spellbook"));
		this.parent = parent;
	}

	@Override
	public void init() {
		int margin = 20;
		int padding = 100;
		float workW = width - padding;
		int loc1 = (int) (workW * .25) + margin;
		int loc2 = (int) (workW * .75) + margin;
		buttons.add(new Button(loc1, height - 25, 80, 20, I18n.format("aov.gui.button.back"), button -> GuiHandler.openGui(GuiHandler.GUI.SKILLS, parent)));
		buttons.add(new Button(loc2, height - 25, 80, 20, I18n.format("aov.gui.button.close"), button -> Objects.requireNonNull(minecraft).player.closeScreen()));

		int xLoc = 50;
		int yLoc = 50;

		{
			int y = height - 47;
			int x = width / 2;
			buttons.add(new BlankButton(x - 88, y, 16, 16, button -> sendPacketTypeRemoveSlot(0)));
			buttons.add(new BlankButton(x - 68, y, 16, 16, button -> sendPacketTypeRemoveSlot(1)));
			buttons.add(new BlankButton(x - 48, y, 16, 16, button -> sendPacketTypeRemoveSlot(2)));
			buttons.add(new BlankButton(x - 28, y, 16, 16, button -> sendPacketTypeRemoveSlot(3)));
			buttons.add(new BlankButton(x - 8, y, 16, 16, button -> sendPacketTypeRemoveSlot(4)));
			buttons.add(new BlankButton(x + 12, y, 16, 16, button -> sendPacketTypeRemoveSlot(5)));
			buttons.add(new BlankButton(x + 32, y, 16, 16, button -> sendPacketTypeRemoveSlot(6)));
			buttons.add(new BlankButton(x + 52, y, 16, 16, button -> sendPacketTypeRemoveSlot(7)));
			buttons.add(new BlankButton(x + 72, y, 16, 16, button -> sendPacketTypeRemoveSlot(8)));
		}
		if (minecraft == null || minecraft.player == null)
			return;
		IAoVCapability cap = CapabilityList.getCap(minecraft.player, CapabilityList.AOV);
		if (cap == null)
			return;
		int index = 0;
		for (Ability ability : cap.getAbilities()) {
			buttons.add(new SpellButton(xLoc + (100 * ((int) Math.floor(index / 6))), yLoc + (25 * (index % 6)), ability.getAbility(), button -> sendPacketTypeAddNearestSlot(((SpellButton) button).getSpell())));
			index++;
		}
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderBackground();
		drawCenteredString(Objects.requireNonNull(minecraft).fontRenderer, I18n.format("aov.gui.title.spellbook"), width / 2, 15, 16777215);
		super.render(mouseX, mouseY, partialTicks);
		renderBar();
		for (Widget b : buttons) {
			if (!b.isMouseOver(mouseX, mouseY))
				continue;
			if (b instanceof SpellButton) {
				SpellButton sb = (SpellButton) b;
				if (sb.getSpell() != null && sb.getSpell().getDescriptionAsTextComponent() != null)
					renderComponentHoverEffect(sb.getSpell().getDescriptionAsTextComponent(), mouseX, mouseY);
			}
		}
	}

	private void renderBar() {
		if (minecraft == null)
			return;
		IAoVCapability cap = CapabilityList.getCap(minecraft.player, CapabilityList.AOV);
		MainWindow sr = minecraft.mainWindow;
		float alpha = 1.0f;
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
		minecraft.getTextureManager().bindTexture(AoVUIBar.widgetsTexPath);
		int i = sr.getScaledWidth() / 2;
		RenderUtils.setup(blitOffset);
		RenderUtils.renderRect(i - 91, sr.getScaledHeight() - 50, 182, 22, 0, 0, 182F / 256F, 22F / 256F);
		RenderSystem.enableRescaleNormal();
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(770, 771, 1, 0);
		RenderHelper.enableGUIStandardItemLighting();
		RenderSystem.pushMatrix();
		RenderSystem.translated(0.01f, 0, 0);
		RenderSystem.translated(-20.01f, 0, 0);
		for (int j = 0; j < 9; ++j) {
			RenderSystem.translated(20.01f, 0, 0);
			if (cap == null || cap.getSlot(j) == null)
				continue;
			int k = sr.getScaledWidth() / 2 - 90 + 2;
			int l = sr.getScaledHeight() - 47;
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, alpha);
			AoVUIBar.renderHotbarIcon(null, j, k, l, cap.getSlot(j).getAbility().getIcon(), (cap.getSlot(j).getAbility() instanceof InvokeMass) && cap.getInvokeMass());
		}
		RenderSystem.popMatrix();
		RenderHelper.disableStandardItemLighting();
		RenderSystem.disableRescaleNormal();
		RenderSystem.disableBlend();
	}

	private void sendPacketTypeRemoveSlot(int slot) {
		AoV.network.sendToServer(new ServerPacketHandlerSpellSkill(ServerPacketHandlerSpellSkill.PacketType.SPELLBAR_REMOVE, null, slot));
	}

	private void sendPacketTypeAddNearestSlot(AbilityBase ability) {
		AoV.network.sendToServer(new ServerPacketHandlerSpellSkill(ServerPacketHandlerSpellSkill.PacketType.SPELLBAR_ADDNEAR, ability, 0));
	}
}
