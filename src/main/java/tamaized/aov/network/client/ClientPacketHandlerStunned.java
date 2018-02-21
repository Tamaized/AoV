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
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.stun.IStunCapability;

public class ClientPacketHandlerStunned implements IMessageHandler<ClientPacketHandlerStunned.Packet, IMessage> {

	@SideOnly(Side.CLIENT)
	private static void processPacket(ClientPacketHandlerStunned.Packet message, World world) {
		Entity e = world.getEntityByID(message.entityID);
		if (e != null) {
			IStunCapability cap = e.hasCapability(CapabilityList.STUN, null) ? e.getCapability(CapabilityList.STUN, null) : null;
			if (cap != null)
				cap.setStunTicks(message.stun);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(ClientPacketHandlerStunned.Packet message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> processPacket(message, Minecraft.getMinecraft().world));
		return null;
	}

	public static class Packet implements IMessage {

		private int entityID;
		private int stun;

		@SuppressWarnings("unused")
		public Packet() {

		}

		public Packet(Entity entity, IStunCapability cap) {
			entityID = entity.getEntityId();
			stun = cap.getStunTicks();
		}

		@Override
		public void fromBytes(ByteBuf stream) {
			entityID = stream.readInt();
			stun = stream.readInt();
		}

		@Override
		public void toBytes(ByteBuf stream) {
			stream.writeInt(entityID);
			stream.writeInt(stun);
		}
	}
}
