package Tamaized.AoV.gui.client;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.DataOutputStream;
import java.io.IOException;

import Tamaized.AoV.AoV;
import Tamaized.AoV.common.handlers.ServerPacketHandler;
import Tamaized.AoV.gui.GuiHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public class ResetSkillsGUI extends GuiScreen {

	private static final int BUTTON_CLOSE = 0;
	private static final int BUTTON_BACK = 1;
	private static final int BUTTON_RESET_MINOR = 2;
	private static final int BUTTON_RESET_FULL = 3;
	
	@Override
	public void initGui(){
		int margin = 20;
		int padding = 100;
		float workW = width-padding;
		int loc1 = (int) (workW*.0) + margin*1;
		int loc2 = (int) (workW*.25) + margin*2;
		int loc3 = (int) (workW*.50) + margin*3;
		int loc4 = (int) (workW*.75) + margin*4;
		buttonList.add(new GuiButton(BUTTON_CLOSE, loc1, height-25, 80, 20, "Close"));
		buttonList.add(new GuiButton(BUTTON_BACK, loc2, height-25, 80, 20, "Back"));
		buttonList.add(new GuiButton(BUTTON_RESET_MINOR, loc3, height-25, 80, 20, "Minor Reset"));
		buttonList.add(new GuiButton(BUTTON_RESET_FULL, loc4, height-25, 80, 20, "Full Reset"));
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException{
		if (button.enabled){
			switch(button.id){
				case BUTTON_CLOSE:
					mc.displayGuiScreen((GuiScreen)null);
					break;
				case BUTTON_BACK:
					GuiHandler.openGUI(GuiHandler.GUI_SKILLS);
					break;
				case BUTTON_RESET_MINOR:
					this.sendPacket(ServerPacketHandler.TYPE_RESETSKILLS_MINOR);
					break;
				case BUTTON_RESET_FULL:
					this.sendPacket(ServerPacketHandler.TYPE_RESETSKILLS_FULL);
					break;
				default:
					break;
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame(){
		return false;
	}
	

	@Override
	public void onGuiClosed(){
		
	}
	

	@Override
	public void updateScreen(){
		
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, "Reset Angel of Vengeance Skills", this.width / 2, 15, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private void sendPacket(int pktType) throws IOException{
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		outputStream.writeInt(pktType);
		FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
		AoV.channel.sendToServer(packet);
		System.out.println("Sent Server Packet");
		outputStream.close();
		bos.close();
	}

}
