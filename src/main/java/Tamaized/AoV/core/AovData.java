package Tamaized.AoV.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import Tamaized.AoV.AoV;
import Tamaized.AoV.core.abilities.AbilityBase;
import Tamaized.AoV.core.abilities.AuraBase;
import Tamaized.AoV.core.abilities.IAura;
import Tamaized.AoV.core.skills.AoVSkill;
import Tamaized.AoV.gui.client.AoVOverlay;

public class AoVData {
	
	private static final int xpScale = 50;
	
	private EntityPlayer player;
	private ArrayList<AoVSkill> obtainedSkills;
	private int skillPoints;
	private int currentPoints;
	private int exp;
	private int level;
	public boolean invokeMass = false;
	private boolean selectiveFocus = false;
	
	private AbilityBase[] slots = new AbilityBase[10]; //This needs to be handled on the server
	
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
	private Map<AuraBase, Integer> auras;
	
	private int tick = 0;
	
	private AoVSkill obtainedCore = null;
	private int currSkillPoints;
	
	public AoVData(){
		auras = new HashMap<AuraBase, Integer>();
		obtainedSkills = new ArrayList<AoVSkill>();
		obtainedCore = null;
	}
	
	public AoVData(EntityPlayer p){
		this();
		player = p;
	}
	
	public AoVData(EntityPlayer p, int sPoints, int cPoints, int xp, int lvl){
		this(p);
		skillPoints = sPoints;
		currentPoints = cPoints;
		exp = xp;
		level = lvl;
	}
	
	public AoVData(EntityPlayer p, int sPoints, int cPoints, int xp, int lvl, Object... skill){
		this(p, sPoints, cPoints, xp, lvl);
		setObtainedSkills(skill);
	}
	
	public AoVData Construct(){
		skillPoints = 1;
		currentPoints = 1;
		exp = 0;
		level = 1;
		return this;
	}
	
	public void resetVar(){
		auras.clear();
		setObtainedSkills(getCoreSkill());
		setCurrentSkillPoints(getMaxSkillPoints()-1);
		clearAllSlots();
		invokeMass = false;
		updateVariables();
	}
	
	public void updateVariables(){
		maxPower = 0;
		costReductionPerc = 0;
		costReductionFlat = 0;
		selectiveFocus = false;
		
		for(AoVSkill skill : obtainedSkills){
			AoVSkill.Buffs buffs = skill.getBuffs();
			maxPower += buffs.divinePower;
			spellPower += buffs.spellPower;
			costReductionPerc += buffs.costReductionPerc;
			costReductionFlat += buffs.costReductionFlat;
			if(buffs.selectiveFocus) selectiveFocus = true;
		}
		if(currPower > maxPower) currPower = maxPower;
		forceSync = true;
	}
	
	public void update(){
		last_exp = exp;
		last_currPower = currPower;
		last_maxPower = maxPower;
		if(tick % 60 == 0){
			Iterator<Entry<AuraBase, Integer>> iter = auras.entrySet().iterator();
			while(iter.hasNext()){
				Entry<AuraBase, Integer> e = iter.next();
				AuraBase k = e.getKey();
				k.update(this);
				if(k.getCurrentLife() >= e.getValue()) iter.remove();
			}
		}
		if(tick % (60*3) == 0){
			if(currPower < maxPower) currPower++;
		}
		if(tick % (60*20) == 0){
			AbilityBase.updateDecay();
			tick = 0;
		}
		tick++;
	}
	
	/**
	 * This will be client specific, it is used to update the client's data after getting the new AoVData from a packet
	 * This is also used to render GUI based things (Like FloatyText)
	 */
	@SideOnly(Side.CLIENT)
	public void updateData(AoVData data){
		if(exp != data.getXP() || level != data.getLevel()){
			AoVOverlay.addFloatyText("+"+String.valueOf(((data.getLevel()*xpScale) + data.getXP())-((getLevel()*xpScale) + getXP()))+"xp");
			if(data.getLevel() != getLevel()) AoVOverlay.addFloatyText("+"+(data.getLevel() - getLevel())+" Level(s)");
		}
	}
	
	public void addAura(IAura aura){
		auras.put(aura.createAura(), aura.getLife());
	}
	
	public void addExp(int amount){
		if(level >= 15) return;
		this.exp+=amount;
		if(getPlayer() == null){
			Tamaized.AoV.gui.client.AoVOverlay.addFloatyText("+"+amount+"xp");
		}
		checkLevels();
		forceSync = true;
	}
	
