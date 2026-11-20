package io.transfinite_void.item;

import io.transfinite_void.TransfiniteVoid;
import io.transfinite_void.registry.ModEffects;
import io.transfinite_void.util.GearTooltip;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.List;
import java.util.function.Function;

/**
 * Titanium armour piece.
 * <p>
 * In 1.21 an armour material carries a single toughness value, but the titanium pieces have different values
 * (2 / 3 / 2.5 / 1.5), so the defence and toughness modifiers are built per piece and installed both as the
 * {@code attribute_modifiers} component (tooltip disabled) and as the item's default modifiers.
 */
public class TitaniumArmorItem extends ArmorItem implements WornEffectsItem {
	private final float defense;
	private final float toughness;
	private final List<WornEffect> wornEffects;
	private final ItemAttributeModifiers modifiers;

	public TitaniumArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, float defense, float toughness,
			List<WornEffect> wornEffects, Item.Properties properties) {
		super(material, type, properties.attributes(buildModifiers(type, defense, toughness)));
		this.defense = defense;
		this.toughness = toughness;
		this.wornEffects = List.copyOf(wornEffects);
		this.modifiers = buildModifiers(type, defense, toughness);
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
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		GearTooltip.armorHeader(tooltip, this.getType());
		GearTooltip.attribute(tooltip, Attributes.ARMOR, this.defense);
		GearTooltip.attribute(tooltip, Attributes.ARMOR_TOUGHNESS, this.toughness);
		GearTooltip.effects(tooltip, this.wornEffects);
		super.appendHoverText(stack, context, tooltip, flag);
	}

	private static ItemAttributeModifiers buildModifiers(ArmorItem.Type type, float defense, float toughness) {
		EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(type.getSlot());

		return ItemAttributeModifiers.builder()
				.add(Attributes.ARMOR,
						new AttributeModifier(TransfiniteVoid.id(type.getName() + "_armor"), defense,
								AttributeModifier.Operation.ADD_VALUE),
						slot)
				.add(Attributes.ARMOR_TOUGHNESS,
						new AttributeModifier(TransfiniteVoid.id(type.getName() + "_toughness"), toughness,
								AttributeModifier.Operation.ADD_VALUE),
						slot)
				.build()
				.withTooltip(false);
	}

	/** 钛头盔: +12 护甲值, +2 护甲韧性, 水下呼吸 + 生命提升 II. */
	public static Function<Item.Properties, TitaniumArmorItem> helmet(Holder<ArmorMaterial> material) {
		return properties -> new TitaniumArmorItem(material, ArmorItem.Type.HELMET, 12.0F, 2.0F,
				List.of(new WornEffect(MobEffects.WATER_BREATHING, 1), new WornEffect(MobEffects.HEALTH_BOOST, 2)),
				properties);
	}

	/** 钛胸甲: +15 护甲值, +3 护甲韧性, 生命回复 II + 抗性提升 II + 饱和 I. */
	public static Function<Item.Properties, TitaniumArmorItem> chestplate(Holder<ArmorMaterial> material) {
		return properties -> new TitaniumArmorItem(material, ArmorItem.Type.CHESTPLATE, 15.0F, 3.0F,
				List.of(new WornEffect(MobEffects.REGENERATION, 2), new WornEffect(MobEffects.DAMAGE_RESISTANCE, 2),
						new WornEffect(MobEffects.SATURATION, 1)),
				properties);
	}

	/** 钛护腿: +13 护甲值, +2.5 护甲韧性, 飞升 I + 跳跃提升 II. */
	public static Function<Item.Properties, TitaniumArmorItem> leggings(Holder<ArmorMaterial> material) {
		return properties -> new TitaniumArmorItem(material, ArmorItem.Type.LEGGINGS, 13.0F, 2.5F,
				List.of(new WornEffect(ModEffects.ASCENSION, 1),
						new WornEffect(MobEffects.JUMP, 2)),
				properties);
	}

	/** 钛靴子: +11 护甲值, +1.5 护甲韧性, 速度 II. */
	public static Function<Item.Properties, TitaniumArmorItem> boots(Holder<ArmorMaterial> material) {
		return properties -> new TitaniumArmorItem(material, ArmorItem.Type.BOOTS, 11.0F, 1.5F,
				List.of(new WornEffect(MobEffects.MOVEMENT_SPEED, 2)), properties);
	}
}
