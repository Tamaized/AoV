package tamaized.aov.network.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.Vec3d;
import tamaized.aov.common.helper.ParticleHelper;
import tamaized.aov.network.NetworkMessages;
import tamaized.aov.proxy.CommonProxy;

public class ClientPacketHandlerParticleMesh implements NetworkMessages.IMessage<ClientPacketHandlerParticleMesh> {

	private static final ParticleHelper.MeshType[] MESH_TYPES = ParticleHelper.MeshType.values();
	private static final CommonProxy.ParticleType[] PARTICLE_TYPES = CommonProxy.ParticleType.values();
	private int mesh;
	private int particle;
	private Vec3d pos;
	private int range;
	private int color;

	public ClientPacketHandlerParticleMesh(ParticleHelper.MeshType mesh, CommonProxy.ParticleType particle, Vec3d pos, int range, int color) {
		this.mesh = mesh.ordinal();
		this.particle = particle.ordinal();
		this.pos = pos;
		this.range = range;
		this.color = color;
	}

	@Override
	public void handle(EntityPlayer player) {
		ParticleHelper.spawnParticleMesh(mesh >= 0 && mesh < MESH_TYPES.length ? MESH_TYPES[mesh] : ParticleHelper.MeshType.BURST, particle >= 0 && particle < PARTICLE_TYPES.length ? PARTICLE_TYPES[particle] : CommonProxy.ParticleType.Fluff, player.world, pos, range, color);
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeInt(mesh);
		packet.writeInt(particle);
		packet.writeDouble(pos.x);
		packet.writeDouble(pos.y);
		packet.writeDouble(pos.z);
		packet.writeInt(range);
		packet.writeInt(color);
	}

	@Override
	public ClientPacketHandlerParticleMesh fromBytes(PacketBuffer packet) {
		mesh = packet.readInt();
		particle = packet.readInt();
		pos = new Vec3d(packet.readDouble(), packet.readDouble(), packet.readDouble());
		range = packet.readInt();
		color = packet.readInt();
		return this;
	}
}
