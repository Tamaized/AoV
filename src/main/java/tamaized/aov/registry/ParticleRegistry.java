package tamaized.aov.registry;

import com.google.common.collect.Lists;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import tamaized.aov.AoV;
import tamaized.aov.network.client.ClientPacketHandlerParticle;

import java.util.List;
import java.util.function.Supplier;

public class ParticleRegistry {

	private static final List<Supplier<IParticleHandler>> HANDLERS = Lists.newArrayList();

	public static int register(Supplier<IParticleHandler> handler) {
		HANDLERS.add(handler);
		return HANDLERS.size() - 1;
	}

	public static IParticleHandler getHandlerFromID(int id) {
		return id < 0 || id >= HANDLERS.size() ? null : HANDLERS.get(id).get();
	}

	public static void spawnFromServer(World world, int id, double x, double y, double z, double dx, double dy, double dz, int... data) {
		AoV.network.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(new BlockPos(x, y, z))), new ClientPacketHandlerParticle(id, x, y, z, dx, dy, dz, data));
	}

	public interface IParticleHandler {

		void execute(ParticleManager manager, World world, double x, double y, double z, double dx, double dy, double dz, int... data);

	}

}
