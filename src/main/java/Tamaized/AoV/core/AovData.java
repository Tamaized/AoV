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
	
	//This is handled Locally, no packets need to be sent.
	private int spellPower = 0;
	private float costReductionPerc = 0;
	private int costReductionFlat = 0;
	
	//Local variables for ServerSide
	public boolean forceSync = false;
	private int last_exp = 0;
	private int last_currPower = 0;
	private int last_maxPower = 0;
	
	private int tick = 0;
	
	private AoVSkill obtainedCore = null;
	private int currSkillPoints;
	
	public AoVData(){
		obtainedSkills = new ArrayList<AoVSkill>();
		obtainedCore = null;
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
	
	public AoVData(EntityPlayer p, int sPoints, int cPoints, int xp, Object... skill){
		this(p, sPoints, cPoints, xp);
		setObtainedSkills(skill);
	}
	
	public AoVData Construct(){
		skillPoints = 1;
		currentPoints = 1;
		exp = 0;
		return this;
	}
	
	public void updateVariables(){
		maxPower = 0;
		costReductionPerc = 0;
		costReductionFlat = 0;
		
		for(AoVSkill skill : obtainedSkills){
			AoVSkill.Buffs buffs = skill.getBuffs();
			maxPower += buffs.divinePower;
			spellPower += buffs.spellPower;
			costReductionPerc += buffs.costReductionPerc;
			costReductionFlat += buffs.costReductionFlat;
		}
		if(currPower > maxPower) currPower = maxPower;
		tick = 0;
		forceSync = true;
	}
	
	public void update(){
		last_exp = exp;
		last_currPower = currPower;
		last_maxPower = maxPower;
		
		if(tick % (60*3) == 0){
			if(currPower < maxPower) currPower++;
		}
		tick++;
	}
	
	public void addExp(int amount){
		int currLevel = this.getLevel();
		if(currLevel >= 15) return;
		this.exp+=amount;
		int points = this.getLevel()-currLevel;
		this.addSkillPoints(points);
		forceSync = true;
	}
	
	public void setObtainedSkills(Object... skill){
		obtainedSkills.clear();
		obtainedCore = null;
		for(Object s : skill){
			if(s instanceof AoVSkill) addObtainedSkill((AoVSkill) s);
		}
		forceSync = true;
	}
	
	public void addObtainedSkill(AoVSkill skill){
		obtainedSkills.add(skill);
		if(skill.isCore) obtainedCore = skill;
		forceSync = true;
	}

	public boolean hasSkill(AoVSkill skill) {
		return obtainedSkills.contains(skill);
	}
	
	public AoVSkill getCoreSkill(){
		return obtainedCore;
	}
	
	public ArrayList<AoVSkill> getAllObtainedSkills(){
		return obtainedSkills;
	}
	
	public boolean hasSkillsBesidesCore(){
		return obtainedSkills.size() > 1;
	}
	
	public boolean removeSkill(AoVSkill skill){
		if(obtainedSkills.contains(skill)){
			obtainedSkills.remove(skill);
			if(skill == obtainedCore) obtainedCore = null;
			forceSync = true;
			return true;
		}
		return false;
	}
	
	public void setPlayer(EntityPlayer p){
		player = p;
	}
	
	public EntityPlayer getPlayer(){
		return player;
	}
	
	public int getLevel(){
		return (int) Math.floor(exp/150) + 1;
	}
	
	public int getXpNeededToLevel(){
		return 150*getLevel();
	}
	
	public void setCurrentSkillPoints(int i){
		currentPoints = i;
		forceSync = true;
	}
	
	public void addSkillPoints(int amount){
		currentPoints+=amount;
		skillPoints+=amount;
		forceSync = true;
	}
	
	public int getCurrentSkillPoints(){
		return currentPoints;
	}
	
	public int getSpentSkillPoints(){
		return skillPoints - currentPoints;
	}
	
	public void setMaxSkillPoints(int i){
		skillPoints = i;
		forceSync = true;
	}
	
	public int getMaxSkillPoints(){
		return skillPoints;
	}
	
	public float getSpellPower(){
		return (float) spellPower;
	}
	
	public float getCostReductionPerc(){
		return costReductionPerc;
	}
	
	public int getCostReductionFlat(){
		return costReductionFlat;
	}
	
	public void setCurrentDivinePower(int i){
		currPower = i;
		forceSync = true;
	}
	
	public void setMaxDivinePower(int i){
		maxPower = i;
		forceSync = true;
	}
	
	public int getCurrentDivinePower(){
		return currPower;
	}
	
	public int getMaxDivinePower(){
		return maxPower;
	}
	
	public String toPacket(){
		String p = skillPoints+":"+currentPoints+":"+exp+":"+currPower+":"+maxPower;
		if(obtainedSkills == null || obtainedSkills.isEmpty()) p = p.concat(":null");
		for(AoVSkill s : obtainedSkills){
			p = p.concat(":"+s.skillName);
		}
		return p;
	}
	
	public static AoVData fromPacket(String p){
		//System.out.println("incomming packet to parse");
		//System.out.println(p);
		String[] packet = p.split(":");
		int sPoint = Integer.parseInt(packet[0]);
		int cPoint = Integer.parseInt(packet[1]);
		int xp = Integer.parseInt(packet[2]);
		int cPower = Integer.parseInt(packet[3]);
		int mPower = Integer.parseInt(packet[4]);
		if(packet[5].equals("null")){
			AoVData dat = new AoVData(null, sPoint, cPoint, xp);
			dat.setCurrentDivinePower(cPower);
			dat.setMaxDivinePower(mPower);
			dat.updateVariables();
			return dat;
		}else{
			ArrayList<AoVSkill> skill = new ArrayList<AoVSkill>();
			for(int i=5; i<packet.length; i++){
				skill.add(AoVSkill.getSkillFromName(packet[i]));
			}
			AoVData dat = new AoVData(null, sPoint, cPoint, xp, skill.toArray());
			dat.setCurrentDivinePower(cPower);
			dat.setMaxDivinePower(mPower);
			dat.updateVariables();
			return dat;
		}
	}

	public boolean wasThereAChange() {
		boolean flag = false;
		if(last_exp != exp || last_currPower != currPower || last_maxPower != maxPower || forceSync) flag = true;
		forceSync = false;
		return flag;
	}

}
