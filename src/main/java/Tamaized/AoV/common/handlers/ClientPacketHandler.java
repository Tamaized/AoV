package Tamaized.AoV.common.handlers;

import java.io.IOException;

import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.capabilities.aov.IAoVCapability;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientPacketHandler {

	public static enum PacketType {
		AOVDATA
	}

	public static int getPacketTypeID(PacketType type) {
		return type.ordinal();
	}

	public static PacketType getPacketTypeFromID(int id) {
		return id < PacketType.values().length ? PacketType.values()[id] : null;
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onClientPacket(ClientCustomPacketEvent event) {
		Minecraft.getMinecraft().addScheduledTask(new Runnable() {
			public void run() {
				try {
					processPacket(event.getPacket().payload(), Side.CLIENT);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SideOnly(Side.CLIENT)
	public static void processPacket(ByteBuf parBB, Side parSide) throws IOException {
		World world = Minecraft.getMinecraft().world;
		if (world == null) return;
		ByteBufInputStream bbis = new ByteBufInputStream(parBB);
		int pktType = bbis.readInt();
		switch (getPacketTypeFromID(pktType)) {
			case AOVDATA: {
				EntityPlayer player = Minecraft.getMinecraft().player;
				if (player != null) {
					IAoVCapability cap = player.getCapability(CapabilityList.AOV, null);
					if (cap != null) {
						cap.decodePacket(bbis);
					}
				}
			}
				break;
			default: {

			}
				break;
		}
		bbis.close();

	}

}
