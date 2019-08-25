package tamaized.aov.network.client;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import tamaized.aov.network.NetworkMessages;

import java.util.Objects;
import java.util.UUID;

// hi-jacked SSpawnObjectPacket
public class ClientPacketHandlerSpawnNonLivingEntity implements NetworkMessages.IMessage<ClientPacketHandlerSpawnNonLivingEntity> {

	private int entityId;
	private UUID uniqueId;
	private double x;
	private double y;
	private double z;
	private int speedX;
	private int speedY;
	private int speedZ;
	private int pitch;
	private int yaw;
	private EntityType<?> type;

	public ClientPacketHandlerSpawnNonLivingEntity(Entity entity) {
		this.entityId = entity.getEntityId();
		this.uniqueId = entity.getUniqueID();
		this.x = entity.posX;
		this.y = entity.posY;
		this.z = entity.posZ;
		this.pitch = MathHelper.floor(entity.rotationPitch * 256.0F / 360.0F);
		this.yaw = MathHelper.floor(entity.rotationYaw * 256.0F / 360.0F);
		this.type = entity.getType();
		Vec3d motion = entity.getMotion();
		this.speedX = (int) (MathHelper.clamp(motion.x, -3.9D, 3.9D) * 8000.0D);
		this.speedY = (int) (MathHelper.clamp(motion.y, -3.9D, 3.9D) * 8000.0D);
		this.speedZ = (int) (MathHelper.clamp(motion.z, -3.9D, 3.9D) * 8000.0D);
	}

	@Override
	public void handle(PlayerEntity player) {
		World world = player.world;
		Entity entity = type.create(world);
		Objects.requireNonNull(entity).func_213312_b(x, y, z);
		entity.setMotion(speedX, speedY, speedZ);
		entity.rotationPitch = (float) (pitch * 360) / 256.0F;
		entity.rotationYaw = (float) (yaw * 360) / 256.0F;
		entity.setEntityId(entityId);
		entity.setUniqueId(uniqueId);
		((ClientWorld) world).addEntity(entityId, entity);
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeVarInt(this.entityId);
		packet.writeUniqueId(this.uniqueId);
		packet.writeVarInt(Registry.ENTITY_TYPE.getId(this.type));
		packet.writeDouble(this.x);
		packet.writeDouble(this.y);
		packet.writeDouble(this.z);
		packet.writeByte(this.pitch);
		packet.writeByte(this.yaw);
		packet.writeShort(this.speedX);
		packet.writeShort(this.speedY);
		packet.writeShort(this.speedZ);
	}

	@Override
	public ClientPacketHandlerSpawnNonLivingEntity fromBytes(PacketBuffer packet) {
		this.entityId = packet.readVarInt();
		this.uniqueId = packet.readUniqueId();
		this.type = Registry.ENTITY_TYPE.getByValue(packet.readVarInt());
		this.x = packet.readDouble();
		this.y = packet.readDouble();
		this.z = packet.readDouble();
		this.pitch = packet.readByte();
		this.yaw = packet.readByte();
		this.speedX = packet.readShort();
		this.speedY = packet.readShort();
		this.speedZ = packet.readShort();
		return this;
	}
}
