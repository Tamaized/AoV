package tamaized.aov.network.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import tamaized.aov.common.helper.MotionHelper;
import tamaized.aov.network.NetworkMessages;

public class ClientPacketHandlerPlayerMotion implements NetworkMessages.IMessage<ClientPacketHandlerPlayerMotion> {

	private int e;
	private Vec3d vel;

	public ClientPacketHandlerPlayerMotion(Entity entity, Vec3d velocity) {
		e = entity.getEntityId();
		vel = velocity;
	}

	@Override
	public void handle(PlayerEntity player) {
		MotionHelper.addMotion(player.world.getEntityByID(e), vel);
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeInt(e);
		packet.writeDouble(vel.x);
		packet.writeDouble(vel.y);
		packet.writeDouble(vel.z);
	}

	@Override
	public ClientPacketHandlerPlayerMotion fromBytes(PacketBuffer packet) {
		e = packet.readInt();
		vel = new Vec3d(packet.readDouble(), packet.readDouble(), packet.readDouble());
		return this;
	}

}
