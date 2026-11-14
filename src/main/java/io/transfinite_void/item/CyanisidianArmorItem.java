package io.transfinite_void.item;

import io.transfinite_void.TransfiniteVoid;
import io.transfinite_void.util.EnchantBaking;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Function;

/**
 * 蓝曜甲 piece.
 * <p>
 * Every defence / toughness / knockback / max-health / movement / jump value of the design is per piece, so the
 * modifiers are built by hand (an {@code ArmorMaterial} only carries one toughness value). All pieces also carry a
 * built-in Unbreaking V.
 */
public class CyanisidianArmorItem extends ArmorItem {
	public static final int BUILT_IN_UNBREAKING = 5;

	private final Stats stats;
	private final ItemAttributeModifiers modifiers;

	public CyanisidianArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Stats stats,
			Item.Properties properties) {
		super(material, type, properties.attributes(buildModifiers(type, stats)));
		this.stats = stats;
		this.modifiers = buildModifiers(type, stats);
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers() {
		return this.modifiers;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		EnchantBaking.apply(stack, level, Enchantments.UNBREAKING, BUILT_IN_UNBREAKING);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("tooltip.transfinite_void.builtin_unbreaking", BUILT_IN_UNBREAKING)
				.withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.unbreaking_note")
				.withStyle(ChatFormatting.DARK_GRAY));

		if (this.stats.knockbackResistance() > 0.0F) {
			tooltip.add(Component.translatable("tooltip.transfinite_void.knockback_resistance",
					format(this.stats.knockbackResistance())).withStyle(ChatFormatting.DARK_GRAY));
		}

		if (this.stats.maxHealth() > 0.0F) {
			tooltip.add(Component.translatable("tooltip.transfinite_void.max_health_bonus",
					format(this.stats.maxHealth())).withStyle(ChatFormatting.DARK_GRAY));
		}

		if (this.stats.movementSpeed() > 0.0F) {
			tooltip.add(Component.translatable("tooltip.transfinite_void.movement_speed_bonus",
					format(this.stats.movementSpeed())).withStyle(ChatFormatting.DARK_GRAY));
		}

		if (this.stats.jumpStrength() > 0.0F) {
			tooltip.add(Component.translatable("tooltip.transfinite_void.jump_strength_bonus",
					format(this.stats.jumpStrength())).withStyle(ChatFormatting.DARK_GRAY));
		}

		super.appendHoverText(stack, context, tooltip, flag);
	}

	private static String format(float value) {
		return value == Math.round(value) ? String.valueOf((int) value) : String.valueOf(value);
	}

	private static ItemAttributeModifiers buildModifiers(ArmorItem.Type type, Stats stats) {
		EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(type.getSlot());
		ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

		add(builder, slot, type, Attributes.ARMOR, "armor", stats.armor());
		add(builder, slot, type, Attributes.ARMOR_TOUGHNESS, "toughness", stats.toughness());
		add(builder, slot, type, Attributes.KNOCKBACK_RESISTANCE, "knockback", stats.knockbackResistance());
		add(builder, slot, type, Attributes.MAX_HEALTH, "health", stats.maxHealth());
		add(builder, slot, type, Attributes.MOVEMENT_SPEED, "speed", stats.movementSpeed());
		add(builder, slot, type, Attributes.JUMP_STRENGTH, "jump", stats.jumpStrength());

		return builder.build();
	}

	private static void add(ItemAttributeModifiers.Builder builder, EquipmentSlotGroup slot, ArmorItem.Type type,
			Holder<Attribute> attribute, String suffix, float amount) {
		if (amount == 0.0F) {
			return;
		}

		builder.add(attribute,
				new AttributeModifier(TransfiniteVoid.id("cyanisidian_" + type.getName() + "_" + suffix), amount,
						AttributeModifier.Operation.ADD_VALUE),
				slot);
	}

	/** Per-piece stats of the design. */
	public record Stats(float armor, float toughness, float knockbackResistance, float maxHealth,
			float movementSpeed, float jumpStrength) {
	}

	/** 蓝曜头盔: +15 armour, +2 toughness, +1 knockback resistance, +1 max health. */
	public static Function<Item.Properties, CyanisidianArmorItem> helmet(Holder<ArmorMaterial> material) {
		return properties -> new CyanisidianArmorItem(material, ArmorItem.Type.HELMET,
				new Stats(15.0F, 2.0F, 1.0F, 1.0F, 0.0F, 0.0F), properties);
	}

	/** 蓝曜胸甲: +20 armour, +3 toughness, +1.5 knockback resistance, +2 max health. */
	public static Function<Item.Properties, CyanisidianArmorItem> chestplate(Holder<ArmorMaterial> material) {
		return properties -> new CyanisidianArmorItem(material, ArmorItem.Type.CHESTPLATE,
				new Stats(20.0F, 3.0F, 1.5F, 2.0F, 0.0F, 0.0F), properties);
	}

	/** 蓝曜护腿: +17 armour, +2.5 toughness, +1.2 knockback resistance, +1 max health. */
	public static Function<Item.Properties, CyanisidianArmorItem> leggings(Holder<ArmorMaterial> material) {
		return properties -> new CyanisidianArmorItem(material, ArmorItem.Type.LEGGINGS,
				new Stats(17.0F, 2.5F, 1.2F, 1.0F, 0.0F, 0.0F), properties);
	}

	/** 蓝曜靴子: +10 armour, +2 toughness, +1 knockback resistance, +0.4 movement speed, +0.2 jump strength. */
	public static Function<Item.Properties, CyanisidianArmorItem> boots(Holder<ArmorMaterial> material) {
		return properties -> new CyanisidianArmorItem(material, ArmorItem.Type.BOOTS,
				new Stats(10.0F, 2.0F, 1.0F, 0.0F, 0.4F, 0.2F), properties);
	}
}
