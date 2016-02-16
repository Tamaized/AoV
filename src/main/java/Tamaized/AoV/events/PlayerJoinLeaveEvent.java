package Tamaized.AoV.events;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import Tamaized.AoV.AoV;
import Tamaized.AoV.common.handlers.ServerPacketHandler;
import Tamaized.AoV.core.AoVCore;
import Tamaized.AoV.core.AoVData;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.skills.AoVSkill;

/**
 * 
 * The Client Side and Server Side are two different instances!!!
 *
 */
public class PlayerJoinLeaveEvent {
	
	@SubscribeEvent
	public void join(PlayerLoggedInEvent e){
		System.out.println("LOGGED IN");
		if(AoV.serverAoVCore == null) return;
		System.out.println("serverAoVCore NOT NULL; Starting setup");
		AoVCore core = AoV.serverAoVCore;
		AoVData dat = readNBT(e.player);
		core.setPlayer(e.player, dat);	
		ServerPacketHandler.sendAovDataPacket((EntityPlayerMP) e.player, dat);
	}
	
	@SubscribeEvent
	public void leave(PlayerLoggedOutEvent e){
		if(AoV.serverAoVCore == null) return;
		AoVCore core = AoV.serverAoVCore;
		AoVData dat = core.getPlayer(e.player);
		writeNBT(e.player, dat);
		core.removePlayer(e.player);
		System.out.println("LOGGED OUT");	
	}
	
	private AoVData readNBT(EntityPlayer player){
		NBTTagCompound ct = player.getEntityData().getCompoundTag("AoV");
		if(ct != null){
			AoVData data;
			NBTTagCompound skills = ct.getCompoundTag("skills");
			if(skills != null){
				ArrayList<AoVSkill> obtainedSkills = new ArrayList<AoVSkill>();
				for(String s : skills.getKeySet()){
					obtainedSkills.add(AoVSkill.getSkillFromName(s));
				}
				data = new AoVData(player, ct.getInteger("skillPointsMax"), ct.getInteger("skillPointsCurrent"), ct.getInteger("xp"), ct.getInteger("level"), obtainedSkills.toArray());
			}else{
				data = new AoVData(player, ct.getInteger("skillPointsMax"), ct.getInteger("skillPointsCurrent"), ct.getInteger("xp"), ct.getInteger("level"));
			}
			NBTTagCompound bar = ct.getCompoundTag("bar");
			if(bar != null){
				for(int i=0; i<9; i++){
					String s = bar.getString(String.valueOf(i));
					if(s == null) data.setSlot(null, i);
					else data.setSlot(AbilityBase.fromName(s), i);
				}
			}
			data.setCurrentDivinePower(ct.getInteger("currentDivinePower"));
			data.setMaxDivinePower(ct.getInteger("maxDivinePower"));
			data.updateVariables();
			return data;
		}
		return new AoVData(player).Construct();
	}
	
	private void writeNBT(EntityPlayer player, AoVData data){
		NBTTagCompound ct = new NBTTagCompound();{
			ct.setInteger("currentDivinePower", data.getCurrentDivinePower());
			ct.setInteger("maxDivinePower", data.getMaxDivinePower());
			ct.setInteger("skillPointsMax", data.getMaxSkillPoints());
			ct.setInteger("skillPointsCurrent", data.getCurrentSkillPoints());
			ct.setInteger("xp", data.getXP());
			ct.setInteger("level", data.getLevel());
			NBTTagCompound skills = new NBTTagCompound();{
				for(AoVSkill s : data.getAllObtainedSkills()){
					skills.setString(s.skillName, s.skillName);
				}
			}
			ct.setTag("skills", skills);
			NBTTagCompound bar = new NBTTagCompound();{
				for(int i=0; i<9; i++){
					AbilityBase ab = data.getSlot(i);
					if(ab == null) bar.setString(String.valueOf(i), "null");
					else bar.setString(String.valueOf(i), ab.getName());
				}
			}
			ct.setTag("bar", bar);
		}
		player.getEntityData().setTag("AoV", ct);
	}
}
