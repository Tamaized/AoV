package tamaized.aov.common.helper;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.network.NetworkDirection;
import tamaized.aov.AoV;
import tamaized.aov.client.gui.FloatyTextOverlay;
import tamaized.aov.network.client.ClientPacketHandlerFloatyText;

public class FloatyTextHelper {

	public static void sendText(PlayerEntity player, String text) {
		if (player instanceof ServerPlayerEntity)
			AoV.network.sendTo(new ClientPacketHandlerFloatyText(text), ((ServerPlayerEntity) player).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
		else
			FloatyTextOverlay.addFloatyText(text);
	}

}