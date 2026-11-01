package io.transfinite_void.effect;

import io.transfinite_void.util.TransfiniteDamage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * 流血 (Bleeding).
 * <p>
 * Damage is dealt once every 3 seconds (60 ticks): {@code 2^(level - 1)} hearts of damage, i.e. with the amplifier
 * {@code n - 1} it is {@code 2^amplifier} health points — level III hits for 4 HP per tick of the effect.
 */
public class BleedingEffect extends MobEffect {
	/** 3 seconds between two damage ticks. */
	public static final int DAMAGE_INTERVAL = 60;

	public BleedingEffect() {
		super(MobEffectCategory.HARMFUL, 0x8A0303);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return duration % DAMAGE_INTERVAL == 0;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity.level() instanceof ServerLevel serverLevel && entity.isAlive()) {
			float damage = (float) Math.pow(2.0D, amplifier);
			entity.hurt(TransfiniteDamage.bleeding(serverLevel), damage);
		}

		return true;
	}
}
