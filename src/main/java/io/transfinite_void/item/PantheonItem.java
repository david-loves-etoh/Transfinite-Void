package io.transfinite_void.item;

import io.transfinite_void.registry.ModEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * 万神殿 / Pantheon — right-click one of the "altar" blocks to summon its master, buffed with Ascension.
 * <ul>
 *     <li>iron block → iron golem, 飞升 X for 10:00</li>
 *     <li>wither skeleton skull → wither, 飞升 V for 10:00</li>
 *     <li>dragon head → ender dragon, 飞升 VI for 6:06</li>
 * </ul>
 */
public class PantheonItem extends Item {
	public static final int GOLEM_DURATION = 12000;   // 10:00
	public static final int WITHER_DURATION = 12000;  // 10:00
	public static final int DRAGON_DURATION = 7320;   // 6:06

	/** Flip to {@code true} to make the Pantheon a single-use summoning item. */
	public static final boolean CONSUME_ON_USE = false;

	public PantheonItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);
		Summoning summoning = Summoning.of(state);

		if (summoning == null) {
			return InteractionResult.PASS;
		}

		if (!(level instanceof ServerLevel serverLevel)) {
			return InteractionResult.SUCCESS;
		}

		Mob mob = summoning.type().spawn(serverLevel, summoning.spawnPos(pos, state), MobSpawnType.COMMAND);

		if (mob == null) {
			return InteractionResult.FAIL;
		}

		mob.setPersistenceRequired();
		mob.addEffect(new MobEffectInstance(
				ModEffects.ASCENSION, summoning.duration(), summoning.amplifier(), false, false, true));

		if (CONSUME_ON_USE && context.getPlayer() != null) {
			context.getItemInHand().shrink(1);
		}

		return InteractionResult.sidedSuccess(level.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(Component.translatable("tooltip.transfinite_void.pantheon").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.pantheon_iron_block")
				.withStyle(ChatFormatting.DARK_GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.pantheon_wither_skull")
				.withStyle(ChatFormatting.DARK_GRAY));
		tooltip.add(Component.translatable("tooltip.transfinite_void.pantheon_dragon_head")
				.withStyle(ChatFormatting.DARK_GRAY));
		super.appendHoverText(stack, context, tooltip, flag);
	}

	private record Summoning(EntityType<? extends Mob> type, int amplifier, int duration, int yOffset) {
		private static Summoning of(BlockState state) {
			if (state.is(Blocks.IRON_BLOCK)) {
				return new Summoning(EntityType.IRON_GOLEM, 9, GOLEM_DURATION, 1);
			}

			if (state.is(Blocks.WITHER_SKELETON_SKULL) || state.is(Blocks.WITHER_SKELETON_WALL_SKULL)) {
				return new Summoning(EntityType.WITHER, 4, WITHER_DURATION, 1);
			}

			if (state.is(Blocks.DRAGON_HEAD) || state.is(Blocks.DRAGON_WALL_HEAD)) {
				return new Summoning(EntityType.ENDER_DRAGON, 5, DRAGON_DURATION, 3);
			}

			return null;
		}

		/**
		 * Floor-mounted altars summon on top of themselves; wall-mounted skulls/heads (which carry a facing
		 * property) summon in front of the wall so the mob is not stuck inside blocks.
		 */
		private BlockPos spawnPos(BlockPos pos, BlockState state) {
			if (state.hasProperty(HorizontalDirectionalBlock.FACING)) {
				Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);

				if (facing.getAxis().isHorizontal()) {
					return pos.relative(facing);
				}
			}

			return pos.above(this.yOffset);
		}
	}
}
