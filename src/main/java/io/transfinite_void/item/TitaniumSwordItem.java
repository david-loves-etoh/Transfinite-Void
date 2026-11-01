package io.transfinite_void.item;

import io.transfinite_void.registry.ModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * 钛剑 — 8848 durability, 10 attack damage, double Mending efficiency (see the {@code EnchantmentHelper} mixin) and
 * bleeding III for 20 seconds on every hit.
 */
public class TitaniumSwordItem extends SwordItem {
	/** 流血 III. */
	public static final int BLEED_AMPLIFIER = 2;
	/** 20 seconds. */
	public static final int BLEED_DURATION = 400;

	public TitaniumSwordItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		super.postHurtEnemy(stack, target, attacker);

		if (!target.level().isClientSide() && target.isAlive()) {
			target.addEffect(new MobEffectInstance(
					ModEffects.BLEEDING, BLEED_DURATION, BLEED_AMPLIFIER, false, true, true));
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("tooltip.transfinite_void.bleeding", BLEED_AMPLIFIER + 1, BLEED_DURATION / 20)
				.withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.mending_x2")
				.withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, context, tooltip, flag);
	}
}
