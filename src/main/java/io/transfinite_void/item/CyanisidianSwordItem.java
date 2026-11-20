package io.transfinite_void.item;

import io.transfinite_void.registry.ModEffects;
import io.transfinite_void.util.GearTooltip;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * 蓝曜剑 — 18 attack damage, 1,000,000 durability. While held it grants +0.1 movement speed and +0.1 jump strength
 * (item attribute modifiers) plus Ascension II and Strength II.
 */
public class CyanisidianSwordItem extends SwordItem implements HeldEffectsItem {
	public static final double HELD_MOVEMENT_SPEED = 0.1D;
	public static final double HELD_JUMP_STRENGTH = 0.1D;
	public static final int ASCENSION_LEVEL = 2;
	public static final int STRENGTH_LEVEL = 2;

	private static final List<WornEffect> HELD_EFFECTS = List.of(
			new WornEffect(ModEffects.ASCENSION, ASCENSION_LEVEL),
			new WornEffect(MobEffects.DAMAGE_BOOST, STRENGTH_LEVEL));

	public CyanisidianSwordItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public List<WornEffect> heldEffects() {
		return HELD_EFFECTS;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		GearTooltip.header(tooltip, "mainhand");
		GearTooltip.baseStat(tooltip, Attributes.ATTACK_DAMAGE, GearTooltip.attackDamage(stack));
		GearTooltip.effects(tooltip, HELD_EFFECTS);
		GearTooltip.baseStat(tooltip, Attributes.ATTACK_SPEED, GearTooltip.attackSpeed(stack));
		super.appendHoverText(stack, context, tooltip, flag);
	}
}
