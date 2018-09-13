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
import tamaized.aov.common.capabilities.leap.ILeapCapability;
import tamaized.tammodized.common.helper.CapabilityHelper;

public class ClientPacketHandlerLeap implements IMessageHandler<ClientPacketHandlerLeap.Packet, IMessage> {

	@SideOnly(Side.CLIENT)
	private static void processPacket(ClientPacketHandlerLeap.Packet message, World world) {
		Entity e = world.getEntityByID(message.entityID);
		if (e != null) {
			ILeapCapability cap = CapabilityHelper.getCap(e, CapabilityList.LEAP, null);
			if (cap != null)
				cap.setLeapDuration(message.duration);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(ClientPacketHandlerLeap.Packet message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> processPacket(message, Minecraft.getMinecraft().world));
		return null;
	}

	public static class Packet implements IMessage {

		private int entityID;
		private int duration;

		@SuppressWarnings("unused")
		public Packet() {

		}

		public Packet(Entity entity, ILeapCapability cap) {
			entityID = entity.getEntityId();
			duration = cap.getLeapDuration();
		}

		@Override
		public void fromBytes(ByteBuf stream) {
			entityID = stream.readInt();
			duration = stream.readInt();
		}

		@Override
		public void toBytes(ByteBuf stream) {
			stream.writeInt(entityID);
			stream.writeInt(duration);
		}
	}
}
