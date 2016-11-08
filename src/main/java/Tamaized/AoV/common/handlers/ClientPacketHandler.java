package Tamaized.AoV.common.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.AoVCoreClient;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.gui.client.AoVOverlay;
import Tamaized.AoV.gui.client.AoVSkillsGUI;
import Tamaized.AoV.helper.ParticleHelper;

public class ClientPacketHandler {

	public static final int TYPE_COREDATA = 0;
	public static final int TYPE_PARTICLE_BURST = 1;

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientPacket(ClientCustomPacketEvent event) {
		Minecraft.getMinecraft().addScheduledTask(new Runnable() {
			public void run() {
				try {
					processPacketOnClient(event.getPacket().payload(), Side.CLIENT);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SideOnly(Side.CLIENT)
	public static void processPacketOnClient(ByteBuf parBB, Side parSide) throws IOException {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if (parSide == Side.CLIENT) {
			ByteBufInputStream bbis = new ByteBufInputStream(parBB);
			int pktType = bbis.readInt();
			switch (pktType) {
				case TYPE_COREDATA:
					String encodedPacket = bbis.readUTF();
					if (AoV.clientAoVCore == null) AoV.clientAoVCore = new AoVCoreClient();
					AoVData newData = AoVData.fromPacket(encodedPacket);
					// if(AoV.clientAoVCore.getPlayer(null) != null) AoV.clientAoVCore.getPlayer(null).updateData(newData);
					AoV.clientAoVCore.setPlayer(null, newData);
					if (AoVSkillsGUI.instance != null) AoVSkillsGUI.doRefresh = true;
					break;
				case TYPE_PARTICLE_BURST: // We assume clientAoVCore is not null, if it is then something is seriously wrong
					ParticleHelper.burstParticles(player.worldObj, bbis.readDouble(), bbis.readDouble(), bbis.readDouble(), player.getRNG(), bbis.readInt());
					break;
				default:
					break;
			}
			bbis.close();
		}
	}
}
