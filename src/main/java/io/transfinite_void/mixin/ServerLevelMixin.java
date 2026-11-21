package io.transfinite_void.mixin;

import io.transfinite_void.util.TimeStop;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 高维怀表: while time is frozen, entities on the server are not ticked at all.
 * <p>
 * The freeze has to happen at the level's tick entry points rather than in {@code Entity#tick}: every entity class
 * overrides {@code tick()} (and {@code LivingEntity#tick} runs its AI <em>around</em> {@code super.tick()}), so only
 * skipping {@code ServerLevel#tickNonPassenger} / {@code tickPassenger} reliably stops movement, AI, projectiles and
 * dropped items.
 */
@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
	@Inject(method = "tickNonPassenger", at = @At("HEAD"), cancellable = true)
	private void transfiniteVoid$freezeDuringTimeStop(Entity entity, CallbackInfo ci) {
		if (TimeStop.shouldFreeze(entity)) {
			ci.cancel();
		}
	}

	@Inject(method = "tickPassenger", at = @At("HEAD"), cancellable = true)
	private void transfiniteVoid$freezePassengers(Entity vehicle, Entity passenger, CallbackInfo ci) {
		if (TimeStop.shouldFreeze(vehicle) || TimeStop.shouldFreeze(passenger)) {
			ci.cancel();
		}
	}
}
