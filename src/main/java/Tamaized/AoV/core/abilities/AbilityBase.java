package Tamaized.AoV.core.abilities;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import Tamaized.AoV.AoV;
import Tamaized.AoV.common.handlers.ServerPacketHandler;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.caster.NimbusRay;
import Tamaized.AoV.core.abilities.healer.CureLightWounds;

public abstract class AbilityBase {
	
	private static Map<String, AbilityBase> map = new HashMap<String, AbilityBase>();
	
	public final int cost;
	public final double maxDistance;
	
	public final List<String> description;
	
	public AbilityBase(int c, double d, String... desc){
		cost = c;
		maxDistance = d;
		description = new ArrayList<String>();
		for(String s : desc) description.add(s);
	}
	
	public void activate(EntityPlayer player, AoVData data, EntityLivingBase e){
		int trueCost = (int) ((float) cost*(1f+data.getCostReductionPerc())) - data.getCostReductionFlat();
		if(trueCost <= data.getCurrentDivinePower()){
			data.setCurrentDivinePower(data.getCurrentDivinePower()-trueCost);
			doAction(player, data, e);
		}
	}
	
	protected abstract void doAction(EntityPlayer player, AoVData data, EntityLivingBase e);
	
	public static void register(){
		map = new HashMap<String, AbilityBase>();
		AbilityBase a;
		
		a = new CureLightWounds(4, 2, 4);
		map.put(a.getName(), a);

		a = new NimbusRay(6, 20, 4);
		map.put(a.getName(), a);
		
		a = null;
	}
	
	public abstract ResourceLocation getIcon();
	
	public abstract String getName();
	public static AbilityBase fromName(String n){
		return map.get(n);
	}
	
	protected static void sendPacketTypeTarget(String abilityName, int entityID){
		ByteBufOutputStream bos = new ByteBufOutputStream(Unpooled.buffer());
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(ServerPacketHandler.TYPE_SPELLCAST_TARGET);
			outputStream.writeUTF(abilityName);
			outputStream.writeInt(entityID);
			FMLProxyPacket pkt = new FMLProxyPacket(new PacketBuffer(bos.buffer()), AoV.networkChannelName);
			if(AoV.channel != null && pkt != null) AoV.channel.sendToServer(pkt);
			bos.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
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
