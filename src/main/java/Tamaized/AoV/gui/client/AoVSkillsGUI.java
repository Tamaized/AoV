package Tamaized.AoV.gui.client;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import Tamaized.AoV.AoV;
import Tamaized.AoV.common.handlers.ServerPacketHandler;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.gui.GuiHandler;
import Tamaized.AoV.gui.SkillButton;

public class AoVSkillsGUI extends GuiScreen {
	
	public static AoVSkillsGUI instance;
	
	private static final int BUTTON_CLOSE = 0;
	private static final int BUTTON_SKILL_CHECK = 1;
	
	public static Map<String, Boolean> spooler = new HashMap<String, Boolean>();
	
	public static boolean doRefresh = false;

	public AoVSkillsGUI() {
		super();
	}
	
	@Override
	public void initGui(){
		buttonList.add(new GuiButton(BUTTON_CLOSE, (width/2)-50, height-25, 100, 20, "Close"));
		if(AoV.clientAoVCore != null){
			buttonList.add(new SkillButton(BUTTON_SKILL_CHECK, (width/2), height/2, "HealerSkillCore"));	
		}else{
			mc.displayGuiScreen((GuiScreen)null);
		}
		instance = this;
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException{
		if (button.enabled){
			switch(button.id){
				case BUTTON_CLOSE:
					mc.displayGuiScreen((GuiScreen)null);
					break;
				case BUTTON_SKILL_CHECK:
					if(!(button instanceof SkillButton)) break;
					SkillButton skillButton = (SkillButton) button;
					skillButton.enabled = false;
					int pktType = ServerPacketHandler.TYPE_SKILLEDIT_CHECK_CANOBTAIN;
					ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
					DataOutputStream outputStream = new DataOutputStream(bos);
					try {
						outputStream.writeInt(pktType);
						outputStream.writeUTF(skillButton.skill.skillName);
		 				FMLProxyPacket packet = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
		 				AoV.channel.sendToServer(packet);
		 				System.out.println("Sent Server Packet");
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					outputStream.close();
	 				bos.close();
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
		instance = null;
    }
	
	@Override
	public void updateScreen(){
		if(doRefresh){
			for(GuiButton button : buttonList){
				if(!(button instanceof SkillButton)) continue;
				SkillButton sb = (SkillButton) button;
				System.out.println(sb.skill.skillName+" : "+spooler.containsKey(sb.skill.skillName));
				System.out.println(spooler.keySet());
				if(spooler.containsKey(sb.skill.skillName)){
					sb.enabled = !spooler.get(sb.skill.skillName);
					sb.isObtained = !sb.enabled;
					spooler.remove(sb.skill.skillName);
				}
			}
			doRefresh = false;
		}
    }
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRendererObj, "Angel of Vengeance: Skills", this.width / 2, 15, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);
		for(GuiButton b : buttonList){
			if(!b.isMouseOver()) continue;
			if(b instanceof SkillButton){
				SkillButton sb = (SkillButton) b;
				if(sb.skill != null && sb.skill.description != null) this.drawHoveringText(sb.skill.description, mouseX, mouseY);
			}
		}
	}
}
