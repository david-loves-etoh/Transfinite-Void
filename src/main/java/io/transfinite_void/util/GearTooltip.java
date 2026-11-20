package io.transfinite_void.util;

import io.transfinite_void.item.WornEffect;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

/**
 * The unified gear tooltip layout, matching vanilla's own attribute tooltip rendering:
 * <pre>
 * Unbreakable / 无法破坏            (vanilla {@code item.unbreakable}, blue, flush left)
 * When in Main Hand: / 在主手时：    (vanilla {@code item.modifiers.*}, gray, flush left)
 *  30 Attack Damage                (one space indent, dark green - the item's totals)
 *  +25 Armor                       (one space indent, blue - plain attribute bonuses)
 *  +3 Magic &amp; True Damage Reduction
 *  Water Breathing III             (one space indent, the effect category colour)
 * </pre>
 * Each line under the header gets vanilla's single-space indent ({@code CommonComponents.space()}).
 */
public final class GearTooltip {
	private GearTooltip() {
	}

	/** 在主手时： / 戴在头上时： … (vanilla wording and colour) */
	public static void header(List<Component> tooltip, String slot) {
		tooltip.add(Component.translatable("item.modifiers." + slot).withStyle(ChatFormatting.GRAY));
	}

	/** Uses the armour slot wording (戴在头上时 / 穿在身上时 / 穿在腿上时 / 穿在脚上时). */
	public static void armorHeader(List<Component> tooltip, ArmorItem.Type type) {
		header(tooltip, slotKey(type));
	}

	public static String slotKey(ArmorItem.Type type) {
		return switch (type) {
			case HELMET -> "head";
			case CHESTPLATE -> "chest";
			case LEGGINGS -> "legs";
			default -> "feet";
		};
	}

	/** "无法破坏" — the vanilla unbreakable line (blue, not indented). */
	public static void unbreakable(List<Component> tooltip) {
		tooltip.add(Component.translatable("item.unbreakable").withStyle(ChatFormatting.BLUE));
	}

	/** "+25 护甲值" — a plain attribute bonus, vanilla {@code attribute.modifier.plus.0}, blue. */
	public static void attribute(List<Component> tooltip, Holder<Attribute> attribute, double value) {
		attributeLine(tooltip, attributeName(attribute), value, false, ChatFormatting.BLUE);
	}

	/** "30 攻击伤害" — the item's own total, vanilla {@code attribute.modifier.equals.0}, dark green. */
	public static void baseStat(List<Component> tooltip, Holder<Attribute> attribute, double value) {
		attributeLine(tooltip, attributeName(attribute), value, false, ChatFormatting.DARK_GREEN);
	}

	/** "+N 名称" for a stat that has no vanilla attribute (magic / true defence). */
	public static void customBonus(List<Component> tooltip, String nameKey, double value) {
		attributeLine(tooltip, Component.translatable(nameKey), value, true, ChatFormatting.BLUE);
	}

	/** "N 名称" for a stat that has no vanilla attribute (true damage). */
	public static void customStat(List<Component> tooltip, String nameKey, double value) {
		attributeLine(tooltip, Component.translatable(nameKey), value, false, ChatFormatting.DARK_GREEN);
	}

	/** "水下呼吸 III" — indented, coloured by the effect category like a potion tooltip. */
	public static void effect(List<Component> tooltip, WornEffect effect) {
		Component name = effect.effect().value().getDisplayName();

		if (effect.level() > 1) {
			name = name.copy().append(" ").append(Component.translatable("potion.potency." + (effect.level() - 1)));
		}

		tooltip.add(indented(name, effect.effect().value().getCategory().getTooltipFormatting()));
	}

	public static void effects(List<Component> tooltip, List<WornEffect> effects) {
		for (WornEffect effect : effects) {
			effect(tooltip, effect);
		}
	}

	/** Free-form ability line ("连锁挖掘：…"), indented and gray. */
	public static void ability(List<Component> tooltip, String key, Object... args) {
		tooltip.add(indented(Component.translatable(key, args), ChatFormatting.GRAY));
	}

	/**
	 * A built-in enchantment printed exactly like a vanilla enchantment line: gray name plus the Roman level, and no
	 * numeral for single-level enchantments such as Silk Touch.
	 */
	public static void enchantment(List<Component> tooltip, ResourceKey<Enchantment> enchantment, int level) {
		MutableComponent name = enchantmentName(enchantment);

		if (level > 1) {
			name.append(" ").append(Component.translatable("enchantment.level." + level));
		}

		tooltip.add(indented(name, ChatFormatting.GRAY));
	}

	/** Single-level enchantment (Silk Touch and friends): no level numeral, like vanilla. */
	public static void singleLevelEnchantment(List<Component> tooltip, ResourceKey<Enchantment> enchantment) {
		tooltip.add(indented(enchantmentName(enchantment), ChatFormatting.GRAY));
	}

	private static MutableComponent enchantmentName(ResourceKey<Enchantment> enchantment) {
		ResourceLocation id = enchantment.location();
		return Component.translatable("enchantment." + id.getNamespace() + "." + id.getPath());
	}

	/** Roman numeral for a level (vanilla {@code enchantment.level.*} keys, I - X). */
	public static Component roman(int level) {
		return Component.translatable("enchantment.level." + Math.max(1, Math.min(10, level)));
	}

	/** Total main-hand attack damage (player base 1 + the item's modifier). */
	public static double attackDamage(ItemStack stack) {
		return 1.0D + modifierAmount(stack, true);
	}

	/** Total main-hand attack speed (player base 4.0 + the item's modifier). */
	public static double attackSpeed(ItemStack stack) {
		return 4.0D + modifierAmount(stack, false);
	}

	/** The {@code attribute.modifier.*} display scale: knockback resistance is shown x10 by vanilla. */
	public static double knockbackDisplay(double value) {
		return value * 10.0D;
	}

	private static Component attributeName(Holder<Attribute> attribute) {
		return Component.translatable(attribute.value().getDescriptionId());
	}

	private static void attributeLine(List<Component> tooltip, Component name, double value, boolean forcePlus,
			ChatFormatting colour) {
		String format = value < 0.0D
				? "attribute.modifier.take.0"
				: forcePlus ? "attribute.modifier.plus.0" : "attribute.modifier.equals.0";

		if (value < 0.0D) {
			value = -value;
		}

		Component line = Component.translatable(format, formatNumber(value), name);
		tooltip.add(indented(line, colour));
	}

	private static Component indented(Component content, ChatFormatting colour) {
		// CommonComponents.space(): vanilla prefixes every line under a slot header with one space.
		return Component.literal(" ").append(content.copy().withStyle(colour));
	}

	private static String formatNumber(double value) {
		return ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(value);
	}

	private static double modifierAmount(ItemStack stack, boolean damage) {
		ItemAttributeModifiers modifiers = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS,
				ItemAttributeModifiers.EMPTY);
		double total = 0.0D;

		for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
			boolean match = damage
					? entry.attribute().value() == Attributes.ATTACK_DAMAGE.value()
					: entry.attribute().value() == Attributes.ATTACK_SPEED.value();

			if (match) {
				total += entry.modifier().amount();
			}
		}

		return total;
	}
}
