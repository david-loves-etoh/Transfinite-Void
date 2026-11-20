package io.transfinite_void.registry;

import io.transfinite_void.TransfiniteVoid;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Two creative tabs: 超限虚空-材料 (ores, ingots, blocks, plates, rods, cores) and 超限虚空-装备 (everything held in the
 * main hand and the armour pieces).
 */
public final class ModCreativeTab {
	public static final ResourceKey<CreativeModeTab> MATERIALS_KEY =
			ResourceKey.create(Registries.CREATIVE_MODE_TAB, TransfiniteVoid.id("materials"));
	public static final ResourceKey<CreativeModeTab> EQUIPMENT_KEY =
			ResourceKey.create(Registries.CREATIVE_MODE_TAB, TransfiniteVoid.id("equipment"));

	private ModCreativeTab() {
	}

	public static void register() {
		tab(MATERIALS_KEY, "itemGroup.transfinite_void.materials",
				() -> new ItemStack(ModItems.CYANISIDIAN_CORE), ModCreativeTab::materials);
		tab(EQUIPMENT_KEY, "itemGroup.transfinite_void.equipment",
				() -> new ItemStack(ModItems.STARDUST_FIST_SWORD), ModCreativeTab::equipment);
	}

	private static void tab(ResourceKey<CreativeModeTab> key, String titleKey,
			Supplier<ItemStack> icon, Consumer<FabricItemGroupEntries> contents) {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key,
				FabricItemGroup.builder()
						.title(Component.translatable(titleKey))
						.icon(icon)
						.build());

		ItemGroupEvents.modifyEntriesEvent(key).register(contents::accept);
	}

	private static void materials(FabricItemGroupEntries entries) {
		// titanium
		entries.accept(ModItems.TITANIUM_ORE);
		entries.accept(ModItems.TITANIUM_BLOCK);
		entries.accept(ModItems.TITANIUM);
		entries.accept(ModItems.TITANIUM_PLATE);
		entries.accept(ModItems.TITANIUM_STICK);

		// cyanisidian
		entries.accept(ModItems.CYANISIDIAN_ORE);
		entries.accept(ModItems.CYANISIDIAN_BLOCK);
		entries.accept(ModItems.CYANISIDIAN);
		entries.accept(ModItems.CYANISIDIAN_CORE);
		entries.accept(ModItems.CYANISIDIAN_PLATE);
		entries.accept(ModItems.CYANISIDIAN_ROD);

		// stardust
		entries.accept(ModItems.STARDUST_ORE);
		entries.accept(ModItems.STARDUST_BLOCK);
		entries.accept(ModItems.STARDUST_DUST);
		entries.accept(ModItems.STARDUST_INGOT);
	}

	private static void equipment(FabricItemGroupEntries entries) {
		// titanium
		entries.accept(ModItems.TITANIUM_PICKAXE);
		entries.accept(ModItems.TITANIUM_AXE);
		entries.accept(ModItems.TITANIUM_SWORD);
		entries.accept(ModItems.TITANIUM_HELMET);
		entries.accept(ModItems.TITANIUM_CHESTPLATE);
		entries.accept(ModItems.TITANIUM_LEGGINGS);
		entries.accept(ModItems.TITANIUM_BOOTS);

		// cyanisidian
		entries.accept(ModItems.CYANISIDIAN_PICKAXE);
		entries.accept(ModItems.CYANISIDIAN_AXE);
		entries.accept(ModItems.CYANISIDIAN_SWORD);
		entries.accept(ModItems.CYANISIDIAN_HELMET);
		entries.accept(ModItems.CYANISIDIAN_CHESTPLATE);
		entries.accept(ModItems.CYANISIDIAN_LEGGINGS);
		entries.accept(ModItems.CYANISIDIAN_BOOTS);

		// stardust
		entries.accept(ModItems.STARDUST_PICKAXE);
		entries.accept(ModItems.STARDUST_AXE);
		entries.accept(ModItems.STARDUST_SWORD);
		entries.accept(ModItems.STARDUST_FIST_SWORD);
		entries.accept(ModItems.STARDUST_HELMET);
		entries.accept(ModItems.STARDUST_CHESTPLATE);
		entries.accept(ModItems.STARDUST_LEGGINGS);
		entries.accept(ModItems.STARDUST_BOOTS);

		// hand-held artefacts
		entries.accept(ModItems.PANTHEON);
		entries.accept(ModItems.POCKET_WATCH);
	}
}
