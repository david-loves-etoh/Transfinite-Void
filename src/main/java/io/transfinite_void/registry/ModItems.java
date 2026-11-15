package io.transfinite_void.registry;

import io.transfinite_void.TransfiniteVoid;
import io.transfinite_void.item.CyanisidianArmorItem;
import io.transfinite_void.item.CyanisidianAxeItem;
import io.transfinite_void.item.CyanisidianPickaxeItem;
import io.transfinite_void.item.CyanisidianSwordItem;
import io.transfinite_void.item.PantheonItem;
import io.transfinite_void.item.StardustArmorItem;
import io.transfinite_void.item.StardustAxeItem;
import io.transfinite_void.item.StardustPickaxeItem;
import io.transfinite_void.item.StardustSwordItem;
import io.transfinite_void.item.TitaniumArmorItem;
import io.transfinite_void.item.TitaniumAxeItem;
import io.transfinite_void.item.TitaniumPickaxeItem;
import io.transfinite_void.item.TitaniumSwordItem;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.Unbreakable;

import java.util.function.Function;

public final class ModItems {
	/** 钛 — the base material, dropped by titanium ore. */
	public static Item TITANIUM;
	/** 钛板 — 2 titanium, the crafting material of every piece of titanium gear. */
	public static Item TITANIUM_PLATE;
	/** 钛棍 — 2 titanium plates, the handle of the pickaxe / axe / sword. */
	public static Item TITANIUM_STICK;

	public static Item TITANIUM_ORE;
	public static Item TITANIUM_BLOCK;

	/** 万神殿 / Pantheon — summons Ascension-buffed bosses. */
	public static Item PANTHEON;

	/** 蓝曜石 / Cyanisidian gem, dropped by the ore. */
	public static Item CYANISIDIAN;
	/** 蓝曜核心 (C) — 4 redstone blocks + 5 cyanisidian. */
	public static Item CYANISIDIAN_CORE;
	/** 蓝曜板 (B) — 2 cyanisidian blocks -> 3 plates. */
	public static Item CYANISIDIAN_PLATE;
	/** 蓝曜棍 (S) — 1 cyanisidian + 1 plate. */
	public static Item CYANISIDIAN_ROD;

	public static Item CYANISIDIAN_ORE;
	public static Item CYANISIDIAN_BLOCK;

	public static Item CYANISIDIAN_PICKAXE;
	public static Item CYANISIDIAN_AXE;
	public static Item CYANISIDIAN_SWORD;

	public static Item CYANISIDIAN_HELMET;
	public static Item CYANISIDIAN_CHESTPLATE;
	public static Item CYANISIDIAN_LEGGINGS;
	public static Item CYANISIDIAN_BOOTS;

	/** 星陨粒 / Stardust Dust — 9 make an ingot. */
	public static Item STARDUST_DUST;
	/** 星陨锭 / Stardust Ingot — 9 make a block. */
	public static Item STARDUST_INGOT;

	public static Item STARDUST_ORE;
	public static Item STARDUST_BLOCK;

	public static Item STARDUST_PICKAXE;
	public static Item STARDUST_AXE;
	public static Item STARDUST_SWORD;

	public static Item STARDUST_HELMET;
	public static Item STARDUST_CHESTPLATE;
	public static Item STARDUST_LEGGINGS;
	public static Item STARDUST_BOOTS;

	public static Item TITANIUM_PICKAXE;
	public static Item TITANIUM_AXE;
	public static Item TITANIUM_SWORD;

	public static Item TITANIUM_HELMET;
	public static Item TITANIUM_CHESTPLATE;
	public static Item TITANIUM_LEGGINGS;
	public static Item TITANIUM_BOOTS;

	private ModItems() {
	}

	public static void register() {
		TITANIUM = register("titanium", Item::new, new Item.Properties());
		TITANIUM_PLATE = register("titanium_plate", Item::new, new Item.Properties());
		TITANIUM_STICK = register("titanium_stick", Item::new, new Item.Properties());

		TITANIUM_ORE = register("titanium_ore",
				properties -> new BlockItem(ModBlocks.TITANIUM_ORE, properties),
				new Item.Properties());

		TITANIUM_BLOCK = register("titanium_block",
				properties -> new BlockItem(ModBlocks.TITANIUM_BLOCK, properties),
				new Item.Properties());

		PANTHEON = register("pantheon", PantheonItem::new,
				new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));

