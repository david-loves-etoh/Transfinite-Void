package io.transfinite_void.item;

import io.transfinite_void.util.EnchantBaking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
 * 钛镐 — mining level 5, 6767 durability, 2x netherite mining speed and a built-in Fortune III.
 */
public class TitaniumPickaxeItem extends PickaxeItem {
	public static final int BUILT_IN_FORTUNE = 3;

	public TitaniumPickaxeItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
		// Runs before Block#playerDestroy rolls the loot table, so the fortune level is already on the stack.
		EnchantBaking.apply(stack, level, Enchantments.FORTUNE, BUILT_IN_FORTUNE);

		return super.mineBlock(stack, level, state, pos, entity);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("tooltip.transfinite_void.mining_level", 5)
				.withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.builtin_fortune", BUILT_IN_FORTUNE)
				.withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, context, tooltip, flag);
	}
}
