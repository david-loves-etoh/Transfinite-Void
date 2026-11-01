package io.transfinite_void;

import io.transfinite_void.event.TitaniumArmorHandler;
import io.transfinite_void.registry.ModArmorMaterials;
import io.transfinite_void.registry.ModBlocks;
import io.transfinite_void.registry.ModCreativeTab;
import io.transfinite_void.registry.ModEffects;
import io.transfinite_void.registry.ModItems;
import io.transfinite_void.registry.ModPotions;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransfiniteVoid implements ModInitializer {
	public static final String MOD_ID = "transfinite_void";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Registration order matters: effects and materials must exist before the
		// items that reference them, and the creative tab must come last.
		ModEffects.register();
		ModArmorMaterials.register();
		ModBlocks.register();
		ModPotions.register();
		ModItems.register();
		ModCreativeTab.register();
		TitaniumArmorHandler.register();

		LOGGER.info("Transfinite Void loaded: titanium content registered.");
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