		TITANIUM_PICKAXE = register("titanium_pickaxe",
				properties -> new TitaniumPickaxeItem(ModTiers.TITANIUM_PICKAXE, properties),
				new Item.Properties().attributes(
						DiggerItem.createAttributes(ModTiers.TITANIUM_PICKAXE, 4.0F, -2.8F)));
		TITANIUM_AXE = register("titanium_axe",
				properties -> new TitaniumAxeItem(ModTiers.TITANIUM_AXE, properties),
				new Item.Properties().attributes(
						DiggerItem.createAttributes(ModTiers.TITANIUM_AXE, 6.0F, -3.0F)));
		TITANIUM_SWORD = register("titanium_sword",
				properties -> new TitaniumSwordItem(ModTiers.TITANIUM_SWORD, properties),
				new Item.Properties().attributes(
						SwordItem.createAttributes(ModTiers.TITANIUM_SWORD, 9, -2.4F)));

		TITANIUM_HELMET = register("titanium_helmet",
				TitaniumArmorItem.helmet(ModArmorMaterials.TITANIUM),
				new Item.Properties().durability(114514));
		TITANIUM_CHESTPLATE = register("titanium_chestplate",
				TitaniumArmorItem.chestplate(ModArmorMaterials.TITANIUM),
				new Item.Properties().durability(214719));
		TITANIUM_LEGGINGS = register("titanium_leggings",
				TitaniumArmorItem.leggings(ModArmorMaterials.TITANIUM),
				new Item.Properties().durability(188888));
		// The boots durability was not specified in the design; 99999 is used as a placeholder.
		TITANIUM_BOOTS = register("titanium_boots",
				TitaniumArmorItem.boots(ModArmorMaterials.TITANIUM),
				new Item.Properties().durability(99999));

