package tamaized.aov.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.registry.ParticleRegistry;

public class ClientPacketHandlerParticle implements IMessageHandler<ClientPacketHandlerParticle.Packet, IMessage> {

	@SideOnly(Side.CLIENT)
	private static void processPacket(ClientPacketHandlerParticle.Packet message, World world) {
		ParticleRegistry.IParticleHandler handler = ParticleRegistry.getHandlerFromID(message.handlerID);
		if (handler != null)
			handler.execute(Minecraft.getMinecraft().effectRenderer, world, message.pos.x, message.pos.y, message.pos.z, message.vel.x, message.vel.y, message.vel.z, message.data);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(ClientPacketHandlerParticle.Packet message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> processPacket(message, Minecraft.getMinecraft().world));
		return null;
	}

	public static class Packet implements IMessage {

		private int handlerID;
		private Vec3d pos;
		private Vec3d vel;
		private int[] data;

		@SuppressWarnings("unused")
		public Packet() {

		}

		public Packet(int id, double x, double y, double z, double dx, double dy, double dz, int... dat) {
			handlerID = id;
			pos = new Vec3d(x, y, z);
			vel = new Vec3d(dx, dy, dz);
			data = dat;
		}

		@Override
		public void fromBytes(ByteBuf stream) {
			handlerID = stream.readInt();
			pos = new Vec3d(stream.readDouble(), stream.readDouble(), stream.readDouble());
			vel = new Vec3d(stream.readDouble(), stream.readDouble(), stream.readDouble());
			int size = stream.readInt();
			data = new int[size];
			for (int i = 0; i < size; i++)
				data[i] = stream.readInt();
		}

		@Override
		public void toBytes(ByteBuf stream) {
			stream.writeInt(handlerID);
			stream.writeDouble(pos.x);
			stream.writeDouble(pos.y);
			stream.writeDouble(pos.z);
			stream.writeDouble(vel.x);
			stream.writeDouble(vel.y);
			stream.writeDouble(vel.z);
			stream.writeInt(data == null ? 0 : data.length);
			for (int dat : data)
				stream.writeInt(dat);
		}
	}
}
