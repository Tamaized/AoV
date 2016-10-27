package Tamaized.AoV.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
 * The Client Side and Server Side are two different instances!!!
 */
public class PlayerJoinLeaveEvent {

	@SubscribeEvent
	public void join(PlayerLoggedInEvent e) {
		if (AoV.serverAoVCore == null) return;
		AoVCore core = AoV.serverAoVCore;
		AoVData dat = readNBT(e.player);
		core.setPlayer(e.player, dat);
		System.out.println(core.getPlayer(e.player));
		ServerPacketHandler.sendAovDataPacket((EntityPlayerMP) e.player, dat);
	}

	@SubscribeEvent
	public void leave(PlayerLoggedOutEvent e) {
		if (AoV.serverAoVCore == null) return;
		AoVCore core = AoV.serverAoVCore;
		AoVData dat = core.getPlayer(e.player);
		writeNBT(e.player, dat);
		core.removePlayer(e.player);
	}

	private AoVData readNBT(EntityPlayer player) {
		NBTTagCompound ct = player.getEntityData().getCompoundTag("AoV");
		if (ct != null) {
			AoVData data;
			NBTTagCompound skills = ct.getCompoundTag("skills");
			if (skills != null) {
				ArrayList<AoVSkill> obtainedSkills = new ArrayList<AoVSkill>();
				for (String s : skills.getKeySet()) {
					obtainedSkills.add(AoVSkill.getSkillFromName(s));
				}
				data = new AoVData(player, ct.getInteger("skillPointsMax"), ct.getInteger("skillPointsCurrent"), ct.getInteger("xp"), ct.getInteger("level"), obtainedSkills.toArray());
			} else {
				data = new AoVData(player, ct.getInteger("skillPointsMax"), ct.getInteger("skillPointsCurrent"), ct.getInteger("xp"), ct.getInteger("level"));
			}
			NBTTagCompound charges = ct.getCompoundTag("charges");
			Map<AbilityBase, Integer> chargeMap = new HashMap<AbilityBase, Integer>();
			System.out.println(charges);
			if (charges != null) {
				for (int i = charges.getInteger("amount") - 1; i >= 0; i--) {
					NBTTagCompound comp = charges.getCompoundTag(String.valueOf(i));
					System.out.println(i);
					System.out.println(comp);
					if (comp != null) chargeMap.put(AbilityBase.fromName(comp.getString("dataS")), comp.getInteger("dataI"));
				}
				data.setAbilityCharges(chargeMap);
			}
			NBTTagCompound bar = ct.getCompoundTag("bar");
			if (bar != null) {
				for (int i = 0; i < 9; i++) {
					String s = bar.getString(String.valueOf(i));
					if (s == null) data.setSlot(null, i);
					else data.setSlot(AbilityBase.fromName(s), i);
				}
			}
			data.invokeMass = ct.getBoolean("invokeMass");
			data.updateVariables();
			return data;
		}
		return new AoVData(player).Construct();
	}

	private void writeNBT(EntityPlayer player, AoVData data) {
		NBTTagCompound ct = new NBTTagCompound();
		{
			ct.setInteger("skillPointsMax", data.getMaxSkillPoints());
			ct.setInteger("skillPointsCurrent", data.getCurrentSkillPoints());
			ct.setInteger("xp", data.getXP());
			ct.setInteger("level", data.getLevel());
			ct.setBoolean("invokeMass", data.invokeMass);
			NBTTagCompound skills = new NBTTagCompound();
			{
				for (AoVSkill s : data.getAllObtainedSkills()) {
					skills.setString(s.skillName, s.skillName);
				}
			}
			ct.setTag("skills", skills);
			NBTTagCompound charges = new NBTTagCompound();
			{
				if (!data.getAbilityCharges().isEmpty()) {
					Iterator<Entry<AbilityBase, Integer>> iter = data.getAbilityCharges().entrySet().iterator();
					int id = 0;
					while (iter.hasNext()) {
						Entry<AbilityBase, Integer> entry = iter.next();
						NBTTagCompound tag = new NBTTagCompound();
						tag.setString("dataS", entry.getKey().name);
						tag.setInteger("dataI", entry.getValue());
						charges.setTag(String.valueOf(id), tag);
						id++;
					}
					charges.setInteger("amount", id);
				}
			}
			ct.setTag("charges", charges);
			NBTTagCompound bar = new NBTTagCompound();
			{
				for (int i = 0; i < 9; i++) {
					AbilityBase ab = data.getSlot(i);
					if (ab == null) bar.setString(String.valueOf(i), "null");
					else bar.setString(String.valueOf(i), ab.getName());
				}
			}
			ct.setTag("bar", bar);
		}
		player.getEntityData().setTag("AoV", ct);
	}
}
