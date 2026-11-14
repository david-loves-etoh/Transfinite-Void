package io.transfinite_void.registry;

import io.transfinite_void.TransfiniteVoid;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

import java.util.ArrayList;
import java.util.List;

/**
 * Potions of the mod.
 * <p>
 * Vanilla's "Food & Drinks" creative tab iterates the potion registry and generates one {@code minecraft:potion},
 * {@code splash_potion}, {@code lingering_potion} and tipped-arrow entry for every registered {@link Potion}, so
 * registering the effects as potions is enough — no custom items are needed. Names come from the
 * {@code item.minecraft.{,splash_,lingering_}potion.effect.<name>} / {@code item.minecraft.tipped_arrow.effect.<name>}
 * translation keys, and the Roman level suffix uses the {@code potion.potency.<amplifier>} keys.
 */
public final class ModPotions {
	/** 飞升 I - X are creative-only for now; each one lasts 3 minutes like a vanilla potion. */
	public static final int ASCENSION_LEVELS = 10;
	public static final int ASCENSION_DURATION = 3600;

	/** 流血 I - X, 20 seconds each (the same duration the titanium sword applies). */
	public static final int BLEEDING_LEVELS = 10;
	public static final int BLEEDING_DURATION = 400;

	private static final List<Holder<Potion>> ASCENSION = new ArrayList<>(ASCENSION_LEVELS);
	private static final List<Holder<Potion>> BLEEDING = new ArrayList<>(BLEEDING_LEVELS);

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

		for (int level = 1; level <= BLEEDING_LEVELS; level++) {
			MobEffectInstance effect = new MobEffectInstance(ModEffects.BLEEDING, BLEEDING_DURATION, level - 1);
			BLEEDING.add(Registry.registerForHolder(
					BuiltInRegistries.POTION,
					TransfiniteVoid.id("bleeding_" + level),
					new Potion("bleeding_" + level, effect)));
		}
	}

	/** @param level 1 - {@link #ASCENSION_LEVELS} */
	public static Holder<Potion> ascension(int level) {
		return ASCENSION.get(level - 1);
	}

	/** @param level 1 - {@link #BLEEDING_LEVELS} */
	public static Holder<Potion> bleeding(int level) {
		return BLEEDING.get(level - 1);
	}
}
