package tamaized.aov.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.astro.IAstroCapability;

public class ClientPacketHandlerAstroAnimation implements IMessageHandler<ClientPacketHandlerAstroAnimation.Packet, IMessage> {

	@SideOnly(Side.CLIENT)
	private static void processPacket(ClientPacketHandlerAstroAnimation.Packet message, World world) {
		Entity e = world.getEntityByID(message.entityID);
		if (e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e;
			if (player.hasCapability(CapabilityList.ASTRO, null)) {
				IAstroCapability cap = player.getCapability(CapabilityList.ASTRO, null);
				if (cap != null) {
					cap.playAnimation(player, message.animation);
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(ClientPacketHandlerAstroAnimation.Packet message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> processPacket(message, Minecraft.getMinecraft().world));
		return null;
	}

	public static class Packet implements IMessage {

		private int entityID;
		private IAstroCapability.IAnimation animation;

		@SuppressWarnings("unused")
		public Packet() {

		}

		public Packet(EntityLivingBase entity, IAstroCapability.IAnimation a) {
			if (entity.hasCapability(CapabilityList.ASTRO, null)) {
				IAstroCapability cap = entity.getCapability(CapabilityList.ASTRO, null);
				if (cap == null)
					return;
				entityID = entity.getEntityId();
				animation = a;
			}
		}

		@Override
		public void fromBytes(ByteBuf stream) {
			entityID = stream.readInt();
			animation = IAstroCapability.IAnimation.getAnimationFromID(stream.readInt());
		}

		@Override
		public void toBytes(ByteBuf stream) {
			stream.writeInt(entityID);
			stream.writeInt(IAstroCapability.IAnimation.getAnimationID(animation));
		}
	}
}
