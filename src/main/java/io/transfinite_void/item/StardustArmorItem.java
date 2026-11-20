package io.transfinite_void.item;

import io.transfinite_void.TransfiniteVoid;
import io.transfinite_void.registry.ModEffects;
import io.transfinite_void.util.GearTooltip;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
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
 * 星陨甲 piece — unbreakable (the {@code unbreakable} component is set by the registry) and carrying the per-piece
 * armour / toughness / max-health plus the mod's own "magic &amp; true defense" value.
 * <p>
 * "魔法，真实防御" has no vanilla attribute: the value is summed from the worn pieces and subtracted from incoming
 * magic damage and from {@code transfinite_void:true_damage} in {@code LivingEntityMixin}.
 */
public class StardustArmorItem extends ArmorItem implements WornEffectsItem {
	private final Stats stats;
	private final List<WornEffect> wornEffects;
	private final ItemAttributeModifiers modifiers;

	public StardustArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Stats stats,
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

	public int magicDefense() {
		return this.stats.magicDefense();
	}

	/** Total flat magic / true-damage reduction granted by the stardust pieces the entity wears. */
	public static int totalMagicDefense(LivingEntity entity) {
		int total = 0;

		for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST,
				EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
			if (entity.getItemBySlot(slot).getItem() instanceof StardustArmorItem piece) {
				total += piece.magicDefense();
			}
		}

		return total;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		GearTooltip.unbreakable(tooltip);
		GearTooltip.armorHeader(tooltip, this.getType());
		GearTooltip.attribute(tooltip, Attributes.ARMOR, this.stats.armor());
		GearTooltip.attribute(tooltip, Attributes.ARMOR_TOUGHNESS, this.stats.toughness());
		GearTooltip.attribute(tooltip, Attributes.MAX_HEALTH, this.stats.maxHealth());
		GearTooltip.customBonus(tooltip, "stat.transfinite_void.magic_defense", this.stats.magicDefense());
		GearTooltip.effects(tooltip, this.wornEffects);
		super.appendHoverText(stack, context, tooltip, flag);
	}

	private static ItemAttributeModifiers buildModifiers(ArmorItem.Type type, Stats stats) {
		EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(type.getSlot());
		ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();

		add(builder, slot, type, Attributes.ARMOR, "armor", stats.armor());
		add(builder, slot, type, Attributes.ARMOR_TOUGHNESS, "toughness", stats.toughness());
		add(builder, slot, type, Attributes.MAX_HEALTH, "health", stats.maxHealth());

		return builder.build().withTooltip(false);
	}

	private static void add(ItemAttributeModifiers.Builder builder, EquipmentSlotGroup slot, ArmorItem.Type type,
			Holder<Attribute> attribute, String suffix, float amount) {
		if (amount == 0.0F) {
			return;
		}

		builder.add(attribute,
				new AttributeModifier(TransfiniteVoid.id("stardust_" + type.getName() + "_" + suffix), amount,
						AttributeModifier.Operation.ADD_VALUE),
				slot);
	}

	/** armour, toughness, max health, magic / true defence. */
	public record Stats(float armor, float toughness, float maxHealth, int magicDefense) {
	}

	/** 星陨头盔: +25 armour, +5 toughness, +5 max health, +3 magic defence, 水下呼吸 III + 急迫 III. */
	public static Function<Item.Properties, StardustArmorItem> helmet(Holder<ArmorMaterial> material) {
		return properties -> new StardustArmorItem(material, ArmorItem.Type.HELMET,
				new Stats(25.0F, 5.0F, 5.0F, 3),
				List.of(new WornEffect(MobEffects.WATER_BREATHING, 3), new WornEffect(MobEffects.DIG_SPEED, 3)),
				properties);
	}

	/** 星陨胸甲: +50 armour, +9 toughness, +10 max health, +6 magic defence, 村庄英雄 III + 飞升 V + 生命提升 III + 生命恢复 III. */
	public static Function<Item.Properties, StardustArmorItem> chestplate(Holder<ArmorMaterial> material) {
		return properties -> new StardustArmorItem(material, ArmorItem.Type.CHESTPLATE,
				new Stats(50.0F, 9.0F, 10.0F, 6),
				List.of(new WornEffect(MobEffects.HERO_OF_THE_VILLAGE, 3), new WornEffect(ModEffects.ASCENSION, 5),
						new WornEffect(MobEffects.HEALTH_BOOST, 3), new WornEffect(MobEffects.REGENERATION, 3)),
				properties);
	}

	/** 星陨护腿: +30 armour, +6 toughness, +8 max health, +4 magic defence, 跳跃提升 IV + 缓降. */
	public static Function<Item.Properties, StardustArmorItem> leggings(Holder<ArmorMaterial> material) {
		return properties -> new StardustArmorItem(material, ArmorItem.Type.LEGGINGS,
				new Stats(30.0F, 6.0F, 8.0F, 4),
				List.of(new WornEffect(MobEffects.JUMP, 4), new WornEffect(MobEffects.SLOW_FALLING, 1)),
				properties);
	}

	/** 星陨靴子: +20 armour, +4 toughness, +15 max health, +1 magic defence, 速度 V. */
	public static Function<Item.Properties, StardustArmorItem> boots(Holder<ArmorMaterial> material) {
		return properties -> new StardustArmorItem(material, ArmorItem.Type.BOOTS,
				new Stats(20.0F, 4.0F, 15.0F, 1),
				List.of(new WornEffect(MobEffects.MOVEMENT_SPEED, 5)),
				properties);
	}
}
