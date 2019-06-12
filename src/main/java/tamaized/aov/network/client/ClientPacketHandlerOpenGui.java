package tamaized.aov.network.client;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import tamaized.aov.common.gui.GuiHandler;
import tamaized.aov.network.NetworkMessages;

public class ClientPacketHandlerOpenGui implements NetworkMessages.IMessage<ClientPacketHandlerOpenGui> {

	private int id;

	public ClientPacketHandlerOpenGui(int id) {
		this.id = id;
	}

	@Override
	public void handle(PlayerEntity player) {
		Minecraft.getInstance().displayGuiScreen(GuiHandler.getGui(id));
	}

	@Override
	public void toBytes(PacketBuffer packet) {
		packet.writeInt(id);
	}

	@Override
	public ClientPacketHandlerOpenGui fromBytes(PacketBuffer packet) {
		id = packet.readInt();
		return this;
	}
}
