package Tamaized.AoV.core;

import java.util.ArrayList;
import java.util.UUID;

import Tamaized.AoV.core.skills.AoVSkill;

public class AoVData {
	
	private UUID player;
	private ArrayList<AoVSkill> obtainedSkills;
	private int skillPoints;
	private int currentPoints;
	private int exp;
	
	//This Data is set by skills, dont set it via packets
	private int currPower = 0;
	private int maxPower = 0;
	
	public AoVData(){
		obtainedSkills = new ArrayList<AoVSkill>();
	}
	
	public AoVData(UUID p){
		this();
		player = p;
	}
	
	public AoVData(UUID p, int sPoints, int cPoints, int xp){
		this(p);
		skillPoints = sPoints;
		currentPoints = cPoints;
		exp = xp;
	}
	
	public AoVData(UUID p, int sPoints, int cPoints, int xp, AoVSkill... skill){
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
	
	public void setPlayer(UUID p){
		player = p;
	}
	
	public int getLevel(){
		return Math.round(exp/150);
	}
	
	public int getXpNeededToLevel(){
		return 150*getLevel();
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
