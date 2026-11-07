package io.transfinite_void.registry;

import io.transfinite_void.TransfiniteVoid;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

/**
 * Dedicated 超限虚空 creative tab.
 * <p>
 * {@code FabricItemGroup.builder()} is used instead of {@code CreativeModeTab.builder(Row, column)} on purpose: vanilla
 * has no free tab slot left in 1.21.1, while Fabric's builder registers the tab with an "auto" position that its client
 * mixin appends after the vanilla tabs.
 */
public final class ModCreativeTab {
	public static final ResourceKey<CreativeModeTab> TAB_KEY =
			ResourceKey.create(Registries.CREATIVE_MODE_TAB, TransfiniteVoid.id("main"));

	private ModCreativeTab() {
	}

	public static void register() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_KEY,
				FabricItemGroup.builder()
						.title(Component.translatable("itemGroup.transfinite_void"))
						.icon(() -> new ItemStack(ModItems.TITANIUM_PLATE))
						.build());

		ItemGroupEvents.modifyEntriesEvent(TAB_KEY).register(entries -> {
			entries.accept(ModItems.TITANIUM_ORE);
			entries.accept(ModItems.TITANIUM_BLOCK);
			entries.accept(ModItems.TITANIUM);
			entries.accept(ModItems.TITANIUM_PLATE);
			entries.accept(ModItems.TITANIUM_STICK);

			entries.accept(ModItems.TITANIUM_PICKAXE);
			entries.accept(ModItems.TITANIUM_AXE);
			entries.accept(ModItems.TITANIUM_SWORD);

			entries.accept(ModItems.TITANIUM_HELMET);
			entries.accept(ModItems.TITANIUM_CHESTPLATE);
			entries.accept(ModItems.TITANIUM_LEGGINGS);
			entries.accept(ModItems.TITANIUM_BOOTS);

			entries.accept(ModItems.PANTHEON);

			// 飞升 I-X 药水 deliberately stay out of this tab: vanilla's "Food & Drinks" tab generates one
			// minecraft:potion entry per registered Potion, so they already show up there (and only there).
		});
	}
}
