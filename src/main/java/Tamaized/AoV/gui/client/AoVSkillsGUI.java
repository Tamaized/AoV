package Tamaized.AoV.gui.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.common.handlers.ServerPacketHandler;
import Tamaized.AoV.gui.GuiHandler;
import Tamaized.AoV.gui.buttons.SkillButton;
import Tamaized.AoV.gui.client.buttonList.CasterSkillRegisterButtons;
import Tamaized.AoV.gui.client.buttonList.DefenderSkillRegisterButtons;
import Tamaized.AoV.gui.client.buttonList.HealerSkillRegisterButtons;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public class AoVSkillsGUI extends GuiScreen {

	private static final int BUTTON_CLOSE = 0;
	private static final int BUTTON_SKILL_CHECK = 1;
	private static final int BUTTON_SPELLBOOK = 2;
	private static final int BUTTON_RESET = 3;
	private static final int BUTTON_CHECKSTATS = 4;

	public static int getSkillButtonID() {
		return BUTTON_SKILL_CHECK;
	}

	private ArrayList<SkillButton> skillButtonList = new ArrayList<SkillButton>();

	private int lastMx = 0;
	private int lastMy = 0;

	private IAoVCapability cap;

	public AoVSkillsGUI() {
		super();
	}

	@Override
	public void initGui() {
		cap = mc.player.getCapability(CapabilityList.AOV, null);
		int margin = 20;
		int padding = 100;
		float workW = width - padding;
		int loc1 = (int) (workW * .0) + margin * 1;
		int loc2 = (int) (workW * .25) + margin * 2;
		int loc3 = (int) (workW * .50) + margin * 3;
		int loc4 = (int) (workW * .75) + margin * 4;
		buttonList.add(new GuiButton(BUTTON_CLOSE, loc1, height - 25, 80, 20, "Close"));
		buttonList.add(new GuiButton(BUTTON_SPELLBOOK, loc2, height - 25, 80, 20, "Spell Book"));
		buttonList.add(new GuiButton(BUTTON_CHECKSTATS, loc3, height - 25, 80, 20, "Check Stats"));
		buttonList.add(new GuiButton(BUTTON_RESET, loc4, height - 25, 80, 20, "Reset Skills"));
		if (cap != null) {
			skillButtonList.clear();

			HealerSkillRegisterButtons.register(this);
			CasterSkillRegisterButtons.register(this);
			DefenderSkillRegisterButtons.register(this);

			sendChargeUpdates();
		} else {
			mc.displayGuiScreen((GuiScreen) null);
		}
	}

	public void addNewButton(SkillButton button) {
		buttonList.add(button);
		skillButtonList.add(button);
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			switch (button.id) {
				case BUTTON_CLOSE: {
					mc.displayGuiScreen((GuiScreen) null);
				}
					break;
				case BUTTON_SKILL_CHECK: {
					if (!(button instanceof SkillButton)) break;
					SkillButton skillButton = (SkillButton) button;
					skillButton.enabled = false;
					if (!beginChecks(skillButton)) skillButton.enabled = true;
				}
					break;
				case BUTTON_SPELLBOOK: {
					GuiHandler.openGUI(GuiHandler.GUI_SPELLBOOK);
				}
					break;
				case BUTTON_RESET: {
					GuiHandler.openGUI(GuiHandler.GUI_RESET);
				}
					break;
				case BUTTON_CHECKSTATS: {
					GuiHandler.openGUI(GuiHandler.GUI_CHECKSTATS);
				}
					break;
				default:
					break;
			}
		}
	}

	private boolean beginChecks(SkillButton button) {
		if ((button.getSkill() == null || !cap.hasSkill(button.getSkill())) && button.canObtain(cap)) {
			try {
				int pktType = ServerPacketHandler.getPacketTypeID(ServerPacketHandler.PacketType.SKILLEDIT_CHECK_CANOBTAIN);
				ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
				DataOutputStream outputStream = new DataOutputStream(bos);
				outputStream.writeInt(pktType);
				outputStream.writeInt(button.getSkill().getID());
				FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
				AoV.channel.sendToServer(packet);
				outputStream.close();
				bos.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	public void onGuiClosed() {

	}

	@Override
	public void updateScreen() {
		for (SkillButton button : skillButtonList) {
			button.update(cap);
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, "Angel of Vengeance: Skills", width / 2, 15, 16777215);
		drawString(fontRendererObj, "Skill Points: " + (cap == null ? "null" : cap.getSkillPoints()), 5, 5, 0xFFFFFF00);
		drawString(fontRendererObj, "Spent: " + (cap == null ? "null" : cap.getSpentSkillPoints()) + " out of " + (cap == null ? "null" : cap.getLevel()), 5, 15, 0xFFFFFF00);
		drawString(fontRendererObj, "Level:", width - 40, 5, 0xFFFFFF00);
		drawString(fontRendererObj, "" + (cap == null ? "null" : cap.getLevel()), width - 40, 15, 0xFFFFFF00);

		drawCenteredString(fontRendererObj, "Tier 4", width / 2 - 135, height - 222, 0xFFFFFF00);
		drawCenteredString(fontRendererObj, "Tier 3", width / 2 - 135, height - 182, 0xFFFFFF00);
		drawCenteredString(fontRendererObj, "Tier 2", width / 2 - 135, height - 142, 0xFFFFFF00);
		drawCenteredString(fontRendererObj, "Tier 1", width / 2 - 135, height - 102, 0xFFFFFF00);
		drawCenteredString(fontRendererObj, "Core", width / 2 - 135, height - 62, 0xFFFFFF00);

		drawCenteredString(fontRendererObj, "Tier 4", width / 2, height - 222, 0xFFFFFF00);
		drawCenteredString(fontRendererObj, "Tier 3", width / 2, height - 182, 0xFFFFFF00);
		drawCenteredString(fontRendererObj, "Tier 2", width / 2, height - 142, 0xFFFFFF00);
		drawCenteredString(fontRendererObj, "Tier 1", width / 2, height - 102, 0xFFFFFF00);
		drawCenteredString(fontRendererObj, "Core", width / 2, height - 62, 0xFFFFFF00);

		drawCenteredString(fontRendererObj, "Tier 4", width / 2 + 135, height - 222, 0xFFFFFF00);
		drawCenteredString(fontRendererObj, "Tier 3", width / 2 + 135, height - 182, 0xFFFFFF00);
		drawCenteredString(fontRendererObj, "Tier 2", width / 2 + 135, height - 142, 0xFFFFFF00);
		drawCenteredString(fontRendererObj, "Tier 1", width / 2 + 135, height - 102, 0xFFFFFF00);
		drawCenteredString(fontRendererObj, "Core", width / 2 + 135, height - 62, 0xFFFFFF00);

		drawRect(width / 2 - 200, height - 225, width / 2 - 200 + 126, height - 27, 0x88000000);
		drawRect(width / 2 - 66, height - 225, width / 2 - 66 + 126, height - 27, 0x88000000);
		drawRect(width / 2 + 68, height - 225, width / 2 + 68 + 126, height - 27, 0x88000000);
		super.drawScreen(mouseX, mouseY, partialTicks);
		if (mouseX != lastMx || mouseY != lastMy) {
			boolean flag = true;
			for (SkillButton b : skillButtonList) {
				if (!b.isMouseOver()) continue;
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

	private void sendChargeUpdates() {
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ServerPacketHandler.getPacketTypeID(ServerPacketHandler.PacketType.CHARGES_RESET));
			FMLProxyPacket pkt = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
			if (AoV.channel != null && pkt != null) AoV.channel.sendToServer(pkt);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
