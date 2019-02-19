package tamaized.aov.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.helper.ParticleHelper;
import tamaized.aov.proxy.CommonProxy;

public class ClientPacketHandlerParticleMesh implements IMessageHandler<ClientPacketHandlerParticleMesh.Packet, IMessage> {

	private static final ParticleHelper.MeshType[] MESH_TYPES = ParticleHelper.MeshType.values();
	private static final CommonProxy.ParticleType[] PARTICLE_TYPES = CommonProxy.ParticleType.values();

	@SideOnly(Side.CLIENT)
	private static void processPacket(ClientPacketHandlerParticleMesh.Packet message, @SuppressWarnings("unused") EntityPlayer player, World world) {
		ParticleHelper.spawnParticleMesh(message.mesh >= 0 && message.mesh < MESH_TYPES.length ? MESH_TYPES[message.mesh] : ParticleHelper.MeshType.BURST, message.particle >= 0 && message.particle < PARTICLE_TYPES.length ? PARTICLE_TYPES[message.particle] : CommonProxy.ParticleType.Fluff, world, message.pos, message.range, message.color);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(ClientPacketHandlerParticleMesh.Packet message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> processPacket(message, Minecraft.getMinecraft().player, Minecraft.getMinecraft().world));
		return null;
	}

	public static class Packet implements IMessage {

		private int mesh;
		private int particle;
		private Vec3d pos;
		private int range;
		private int color;

		@SuppressWarnings("unused")
		public Packet() {

		}

		public Packet(ParticleHelper.MeshType mesh, CommonProxy.ParticleType particle, Vec3d pos, int range, int color) {
			this.mesh = mesh.ordinal();
			this.particle = particle.ordinal();
			this.pos = pos;
			this.range = range;
			this.color = color;
		}


		@Override
		public void fromBytes(ByteBuf buf) {
			mesh = buf.readInt();
			particle = buf.readInt();
			pos = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
			range = buf.readInt();
			color = buf.readInt();
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(mesh);
			buf.writeInt(particle);
			buf.writeDouble(pos.x);
			buf.writeDouble(pos.y);
			buf.writeDouble(pos.z);
			buf.writeInt(range);
			buf.writeInt(color);
		}
	}
}
