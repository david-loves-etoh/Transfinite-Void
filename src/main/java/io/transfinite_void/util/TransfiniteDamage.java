package io.transfinite_void.util;

import io.transfinite_void.TransfiniteVoid;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

/**
 * Damage types added by the mod ({@code data/transfinite_void/damage_type/...}).
 */
public final class TransfiniteDamage {
	/** {@code transfinite_void:bleeding} — bypasses armour, see the datapack tags. */
	public static final ResourceKey<DamageType> BLEEDING =
			ResourceKey.create(Registries.DAMAGE_TYPE, TransfiniteVoid.id("bleeding"));

	private TransfiniteDamage() {
	}

	public static DamageSource bleeding(ServerLevel level) {
		return level.registryAccess()
				.lookupOrThrow(Registries.DAMAGE_TYPE)
				.get(BLEEDING)
				.<DamageSource>map(DamageSource::new)
				.orElseGet(() -> level.damageSources().magic());
	}
}
