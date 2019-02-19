package tamaized.aov.network.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.network.NetworkMessages;

public class ClientPacketHandlerAstroData implements NetworkMessages.IMessage<ClientPacketHandlerAstroData> {

	private int entityID;
	private IAstroCapability.ICard draw;
	private IAstroCapability.ICard burn;
	private IAstroCapability.ICard spread;
	private int drawTime;

	public ClientPacketHandlerAstroData(EntityPlayer player) {
		IAstroCapability cap = CapabilityList.getCap(player, CapabilityList.ASTRO);
		if (cap == null)
			return;
		entityID = player.getEntityId();
		draw = cap.getDraw();
		burn = cap.getBurn();
		spread = cap.getSpread();
		drawTime = cap.getDrawTime();
	}

	@Override
	public void handle(EntityPlayer player) {
		Entity e = player.world.getEntityByID(entityID);
		if (e instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) e;
			IAstroCapability cap = CapabilityList.getCap(p, CapabilityList.ASTRO);
			if (cap != null) {
				cap.setDraw(draw);
				cap.setBurn(burn);
				cap.setSpread(spread);
				cap.setDrawTime(drawTime);
			}
		}
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeInt(entityID);
		packet.writeInt(IAstroCapability.ICard.getCardID(draw));
		packet.writeInt(IAstroCapability.ICard.getCardID(burn));
		packet.writeInt(IAstroCapability.ICard.getCardID(spread));
		packet.writeInt(drawTime);
	}

	@Override
	public ClientPacketHandlerAstroData fromBytes(PacketBuffer packet) {
		entityID = packet.readInt();
		draw = IAstroCapability.ICard.getCardFromID(packet.readInt());
		burn = IAstroCapability.ICard.getCardFromID(packet.readInt());
		spread = IAstroCapability.ICard.getCardFromID(packet.readInt());
		drawTime = packet.readInt();
		return this;
	}
}
