package io.transfinite_void.item;

import io.transfinite_void.util.ComboDamage;
import io.transfinite_void.util.GearTooltip;
import net.minecraft.world.entity.ai.attributes.Attributes;
import io.transfinite_void.util.TransfiniteDamage;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * 星陨剑 — 30 attack damage, 299999 durability, attack speed 4.8 (a third of a netherite sword's cooldown),
 * 10 extra points of true damage per hit and a 10% chance to chain a double-damage follow-up hit.
 */
public class StardustSwordItem extends SwordItem {
	/** The weapon's listed attack damage, used as the base of the combo chain. */
	public static final float ATTACK_DAMAGE = 30.0F;
	public static final float TRUE_DAMAGE = 10.0F;

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
			ComboDamage.tryCombo(serverLevel, target, attacker, ATTACK_DAMAGE * 2.0F, 1);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		GearTooltip.header(tooltip, "mainhand");
		GearTooltip.baseStat(tooltip, Attributes.ATTACK_DAMAGE, GearTooltip.attackDamage(stack));
		GearTooltip.customStat(tooltip, "stat.transfinite_void.true_damage", TRUE_DAMAGE);
		GearTooltip.ability(tooltip, "tooltip.transfinite_void.combo_line",
				Math.round(ComboDamage.CHANCE * 100.0F), Math.round(ComboDamage.CHANCE * 100.0F));
		GearTooltip.baseStat(tooltip, Attributes.ATTACK_SPEED, GearTooltip.attackSpeed(stack));
		super.appendHoverText(stack, context, tooltip, flag);
	}
}
