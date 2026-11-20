package io.transfinite_void.item;

import io.transfinite_void.util.GearTooltip;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
	public static final int MINING_LEVEL = 7;

	public StardustPickaxeItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		GearTooltip.header(tooltip, "mainhand");
		GearTooltip.baseStat(tooltip, Attributes.ATTACK_DAMAGE, GearTooltip.attackDamage(stack));
		GearTooltip.baseStat(tooltip, Attributes.ATTACK_SPEED, GearTooltip.attackSpeed(stack));
		super.appendHoverText(stack, context, tooltip, flag);
	}
}
