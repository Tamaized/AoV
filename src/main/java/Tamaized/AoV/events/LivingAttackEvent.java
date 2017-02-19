package Tamaized.AoV.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper.UnableToFindMethodException;

public class LivingAttackEvent {

	@SubscribeEvent
	public void onLivingAttack(net.minecraftforge.event.entity.living.LivingAttackEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		World world = entity != null ? entity.world : null;
		DamageSource source = event.getSource();
		float amount = event.getAmount();
		if (world != null && !world.isRemote) {
			if (amount > 0.0F && (!source.isUnblockable() && entity.isActiveItemStackBlocking() && entity.hasCapability(CapabilityList.AOV, null) && entity.getCapability(CapabilityList.AOV, null).hasSkill(AoVSkill.defender_core_4))) {
				try {
					event.setCanceled(true);
					Method method = ReflectionHelper.findMethod(EntityLivingBase.class, entity, new String[] { "func_184590_k", "damageShield" }, float.class);
					method.setAccessible(true);
					method.invoke(entity, amount);
					if (!source.isProjectile()) {
						Entity e = source.getSourceOfDamage();
						if (e instanceof EntityLivingBase) {
							method = ReflectionHelper.findMethod(EntityLivingBase.class, entity, new String[] { "func_190629_c", "blockUsingShield" }, EntityLivingBase.class);
							method.setAccessible(true);
							method.invoke(entity, (EntityLivingBase) e);
							world.setEntityState(entity, (byte) 29);
						}
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | UnableToFindMethodException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
