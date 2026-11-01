package io.transfinite_void.event;

import io.transfinite_void.TransfiniteVoid;
import io.transfinite_void.registry.ModEffects;
import io.transfinite_void.registry.ModItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Applies the wearer effects of the titanium armour and grants the "A New Beginning" advancement.
 * <p>
 * The effects are re-applied from a server tick instead of {@code Item#inventoryTick} so they work no matter how the
 * piece was equipped (inventory, dispenser, {@code /item replace}, armour stand swap, ...).
 */
public final class TitaniumArmorHandler {
	/** Long enough that the buff never flickers, short enough that it fades quickly after unequipping. */
	private static final int EFFECT_DURATION = 220;
	private static final int REFRESH_BELOW = 200;
	private static final int ADVANCEMENT_INTERVAL = 20;

	private static final ResourceLocation A_NEW_BEGINNING =
			TransfiniteVoid.id("a_new_beginning");
	private static final String FULL_SET_CRITERION = "wear_full_titanium_set";

	private static final EffectSpec[] HELMET_EFFECTS = {
			new EffectSpec(MobEffects.WATER_BREATHING, 0),
			new EffectSpec(MobEffects.HEALTH_BOOST, 1)
	};
	private static final EffectSpec[] CHESTPLATE_EFFECTS = {
			new EffectSpec(MobEffects.REGENERATION, 1),
			new EffectSpec(MobEffects.DAMAGE_RESISTANCE, 1),
			new EffectSpec(MobEffects.SATURATION, 0)
	};
	private static final EffectSpec[] LEGGINGS_EFFECTS = {
			new EffectSpec(ModEffects.ASCENSION, 0),
			new EffectSpec(MobEffects.JUMP, 1)
	};
	private static final EffectSpec[] BOOTS_EFFECTS = {
			new EffectSpec(MobEffects.MOVEMENT_SPEED, 1)
	};

	private TitaniumArmorHandler() {
	}

	public static void register() {
		ServerTickEvents.END_SERVER_TICK.register(TitaniumArmorHandler::tickServer);
	}

	private static void tickServer(MinecraftServer server) {
		for (ServerPlayer player : server.getPlayerList().getPlayers()) {
			tickPlayer(server, player);
		}
	}

	private static void tickPlayer(MinecraftServer server, ServerPlayer player) {
		applyEffects(player, EquipmentSlot.HEAD, ModItems.TITANIUM_HELMET, HELMET_EFFECTS);
		applyEffects(player, EquipmentSlot.CHEST, ModItems.TITANIUM_CHESTPLATE, CHESTPLATE_EFFECTS);
		applyEffects(player, EquipmentSlot.LEGS, ModItems.TITANIUM_LEGGINGS, LEGGINGS_EFFECTS);
		applyEffects(player, EquipmentSlot.FEET, ModItems.TITANIUM_BOOTS, BOOTS_EFFECTS);

		if (player.tickCount % ADVANCEMENT_INTERVAL == 0) {
			checkFullSet(server, player);
		}
	}

	private static void applyEffects(ServerPlayer player, EquipmentSlot slot, Item expected, EffectSpec[] effects) {
		if (!player.getItemBySlot(slot).is(expected)) {
			return;
		}

		for (EffectSpec effect : effects) {
			refresh(player, effect.effect(), effect.amplifier());
		}
	}

	private static void refresh(ServerPlayer player, Holder<MobEffect> effect, int amplifier) {
		MobEffectInstance current = player.getEffect(effect);

		if (current != null && current.getAmplifier() >= amplifier && current.getDuration() > REFRESH_BELOW) {
			return;
		}

		player.addEffect(new MobEffectInstance(effect, EFFECT_DURATION, amplifier, false, false, true));
	}

	private static void checkFullSet(MinecraftServer server, ServerPlayer player) {
		if (!hasFullSet(player)) {
			return;
		}

		AdvancementHolder holder = server.getAdvancements().get(A_NEW_BEGINNING);

		if (holder == null) {
			return;
		}

		if (player.getAdvancements().getOrStartProgress(holder).isDone()) {
			return;
		}

		player.getAdvancements().award(holder, FULL_SET_CRITERION);
	}

	/** True when every titanium piece is somewhere in the player's inventory (armour slots included). */
	private static boolean hasFullSet(ServerPlayer player) {
		Inventory inventory = player.getInventory();
		boolean helmet = false;
		boolean chestplate = false;
		boolean leggings = false;
		boolean boots = false;

		for (int slot = 0; slot < inventory.getContainerSize(); slot++) {
			ItemStack stack = inventory.getItem(slot);

			if (stack.isEmpty()) {
				continue;
			}

			Item item = stack.getItem();

			if (item == ModItems.TITANIUM_HELMET) {
				helmet = true;
			} else if (item == ModItems.TITANIUM_CHESTPLATE) {
				chestplate = true;
			} else if (item == ModItems.TITANIUM_LEGGINGS) {
				leggings = true;
			} else if (item == ModItems.TITANIUM_BOOTS) {
				boots = true;
			}

			if (helmet && chestplate && leggings && boots) {
				return true;
			}
		}

		return false;
	}

	private record EffectSpec(Holder<MobEffect> effect, int amplifier) {
	}
}
