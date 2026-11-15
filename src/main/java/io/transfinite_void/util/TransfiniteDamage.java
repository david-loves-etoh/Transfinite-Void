package io.transfinite_void.util;

import io.transfinite_void.TransfiniteVoid;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

/**
 * Damage types added by the mod ({@code data/transfinite_void/damage_type/...}).
 */
public final class TransfiniteDamage {
	/** {@code transfinite_void:bleeding} — bypasses armour, see the datapack tags. */
	public static final ResourceKey<DamageType> BLEEDING =
			ResourceKey.create(Registries.DAMAGE_TYPE, TransfiniteVoid.id("bleeding"));
	/** {@code transfinite_void:true_damage} — bypasses armour, effects, enchantments, resistance and i-frames. */
	public static final ResourceKey<DamageType> TRUE_DAMAGE =
			ResourceKey.create(Registries.DAMAGE_TYPE, TransfiniteVoid.id("true_damage"));

	private TransfiniteDamage() {
	}

	public static DamageSource bleeding(ServerLevel level) {
		return level.registryAccess()
				.lookupOrThrow(Registries.DAMAGE_TYPE)
				.get(BLEEDING)
				.<DamageSource>map(DamageSource::new)
				.orElseGet(() -> level.damageSources().magic());
	}

	public static DamageSource trueDamage(ServerLevel level, Entity attacker) {
		return level.registryAccess()
				.lookupOrThrow(Registries.DAMAGE_TYPE)
				.get(TRUE_DAMAGE)
				.<DamageSource>map(holder -> new DamageSource(holder, attacker, attacker))
				.orElseGet(() -> level.damageSources().generic());
	}
}
