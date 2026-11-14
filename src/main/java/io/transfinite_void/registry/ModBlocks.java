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
	/** 钛块 / Block of Titanium — mining level 4, like the ore. */
	public static Block TITANIUM_BLOCK;

	/** 蓝曜石矿 / Cyanisidian Ore — mining level 5, hardness = 3x reinforced deepslate (55). */
	public static Block CYANISIDIAN_ORE;
	/** 蓝曜块 / Block of Cyanisidian. */
	public static Block CYANISIDIAN_BLOCK;

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

		TITANIUM_BLOCK = Registry.register(
				BuiltInRegistries.BLOCK,
				TransfiniteVoid.id("titanium_block"),
				new Block(BlockBehaviour.Properties.of()
						.mapColor(MapColor.METAL)
						.instrument(NoteBlockInstrument.IRON_XYLOPHONE)
						.requiresCorrectToolForDrops()
						.strength(5.0F, 6.0F)
						.sound(SoundType.METAL)));

		// Reinforced deepslate is strength(55.0F, 1200.0F); the ore is three times as hard to break.
		// Mining level 5: it is listed in #minecraft:needs_diamond_tool, #incorrect_for_diamond_tool and
		// #incorrect_for_netherite_tool, so only the titanium (5) and cyanisidian (6) tiers can take drops.
		CYANISIDIAN_ORE = Registry.register(
				BuiltInRegistries.BLOCK,
				TransfiniteVoid.id("cyanisidian_ore"),
				new DropExperienceBlock(UniformInt.of(5, 12),
						BlockBehaviour.Properties.of()
								.mapColor(MapColor.COLOR_CYAN)
								.instrument(NoteBlockInstrument.BASEDRUM)
								.requiresCorrectToolForDrops()
								.strength(165.0F, 3600.0F)
								.sound(SoundType.STONE)));

		CYANISIDIAN_BLOCK = Registry.register(
				BuiltInRegistries.BLOCK,
				TransfiniteVoid.id("cyanisidian_block"),
				new Block(BlockBehaviour.Properties.of()
						.mapColor(MapColor.COLOR_CYAN)
						.instrument(NoteBlockInstrument.BASEDRUM)
						.requiresCorrectToolForDrops()
						.strength(50.0F, 1200.0F)
						.sound(SoundType.STONE)));
	}
}
