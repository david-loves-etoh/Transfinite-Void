package io.transfinite_void.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

/**
 * One continuous effect granted by a piece of gear, expressed as a level (I = 1) like the design documents use.
 */
public record WornEffect(Holder<MobEffect> effect, int level) {
	public int amplifier() {
		return Math.max(0, this.level - 1);
	}
}
