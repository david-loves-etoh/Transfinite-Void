package io.transfinite_void.mixin;

import io.transfinite_void.item.StardustArmorItem;
import io.transfinite_void.util.TransfiniteDamage;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 星陨甲的「魔法，真实防御」: a flat reduction applied to magic damage ({@code #minecraft:witch_resistant_to}) and to
 * the mod's own {@code transfinite_void:true_damage}.
 * <p>
 * Vanilla has no attribute or hook for this, so the incoming amount is scaled by re-entering {@link LivingEntity#hurt}
 * with the reduced value; a thread-local guard makes sure the reduction is only applied once per hit.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Unique
	private static final ThreadLocal<Boolean> TRANSFINITE_VOID_REDUCING = ThreadLocal.withInitial(() -> Boolean.FALSE);

	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	private void transfiniteVoid$magicAndTrueDefense(DamageSource source, float amount,
			CallbackInfoReturnable<Boolean> cir) {
		if (amount <= 0.0F || TRANSFINITE_VOID_REDUCING.get()) {
			return;
		}

		boolean magicOrTrue = source.is(DamageTypeTags.WITCH_RESISTANT_TO) || source.is(TransfiniteDamage.TRUE_DAMAGE);

		if (!magicOrTrue) {
			return;
		}

		LivingEntity entity = (LivingEntity) (Object) this;
		int defense = StardustArmorItem.totalMagicDefense(entity);

		if (defense <= 0) {
			return;
		}

		float reduced = Math.max(0.0F, amount - defense);

		if (reduced >= amount) {
			return;
		}

		TRANSFINITE_VOID_REDUCING.set(Boolean.TRUE);

		try {
			cir.setReturnValue(reduced > 0.0F && entity.hurt(source, reduced));
		} finally {
			TRANSFINITE_VOID_REDUCING.set(Boolean.FALSE);
		}
	}
}
