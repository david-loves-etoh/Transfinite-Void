package io.transfinite_void.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * Chain mining of the cyanisidian axe.
 * <p>
 * Only blocks in {@code #minecraft:logs} are chained — planks, fences and every other wood product are left alone.
 * The whole chain costs {@code floor(broken / 4)} durability instead of one point per block (the block that was
 * mined by hand still costs its normal point).
 */
public final class ChainMining {
	/** Safety cap so a giant custom tree cannot lock up the server. */
	public static final int BLOCK_LIMIT = 64;
	public static final int DURABILITY_DIVISOR = 4;

	private ChainMining() {
	}

	public static void mine(ServerLevel level, BlockPos origin, ServerPlayer player, ItemStack tool) {
		Set<BlockPos> visited = new HashSet<>();
		Deque<BlockPos> frontier = new ArrayDeque<>();
		visited.add(origin);
		frontier.add(origin);

		int broken = 0;

		while (!frontier.isEmpty() && broken < BLOCK_LIMIT) {
			BlockPos current = frontier.poll();

			for (Direction direction : Direction.values()) {
				BlockPos next = current.relative(direction);

				if (!visited.add(next)) {
					continue;
				}

				BlockState state = level.getBlockState(next);

				if (!state.is(BlockTags.LOGS)) {
					continue;
				}

				level.destroyBlock(next, true, player, 512);
				frontier.add(next);
				broken++;

				if (broken >= BLOCK_LIMIT) {
					break;
				}
			}
		}

		int cost = broken / DURABILITY_DIVISOR;

		if (cost > 0) {
			tool.hurtAndBreak(cost, player, EquipmentSlot.MAINHAND);
		}
	}
}
