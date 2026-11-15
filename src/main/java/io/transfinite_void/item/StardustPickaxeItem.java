package io.transfinite_void.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * 星陨镐 — mining level 7, 99999 durability, twice the mining speed of the cyanisidian pickaxe (27.0 -> 54.0).
 */
public class StardustPickaxeItem extends PickaxeItem {
	public StardustPickaxeItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("tooltip.transfinite_void.mining_level", 7).withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, context, tooltip, flag);
	}
}
