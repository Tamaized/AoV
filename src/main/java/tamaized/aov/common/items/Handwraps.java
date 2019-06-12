package tamaized.aov.common.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tamaized.aov.AoV;

import javax.annotation.Nonnull;

public class Handwraps extends SwordItem {

	public Handwraps(IItemTier tier, int attackDamage, float attackSpeed, Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}

	@Nonnull
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType equipmentSlot) {
		return HashMultimap.create();
	}

	@Nonnull
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot, stack);

		if (slot == EquipmentSlotType.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 1F, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", AoV.config.handwrapsSpeed.get() ? 6F : 0F, 0));
		}

		return multimap;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, BlockState state) {
		return 1F;
	}

	@Override
	public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
		return false;
	}

	@Override
	public boolean canHarvestBlock(BlockState blockIn) {
		return false;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return false;
	}
}