		registerCyanisidian();
	}

	private static void registerCyanisidian() {
		CYANISIDIAN = register("cyanisidian", Item::new, new Item.Properties().rarity(Rarity.RARE));
		CYANISIDIAN_CORE = register("cyanisidian_core", Item::new, new Item.Properties().rarity(Rarity.EPIC));
		CYANISIDIAN_PLATE = register("cyanisidian_plate", Item::new, new Item.Properties());
		CYANISIDIAN_ROD = register("cyanisidian_rod", Item::new, new Item.Properties());

		CYANISIDIAN_ORE = register("cyanisidian_ore",
				properties -> new BlockItem(ModBlocks.CYANISIDIAN_ORE, properties),
				new Item.Properties());
		CYANISIDIAN_BLOCK = register("cyanisidian_block",
				properties -> new BlockItem(ModBlocks.CYANISIDIAN_BLOCK, properties),
				new Item.Properties());

		CYANISIDIAN_PICKAXE = register("cyanisidian_pickaxe",
				properties -> new CyanisidianPickaxeItem(ModTiers.CYANISIDIAN_PICKAXE, properties),
				new Item.Properties().attributes(
						DiggerItem.createAttributes(ModTiers.CYANISIDIAN_PICKAXE, 4.0F, -2.8F)));

		// Efficiency V is the mining_efficiency attribute (+26), which is exactly what the enchantment grants.
		CYANISIDIAN_AXE = register("cyanisidian_axe",
				properties -> new CyanisidianAxeItem(ModTiers.CYANISIDIAN_AXE, properties),
				new Item.Properties().attributes(
						DiggerItem.createAttributes(ModTiers.CYANISIDIAN_AXE, 6.0F, -3.0F)
								.withModifierAdded(Attributes.MINING_EFFICIENCY,
										new AttributeModifier(TransfiniteVoid.id("cyanisidian_axe_efficiency"),
												CyanisidianAxeItem.BUILT_IN_EFFICIENCY,
												AttributeModifier.Operation.ADD_VALUE),
										EquipmentSlotGroup.MAINHAND)));

		CYANISIDIAN_SWORD = register("cyanisidian_sword",
				properties -> new CyanisidianSwordItem(ModTiers.CYANISIDIAN_SWORD, properties),
				new Item.Properties().attributes(
						SwordItem.createAttributes(ModTiers.CYANISIDIAN_SWORD, 17, -2.4F)
								.withModifierAdded(Attributes.MOVEMENT_SPEED,
										new AttributeModifier(TransfiniteVoid.id("cyanisidian_sword_speed"),
												CyanisidianSwordItem.HELD_MOVEMENT_SPEED,
												AttributeModifier.Operation.ADD_VALUE),
										EquipmentSlotGroup.MAINHAND)
								.withModifierAdded(Attributes.JUMP_STRENGTH,
										new AttributeModifier(TransfiniteVoid.id("cyanisidian_sword_jump"),
												CyanisidianSwordItem.HELD_JUMP_STRENGTH,
												AttributeModifier.Operation.ADD_VALUE),
										EquipmentSlotGroup.MAINHAND)));

		CYANISIDIAN_HELMET = register("cyanisidian_helmet",
				CyanisidianArmorItem.helmet(ModArmorMaterials.CYANISIDIAN),
				new Item.Properties().durability(1000000));
		CYANISIDIAN_CHESTPLATE = register("cyanisidian_chestplate",
				CyanisidianArmorItem.chestplate(ModArmorMaterials.CYANISIDIAN),
				new Item.Properties().durability(2000000));
		CYANISIDIAN_LEGGINGS = register("cyanisidian_leggings",
				CyanisidianArmorItem.leggings(ModArmorMaterials.CYANISIDIAN),
				new Item.Properties().durability(1000000));
		CYANISIDIAN_BOOTS = register("cyanisidian_boots",
				CyanisidianArmorItem.boots(ModArmorMaterials.CYANISIDIAN),
				new Item.Properties().durability(1000000));

		registerStardust();
	}

	private static void registerStardust() {
		STARDUST_DUST = register("stardust_dust", Item::new, new Item.Properties().rarity(Rarity.RARE));
		STARDUST_INGOT = register("stardust_ingot", Item::new, new Item.Properties().rarity(Rarity.EPIC));

		STARDUST_ORE = register("stardust_ore",
				properties -> new BlockItem(ModBlocks.STARDUST_ORE, properties),
				new Item.Properties());
		STARDUST_BLOCK = register("stardust_block",
				properties -> new BlockItem(ModBlocks.STARDUST_BLOCK, properties),
				new Item.Properties());

		STARDUST_PICKAXE = register("stardust_pickaxe",
				properties -> new StardustPickaxeItem(ModTiers.STARDUST_PICKAXE, properties),
				new Item.Properties().attributes(
						DiggerItem.createAttributes(ModTiers.STARDUST_PICKAXE, 4.0F, -2.8F)));

		STARDUST_AXE = register("stardust_axe",
				properties -> new StardustAxeItem(ModTiers.STARDUST_AXE, properties),
				new Item.Properties().attributes(
						DiggerItem.createAttributes(ModTiers.STARDUST_AXE, 6.0F, -3.0F)));

		// +0.8 attack speed modifier -> 4.8 attacks per second, i.e. a third of a netherite sword's cooldown (4.0 - 2.4).
		STARDUST_SWORD = register("stardust_sword",
				properties -> new StardustSwordItem(ModTiers.STARDUST_SWORD, properties),
				new Item.Properties().attributes(
						SwordItem.createAttributes(ModTiers.STARDUST_SWORD, 29, 0.8F)));

		STARDUST_HELMET = register("stardust_helmet",
				StardustArmorItem.helmet(ModArmorMaterials.STARDUST), unbreakableArmor());
		STARDUST_CHESTPLATE = register("stardust_chestplate",
				StardustArmorItem.chestplate(ModArmorMaterials.STARDUST), unbreakableArmor());
		STARDUST_LEGGINGS = register("stardust_leggings",
				StardustArmorItem.leggings(ModArmorMaterials.STARDUST), unbreakableArmor());
		STARDUST_BOOTS = register("stardust_boots",
				StardustArmorItem.boots(ModArmorMaterials.STARDUST), unbreakableArmor());
	}

	/**
	 * Unbreakable, non-stacking armour. Without a MAX_DAMAGE component an item would stack to 64, so stacksTo(1) is
	 * set explicitly and the durability is handled purely by the UNBREAKABLE component.
	 */
	private static Item.Properties unbreakableArmor() {
		return new Item.Properties()
				.stacksTo(1)
				.component(DataComponents.UNBREAKABLE, new Unbreakable(false));
	}

	private static <T extends Item> T register(String name, Function<Item.Properties, T> factory,
			Item.Properties properties) {
		return Registry.register(BuiltInRegistries.ITEM, TransfiniteVoid.id(name), factory.apply(properties));
	}
}
