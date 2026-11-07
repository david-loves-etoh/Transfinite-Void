package io.transfinite_void.registry;

import io.transfinite_void.TransfiniteVoid;
import io.transfinite_void.item.TitaniumArmorItem;
import io.transfinite_void.item.TitaniumAxeItem;
import io.transfinite_void.item.TitaniumPickaxeItem;
import io.transfinite_void.item.TitaniumSwordItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

import java.util.function.Function;

public final class ModItems {
	/** 钛 — the base material, dropped by titanium ore. */
	public static Item TITANIUM;
	/** 钛板 — 2 titanium, the crafting material of every piece of titanium gear. */
	public static Item TITANIUM_PLATE;
	/** 钛棍 — 2 titanium plates, the handle of the pickaxe / axe / sword. */
	public static Item TITANIUM_STICK;

	public static Item TITANIUM_ORE;

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
	}

	private static <T extends Item> T register(String name, Function<Item.Properties, T> factory,
			Item.Properties properties) {
		return Registry.register(BuiltInRegistries.ITEM, TransfiniteVoid.id(name), factory.apply(properties));
	}
}
