package Tamaized.AoV.core;

import java.util.ArrayList;

import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.entity.player.EntityPlayer;

public class AoVData {
	
	private EntityPlayer player;
	private ArrayList<AoVSkill> obtainedSkills;
	private int skillPoints;
	private int currentPoints;
	private int exp;
	
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

}
