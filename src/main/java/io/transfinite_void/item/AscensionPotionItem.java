package io.transfinite_void.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;

/**
 * A creative-only 飞升 (Ascension) potion. Each level is its own item so the name and the creative tab entry are
 * unambiguous; the effect itself lives in the {@code potion_contents} component.
 */
public class AscensionPotionItem extends PotionItem {
	private final int level;

	public AscensionPotionItem(int level, Item.Properties properties) {
		super(properties);
		this.level = level;
	}

	public int getAscensionLevel() {
		return this.level;
	}

	@Override
	public Component getName(ItemStack stack) {
		return Component.translatable("item.transfinite_void.ascension_potion_" + this.level);
	}
}
