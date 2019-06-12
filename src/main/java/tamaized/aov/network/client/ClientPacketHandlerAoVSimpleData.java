package tamaized.aov.network.client;

import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.capabilities.polymorph.IPolymorphCapability;
import tamaized.aov.common.core.abilities.Aura;
import tamaized.aov.network.NetworkMessages;

import java.util.List;

public class ClientPacketHandlerAoVSimpleData implements NetworkMessages.IMessage<ClientPacketHandlerAoVSimpleData> {

	private int id;
	private List<Aura> auras;
	private IPolymorphCapability.Morph polymorph;
	private byte renderBits;

	public ClientPacketHandlerAoVSimpleData(IAoVCapability cap, IPolymorphCapability poly, int entityID) {
		auras = cap.getAuras();
		polymorph = poly.getMorph();
		renderBits = poly.getFlagBits();
		id = entityID;
	}

	@Override
	public void handle(PlayerEntity player) {
		Entity entity = player.world.getEntityByID(id);
		IAoVCapability cap = CapabilityList.getCap(entity, CapabilityList.AOV);
		if (cap != null) {
			cap.clearAuras();
			for (Aura aura : auras)
				cap.addAura(aura);
		}
		IPolymorphCapability poly = CapabilityList.getCap(entity, CapabilityList.POLYMORPH);
		if (poly != null) {
			poly.morph(polymorph);
			poly.setFlagBits(renderBits);
		}
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeInt(id);
		packet.writeInt(auras.size());
		for (Aura aura : auras)
			aura.encode(packet);
		packet.writeInt(polymorph == null ? -1 : polymorph.ordinal());
		packet.writeByte(renderBits);
	}

	@Override
	public ClientPacketHandlerAoVSimpleData fromBytes(PacketBuffer packet) {
		id = packet.readInt();
		auras = Lists.newArrayList();
		int size = packet.readInt();
		for (int index = 0; index < size; index++) {
			auras.add(Aura.construct(packet));
		}
		polymorph = IPolymorphCapability.Morph.getMorph(packet.readInt());
		renderBits = packet.readByte();
		return this;
	}
}
