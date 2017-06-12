package Tamaized.AoV.gui.buttons;

import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.gui.client.AoVUIBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class SkillButton extends GuiButton {

	private final AoVSkill skill;
	private boolean isObtained;
	private boolean notEnoughPoints = false;

	/**
	 * @param buttonId
	 * @param x
	 * @param y
	 * @param skillName
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
		if (cap == null) return;

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
		if (cap == null) return;
		if (canObtain(cap)) {
			notEnoughPoints = false;
			enabled = true;
		}
	}
	
	public boolean canObtain(IAoVCapability cap){
		return cap != null && cap.getSkillPoints() >= skill.getCost() && cap.getLevel() >= skill.getLevel() && cap.getSpentSkillPoints() >= skill.getSpentPoints();
	}

	@Override
	public void func_191745_a(Minecraft mc, int mouseX, int mouseY, float p_191745_4_) {
		if (visible) {
			FontRenderer fontrenderer = mc.fontRendererObj;
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			hovered = mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
			int i = getHoverState(hovered);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.blendFunc(770, 771);
			// drawRect(xPosition + width / 2, yPosition, width / 2, height, 0xFFFFFFFF);
			mouseDragged(mc, mouseX, mouseY);
			int j = 0xBBFFFFFF;

			if (packedFGColour != 0) {
				// j = packedFGColour;
			} else if (!enabled) {
				j = 0xFF888888;
			} else if (hovered) {
				j = 0xFFFFFFFF;
			}
			if (notEnoughPoints) j = 0xAAFF0000;
			if (isObtained) j = 0xFF00FF00;

			drawRect(xPosition, yPosition, xPosition + width, yPosition + height, j);
			float alpha = (float) (j >> 24 & 255) / 255.0F;
			GlStateManager.color(1.0f, 1.0f, 1.0f, alpha);
			AoVUIBar.renderHotbarIcon(this, null, 0, xPosition + 1, yPosition + 1, 0, skill == null ? null : skill.getIcon(), false);
		}
	}

}
