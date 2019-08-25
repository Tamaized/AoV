package tamaized.aov.client.gui;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import tamaized.aov.AoV;
import tamaized.aov.common.blocks.BlockAngelicBlock;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.gui.GuiHandler;
import tamaized.aov.network.server.ServerPacketHandlerSpellSkill;
import tamaized.aov.proxy.ClientProxy;

import java.util.Objects;

public class ResetSkillsGUI extends GuiScreenClose {

	private static final int BUTTON_CLOSE = 0;
	private static final int BUTTON_BACK = 1;
	private static final int BUTTON_RESET_MINOR = 2;
	private static final int BUTTON_RESET_FULL = 3;

	private final BlockAngelicBlock.ClassType parent;

	public ResetSkillsGUI(BlockAngelicBlock.ClassType parent) {
		super(makeTranslationKey("resetskills"));
		this.parent = parent;
	}

	@Override
	public void init() {
		buttons.add(new Button(10, height - 25, 80, 20, I18n.format("aov.gui.button.close"), button -> Objects.requireNonNull(minecraft).player.closeScreen()));
		buttons.add(new Button(110, height - 25, 80, 20, I18n.format("aov.gui.button.back"), button -> GuiHandler.openGui(GuiHandler.GUI.SKILLS, parent)));
		buttons.add(new Button(width - 190, height - 25, 80, 20, I18n.format("aov.gui.button.fullreset"), button -> {
			AoV.network.sendToServer(new ServerPacketHandlerSpellSkill(ServerPacketHandlerSpellSkill.PacketType.RESETSKILLS_FULL, null, 0));
			ClientProxy.barToggle = false;

		}));
		buttons.add(new Button(width - 90, height - 25, 80, 20, I18n.format("aov.gui.button.minorreset"), button -> {
			IAoVCapability cap = CapabilityList.getCap(Objects.requireNonNull(minecraft).player, CapabilityList.AOV);
			if (cap == null)
				return;
			if (cap.getObtainedSkills().size() > 1)
				AoV.network.sendToServer(new ServerPacketHandlerSpellSkill(ServerPacketHandlerSpellSkill.PacketType.RESETSKILLS_MINOR, null, 0));

		}));

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
		drawCenteredString(Objects.requireNonNull(minecraft).fontRenderer, I18n.format("aov.gui.title.reset"), width / 2, 15, 16777215);
		minecraft.fontRenderer.drawSplitString(I18n.format("aov.gui.reset.full"), 40, 40, width - 80, 0x00FFFF);
		minecraft.fontRenderer.drawSplitString(I18n.format("aov.gui.reset.minor"), 40, 125, width - 80, 0xFFFF00);
		super.render(mouseX, mouseY, partialTicks);
	}
}
