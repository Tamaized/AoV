package tamaized.aov.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientPacketHandlerStunned implements IMessageHandler<ClientPacketHandlerStunned.Packet, IMessage> {

	@SideOnly(Side.CLIENT)
	private static void processPacket(ClientPacketHandlerStunned.Packet message, World world) {
		Entity e = world.getEntityByID(message.entityID);
		if (e != null)
			e.updateBlocked = message.stun;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(ClientPacketHandlerStunned.Packet message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> processPacket(message, Minecraft.getMinecraft().world));
		return null;
	}

	public static class Packet implements IMessage {

		private int entityID;
		private boolean stun;

		@SuppressWarnings("unused")
		public Packet() {

		}

		public Packet(Entity entity) {
			entityID = entity.getEntityId();
			stun = entity.updateBlocked;
		}

		@Override
		public void fromBytes(ByteBuf stream) {
			entityID = stream.readInt();
			stun = stream.readBoolean();
		}

		@Override
		public void toBytes(ByteBuf stream) {
			stream.writeInt(entityID);
			stream.writeBoolean(stun);
		}
	}
}
