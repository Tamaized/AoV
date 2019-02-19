package tamaized.aov.network.server;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.tammodized.common.helper.CapabilityHelper;

public class ServerPacketHandlerPolymorphDogAttack implements IMessageHandler<ServerPacketHandlerPolymorphDogAttack.Packet, IMessage> {

	private static void processPacket(EntityPlayerMP player) {
		IPolymorphCapability cap = CapabilityHelper.getCap(player, CapabilityList.POLYMORPH, null);
		if (cap != null)
			cap.doAttack(player, false);
	}

	@Override
	public IMessage onMessage(Packet message, MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().player;
		MinecraftServer server = player.getServer();
		if (server != null)
			server.addScheduledTask(() -> processPacket(player));
		return null;
	}

	public static class Packet implements IMessage {

		public Packet() {

		}

		@Override
		public void fromBytes(ByteBuf buf) {

		}

		@Override
		public void toBytes(ByteBuf buf) {

		}
	}
}