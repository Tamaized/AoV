package Tamaized.AoV.capabilities.aov;

import java.io.IOException;
import java.util.List;

import Tamaized.AoV.core.abilities.Ability;
import Tamaized.AoV.core.abilities.IAura;
import Tamaized.AoV.core.skills.AoVSkill;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public interface IAoVCapability {

	void update(EntityPlayer player);

	List<Ability> getAbilities();

	boolean canUseAbility(Ability ability);

	void addAbility(Ability ability);

	void removeAbility(Ability ability);

	void castAbility(Ability ability, EntityPlayer caster, EntityLivingBase target);

	void addAura(IAura aura);

	void addExp(int amount);

	void addObtainedSkill(AoVSkill skill);

	boolean hasSkill(AoVSkill skill);

	boolean hasCoreSkill();

	AoVSkill getCoreSkill();

	List<AoVSkill> getObtainedSkills();

	void removeSkill(AoVSkill skill);

	int getLevel();
	
	int getMaxLevel();

	int getXP();

	int getXpNeededToLevel();

	int getSkillPoints();

	int getSpentSkillPoints();

	int getMaxSkillPoints();

	float getSpellPower();

	int getExtraCharges();

	boolean hasSelectiveFocus();

	void toggleInvokeMass(boolean b);

	void toggleInvokeMass();

	boolean hasInvokeMass();

	void setSlot(Ability ability, int slot);

	Ability getSlot(int slot);

	int getCurrentSlot();

	boolean slotsContain(Ability ability);

	void addToNearestSlot(Ability ability);

	void removeSlot(int slot);

	void clearAllSlots();

	void copyFrom(IAoVCapability cap);

	void decodePacket(ByteBufInputStream stream) throws IOException;

}
