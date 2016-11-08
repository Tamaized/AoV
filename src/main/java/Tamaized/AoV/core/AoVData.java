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
	private Map<AbilityBase, Integer> coolDowns;
	private int skillPoints;
	private int currentPoints;
	private int exp;
	private int level;
	public boolean invokeMass = false;
	private boolean selectiveFocus = false;
	private Map<AbilityBase, Integer> charges = new HashMap<AbilityBase, Integer>();
	private int extraCharges = 0;
	
	private AbilityBase[] slots = new AbilityBase[10]; //This needs to be handled on the server
	
	//This is handled Locally, no packets need to be sent.
	private int spellPower = 0;
	
	//Local variables for ServerSide
	public boolean forceSync = false;
	private int last_exp = 0;
	private Map<AuraBase, Integer> auras;
	
	private int tick = 0;
	
	private AoVSkill obtainedCore = null;
	private int currSkillPoints;
	
	public AoVData(){
		coolDowns = new HashMap<AbilityBase, Integer>();
		charges = new HashMap<AbilityBase, Integer>();
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
		charges = new HashMap<AbilityBase, Integer>();
		skillPoints = 1;
		currentPoints = 1;
		exp = 0;
		level = 1;
		extraCharges = 0;
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
		selectiveFocus = false;
		extraCharges = 0;
		spellPower = 0;
		
		for(AoVSkill skill : obtainedSkills){
			AoVSkill.Buffs buffs = skill.getBuffs();
			extraCharges += buffs.charges;
			spellPower += buffs.spellPower;
			if(buffs.selectiveFocus) selectiveFocus = true;
		}
		forceSync = true;
	}
	
	public void update(){
		last_exp = exp;
		if(tick % 20 == 0){
			{
				Iterator<Entry<AuraBase, Integer>> iter = auras.entrySet().iterator();
				while(iter.hasNext()){
					Entry<AuraBase, Integer> e = iter.next();
					AuraBase k = e.getKey();
					k.update(this);
					if(k.getCurrentLife() >= e.getValue()) iter.remove();
				}
			}
			{
				Iterator<Entry<AbilityBase, Integer>> iter = coolDowns.entrySet().iterator();
				while(iter.hasNext()){
					Entry<AbilityBase, Integer> e = iter.next();
					int v = e.getValue()-1;
					if(v<=0) iter.remove();
					else e.setValue(v);
				}
			}
		}
		if(tick % (20*20) == 0){
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
	
	/**
	 * Returns the amount of charges remaining or -1 if the ability doesnt exist in the list
	 */
	public int getAbilityCharge(AbilityBase ab){
		if(charges.containsKey(ab)) return charges.get(ab);
		return -1;
	}
	
	public Map<AbilityBase, Integer> getAbilityCharges(){
		return charges;
	}
	
	public void reduceAbilityCharges(AbilityBase ab, int amount){
		int a = charges.get(ab);
		if(amount > -1) charges.put(ab, a-amount);
		forceSync = true;
	}
	
	private void addAbilityCharge(AbilityBase ab, int c){
		if(ab == null) return;
		charges.put(ab, c + (c < 0 ? 0 : extraCharges));
	}
	
	public void setAbilityCharges(AbilityBase ab, int c){
		addAbilityCharge(ab, c);
		forceSync = true;
	}
	
	public void setAbilityCharges(Map<AbilityBase, Integer> chargeMap){
		charges = new HashMap<AbilityBase, Integer>();
		charges.putAll(chargeMap);
		charges.remove(null);
	}
	
	public void clearAbilityCharges(){
		charges.clear();
	}
	
	public void resetAbilityCharges(){
		ArrayList<AbilityBase> ab = new ArrayList<AbilityBase>();
		for(AbilityBase a : charges.keySet()) ab.add(a);
		for(AbilityBase b : ab){
			if(b == null) continue;
			addAbilityCharge(b, b.charges);
		}
		forceSync = true;
	}
	
	public boolean castAbility(AbilityBase ab){
		if(coolDowns.containsKey(ab)) return false;
		coolDowns.put(ab, ab.usesInvoke() ? ab.getCoolDown()*2 : ab.getCoolDown());
		this.forceSync = true;
		return true;
	}
	
	public int getCoolDown(AbilityBase ab){
		if(coolDowns.containsKey(ab)) return coolDowns.get(ab);
		return 0;
	}
	
	public void setCoolDown(AbilityBase ab, int v){
		coolDowns.put(ab, v);
	}
	
	public void setCoolDowns(Map<AbilityBase, Integer> map){
		coolDowns.putAll(map);
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
	
	public boolean hasSkillCore(){
		return obtainedCore != null;
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
	
	public int getExtraCharges(){
		return extraCharges;
	}
	
	public boolean hasSelectiveFocus(){
		return selectiveFocus;
	}
	
	private static final String packetDataSplitter = "<!Split!>";
	private static final String packetDataSplitter_Child = "<!S!>";
	
	public String toPacket(){
		String p = "Values{"+skillPoints+","+currentPoints+","+exp+","+level+","+String.valueOf(invokeMass)+"}";
		
		{
			String tmp = "";
			for(int i=0; i<9; i++){
				tmp = tmp.concat(",".concat(slots[i] == null ? "null" : slots[i].getName()));
			}
			p = p.concat(packetDataSplitter+"Slots{"+tmp.substring(1)+"}");
		}
		
		{
			String tmp = "";
			for(AoVSkill s : obtainedSkills){
				tmp = tmp.concat(","+s.skillName);
			}
			tmp = tmp.length()>0 ? tmp : ",";
			p = p.concat(packetDataSplitter+"ObtainedSkills{"+tmp.substring(1)+"}");
		}
		
		{
			String tmp = "";
			for(Entry<AbilityBase, Integer> e : coolDowns.entrySet()){
				tmp = tmp.concat(","+e.getKey().getName()+packetDataSplitter_Child+e.getValue());
			}
			tmp = tmp.length()>0 ? tmp : ",";
			p = p.concat(packetDataSplitter+"CoolDowns{"+tmp.substring(1)+"}");
		}
		
		{
			String tmp = "";
			if(!charges.isEmpty()){
				for(Entry<AbilityBase, Integer> e : charges.entrySet()){
					if(e.getKey() == null) continue;
					tmp = tmp.concat(","+e.getKey().getName()+packetDataSplitter_Child+e.getValue());
				}
			}
			tmp = tmp.length()>0 ? tmp : ",";
			p = p.concat(packetDataSplitter+"Charges{"+tmp.substring(1)+"}");
		}
		return p;
	}
	
	public static AoVData fromPacket(String p){
		int sPoint = 1;
		int cPoint = 1;
		int xp = 0;
		int lvl = 1;
		boolean invoke = false;
		AbilityBase[] slotz = new AbilityBase[10];
		Map<AbilityBase, Integer> coolDownMap = new HashMap<AbilityBase, Integer>();
		Map<AbilityBase, Integer> chargeMap = new HashMap<AbilityBase, Integer>();
		AoVData dat = null;
		
		boolean flag = true;
		String[] packet = p.split(packetDataSplitter);
		for(String rawData : packet){
			if(rawData.contains("Values{")){
				String snipData = rawData.substring("Values{".length(), rawData.length()-1);
				String[] dataValues = snipData.split(",");
				sPoint = Integer.parseInt(dataValues[0]);
				cPoint = Integer.parseInt(dataValues[1]);
				xp = Integer.parseInt(dataValues[2]);
				lvl = Integer.parseInt(dataValues[3]);
				invoke = Boolean.parseBoolean(dataValues[4]);
			}
			else if(rawData.contains("Slots{")){
				String snipData = rawData.substring("Slots{".length(), rawData.length()-1);
				String[] dataValues = snipData.split(",");
				for(int i=0; i<9; i++){
					String s = dataValues[i];
					slotz[i] = s == null ? null : AbilityBase.fromName(s);
				}
			}
			else if(rawData.contains("CoolDowns{")){
				String snipData = rawData.substring("CoolDowns{".length(), rawData.length()-1);
				String[] dataValues = snipData.split(",");
				for(int i=0; i<dataValues.length; i++){
					if(!dataValues[i].contains(packetDataSplitter_Child)) continue;
					AbilityBase ab = AbilityBase.fromName(dataValues[i].split(packetDataSplitter_Child)[0]);
					int cd = Integer.valueOf(dataValues[i].split(packetDataSplitter_Child)[1]);
					coolDownMap.put(ab, cd);
				}
			}
			else if(rawData.contains("Charges{")){
				String snipData = rawData.substring("Charges{".length(), rawData.length()-1);
				String[] dataValues = snipData.split(",");
				for(int i=0; i<dataValues.length; i++){
					if(!dataValues[i].contains(packetDataSplitter_Child)) continue;
					AbilityBase ab = AbilityBase.fromName(dataValues[i].split(packetDataSplitter_Child)[0]);
					int charge = Integer.valueOf(dataValues[i].split(packetDataSplitter_Child)[1]);
					chargeMap.put(ab, charge);
				}
			}
			else if(rawData.contains("ObtainedSkills{")){
				flag = false;
				String snipData = rawData.substring("ObtainedSkills{".length(), rawData.length()-1);
				String[] dataValues = snipData.split(",");
				if(dataValues.length > 0){
					ArrayList<AoVSkill> skill = new ArrayList<AoVSkill>();
					for(int i=0; i<dataValues.length; i++){
						skill.add(AoVSkill.getSkillFromName(dataValues[i]));
					}
					dat = new AoVData(null, sPoint, cPoint, xp, lvl, skill.toArray());
					dat.invokeMass = invoke;
					dat.updateVariables();
					dat.setAllSlots(slotz);
				}else{
					dat = new AoVData(null, sPoint, cPoint, xp, lvl);
					dat.invokeMass = invoke;
					dat.updateVariables();
					dat.setAllSlots(slotz);
				}
			}
		}
		if(flag){
			dat = new AoVData(null, sPoint, cPoint, xp, lvl);
			dat.invokeMass = invoke;
			dat.updateVariables();
			dat.setAllSlots(slotz);
		}
		dat.setCoolDowns(coolDownMap);
		dat.setAbilityCharges(chargeMap);
		return dat;
	}

	public boolean wasThereAChange() {
		boolean flag = false;
		if(last_exp != exp || forceSync) flag = true;
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
