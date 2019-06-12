package tamaized.aov.common.helper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import tamaized.aov.AoV;
import tamaized.aov.network.client.ClientPacketHandlerPlayerMotion;

public class MotionHelper {

	public static void addMotion(Entity e, Vec3d vel) {
		if (e instanceof ServerPlayerEntity)
			sendPacketToPlayer((ServerPlayerEntity) e, vel);
		else
			e.addVelocity(vel.x, vel.y, vel.z);
	}

	private static void sendPacketToPlayer(ServerPlayerEntity e, Vec3d vel) {
		AoV.network.send(PacketDistributor.PLAYER.with(() -> e), new ClientPacketHandlerPlayerMotion(e, vel));
	}

	@Deprecated
	@OnlyIn(Dist.CLIENT)
	public static void updatePlayerMotion(double x, double y, double z) {
		net.minecraft.client.Minecraft.getInstance().player.addVelocity(x, y, z);
	}

}
