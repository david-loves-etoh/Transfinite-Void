package io.transfinite_void.util;

import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 * Copper helpers for the titanium axe.
 * <p>
 * A vanilla axe first strips logs, then de-oxidises copper, then removes wax. The titanium axe additionally
 * scrapes <em>waxed</em> copper by one oxidation step while keeping the wax block in place.
 */
public final class CopperWaxing {
	private CopperWaxing() {
	}

	public static boolean isWaxed(BlockState state) {
		return HoneycombItem.WAX_OFF_BY_BLOCK.get().containsKey(state.getBlock());
	}

	/**
	 * @return the waxed, one-oxidation-step-less state, or {@code null} when the block is not waxed copper or is
	 *         already at the least oxidised stage (waxed copper block / waxed cut copper).
	 */
	public static BlockState scrapeKeepingWax(BlockState state) {
		Block unwaxedBlock = HoneycombItem.WAX_OFF_BY_BLOCK.get().get(state.getBlock());

		if (unwaxedBlock == null) {
			return null;
		}

		BlockState unwaxedState = unwaxedBlock.withPropertiesOf(state);
		Optional<BlockState> previous = WeatheringCopper.getPrevious(unwaxedState);

		if (previous.isEmpty()) {
			return null;
		}

		Block waxedPreviousBlock = HoneycombItem.WAXABLES.get().get(previous.get().getBlock());

		if (waxedPreviousBlock == null) {
			return null;
		}

		return waxedPreviousBlock.withPropertiesOf(previous.get());
	}
}
