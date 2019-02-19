package tamaized.aov.network.client;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.astro.IAstroCapability;
import tamaized.aov.network.NetworkMessages;

public class ClientPacketHandlerAstroAnimation implements NetworkMessages.IMessage<ClientPacketHandlerAstroAnimation> {

	private int entityID;
	private IAstroCapability.IAnimation animation;

	public ClientPacketHandlerAstroAnimation(EntityLivingBase entity, IAstroCapability.IAnimation a) {
		IAstroCapability cap = CapabilityList.getCap(entity, CapabilityList.ASTRO, null);
		if (cap == null)
			return;
		entityID = entity.getEntityId();
		animation = a;
	}

	@Override
	public void handle(EntityPlayer player) {
		Entity e = player.world.getEntityByID(entityID);
		if (e instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) e;
			IAstroCapability cap = CapabilityList.getCap(player, CapabilityList.ASTRO);
			if (cap != null)
				cap.playAnimation(p, animation);
		}
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeInt(entityID);
		packet.writeInt(IAstroCapability.IAnimation.getAnimationID(animation));
	}

	@Override
	public ClientPacketHandlerAstroAnimation fromBytes(PacketBuffer packet) {
		entityID = packet.readInt();
		animation = IAstroCapability.IAnimation.getAnimationFromID(packet.readInt());
		return this;
	}
}
