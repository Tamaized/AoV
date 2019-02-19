package tamaized.aov.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.astro.IAstroCapability;

public class ClientPacketHandlerAstroData implements IMessageHandler<ClientPacketHandlerAstroData.Packet, IMessage> {

	@SideOnly(Side.CLIENT)
	private static void processPacket(ClientPacketHandlerAstroData.Packet message, World world) {
		Entity e = world.getEntityByID(message.entityID);
		if (e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e;
			if (player.hasCapability(CapabilityList.ASTRO, null)) {
				IAstroCapability cap = player.getCapability(CapabilityList.ASTRO, null);
				if (cap != null) {
					cap.setDraw(message.draw);
					cap.setBurn(message.burn);
					cap.setSpread(message.spread);
					cap.setDrawTime(message.drawTime);
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(ClientPacketHandlerAstroData.Packet message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> processPacket(message, Minecraft.getMinecraft().world));
		return null;
	}

	public static class Packet implements IMessage {

		private int entityID;
		private IAstroCapability.ICard draw;
		private IAstroCapability.ICard burn;
		private IAstroCapability.ICard spread;
		private int drawTime;

		@SuppressWarnings("unused")
		public Packet() {

		}

		public Packet(EntityPlayer player) {
			if (player.hasCapability(CapabilityList.ASTRO, null)) {
				IAstroCapability cap = player.getCapability(CapabilityList.ASTRO, null);
				if (cap == null)
					return;
				entityID = player.getEntityId();
				draw = cap.getDraw();
				burn = cap.getBurn();
				spread = cap.getSpread();
				drawTime = cap.getDrawTime();
			}
		}

		@Override
		public void fromBytes(ByteBuf stream) {
			entityID = stream.readInt();
			draw = IAstroCapability.ICard.getCardFromID(stream.readInt());
			burn = IAstroCapability.ICard.getCardFromID(stream.readInt());
			spread = IAstroCapability.ICard.getCardFromID(stream.readInt());
			drawTime = stream.readInt();
		}

		@Override
		public void toBytes(ByteBuf stream) {
			stream.writeInt(entityID);
			stream.writeInt(IAstroCapability.ICard.getCardID(draw));
			stream.writeInt(IAstroCapability.ICard.getCardID(burn));
			stream.writeInt(IAstroCapability.ICard.getCardID(spread));
			stream.writeInt(drawTime);
		}
	}
}
