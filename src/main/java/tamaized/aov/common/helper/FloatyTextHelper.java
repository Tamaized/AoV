package tamaized.aov.common.helper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.network.NetworkDirection;
import tamaized.aov.AoV;
import tamaized.aov.client.gui.FloatyTextOverlay;
import tamaized.aov.network.client.ClientPacketHandlerFloatyText;

public class FloatyTextHelper {

	public static void sendText(EntityPlayer player, String text) {
		if (player instanceof EntityPlayerMP)
			AoV.network.sendTo(new ClientPacketHandlerFloatyText(text), ((EntityPlayerMP) player).connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
		else
			FloatyTextOverlay.addFloatyText(text);
	}

}