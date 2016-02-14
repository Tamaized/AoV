package Tamaized.AoV.core.abilities;

import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import Tamaized.AoV.core.abilities.healer.CureModWounds;

public abstract class AbilityBase {
	
	private static Map<String, AbilityBase> map = new HashMap<String, AbilityBase>();
	
	public final int cost;
	public final double maxDistance;
	
	public final List<String> description;

	private static Map<String, Integer> decay = new HashMap<String, Integer>();
	
	public AbilityBase(int c, double d, String... desc){
		cost = c;
		maxDistance = d;
		description = new ArrayList<String>();
		for(String s : desc) description.add(s);
	}
	
	public void activate(EntityPlayer player, AoVData data, EntityLivingBase e){
		int trueCost = getTrueCost(data);
		if(trueCost <= data.getCurrentDivinePower()){
			data.setCurrentDivinePower(data.getCurrentDivinePower()-trueCost);
			doAction(player, data, e);
		}
	}
	
	public int getTrueCost(AoVData data){
		int tCost =  (int) (cost - Math.ceil(cost*(data.getCostReductionPerc()/100))) - data.getCostReductionFlat();
		return tCost < 1 ? 1 : tCost;
	}
	
	protected abstract void doAction(EntityPlayer player, AoVData data, EntityLivingBase e);
	
	protected void addXP(AoVData data, int amount){
		String w = data.getPlayer() == null ? "c" : data.getPlayer().worldObj.isRemote ? "c" : "s";
		int decay = getDecay(data.getPlayer()+"_"+w+"_"+getName());
		addDecay(data.getPlayer()+"_"+w+"_"+getName());
		if(decay < 1) decay = 1;
		int truexp = (int) Math.floor(amount/decay);
		data.addExp(truexp);
	}
	
	public static void register(){
		map = new HashMap<String, AbilityBase>();
		AbilityBase a;
		
		//Healer
		a = new CureLightWounds(4, 2, 4);
		map.put(a.getName(), a);
		
		a = new CureModWounds(8, 2, 8);
		map.put(a.getName(), a);

		//Caster
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
	
	public static void addDecay(String ability){
		decay.put(ability, decay.containsKey(ability) ? decay.get(ability)+1 : 2);
	}
	
	public static void updateDecay(){
		Iterator<Entry<String, Integer>> iter = decay.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, Integer> entry = iter.next();
			if(entry.getValue() < 1) iter.remove();
			else decay.put(entry.getKey(), entry.getValue()-1);
		}
	}
	
	public static int getDecay(String ability){
		return decay.containsKey(ability) ? decay.get(ability) : 1;
	}

}
