package io.transfinite_void.item;

import io.transfinite_void.util.TransfiniteDamage;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * 星陨剑 — 30 attack damage, 299999 durability, attack speed 4.8 (a third of a netherite sword's cooldown),
 * 10 extra points of true damage per hit and a 10% chance to chain a double-damage follow-up hit (each follow-up
 * can chain again with the same 10% chance).
 */
public class StardustSwordItem extends SwordItem {
	/** The weapon's listed attack damage, used as the base of the combo chain. */
	public static final float ATTACK_DAMAGE = 30.0F;
	public static final float TRUE_DAMAGE = 10.0F;
	public static final float COMBO_CHANCE = 0.10F;
	public static final int MAX_COMBO_DEPTH = 10;

	public StardustSwordItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		super.postHurtEnemy(stack, target, attacker);

		if (!(attacker.level() instanceof ServerLevel serverLevel) || !target.isAlive()) {
			return;
		}

		// 附带真实伤害: bypasses armour, effects, enchantments, resistance and the invulnerability window.
		target.hurt(TransfiniteDamage.trueDamage(serverLevel, attacker), TRUE_DAMAGE);

		if (target.isAlive()) {
			triggerCombo(serverLevel, target, attacker, ATTACK_DAMAGE * 2.0F, 1);
		}
	}

	private static void triggerCombo(ServerLevel level, LivingEntity target, LivingEntity attacker, float damage, int depth) {
		if (depth > MAX_COMBO_DEPTH || !target.isAlive() || level.random.nextFloat() >= COMBO_CHANCE) {
			return;
		}

		DamageSource source = attacker instanceof Player player
				? level.damageSources().playerAttack(player)
				: level.damageSources().mobAttack(attacker);

		if (target.hurt(source, damage)) {
			triggerCombo(level, target, attacker, damage * 2.0F, depth + 1);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("tooltip.transfinite_void.true_damage", TRUE_DAMAGE)
				.withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.combo_chance",
				Math.round(COMBO_CHANCE * 100.0F)).withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, context, tooltip, flag);
	}
}
