package tamaized.aov.common.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tamaized.aov.common.capabilities.CapabilityList;
import tamaized.aov.common.capabilities.aov.IAoVCapability;
import tamaized.aov.common.core.abilities.AbilityBase;
import tamaized.aov.common.core.skills.AoVSkill;
import tamaized.aov.registry.AoVPotions;
import tamaized.tammodized.common.helper.FloatyTextHelper;

public class LivingAttackEvent {

	private boolean state = true;

	@SubscribeEvent
	public void onLivingFallEvent(LivingFallEvent event) {
		if (event.getEntityLiving() != null && event.getEntityLiving().getActivePotionEffect(AoVPotions.slowFall) != null)
			event.setDamageMultiplier(0);
	}

	@SubscribeEvent
	public void onLivingHurtEvent(LivingHurtEvent event) {
		if (event.getEntityLiving() != null && event.getEntityLiving().getActivePotionEffect(AoVPotions.shieldOfFaith) != null)
			event.setAmount(event.getAmount() / 2F);
	}

	@SubscribeEvent
	public void onLivingAttack(net.minecraftforge.event.entity.living.LivingAttackEvent event) {
		Entity attacker = event.getSource().getTrueSource();
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.world.isRemote)
			return;

		// DoubleStrike
		if (attacker != null && attacker.hasCapability(CapabilityList.AOV, null)) {
			IAoVCapability cap = attacker.getCapability(CapabilityList.AOV, null);
			if (cap != null && state && attacker.world.rand.nextInt(cap.getDoubleStrikeForRand()) == 0) {
				state = false;
				cap.addExp(attacker, 20, AbilityBase.defenderDoublestrike);
				if (attacker instanceof EntityPlayer)
					FloatyTextHelper.sendText((EntityPlayer) attacker, "Doublestrike");
				entity.attackEntityFrom(event.getSource(), event.getAmount());
				entity.hurtResistantTime = 0;
				state = true;
			}
			EntityLivingBase attackerLiving = null;
			if (attacker instanceof EntityLivingBase)
				attackerLiving = (EntityLivingBase) attacker;
			if (cap != null && cap.hasSkill(AoVSkill.defender_core_3) && attackerLiving != null && ((!attackerLiving.getHeldItemMainhand().isEmpty() && attackerLiving.getHeldItemMainhand().getItem() instanceof ItemShield) || (!attackerLiving.getHeldItemOffhand().isEmpty() && attackerLiving.getHeldItemOffhand().getItem() instanceof ItemShield))) {
				double d1 = attacker.posX - entity.posX;
				double d0;
				for (d0 = attacker.posZ - entity.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
					d1 = (Math.random() - Math.random()) * 0.01D;
				}
				entity.attackedAtYaw = (float) (MathHelper.atan2(d0, d1) * (180D / Math.PI) - (double) entity.rotationYaw);
				entity.knockBack(attacker, 1.0F, d1, d0);
			}
		}

		if (entity.hasCapability(CapabilityList.AOV, null)) {
			IAoVCapability cap = entity.getCapability(CapabilityList.AOV, null);

			if (cap != null && cap.hasSkill(AoVSkill.defender_core_1) && entity instanceof EntityPlayer) {
				if (canBlockDamageSource((EntityPlayer) entity, event.getSource(), false) && event.getAmount() > 0.0F) {
					cap.addExp(entity, 20, AbilityBase.defenderBlocking);
				}
			}

			// Dodge
			if (cap != null && isWhiteListed(event.getSource()) && cap.getDodge() > 0 && entity.world.rand.nextInt(cap.getDodgeForRand()) == 0) {
				cap.addExp(entity, 20, AbilityBase.defenderDodge);
				if (entity instanceof EntityPlayer)
					FloatyTextHelper.sendText((EntityPlayer) entity, "Dodged");
				event.setCanceled(true);
				return;
			}
			// Full Radial Shield
			if (cap != null && cap.hasSkill(AoVSkill.defender_core_4)) {
				handleShield(event, true);
			}
		}
	}

	private void handleShield(net.minecraftforge.event.entity.living.LivingAttackEvent e, boolean fullRadial) {
		float damage = e.getAmount();
		EntityPlayer player;
		if (!(e.getEntityLiving() instanceof EntityPlayer)) {
			return;
		}
		player = (EntityPlayer) e.getEntityLiving();

		if (canBlockDamageSource(player, e.getSource(), fullRadial) && damage > 0.0F) {
			damageShield(player, damage);
			e.setCanceled(true);
			if (!e.getSource().isProjectile()) {
				Entity entity = e.getSource().getImmediateSource();

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
			Vec3d vec3d = damageSourceIn.getDamageLocation();

			if (vec3d != null) {
				if (fullRadial)
					return true;
				Vec3d vec3d1 = player.getLook(1.0F);
				Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(player.posX, player.posY, player.posZ)).normalize();
				vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);

				if (vec3d2.dotProduct(vec3d1) < 0.0D) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean isWhiteListed(DamageSource source) {
		return source.damageType.equals("generic") || source.damageType.equals("mob") || source.damageType.equals("player") || source.damageType.equals("arrow") || source.damageType.equals("thrown");
	}

}
