package io.transfinite_void.item;

import io.transfinite_void.util.ChainMining;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * 蓝曜斧 — mining level 6, 666666 durability, netherite axe base speed plus a built-in Efficiency V
 * (attribute modifier on {@code minecraft:player.mining_efficiency}, exactly what the enchantment does)
 * and chain mining of logs.
 */
public class CyanisidianAxeItem extends AxeItem {
	/** Efficiency V adds {@code level^2 + 1 = 26} to the mining_efficiency attribute. */
	public static final double BUILT_IN_EFFICIENCY = 26.0D;

	public CyanisidianAxeItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
		boolean result = super.mineBlock(stack, level, state, pos, entity);

		if (level instanceof ServerLevel serverLevel
				&& entity instanceof ServerPlayer player
				&& state.is(BlockTags.LOGS)) {
			ChainMining.mine(serverLevel, pos, player, stack);
		}

		return result;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("tooltip.transfinite_void.mining_level", 6).withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.efficiency", 5).withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.chain_logs", ChainMining.BLOCK_LIMIT / 4)
				.withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, context, tooltip, flag);
	}
}