	private void checkLevels(){
		if(exp >= this.getXpNeededToLevel()){
			exp -= this.getXpNeededToLevel();
			level++;
			if(getPlayer() == null){
				Tamaized.AoV.gui.client.AoVOverlay.addFloatyText("Level Up");
			}
			this.addSkillPoints(1);
			checkLevels();
		}
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
		return level;
	}
	
	public int getXP(){
		return exp;
	}
	
	public int getXpNeededToLevel(){
		return xpScale*level;
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
	
	public boolean hasSelectiveFocus(){
		return selectiveFocus;
	}
	
	public String toPacket(){
		String p = skillPoints+":"+currentPoints+":"+exp+":"+level+":"+currPower+":"+maxPower+":"+String.valueOf(invokeMass);
		for(int i=0; i<9; i++){
			p = p.concat(":".concat(slots[i] == null ? "null" : slots[i].getName()));
		}
		if(obtainedSkills == null || obtainedSkills.isEmpty()) p = p.concat(":null");
		for(AoVSkill s : obtainedSkills){
			p = p.concat(":"+s.skillName);
		}
		return p;
	}
	
	public static AoVData fromPacket(String p){
		String[] packet = p.split(":");
		int sPoint = Integer.parseInt(packet[0]);
		int cPoint = Integer.parseInt(packet[1]);
		int xp = Integer.parseInt(packet[2]);
		int lvl = Integer.parseInt(packet[3]);
		int cPower = Integer.parseInt(packet[4]);
		int mPower = Integer.parseInt(packet[5]);
		boolean invoke = Boolean.parseBoolean(packet[6]);
		int packetID_Slots = 7;
		int packetID_Skills = packetID_Slots+9;
		AbilityBase[] slotz = new AbilityBase[10];
		for(int i=0; i<9; i++){
			String s = packet[packetID_Slots+i];
			slotz[i] = s == null ? null : AbilityBase.fromName(s);
		}
		if(packet[packetID_Skills].equals("null")){
			AoVData dat = new AoVData(null, sPoint, cPoint, xp, lvl);
			dat.setCurrentDivinePower(cPower);
			dat.setMaxDivinePower(mPower);
			dat.invokeMass = invoke;
			dat.updateVariables();
			dat.setAllSlots(slotz);
			return dat;
		}else{
			ArrayList<AoVSkill> skill = new ArrayList<AoVSkill>();
			for(int i=packetID_Skills; i<packet.length; i++){
				skill.add(AoVSkill.getSkillFromName(packet[i]));
			}
			AoVData dat = new AoVData(null, sPoint, cPoint, xp, lvl, skill.toArray());
			dat.setCurrentDivinePower(cPower);
			dat.setMaxDivinePower(mPower);
			dat.invokeMass = invoke;
			dat.updateVariables();
			dat.setAllSlots(slotz);
			return dat;
		}
	}

	public boolean wasThereAChange() {
		boolean flag = false;
		if(last_exp != exp || last_currPower != currPower || last_maxPower != maxPower || forceSync) flag = true;
		forceSync = false;
		return flag;
	}
	
	public void setSlot(AbilityBase spell, int slot){
		if(slot < 0 || slot > 9){
			AoV.logger.error("ISSUE! An Ability was attempted to be set out of bounds on AoVUIBar!");
			return;
		}
		slots[slot] = spell;
	}
	
	public AbilityBase getSlot(int slot){
		return slots[slot];
	}
	
	@SideOnly(Side.CLIENT)
	public AbilityBase getCurrentSlot(){
		return slots[Tamaized.AoV.common.client.ClientProxy.bar.slotLoc];
	}
	
	public boolean contains(AbilityBase spell){
		for(AbilityBase ab : slots){
			if(ab == spell) return true;
		}
		return false;
	}
	
	public void addToNearestSlot(AbilityBase spell){
		if(contains(spell)) return;
		for(int i=0; i<9; i++){
			if(slots[i] == null){
				setSlot(spell, i);
				break;
			}
		}
	}
	
	public void setAllSlots(AbilityBase... ab){
		for(int i=0; i<ab.length; i++){
			setSlot(ab[i], i);
		}
	}
	
	public void removeSlot(int i){
		slots[i] = null;
	}
	
	public void clearAllSlots(){
		for(int i=0; i<9; i++){
			slots[i] = null;
		}
	}

}
