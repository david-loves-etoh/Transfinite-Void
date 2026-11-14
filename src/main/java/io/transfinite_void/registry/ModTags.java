package io.transfinite_void.registry;

import io.transfinite_void.TransfiniteVoid;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/**
 * Tags owned by this mod.
 * <p>
 * {@code #transfinite_void:incorrect_for_titanium_tool} is deliberately empty: a {@link net.minecraft.world.item.Tier}
 * only refuses drops for blocks listed in its {@code incorrect for ... tool} tag, so an empty tag means the titanium
 * tier (mining level 5) can get drops from every block in the game.
 */
public final class ModTags {
	public static final TagKey<Block> INCORRECT_FOR_TITANIUM_TOOL =
			TagKey.create(Registries.BLOCK, TransfiniteVoid.id("incorrect_for_titanium_tool"));
	public static final TagKey<Block> INCORRECT_FOR_CYANISIDIAN_TOOL =
			TagKey.create(Registries.BLOCK, TransfiniteVoid.id("incorrect_for_cyanisidian_tool"));

	private ModTags() {
	}
}
