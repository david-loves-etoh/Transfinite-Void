package io.transfinite_void.event;

import io.transfinite_void.TransfiniteVoid;
import io.transfinite_void.item.HeldEffectsItem;
import io.transfinite_void.item.WornEffect;
import io.transfinite_void.item.WornEffectsItem;
import io.transfinite_void.registry.ModItems;
import io.transfinite_void.util.TimeStop;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Single tick handler for every continuous gear effect of the mod.
 * <p>
 * The effect lists live on the item classes ({@link WornEffectsItem} for armour, {@link HeldEffectsItem} for weapons),
 * so the tooltip and the actual behaviour can never drift apart.
 */
public final class GearEffectHandler {
	private static final EquipmentSlot[] ARMOR_SLOTS = {EquipmentSlot.HEAD, EquipmentSlot.CHEST,
			EquipmentSlot.LEGS, EquipmentSlot.FEET};
	private static final int EFFECT_DURATION = 240;
	private static final int REFRESH_BELOW = 200;
	private static final int ADVANCEMENT_INTERVAL = 20;

	private static final ResourceLocation A_NEW_BEGINNING = TransfiniteVoid.id("a_new_beginning");
	private static final String FULL_SET_CRITERION = "wear_full_titanium_set";

	private GearEffectHandler() {
	}

	public static void register() {
		ServerTickEvents.END_SERVER_TICK.register(GearEffectHandler::tickServer);
	}

	private static void tickServer(MinecraftServer server) {
		TimeStop.validate();

		for (ServerPlayer player : server.getPlayerList().getPlayers()) {
			tickPlayer(server, player);
		}
	}

	private static void tickPlayer(MinecraftServer server, ServerPlayer player) {
		for (EquipmentSlot slot : ARMOR_SLOTS) {
			if (player.getItemBySlot(slot).getItem() instanceof WornEffectsItem worn) {
				apply(player, worn.wornEffects());
			}
		}

		if (player.getMainHandItem().getItem() instanceof HeldEffectsItem held) {
			apply(player, held.heldEffects());
		}

		if (player.tickCount % ADVANCEMENT_INTERVAL == 0 && hasFullTitaniumSet(player)) {
			awardFullSet(server, player);
		}
	}

	private static void apply(ServerPlayer player, List<WornEffect> effects) {
		for (WornEffect effect : effects) {
			refresh(player, effect);
		}
	}

	private static void refresh(ServerPlayer player, WornEffect worn) {
		MobEffectInstance current = player.getEffect(worn.effect());

		if (current != null && current.getAmplifier() >= worn.amplifier() && current.getDuration() > REFRESH_BELOW) {
			return;
		}

		player.addEffect(new MobEffectInstance(worn.effect(), EFFECT_DURATION, worn.amplifier(), false, false, true));
	}

	/** True when every titanium piece is somewhere in the player's inventory (armour slots included). */
	private static boolean hasFullTitaniumSet(ServerPlayer player) {
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

	private static void awardFullSet(MinecraftServer server, ServerPlayer player) {
		AdvancementHolder holder = server.getAdvancements().get(A_NEW_BEGINNING);

		if (holder == null || player.getAdvancements().getOrStartProgress(holder).isDone()) {
			return;
		}

		player.getAdvancements().award(holder, FULL_SET_CRITERION);
	}
}
