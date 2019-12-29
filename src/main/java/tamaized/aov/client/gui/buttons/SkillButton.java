package tamaized.aov.client.gui.buttons;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import tamaized.aov.client.gui.AoVSkillsGUI;
import tamaized.aov.client.gui.AoVUIBar;
import tamaized.aov.client.gui.RenderUtils;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.skills.AoVSkill;

public class SkillButton extends Button {

	private final AoVSkill skill;
	private boolean isObtained;
	private boolean notEnoughPoints = false;

	public SkillButton(int x, int y, AoVSkill s) {
		super(x, y, 18, 18, "", p_onPress_1_ -> {

		});
		skill = s;
		update(null);
	}

	@Override
	public void onClick(double mouseX, double mouseY) {
		super.onClick(mouseX, mouseY);
		Screen screen = Minecraft.getInstance().currentScreen;
		if (screen instanceof AoVSkillsGUI)
			((AoVSkillsGUI) screen).doSkillButton(this);
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
		active = false;
		notEnoughPoints = true;
		if (cap == null)
			return;

		if ((!cap.hasCoreSkill() || !skill.isClassCore()) && (skill.getParent() == null || cap.hasSkill(skill.getParent()))) {
			doChecks(cap);
		}

		if (cap.hasSkill(skill)) {
			active = false;
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
			active = true;
		}
	}

	public boolean canObtain(IAoVCapability cap) {
		return cap != null && cap.getSkillPoints() >= skill.getCost() && cap.getLevel() >= skill.getLevel() && cap.getSpentSkillPoints() >= skill.getSpentPoints();
	}

	@Override
	public void render(int mouseX, int mouseY, float p_191745_4_) {
		if (visible) {
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			isHovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
			RenderSystem.blendFuncSeparate(770, 771, 1, 0);
			RenderSystem.blendFunc(770, 771);
			// drawRect(x + width / 2, y, width / 2, height, 0xFFFFFFFF);
			mouseDragged(mouseX, mouseY, 0, mouseX, mouseY);
			RenderSystem.enableBlend();
			int j = 0xFFFFFFBB;

			if (packedFGColor != 0) {
				j = packedFGColor;
			} else if (!active) {
				j = 0x888888FF;
			} else if (isHovered) {
				j = 0xFFFFFFFF;
			}
			if (notEnoughPoints)
				j = 0xFF0000AA;
			if (isObtained)
				j = 0x00FF00FF;

			RenderUtils.setup(blitOffset);
			RenderUtils.renderRect(x, y, width, height, false, j);
			float alpha = ((float) (j & 0xFF)) / 255F;
			RenderSystem.color4f(1.0f, 1.0f, 1.0f, alpha);
			AoVUIBar.renderHotbarIcon(null, 0, x + 1, y + 1, skill == null ? null : skill.getIcon(), false);
		}
	}

}
