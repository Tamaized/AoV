package tamaized.aov.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SSpawnObjectPacket;

import java.util.Objects;

public class SpawnEntityPacket extends SSpawnObjectPacket {

	public SpawnEntityPacket(Entity e) {
		super(e);
	}

	@Override
	public void processPacket(IClientPlayNetHandler handler) {
		ClientWorld world = Minecraft.getInstance().world;
		Entity entity = getType().create(world);
		Objects.requireNonNull(entity).func_213312_b(getX(), getY(), getZ());
		entity.setMotion(func_218693_g(), func_218695_h(), func_218692_i());
		entity.rotationPitch = (float) (getPitch() * 360) / 256.0F;
		entity.rotationYaw = (float) (getYaw() * 360) / 256.0F;
		entity.setEntityId(getEntityID());
		entity.setUniqueId(getUniqueId());
		world.addEntity(getEntityID(), entity);
	}
}
