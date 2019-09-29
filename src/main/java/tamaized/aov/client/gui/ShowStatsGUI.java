package tamaized.aov.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import tamaized.aov.common.blocks.BlockAngelicBlock;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.AoVCapabilityHandler;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.gui.GuiHandler;

public class ShowStatsGUI extends GuiScreenClose {

	private final BlockAngelicBlock.ClassType parent;

	public ShowStatsGUI(BlockAngelicBlock.ClassType parent) {
		super(makeTranslationKey("stats"));
		this.parent = parent;
	}

	@Override
	protected void init() {
		int margin = 20;
		int padding = 100;
		float workW = width - padding;
		int loc1 = (int) (workW * .25) + margin;
		int loc2 = (int) (workW * .75) + margin;
		buttons.add(new Button(loc1, height - 25, 80, 20, I18n.format("aov.gui.button.back"), button -> GuiHandler.openGui(GuiHandler.GUI.SKILLS, parent)));
		buttons.add(new Button(loc2, height - 25, 80, 20, I18n.format("aov.gui.button.close"), button -> Minecraft.getInstance().player.closeScreen()));
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		this.drawCenteredString(this.font, I18n.format("aov.gui.title.stats"), this.width / 2, 15, 16777215);
		IAoVCapability cap = minecraft == null ? null : CapabilityList.getCap(minecraft.player, CapabilityList.AOV);
		String s = cap == null ? "null" : cap.getLevel() >= cap.getMaxLevel() ? I18n.format("aov.gui.stats.max") : cap.getExp() + "/" + AoVCapabilityHandler.getExpForLevel(cap.getLevel() + 1);
		this.drawCenteredString(font, I18n.format("aov.gui.stats.experience", s), width / 2, 50, 0xFFFF00);
		this.drawCenteredString(font, I18n.format("aov.gui.stats.level", cap == null ? "null" : cap.getLevel()), width / 2, 60, 0xFFFF00);
		this.drawCenteredString(font, I18n.format("aov.gui.stats.charges", cap == null ? "null" : cap.getExtraCharges(null, null)), width / 2, 70, 0x00BBFF);
		this.drawCenteredString(font, I18n.format("aov.gui.stats.spellpower", cap == null ? "null" : (int) cap.getSpellPower()), width / 2, 80, 0x00FF00);
		this.drawCenteredString(font, I18n.format("aov.gui.stats.dodge", cap == null ? "null" : cap.getDodge()) + "%", width / 2, 90, 0x00FF00);
		this.drawCenteredString(font, I18n.format("aov.gui.stats.doublestrike", cap == null ? "null" : cap.getDoubleStrike()) + "%", width / 2, 100, 0x00FF00);
		super.render(mouseX, mouseY, partialTicks);
	}

}
