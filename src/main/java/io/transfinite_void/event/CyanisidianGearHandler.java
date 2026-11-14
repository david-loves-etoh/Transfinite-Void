package io.transfinite_void.event;

import io.transfinite_void.item.CyanisidianSwordItem;
import io.transfinite_void.registry.ModEffects;
import io.transfinite_void.registry.ModItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.Holder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;

/**
 * Continuous buffs of the cyanisidian gear:
 * <ul>
 *     <li>蓝曜胸甲 worn → 飞升 III + 生命提升 III</li>
 *     <li>蓝曜剑 in the main hand → 飞升 II + 力量 II</li>
 * </ul>
 * The remaining values (armour, toughness, knockback resistance, max health, speed, jump) are plain item attribute
 * modifiers, so they need no tick handling.
 */
public final class CyanisidianGearHandler {
	private static final int EFFECT_DURATION = 220;
	private static final int REFRESH_BELOW = 200;

	/** 飞升 III (amplifier 2) and 生命提升 III (amplifier 2) from the chestplate. */
	private static final int CHESTPLATE_ASCENSION_AMPLIFIER = 2;
	private static final int CHESTPLATE_HEALTH_BOOST_AMPLIFIER = 2;
	/** 飞升 II (amplifier 1) and 力量 II (amplifier 1) from the held sword. */
	private static final int SWORD_ASCENSION_AMPLIFIER = CyanisidianSwordItem.ASCENSION_LEVEL - 1;
	private static final int SWORD_STRENGTH_AMPLIFIER = CyanisidianSwordItem.STRENGTH_LEVEL - 1;

	private CyanisidianGearHandler() {
	}

	public static void register() {
		ServerTickEvents.END_SERVER_TICK.register(CyanisidianGearHandler::tickServer);
	}

	private static void tickServer(MinecraftServer server) {
		for (ServerPlayer player : server.getPlayerList().getPlayers()) {
			tickPlayer(player);
		}
	}

	private static void tickPlayer(ServerPlayer player) {
		if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.CYANISIDIAN_CHESTPLATE)) {
			refresh(player, ModEffects.ASCENSION, CHESTPLATE_ASCENSION_AMPLIFIER);
			refresh(player, MobEffects.HEALTH_BOOST, CHESTPLATE_HEALTH_BOOST_AMPLIFIER);
		}

		if (player.getMainHandItem().is(ModItems.CYANISIDIAN_SWORD)) {
			refresh(player, ModEffects.ASCENSION, SWORD_ASCENSION_AMPLIFIER);
			refresh(player, MobEffects.DAMAGE_BOOST, SWORD_STRENGTH_AMPLIFIER);
		}
	}

	private static void refresh(ServerPlayer player, Holder<MobEffect> effect, int amplifier) {
		MobEffectInstance current = player.getEffect(effect);

		if (current != null && current.getAmplifier() >= amplifier && current.getDuration() > REFRESH_BELOW) {
			return;
		}

		player.addEffect(new MobEffectInstance(effect, EFFECT_DURATION, amplifier, false, false, true));
	}
}
