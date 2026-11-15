package io.transfinite_void.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * 星陨斧 — mining level 7, 199999 durability, twice the effective mining speed of the cyanisidian axe
 * (its 9.0 base plus the built-in Efficiency V bonus of 26 gives 35, so this tier runs at 70.0).
 */
public class StardustAxeItem extends AxeItem {
	public StardustAxeItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("tooltip.transfinite_void.mining_level", 7).withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, context, tooltip, flag);
	}
}
