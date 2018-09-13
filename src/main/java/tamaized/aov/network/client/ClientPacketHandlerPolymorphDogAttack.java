package tamaized.aov.network.client;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.tammodized.common.helper.CapabilityHelper;

public class ClientPacketHandlerPolymorphDogAttack implements IMessageHandler<ClientPacketHandlerPolymorphDogAttack.Packet, IMessage> {

	@SideOnly(Side.CLIENT)
	private static void processPacket(EntityPlayer player) {
		IPolymorphCapability cap = CapabilityHelper.getCap(player, CapabilityList.POLYMORPH, null);
		if (cap != null)
			cap.doAttack(player, true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(ClientPacketHandlerPolymorphDogAttack.Packet message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> processPacket(Minecraft.getMinecraft().player));
		return null;
	}

	public static class Packet implements IMessage {

		public Packet() {

		}

		@Override
		public void fromBytes(ByteBuf stream) {

		}

		@Override
		public void toBytes(ByteBuf stream) {

		}

	}
}
