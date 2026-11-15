package io.transfinite_void.event;

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
 * Continuous buffs of the stardust armour:
 * <ul>
 *     <li>头盔: 水下呼吸 III + 急迫 III</li>
 *     <li>胸甲: 村庄英雄 III + 飞升 V + 生命提升 III + 生命恢复 III</li>
 *     <li>护腿: 跳跃提升 IV + 缓降 I</li>
 *     <li>靴子: 速度 V</li>
 * </ul>
 */
public final class StardustGearHandler {
	private static final int EFFECT_DURATION = 220;
	private static final int REFRESH_BELOW = 200;

	private StardustGearHandler() {
	}

	public static void register() {
		ServerTickEvents.END_SERVER_TICK.register(StardustGearHandler::tickServer);
	}

	private static void tickServer(MinecraftServer server) {
		for (ServerPlayer player : server.getPlayerList().getPlayers()) {
			tickPlayer(player);
		}
	}

	private static void tickPlayer(ServerPlayer player) {
		if (player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.STARDUST_HELMET)) {
			refresh(player, MobEffects.WATER_BREATHING, 2);
			refresh(player, MobEffects.DIG_SPEED, 2);
		}

		if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.STARDUST_CHESTPLATE)) {
			refresh(player, MobEffects.HERO_OF_THE_VILLAGE, 2);
			refresh(player, ModEffects.ASCENSION, 4);
			refresh(player, MobEffects.HEALTH_BOOST, 2);
			refresh(player, MobEffects.REGENERATION, 2);
		}

		if (player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.STARDUST_LEGGINGS)) {
			refresh(player, MobEffects.JUMP, 3);
			refresh(player, MobEffects.SLOW_FALLING, 0);
		}

		if (player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.STARDUST_BOOTS)) {
			refresh(player, MobEffects.MOVEMENT_SPEED, 4);
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
