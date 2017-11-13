package tamaized.aov.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.client.sound.EntityMovingSound;
import tamaized.tammodized.common.helper.MotionHelper;

public class ClientPacketHandlerMovingSound implements IMessageHandler<ClientPacketHandlerMovingSound.Packet, IMessage> {

	@SideOnly(Side.CLIENT)
	private static void processPacket(ClientPacketHandlerMovingSound.Packet message, @SuppressWarnings("unused") EntityPlayer player, World world) {
		Minecraft.getMinecraft().getSoundHandler().playSound(new EntityMovingSound(SoundEvent.REGISTRY.getObjectById(message.soundID), SoundCategory.PLAYERS, world.getEntityByID(message.e), false, 0, message.volume, message.pitch));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(ClientPacketHandlerMovingSound.Packet message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> processPacket(message, Minecraft.getMinecraft().player, Minecraft.getMinecraft().world));
		return null;
	}

	public static class Packet implements IMessage {

		private int e;
		private int soundID;
		private float volume;
		private float pitch;

		@SuppressWarnings("unused")
		public Packet() {

		}

		public Packet(Entity entity, int sound) {
			e = entity.getEntityId();
			soundID = sound;
		}

		public Packet(Entity entity, int sound, float v, float p){
			this(entity, sound);
			volume = v;
			pitch = p;
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			e = buf.readInt();
			soundID = buf.readInt();
			volume = buf.readFloat();
			pitch = buf.readFloat();
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(e);
			buf.writeInt(soundID);
			buf.writeFloat(volume);
			buf.writeFloat(pitch);
		}
	}
}
