package tamaized.aov.registry;

import com.google.common.collect.Lists;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.AoV;
import tamaized.aov.network.client.ClientPacketHandlerParticle;

import java.util.List;

public class ParticleRegistry {

	private static final List<IParticleHandler> HANDLERS = Lists.newArrayList();

	public static int register(IParticleHandler handler) {
		HANDLERS.add(handler);
		return HANDLERS.size() - 1;
	}

	public static IParticleHandler getHandlerFromID(int id) {
		return id < 0 || id >= HANDLERS.size() ? null : HANDLERS.get(id);
	}

	public static void spawnFromServer(World world, int id, double x, double y, double z, double dx, double dy, double dz, int... data) {
		AoV.network.sendToAllAround(new ClientPacketHandlerParticle.Packet(id, x, y, z, dx, dy, dz, data), new NetworkRegistry.TargetPoint(world.provider.getDimension(), x, y, z, 64));
	}

	public interface IParticleHandler {

		@SideOnly(Side.CLIENT)
		void execute(ParticleManager manager, World world, double x, double y, double z, double dx, double dy, double dz, int... data);

	}

}
