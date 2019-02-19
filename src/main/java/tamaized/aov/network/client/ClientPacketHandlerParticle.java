package tamaized.aov.network.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import tamaized.aov.network.NetworkMessages;
import tamaized.aov.registry.ParticleRegistry;

public class ClientPacketHandlerParticle implements NetworkMessages.IMessage<ClientPacketHandlerParticle> {

	private int handlerID;
	private Vec3d pos;
	private Vec3d vel;
	private int[] data;

	public ClientPacketHandlerParticle(int id, double x, double y, double z, double dx, double dy, double dz, int... dat) {
		handlerID = id;
		pos = new Vec3d(x, y, z);
		vel = new Vec3d(dx, dy, dz);
		data = dat;
	}

	@Override
	public void handle(EntityPlayer player) {
		ParticleRegistry.IParticleHandler handler = ParticleRegistry.getHandlerFromID(handlerID);
		if (handler != null)
			handler.execute(Minecraft.getInstance().particles, player.world, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z, data);
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeInt(handlerID);
		packet.writeDouble(pos.x);
		packet.writeDouble(pos.y);
		packet.writeDouble(pos.z);
		packet.writeDouble(vel.x);
		packet.writeDouble(vel.y);
		packet.writeDouble(vel.z);
		packet.writeInt(data == null ? 0 : data.length);
		for (int dat : data)
			packet.writeInt(dat);
	}

	@Override
	public ClientPacketHandlerParticle fromBytes(PacketBuffer packet) {
		handlerID = packet.readInt();
		pos = new Vec3d(packet.readDouble(), packet.readDouble(), packet.readDouble());
		vel = new Vec3d(packet.readDouble(), packet.readDouble(), packet.readDouble());
		int size = packet.readInt();
		data = new int[size];
		for (int i = 0; i < size; i++)
			data[i] = packet.readInt();
		return this;
	}
}
