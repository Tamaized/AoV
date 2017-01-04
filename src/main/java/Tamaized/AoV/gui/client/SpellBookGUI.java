package Tamaized.AoV.gui.client;

import java.io.DataOutputStream;
import java.io.IOException;

import Tamaized.AoV.AoV;
import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import Tamaized.AoV.common.handlers.ServerPacketHandler;
import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.universal.InvokeMass;
import Tamaized.AoV.gui.GuiHandler;
import Tamaized.AoV.gui.buttons.BlankButton;
import Tamaized.AoV.gui.buttons.SpellButton;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public class SpellBookGUI extends GuiScreen {

	private static final int BUTTON_CLOSE = 0;
	private static final int BUTTON_BACK = 1;

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

	@Override
	public void initGui() {
		int margin = 20;
		int padding = 100;
		float workW = width - padding;
		int loc1 = (int) (workW * .25) + margin;
		int loc2 = (int) (workW * .75) + margin;
		buttonList.add(new GuiButton(BUTTON_BACK, loc1, height - 25, 80, 20, "Back"));
		buttonList.add(new GuiButton(BUTTON_CLOSE, loc2, height - 25, 80, 20, "Close"));

		int xLoc = 50;
		int yLoc = 50;

		{
			int y = height - 47;
			int x = width / 2;
			buttonList.add(new BlankButton(BUTTON_BAR_SLOT_0, x - 88, y, 16, 16, false));
			buttonList.add(new BlankButton(BUTTON_BAR_SLOT_1, x - 68, y, 16, 16, false));
			buttonList.add(new BlankButton(BUTTON_BAR_SLOT_2, x - 48, y, 16, 16, false));
			buttonList.add(new BlankButton(BUTTON_BAR_SLOT_3, x - 28, y, 16, 16, false));
			buttonList.add(new BlankButton(BUTTON_BAR_SLOT_4, x - 8, y, 16, 16, false));
			buttonList.add(new BlankButton(BUTTON_BAR_SLOT_5, x + 12, y, 16, 16, false));
			buttonList.add(new BlankButton(BUTTON_BAR_SLOT_6, x + 32, y, 16, 16, false));
			buttonList.add(new BlankButton(BUTTON_BAR_SLOT_7, x + 52, y, 16, 16, false));
			buttonList.add(new BlankButton(BUTTON_BAR_SLOT_8, x + 72, y, 16, 16, false));
		}
		if (mc == null || mc.player == null || !mc.player.hasCapability(CapabilityList.AOV, null)) return;
		IAoVCapability cap = mc.player.getCapability(CapabilityList.AOV, null);
		int index = 0;
		for (Ability ability : cap.getAbilities()) {
			buttonList.add(new SpellButton(BUTTON_SPELL, xLoc, yLoc + (25 * (index)), ability));
			index++;
		}
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.enabled) {
			switch (button.id) {
				case BUTTON_CLOSE:
					mc.displayGuiScreen((GuiScreen) null);
					break;
				case BUTTON_BACK:
					GuiHandler.openGUI(GuiHandler.GUI_SKILLS);
					break;
				case BUTTON_SPELL:
					if (button instanceof SpellButton) sendPacketTypeAddNearestSlot(((SpellButton) button).getSpell());
					break;
				case BUTTON_BAR_SLOT_0:
					sendPacketTypeRemoveSlot(0);
					break;
				case BUTTON_BAR_SLOT_1:
					sendPacketTypeRemoveSlot(1);
					break;
				case BUTTON_BAR_SLOT_2:
					sendPacketTypeRemoveSlot(2);
					break;
				case BUTTON_BAR_SLOT_3:
					sendPacketTypeRemoveSlot(3);
					break;
				case BUTTON_BAR_SLOT_4:
					sendPacketTypeRemoveSlot(4);
					break;
				case BUTTON_BAR_SLOT_5:
					sendPacketTypeRemoveSlot(5);
					break;
				case BUTTON_BAR_SLOT_6:
					sendPacketTypeRemoveSlot(6);
					break;
				case BUTTON_BAR_SLOT_7:
					sendPacketTypeRemoveSlot(7);
					break;
				case BUTTON_BAR_SLOT_8:
					sendPacketTypeRemoveSlot(8);
					break;
				default:
					break;
			}
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
	public void updateScreen() {

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		drawCenteredString(fontRendererObj, "Angel of Vengeance: SpellBook", width / 2, 15, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderBar(partialTicks);
		for (GuiButton b : buttonList) {
			if (!b.isMouseOver()) continue;
			if (b instanceof SpellButton) {
				SpellButton sb = (SpellButton) b;
				if (sb.getSpell() != null && sb.getSpell().getAbility() != null && sb.getSpell().getAbility().getDescription() != null) drawHoveringText(sb.getSpell().getAbility().getDescription(), mouseX, mouseY);
			}
		}
	}

	private void renderBar(float partialTicks) {
		IAoVCapability cap = mc == null || mc.player == null ? null : mc.player.getCapability(CapabilityList.AOV, null);
		ScaledResolution sr = new ScaledResolution(mc);
		float alpha = 1.0f;
		GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
		mc.getTextureManager().bindTexture(AoVUIBar.widgetsTexPath);
		int i = sr.getScaledWidth() / 2;
		drawTexturedModalRect(i - 91, sr.getScaledHeight() - 50, 0, 0, 182, 22);
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.01f, 0, 0);
		GlStateManager.translate(-20.01f, 0, 0);
		for (int j = 0; j < 9; ++j) {
			GlStateManager.translate(20.01f, 0, 0);
			if (cap == null || cap.getSlot(j) == null) continue;
			int k = sr.getScaledWidth() / 2 - 90 + 2;
			int l = sr.getScaledHeight() - 47;
			GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
			AoVUIBar.renderHotbarIcon(this, null, j, k, l, partialTicks, cap.getSlot(j).getAbility().getIcon(), (cap.getSlot(j).getAbility() instanceof InvokeMass) ? cap.getInvokeMass() : false);
		}
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.disableBlend();
	}

	private void sendPacketTypeRemoveSlot(int slot) {
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ServerPacketHandler.getPacketTypeID(ServerPacketHandler.PacketType.SPELLBAR_REMOVE));
			outputStream.writeInt(slot);
			FMLProxyPacket pkt = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
			if (AoV.channel != null && pkt != null) AoV.channel.sendToServer(pkt);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sendChargeUpdates();
	}

	private void sendPacketTypeAddNearestSlot(Ability ability) {
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ServerPacketHandler.getPacketTypeID(ServerPacketHandler.PacketType.SPELLBAR_ADDNEAR));
			ability.encode(outputStream);
			FMLProxyPacket pkt = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
			if (AoV.channel != null && pkt != null) AoV.channel.sendToServer(pkt);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sendChargeUpdates();
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
