package io.transfinite_void.registry;

import io.transfinite_void.TransfiniteVoid;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

import java.util.ArrayList;
import java.util.List;

public final class ModPotions {
	/** 飞升 I - X are creative-only for now; each one lasts 3 minutes like a vanilla potion. */
	public static final int ASCENSION_LEVELS = 10;
	public static final int ASCENSION_DURATION = 3600;

	private static final List<Holder<Potion>> ASCENSION = new ArrayList<>(ASCENSION_LEVELS);

	private ModPotions() {
	}

	public static void register() {
		for (int level = 1; level <= ASCENSION_LEVELS; level++) {
			MobEffectInstance effect = new MobEffectInstance(ModEffects.ASCENSION, ASCENSION_DURATION, level - 1);
			ASCENSION.add(Registry.registerForHolder(
					BuiltInRegistries.POTION,
					TransfiniteVoid.id("ascension_" + level),
					new Potion("ascension_" + level, effect)));
		}
	}

	/** @param level 1 - {@link #ASCENSION_LEVELS} */
	public static Holder<Potion> ascension(int level) {
		return ASCENSION.get(level - 1);
	}
}
