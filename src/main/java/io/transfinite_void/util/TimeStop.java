package io.transfinite_void.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;

/**
 * Global time-stop state of the 高维怀表 / Hyperdimensional Pocket Watch.
 * <p>
 * While active every non-player entity skips its tick (see {@code EntityMixin}) and the daylight cycle is switched
 * off, so mobs, projectiles and falling items freeze in place. Players keep ticking — freezing another player's client
 * would only produce desync — and the item's holder can act freely inside the frozen world.
 */
public final class TimeStop {
	private static ServerPlayer owner;
	private static boolean previousDaylight = true;

	private TimeStop() {
	}

	public static boolean isActive() {
		return owner != null;
	}

	/** True for entities that must skip their tick while time is frozen. */
	public static boolean shouldFreeze(Entity entity) {
		if (owner == null || entity == owner || entity instanceof Player) {
			return false;
		}

		return entity.level() instanceof ServerLevel;
	}

	/** @return {@code true} when time is now paused, {@code false} when it was resumed. */
	public static boolean toggle(ServerPlayer player) {
		if (owner != null) {
			release();
			return false;
		}

		owner = player;

		MinecraftServer server = player.getServer();

		if (server != null) {
			GameRules.BooleanValue daylight = server.getGameRules().getRule(GameRules.RULE_DAYLIGHT);
			previousDaylight = daylight.get();
			daylight.set(false, server);
		}

		return true;
	}

	/** Resumes time if the holder disconnected or died. Called from the server tick. */
	public static void validate() {
		if (owner != null && (owner.hasDisconnected() || !owner.isAlive())) {
			release();
		}
	}

	private static void release() {
		ServerPlayer player = owner;
		owner = null;

		MinecraftServer server = player == null ? null : player.getServer();

		if (server != null) {
			server.getGameRules().getRule(GameRules.RULE_DAYLIGHT).set(previousDaylight, server);
		}
	}
}
