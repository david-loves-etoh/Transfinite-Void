package io.transfinite_void.mixin;

import io.transfinite_void.registry.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 钛剑: 经验修补效率 x2 — doubles the durability restored per experience point, but only while the sword actually has
 * the Mending enchantment.
 */
@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
	@Inject(method = "modifyDurabilityToRepairFromXp(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;I)I",
			at = @At("RETURN"), cancellable = true)
	private static void transfiniteVoid$doubleMendingRepair(ServerLevel level, ItemStack stack, int amount,
			CallbackInfoReturnable<Integer> cir) {
		if (!stack.is(ModItems.TITANIUM_SWORD)) {
			return;
		}

		HolderLookup.RegistryLookup<Enchantment> lookup = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
		Holder.Reference<Enchantment> mending = lookup.get(Enchantments.MENDING).orElse(null);

		if (mending == null || stack.getEnchantments().getLevel(mending) <= 0) {
			return;
		}

		cir.setReturnValue(cir.getReturnValue() * 2);
	}
}
