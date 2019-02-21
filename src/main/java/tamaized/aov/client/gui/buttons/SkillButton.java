package tamaized.aov.client.gui.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import tamaized.aov.client.gui.AoVUIBar;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.skills.AoVSkill;

public class SkillButton extends GuiButton {

	private final AoVSkill skill;
	private boolean isObtained;
	private boolean notEnoughPoints = false;

	/**
	 * @param buttonId
	 * @param x
	 * @param y
	 * @param s
	 */
	public SkillButton(int buttonId, int x, int y, AoVSkill s) {
		super(buttonId, x, y, 18, 18, "");
		skill = s;
		update(null);
	}

	public AoVSkill getSkill() {
		return skill;
	}

	public boolean isObtained() {
		return isObtained;
	}

	public boolean hasEnoughPoints() {
		return !notEnoughPoints;
	}

	public void update(IAoVCapability cap) {
		enabled = false;
		notEnoughPoints = true;
		if (cap == null)
			return;

		if ((!cap.hasCoreSkill() || !skill.isClassCore()) && (skill.getParent() == null || cap.hasSkill(skill.getParent()))) {
			doChecks(cap);
		}

		if (cap.hasSkill(skill)) {
			enabled = false;
			isObtained = true;
		} else {
			isObtained = false;
		}
	}

	private void doChecks(IAoVCapability cap) {
		if (cap == null)
			return;
		if (canObtain(cap)) {
			notEnoughPoints = false;
			enabled = true;
		}
	}

	public boolean canObtain(IAoVCapability cap) {
		return cap != null && cap.getSkillPoints() >= skill.getCost() && cap.getLevel() >= skill.getLevel() && cap.getSpentSkillPoints() >= skill.getSpentPoints();
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float p_191745_4_) {
		if (visible) {
			FontRenderer fontrenderer = mc.fontRenderer;
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
			int i = getHoverState(hovered);
			GlStateManager.enableBlend();
			GlStateManager.blendFuncSeparate(770, 771, 1, 0);
			GlStateManager.blendFunc(770, 771);
			// drawRect(x + width / 2, y, width / 2, height, 0xFFFFFFFF);
			mouseDragged(mc, mouseX, mouseY);
			int j = 0xBBFFFFFF;

			if (packedFGColour != 0) {
				// j = packedFGColour;
			} else if (!enabled) {
				j = 0xFF888888;
			} else if (hovered) {
				j = 0xFFFFFFFF;
			}
			if (notEnoughPoints)
				j = 0xAAFF0000;
			if (isObtained)
				j = 0xFF00FF00;

			drawRect(x, y, x + width, y + height, j);
			float alpha = (float) (j >> 24 & 255) / 255.0F;
			GlStateManager.color4f(1.0f, 1.0f, 1.0f, alpha);
			AoVUIBar.renderHotbarIcon(this, null, 0, x + 1, y + 1, skill == null ? null : skill.getIcon(), false);
		}
	}

}
