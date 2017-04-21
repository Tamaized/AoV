package Tamaized.AoV.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import Tamaized.AoV.capabilities.CapabilityList;
import Tamaized.AoV.core.skills.AoVSkill;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper.UnableToFindMethodException;

public class LivingAttackEvent {

	@SubscribeEvent
	public void onLivingAttack(net.minecraftforge.event.entity.living.LivingAttackEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.hasCapability(CapabilityList.AOV, null) && entity.getCapability(CapabilityList.AOV, null).hasSkill(AoVSkill.defender_core_4)) {
			handleShield(event, true);
		}
	}

	private void handleShield(net.minecraftforge.event.entity.living.LivingAttackEvent e, boolean fullRadial) {
		float damage = e.getAmount();
		ItemStack activeItemStack;
		EntityPlayer player;
		if (!(e.getEntityLiving() instanceof EntityPlayer)) {
			return;
		}
		player = (EntityPlayer) e.getEntityLiving();

		if (canBlockDamageSource(player, e.getSource(), fullRadial) && damage > 0.0F) {
			damageShield(player, damage);
			e.setCanceled(true);
			if (!e.getSource().isProjectile()) {
				Entity entity = e.getSource().getSourceOfDamage();

				if (entity instanceof EntityLivingBase) {
					EntityLivingBase p_190629_1_ = (EntityLivingBase) entity;
					p_190629_1_.knockBack(player, 0.5F, player.posX - p_190629_1_.posX, player.posZ - p_190629_1_.posZ);
				}
				player.world.setEntityState(player, (byte) 29);
			}
		}
	}

	private void damageShield(EntityPlayer player, float damage) {
		if (damage >= 3.0F && !player.getActiveItemStack().isEmpty()) {
			int i = 1 + MathHelper.floor(damage);
			player.getActiveItemStack().damageItem(i, player);

			if (player.getActiveItemStack().isEmpty()) {
				EnumHand enumhand = player.getActiveHand();
				net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, player.getActiveItemStack(), enumhand);

				if (enumhand == EnumHand.MAIN_HAND) {
					player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
				} else {
					player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
				}

				player.resetActiveHand();
				player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.world.rand.nextFloat() * 0.4F);
			}
		}
	}

	private boolean canBlockDamageSource(EntityPlayer player, DamageSource damageSourceIn, boolean fullRadial) {
		if (!damageSourceIn.isUnblockable() && player.isActiveItemStackBlocking()) {
			if (fullRadial) return true;
			Vec3d vec3d = damageSourceIn.getDamageLocation();

			if (vec3d != null) {
				Vec3d vec3d1 = player.getLook(1.0F);
				Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(player.posX, player.posY, player.posZ)).normalize();
				vec3d2 = new Vec3d(vec3d2.xCoord, 0.0D, vec3d2.zCoord);

				if (vec3d2.dotProduct(vec3d1) < 0.0D) {
					return true;
				}
			}
		}

		return false;
	}

}
