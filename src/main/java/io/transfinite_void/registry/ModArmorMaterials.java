package io.transfinite_void.registry;

import io.transfinite_void.TransfiniteVoid;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public final class ModArmorMaterials {
	/** Armour texture layers: assets/transfinite_void/textures/models/armor/titanium_layer_1|2.png */
	public static Holder<ArmorMaterial> TITANIUM;
	/** 蓝曜甲: layers at assets/transfinite_void/textures/models/armor/cyanisidian_layer_1|2.png */
	public static Holder<ArmorMaterial> CYANISIDIAN;

	private ModArmorMaterials() {
	}

	public static void register() {
		EnumMap<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
		defense.put(ArmorItem.Type.HELMET, 12);
		defense.put(ArmorItem.Type.CHESTPLATE, 15);
		defense.put(ArmorItem.Type.LEGGINGS, 13);
		defense.put(ArmorItem.Type.BOOTS, 11);
		defense.put(ArmorItem.Type.BODY, 15);

		ArmorMaterial material = new ArmorMaterial(
				Map.copyOf(defense),
				15,
				SoundEvents.ARMOR_EQUIP_NETHERITE,
				() -> Ingredient.of(ModItems.TITANIUM_PLATE),
				List.of(new ArmorMaterial.Layer(TransfiniteVoid.id("titanium"))),
				2.0F,
				0.0F);

		TITANIUM = Registry.registerForHolder(
				BuiltInRegistries.ARMOR_MATERIAL, TransfiniteVoid.id("titanium"), material);

		EnumMap<ArmorItem.Type, Integer> cyanisidianDefense = new EnumMap<>(ArmorItem.Type.class);
		cyanisidianDefense.put(ArmorItem.Type.HELMET, 15);
		cyanisidianDefense.put(ArmorItem.Type.CHESTPLATE, 20);
		cyanisidianDefense.put(ArmorItem.Type.LEGGINGS, 17);
		cyanisidianDefense.put(ArmorItem.Type.BOOTS, 10);
		cyanisidianDefense.put(ArmorItem.Type.BODY, 20);

		ArmorMaterial cyanisidian = new ArmorMaterial(
				Map.copyOf(cyanisidianDefense),
				15,
				SoundEvents.ARMOR_EQUIP_NETHERITE,
				() -> Ingredient.of(ModItems.CYANISIDIAN_PLATE),
				List.of(new ArmorMaterial.Layer(TransfiniteVoid.id("cyanisidian"))),
				3.0F,
				0.0F);

		CYANISIDIAN = Registry.registerForHolder(
				BuiltInRegistries.ARMOR_MATERIAL, TransfiniteVoid.id("cyanisidian"), cyanisidian);
	}
}
