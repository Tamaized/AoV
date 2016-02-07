package Tamaized.AoV.core.abilities;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import Tamaized.AoV.AoV;
import Tamaized.AoV.common.handlers.ClientPacketHandler;
import Tamaized.AoV.common.handlers.ServerPacketHandler;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.healer.CureLightWounds;

public abstract class AbilityBase {
	
	private static Map<String, AbilityBase> map = new HashMap<String, AbilityBase>();
	
	public final int cost;
	public final double maxDistance;
	
	public AbilityBase(int c, double d){
		cost = c;
		maxDistance = d;
	}
	
	public void activate(EntityPlayer player, AoVData data, Entity e){
		int trueCost = (int) ((float) cost*(1f+data.getCostReductionPerc())) - data.getCostReductionFlat();
		if(trueCost <= data.getCurrentDivinePower()){
			data.setCurrentDivinePower(data.getCurrentDivinePower()-trueCost);
			doAction(player, data, e);
		}
	}
	
	protected abstract void doAction(EntityPlayer player, AoVData data, Entity e);
	
	public abstract String getName();
	
	public static void register(){
		map = new HashMap<String, AbilityBase>();
		AbilityBase a;
		
		a = new CureLightWounds(4, 2);
		map.put(a.getName(), a);
		
		a = null;
	}
	
	public static AbilityBase fromName(String n){
		return map.get(n);
	}
	
	protected static void sendPacketTypeTarget(String abilityName, int entityID){
		
	}
	
	protected static void sendPacketTypeSelf(String abilityName){
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ServerPacketHandler.TYPE_SPELLCAST_SELF);
			outputStream.writeUTF(abilityName);
			FMLProxyPacket pkt = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
			if(AoV.channel != null && pkt != null) AoV.channel.sendToServer(pkt);
			bos.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
