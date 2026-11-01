package io.transfinite_void.item;

import io.transfinite_void.util.CopperWaxing;
import io.transfinite_void.util.EnchantBaking;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * 钛斧 — mining level 5, 5555 durability, 2.5x netherite mining speed, a built-in Silk Touch and a special
 * copper interaction: on <em>waxed</em> copper it removes one oxidation step but never the wax.
 */
public class TitaniumAxeItem extends AxeItem {
	public TitaniumAxeItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity) {
		EnchantBaking.apply(stack, level, Enchantments.SILK_TOUCH, 1);

		return super.mineBlock(stack, level, state, pos, entity);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);
		BlockState scraped = CopperWaxing.scrapeKeepingWax(state);

		if (scraped != null) {
			Player player = context.getPlayer();
			ItemStack stack = context.getItemInHand();

			if (player instanceof ServerPlayer serverPlayer) {
				CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
			}

			level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
			level.levelEvent(player, 3005, pos, 0);

			if (!level.isClientSide()) {
				level.setBlockAndUpdate(pos, scraped);

				if (player != null && !player.getAbilities().instabuild) {
					stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
				}
			}

			if (player != null) {
				player.swing(context.getHand());
			}

			return InteractionResult.sidedSuccess(level.isClientSide());
		}

		if (CopperWaxing.isWaxed(state)) {
			// Waxed copper that is already at its least weathered stage: the titanium axe leaves it untouched.
			return InteractionResult.PASS;
		}

		return super.useOn(context);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("tooltip.transfinite_void.mining_level", 5)
				.withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.builtin_silk_touch")
				.withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.copper_keeps_wax")
				.withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, context, tooltip, flag);
	}
}
