package io.transfinite_void.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * The 10%-chance double-damage combo shared by the stardust weapons.
 * <p>
 * Every triggering hit deals twice the previous damage and may immediately chain again with the same 10% chance;
 * the invulnerability window is cleared before each hit so a follow-up always lands as a full hit instead of being
 * swallowed by the target's i-frames. The chain is capped at {@link #MAX_DEPTH} steps so a lucky streak cannot loop.
 */
public final class ComboDamage {
	public static final float CHANCE = 0.10F;
	public static final int MAX_DEPTH = 10;

	private ComboDamage() {
	}

	/**
	 * @param damage damage of the *next* combo step (already doubled by the caller for the first step)
	 * @param depth  1 for the first combo step
	 */
	public static void tryCombo(ServerLevel level, LivingEntity target, LivingEntity attacker, float damage, int depth) {
		if (depth > MAX_DEPTH || damage <= 0.0F || !target.isAlive() || level.random.nextFloat() >= CHANCE) {
			return;
		}

		DamageSource source = attacker instanceof Player player
				? level.damageSources().playerAttack(player)
				: level.damageSources().mobAttack(attacker);

		target.invulnerableTime = 0;

		if (target.hurt(source, damage)) {
			tryCombo(level, target, attacker, damage * 2.0F, depth + 1);
		}
	}
}
