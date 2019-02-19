package tamaized.aov.network.client;

import com.google.common.collect.Lists;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Aura;
import tamaized.tammodized.common.helper.CapabilityHelper;

import java.util.List;

public class ClientPacketHandlerAoVSimpleData implements IMessageHandler<ClientPacketHandlerAoVSimpleData.Packet, IMessage> {

	@SideOnly(Side.CLIENT)
	private static void processPacket(ClientPacketHandlerAoVSimpleData.Packet message, World world) {
		Entity entity = world.getEntityByID(message.id);
		IAoVCapability cap = CapabilityHelper.getCap(entity, CapabilityList.AOV, null);
		if (cap != null) {
			cap.clearAuras();
			for (Aura aura : message.auras)
				cap.addAura(aura);
		}
		IPolymorphCapability poly = CapabilityHelper.getCap(entity, CapabilityList.POLYMORPH, null);
		if (poly != null) {
			poly.morph(message.polymorph);
			poly.setFlagBits(message.renderBits);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IMessage onMessage(ClientPacketHandlerAoVSimpleData.Packet message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(() -> processPacket(message, Minecraft.getMinecraft().world));
		return null;
	}

	public static class Packet implements IMessage {

		private int id;
		private List<Aura> auras = Lists.newArrayList();
		private IPolymorphCapability.Morph polymorph;
		private byte renderBits;

		@SuppressWarnings("unused")
		public Packet() {

		}

		public Packet(IAoVCapability cap, IPolymorphCapability poly, int entityID) {
			auras = cap.getAuras();
			polymorph = poly.getMorph();
			renderBits = poly.getFlagBits();
			id = entityID;
		}

		@Override
		public void fromBytes(ByteBuf stream) {
			id = stream.readInt();
			int size = stream.readInt();
			for (int index = 0; index < size; index++) {
				auras.add(Aura.construct(stream));
			}
			polymorph = IPolymorphCapability.Morph.getMorph(stream.readInt());
			renderBits = stream.readByte();
		}

		@Override
		public void toBytes(ByteBuf stream) {
			stream.writeInt(id);
			stream.writeInt(auras.size());
			for (Aura aura : auras)
				aura.encode(stream);
			stream.writeInt(polymorph == null ? -1 : polymorph.ordinal());
			stream.writeByte(renderBits);
		}
	}
}
