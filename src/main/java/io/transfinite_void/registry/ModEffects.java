package io.transfinite_void.registry;

import io.transfinite_void.TransfiniteVoid;
import io.transfinite_void.effect.AscensionEffect;
import io.transfinite_void.effect.BleedingEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public final class ModEffects {
	/** 流血 / Bleeding — harmful, damage over time. */
	public static Holder<MobEffect> BLEEDING;
	/** 飞升 / Ascension — max health, attack damage and periodic healing scaled by level. */
	public static Holder<MobEffect> ASCENSION;

	private ModEffects() {
	}

	public static void register() {
		BLEEDING = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
				TransfiniteVoid.id("bleeding"), new BleedingEffect());
		ASCENSION = Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT,
				TransfiniteVoid.id("ascension"), new AscensionEffect());
	}
}
