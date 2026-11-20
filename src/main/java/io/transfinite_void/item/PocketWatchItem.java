package io.transfinite_void.item;

import io.transfinite_void.util.GearTooltip;
import io.transfinite_void.util.TimeStop;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 高维怀表 / Hyperdimensional Pocket Watch — right-click freezes time (all non-player entities stop ticking and the
 * daylight cycle is paused), right-click again resumes it.
 */
public class PocketWatchItem extends Item {
	public static final int COOLDOWN_TICKS = 10;

	public PocketWatchItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (!(player instanceof ServerPlayer serverPlayer)) {
			return InteractionResultHolder.sidedSuccess(stack, true);
		}

		boolean paused = TimeStop.toggle(serverPlayer);

		serverPlayer.displayClientMessage(Component.translatable(
				paused ? "message.transfinite_void.time_paused" : "message.transfinite_void.time_resumed"), true);
		level.playSound(null, player.getX(), player.getY(), player.getZ(),
				paused ? SoundEvents.BEACON_ACTIVATE : SoundEvents.BEACON_DEACTIVATE,
				SoundSource.PLAYERS, 0.6F, 1.6F);
		player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);
		player.swing(hand);

		return InteractionResultHolder.sidedSuccess(stack, false);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		GearTooltip.unbreakable(tooltip);
		GearTooltip.header(tooltip, "mainhand");
		GearTooltip.ability(tooltip, "tooltip.transfinite_void.pocket_watch");

		if (TimeStop.isActive()) {
			GearTooltip.ability(tooltip, "tooltip.transfinite_void.pocket_watch_active");
		}

		super.appendHoverText(stack, context, tooltip, flag);
	}
}
