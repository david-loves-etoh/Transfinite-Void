package io.transfinite_void.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * 蓝曜剑 — 18 attack damage, 1,000,000 durability. While held in the main hand it grants +0.1 movement speed,
 * +0.1 jump strength (both item attribute modifiers) plus Ascension II and Strength II (see
 * {@code CyanisidianGearHandler}).
 */
public class CyanisidianSwordItem extends SwordItem {
	public static final double HELD_MOVEMENT_SPEED = 0.1D;
	public static final double HELD_JUMP_STRENGTH = 0.1D;
	public static final int ASCENSION_LEVEL = 2;
	public static final int STRENGTH_LEVEL = 2;

	public CyanisidianSwordItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("tooltip.transfinite_void.held_bonus").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.held_speed_jump",
				HELD_MOVEMENT_SPEED, HELD_JUMP_STRENGTH).withStyle(ChatFormatting.DARK_GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.held_ascension", ASCENSION_LEVEL)
				.withStyle(ChatFormatting.DARK_GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.held_strength", STRENGTH_LEVEL)
				.withStyle(ChatFormatting.DARK_GRAY));
		super.appendHoverText(stack, context, tooltip, flag);
	}
}
