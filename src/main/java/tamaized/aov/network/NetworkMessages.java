package tamaized.aov.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import tamaized.aov.network.client.ClientPacketHandlerAoVData;
import tamaized.aov.network.client.ClientPacketHandlerAoVSimpleData;
import tamaized.aov.network.client.ClientPacketHandlerAstroAnimation;
import tamaized.aov.network.client.ClientPacketHandlerAstroData;
import tamaized.aov.network.client.ClientPacketHandlerLeap;
import tamaized.aov.network.client.ClientPacketHandlerMovingSound;
import tamaized.aov.network.client.ClientPacketHandlerParticle;
import tamaized.aov.network.client.ClientPacketHandlerParticleMesh;
import tamaized.aov.network.client.ClientPacketHandlerPolymorphDogAttack;
import tamaized.aov.network.client.ClientPacketHandlerStunned;
import tamaized.aov.network.server.ServerPacketHandlerPolymorphDogAttack;
import tamaized.aov.network.server.ServerPacketHandlerSpellSkill;

import java.util.function.Supplier;

public class NetworkMessages {

	private static int index = 0;

	public static void register(SimpleChannel network) {
		registerMessage(network, ServerPacketHandlerSpellSkill.class, IMessage.Side.SERVER);
		registerMessage(network, ServerPacketHandlerPolymorphDogAttack.class, IMessage.Side.SERVER);

		registerMessage(network, ClientPacketHandlerAoVData.class, IMessage.Side.CLIENT);
		registerMessage(network, ClientPacketHandlerAstroData.class, IMessage.Side.CLIENT);
		registerMessage(network, ClientPacketHandlerAstroAnimation.class, IMessage.Side.CLIENT);
		registerMessage(network, ClientPacketHandlerMovingSound.class, IMessage.Side.CLIENT);
		registerMessage(network, ClientPacketHandlerStunned.class, IMessage.Side.CLIENT);
		registerMessage(network, ClientPacketHandlerParticleMesh.class, IMessage.Side.CLIENT);
		registerMessage(network, ClientPacketHandlerLeap.class, IMessage.Side.CLIENT);
		registerMessage(network, ClientPacketHandlerPolymorphDogAttack.class, IMessage.Side.CLIENT);
		registerMessage(network, ClientPacketHandlerParticle.class, IMessage.Side.CLIENT);
		registerMessage(network, ClientPacketHandlerAoVSimpleData.class, IMessage.Side.CLIENT);
	}

	private static <M extends IMessage<M>> void registerMessage(SimpleChannel network, Class<M> type, IMessage.Side side) {
		network.registerMessage(index++, type, IMessage::encode, p -> IMessage.decode(p, type), (m, s) -> IMessage.onMessage(m, s, side));
	}

	public interface IMessage<SELF extends IMessage<SELF>> {

		static <M extends IMessage<M>> void encode(M message, PacketBuffer packet) {
			message.toBytes(packet);
		}

		static <M extends IMessage<M>> M decode(PacketBuffer packet, Class<M> type) {
			try {
				return type.newInstance().fromBytes(packet);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}

		static void onMessage(IMessage message, Supplier<NetworkEvent.Context> context, Side side) {
			switch (side) {
				case CLIENT:
					Minecraft.getInstance().addScheduledTask(() -> message.handle(Minecraft.getInstance().player));
					break;
				case SERVER:
					EntityPlayerMP player = context.get().getSender();
					if (player != null)
						player.getServerWorld().addScheduledTask(() -> message.handle(player));
					break;
			}
		}

		void handle(EntityPlayer player);

		void toBytes(PacketBuffer packet);

		SELF fromBytes(PacketBuffer packet);

		enum Side {
			CLIENT, SERVER
		}

	}
}
