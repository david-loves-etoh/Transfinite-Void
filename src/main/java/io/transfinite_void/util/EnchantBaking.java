package io.transfinite_void.util;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

/**
 * Turns the "built-in" enchantments of the titanium tools into real enchantment components.
 * <p>
 * Enchantments became data driven in 1.21, so the {@code Holder} for e.g. {@code minecraft:fortune} only exists once
 * a level/registry is available — that means registration time is too early. Instead the enchantment is baked into
 * the stack the moment the tool is actually used (block break), which happens before the loot table is rolled. The
 * enchantment is stored with its tooltip hidden, because the item ships its own translated tooltip lines.
 */
public final class EnchantBaking {
	private EnchantBaking() {
	}

	public static void apply(ItemStack stack, Level level, ResourceKey<Enchantment> enchantment, int targetLevel) {
		if (level.isClientSide()) {
			return;
		}

		HolderLookup.RegistryLookup<Enchantment> lookup = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
		Holder.Reference<Enchantment> holder = lookup.get(enchantment).orElse(null);

		if (holder == null) {
			return;
		}

		ItemEnchantments current = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);

		if (current.getLevel(holder) >= targetLevel) {
			return;
		}

		ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(current);
		mutable.set(holder, targetLevel);
		stack.set(DataComponents.ENCHANTMENTS, mutable.toImmutable().withTooltip(false));
	}
}
