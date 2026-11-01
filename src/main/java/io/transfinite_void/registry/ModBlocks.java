package io.transfinite_void.registry;

import io.transfinite_void.TransfiniteVoid;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public final class ModBlocks {
	/**
	 * 钛矿 / Titanium ore. Requires a netherite pickaxe (mining level 4) to drop anything — see
	 * {@code data/minecraft/tags/block/incorrect_for_diamond_tool.json}.
	 * <p>
	 * No world generation and no loot source is wired up on purpose: the block is currently only obtainable from the
	 * creative tab. The loot table already exists, so adding a configured feature later is all that is needed.
	 */
	public static Block TITANIUM_ORE;

	private ModBlocks() {
	}

	public static void register() {
		TITANIUM_ORE = Registry.register(
				BuiltInRegistries.BLOCK,
				TransfiniteVoid.id("titanium_ore"),
				new DropExperienceBlock(
						UniformInt.of(3, 7),
						BlockBehaviour.Properties.of()
								.mapColor(MapColor.STONE)
								.instrument(NoteBlockInstrument.BASEDRUM)
								.requiresCorrectToolForDrops()
								.strength(4.5F, 4.5F)
								.sound(SoundType.STONE)));
	}
}
