package io.transfinite_void.item;

import io.transfinite_void.util.EnchantBaking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * 蓝曜镐 — mining level 6, 666666 durability, 3x netherite mining speed and a built-in Fortune V.
 */
public class CyanisidianPickaxeItem extends PickaxeItem {
	public static final int BUILT_IN_FORTUNE = 5;

	public CyanisidianPickaxeItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		// Bake early so the enchantment is visible/active before the first block is mined.
		EnchantBaking.apply(stack, level, Enchantments.FORTUNE, BUILT_IN_FORTUNE);
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
		// Runs before the loot table is rolled, so Fortune V applies to the very first block too.
		EnchantBaking.apply(stack, level, Enchantments.FORTUNE, BUILT_IN_FORTUNE);

		return super.mineBlock(stack, level, state, pos, entity);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("tooltip.transfinite_void.mining_level", 6).withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.builtin_fortune", BUILT_IN_FORTUNE)
				.withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, context, tooltip, flag);
	}
}
