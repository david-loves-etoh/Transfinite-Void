package io.transfinite_void.mixin;

import io.transfinite_void.util.TimeStop;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 高维怀表: while time is frozen every non-player entity skips its tick, so mobs, projectiles and dropped items stand
 * completely still.
 */
@Mixin(Entity.class)
public abstract class EntityMixin {
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void transfiniteVoid$freezeDuringTimeStop(CallbackInfo ci) {
		if (TimeStop.shouldFreeze((Entity) (Object) this)) {
			ci.cancel();
		}
	}
}
