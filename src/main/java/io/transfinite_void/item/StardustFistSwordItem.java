package io.transfinite_void.item;

import io.transfinite_void.util.ComboDamage;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

/**
 * 星陨拳剑 — 42 attack damage; right-click performs an area attack that hits every hostile mob in a 6x6x6 box
 * centred on the player with three guaranteed 20-damage hits, and each hit has the usual 10% chance to chain a
 * double-damage combo.
 */
public class StardustFistSwordItem extends SwordItem {
	public static final float SEGMENT_DAMAGE = 20.0F;
	public static final int SEGMENTS = 3;
	/** Half extent of the 6x6x6 cube around the player. */
	public static final double AOE_HALF_SIZE = 3.0D;

	public StardustFistSwordItem(Tier tier, Item.Properties properties) {
		super(tier, properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (!(level instanceof ServerLevel serverLevel)) {
			return InteractionResultHolder.sidedSuccess(stack, true);
		}

		AABB area = new AABB(
				player.getX() - AOE_HALF_SIZE, player.getY() - AOE_HALF_SIZE, player.getZ() - AOE_HALF_SIZE,
				player.getX() + AOE_HALF_SIZE, player.getY() + AOE_HALF_SIZE, player.getZ() + AOE_HALF_SIZE);

		List<LivingEntity> targets = serverLevel.getEntitiesOfClass(LivingEntity.class, area,
				entity -> entity instanceof Enemy && entity.isAlive());

		for (LivingEntity target : targets) {
			for (int segment = 0; segment < SEGMENTS; segment++) {
				if (!target.isAlive()) {
					break;
				}

				target.invulnerableTime = 0;

				if (target.hurt(serverLevel.damageSources().playerAttack(player), SEGMENT_DAMAGE)) {
					ComboDamage.tryCombo(serverLevel, target, player, SEGMENT_DAMAGE * 2.0F, 1);
				}
			}
		}

		serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
				SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1.0F, 1.0F);
		player.swing(hand);

		return InteractionResultHolder.sidedSuccess(stack, false);
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("tooltip.transfinite_void.unbreakable").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.aoe_hits",
				SEGMENTS, (int) SEGMENT_DAMAGE, (int) (AOE_HALF_SIZE * 2)).withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.combo_chance",
				Math.round(ComboDamage.CHANCE * 100.0F)).withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, context, tooltip, flag);
	}
}
