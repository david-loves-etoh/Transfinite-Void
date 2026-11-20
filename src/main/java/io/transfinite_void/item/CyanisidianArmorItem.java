package io.transfinite_void.item;

import io.transfinite_void.TransfiniteVoid;
import io.transfinite_void.registry.ModEffects;
import io.transfinite_void.util.EnchantBaking;
import io.transfinite_void.util.GearTooltip;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlotGroup;
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
 * 蓝曜甲 piece: per-piece armour / toughness / knockback / max health / movement / jump modifiers, a built-in
 * Unbreaking V and (chestplate only) 飞升 III + 生命提升 III while worn.
 */
public class CyanisidianArmorItem extends ArmorItem implements WornEffectsItem {
	public static final int BUILT_IN_UNBREAKING = 5;

	private final Stats stats;
	private final List<WornEffect> wornEffects;
	private final ItemAttributeModifiers modifiers;

	public CyanisidianArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Stats stats,
			List<WornEffect> wornEffects, Item.Properties properties) {
		super(material, type, properties.attributes(buildModifiers(type, stats)));
		this.stats = stats;
		this.wornEffects = List.copyOf(wornEffects);
		this.modifiers = buildModifiers(type, stats);
	}

	@Override
	public ItemAttributeModifiers getDefaultAttributeModifiers() {
		return this.modifiers;
	}

	@Override
	public List<WornEffect> wornEffects() {
		return this.wornEffects;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		EnchantBaking.apply(stack, level, Enchantments.UNBREAKING, BUILT_IN_UNBREAKING);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		GearTooltip.armorHeader(tooltip, this.getType());
		GearTooltip.attribute(tooltip, Attributes.ARMOR, this.stats.armor());
		GearTooltip.attribute(tooltip, Attributes.ARMOR_TOUGHNESS, this.stats.toughness());

		if (this.stats.maxHealth() > 0.0F) {
			GearTooltip.attribute(tooltip, Attributes.MAX_HEALTH, this.stats.maxHealth());
		}

		if (this.stats.knockbackResistance() > 0.0F) {
			GearTooltip.attribute(tooltip, Attributes.KNOCKBACK_RESISTANCE, GearTooltip.knockbackDisplay(this.stats.knockbackResistance()));
		}

		if (this.stats.movementSpeed() > 0.0F) {
			GearTooltip.attribute(tooltip, Attributes.MOVEMENT_SPEED, this.stats.movementSpeed());
		}

		if (this.stats.jumpStrength() > 0.0F) {
			GearTooltip.attribute(tooltip, Attributes.JUMP_STRENGTH, this.stats.jumpStrength());
		}

		GearTooltip.enchantment(tooltip, Enchantments.UNBREAKING, BUILT_IN_UNBREAKING);
		GearTooltip.effects(tooltip, this.wornEffects);
		super.appendHoverText(stack, context, tooltip, flag);
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

		return builder.build().withTooltip(false);
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

	/** armour, toughness, knockback resistance, max health, movement speed, jump strength. */
	public record Stats(float armor, float toughness, float knockbackResistance, float maxHealth,
			float movementSpeed, float jumpStrength) {
	}

	private static final List<WornEffect> NONE = List.of();
	private static final List<WornEffect> CHEST_EFFECTS = List.of(
			new WornEffect(ModEffects.ASCENSION, 3),
			new WornEffect(MobEffects.HEALTH_BOOST, 3));

	/** 蓝曜头盔: +15 armour, +2 toughness, +1 knockback resistance, +1 max health. */
	public static Function<Item.Properties, CyanisidianArmorItem> helmet(Holder<ArmorMaterial> material) {
		return properties -> new CyanisidianArmorItem(material, ArmorItem.Type.HELMET,
				new Stats(15.0F, 2.0F, 0.1F, 1.0F, 0.0F, 0.0F), NONE, properties);
	}

	/** 蓝曜胸甲: +20 armour, +3 toughness, +1.5 knockback resistance, +2 max health, 飞升 III + 生命提升 III. */
	public static Function<Item.Properties, CyanisidianArmorItem> chestplate(Holder<ArmorMaterial> material) {
		return properties -> new CyanisidianArmorItem(material, ArmorItem.Type.CHESTPLATE,
				new Stats(20.0F, 3.0F, 0.15F, 2.0F, 0.0F, 0.0F), CHEST_EFFECTS, properties);
	}

	/** 蓝曜护腿: +17 armour, +2.5 toughness, +1.2 knockback resistance, +1 max health. */
	public static Function<Item.Properties, CyanisidianArmorItem> leggings(Holder<ArmorMaterial> material) {
		return properties -> new CyanisidianArmorItem(material, ArmorItem.Type.LEGGINGS,
				new Stats(17.0F, 2.5F, 0.12F, 1.0F, 0.0F, 0.0F), NONE, properties);
	}

	/** 蓝曜靴子: +10 armour, +2 toughness, +1 knockback resistance, +0.4 movement speed, +0.2 jump strength. */
	public static Function<Item.Properties, CyanisidianArmorItem> boots(Holder<ArmorMaterial> material) {
		return properties -> new CyanisidianArmorItem(material, ArmorItem.Type.BOOTS,
				new Stats(10.0F, 2.0F, 0.1F, 0.0F, 0.4F, 0.2F), NONE, properties);
	}
}
