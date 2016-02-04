package Tamaized.AoV.core;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import Tamaized.AoV.core.skills.AoVSkill;

public class AoVData {
	
	private EntityPlayer player;
	private ArrayList<AoVSkill> obtainedSkills;
	private int skillPoints;
	private int currentPoints;
	private int exp;
	
	//On Server this needs to be handled by skills; we then send the information to the client everytime it changes, dont do it every tick!
	private int currPower = 0;
	private int maxPower = 0;
	
	public AoVData(){
		obtainedSkills = new ArrayList<AoVSkill>();
	}
	
	public AoVData(EntityPlayer p){
		this();
		player = p;
	}
	
	public AoVData(EntityPlayer p, int sPoints, int cPoints, int xp){
		this(p);
		skillPoints = sPoints;
		currentPoints = cPoints;
		exp = xp;
	}
	
	public AoVData(EntityPlayer p, int sPoints, int cPoints, int xp, AoVSkill... skill){
		this(p, sPoints, cPoints, xp);
		setObtainedSkills(skill);
	}
	
	public AoVData Construct(){
		skillPoints = 1;
		currentPoints = 1;
		exp = 0;
		return this;
	}
	
	public void setObtainedSkills(AoVSkill... skill){
		obtainedSkills.clear();
		for(AoVSkill s : skill){
			obtainedSkills.add(s);
		}
	}
	
	public boolean removeSkill(AoVSkill skill){
		if(obtainedSkills.contains(skill)){
			obtainedSkills.remove(skill);
			return true;
		}
		return false;
	}
	
	public void setPlayer(EntityPlayer p){
		player = p;
	}
	
	public int getLevel(){
		return Math.round(exp/150);
	}
	
	public int getXpNeededToLevel(){
		return 150*getLevel();
	}
	
	public void setCurrentSkillPoints(int i){
		currentPoints = i;
	}
	
	public int getCurrentSkillPoints(){
		return currentPoints;
	}
	
	public void setMaxSkillPoints(int i){
		skillPoints = i;
	}
	
	public int getMaxSkillPoints(){
		return skillPoints;
	}
	
	public void setCurrentDivinePower(int i){
		currPower = i;
	}
	
	public void setMaxDivinePower(int i){
		maxPower = i;
	}
	
	public int getCurrentDivinePower(){
		return currPower;
	}
	
	public int getMaxDivinePower(){
		return maxPower;
	}
	
	public String toPacket(){
		String p = skillPoints+":"+currentPoints+":"+exp;
		if(obtainedSkills == null || obtainedSkills.isEmpty()) p = p.concat(":null");
		for(AoVSkill s : obtainedSkills){
			p = p.concat(":"+s.skillName);
		}
		return p;
	}
	
	public static AoVData fromPacket(String p){
		System.out.println("incomming packet to parse");
		System.out.println(p);
		String[] packet = p.split(":");
		int sPoint = Integer.parseInt(packet[0]);
		int cPoint = Integer.parseInt(packet[1]);
		int xp = Integer.parseInt(packet[2]);
		if(packet[3].equals("null")){
			return new AoVData(null, sPoint, cPoint, xp);
		}else{
			ArrayList<AoVSkill> skill = new ArrayList<AoVSkill>();
			for(int i=3; i<packet.length; i++){
				skill.add(AoVSkill.getSkillFromName(packet[i]));
			}
			return new AoVData(null, sPoint, cPoint, xp, (AoVSkill[]) skill.toArray());
		}
	}

}
