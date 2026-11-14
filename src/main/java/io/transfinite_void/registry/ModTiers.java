package io.transfinite_void.registry;

import io.transfinite_void.TransfiniteVoid;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

/**
 * Tool tiers of the titanium and cyanisidian sets.
 * <p>
 * The number of the "mining level" is expressed through the {@code incorrect for ... tool} tag: a tier only refuses
 * drops for blocks listed in its tag, so an <em>empty</em> tag means "can take drops from everything".
 * <ul>
 *     <li>titanium = mining level 5 → {@link ModTags#INCORRECT_FOR_TITANIUM_TOOL} is empty</li>
 *     <li>cyanisidian = mining level 6 → {@link ModTags#INCORRECT_FOR_CYANISIDIAN_TOOL} is empty as well, the blocks
 *     that need level 5 live in {@code #minecraft:incorrect_for_netherite_tool}</li>
 * </ul>
 * Vanilla netherite pickaxes/axes run at speed 9.0, so "2x / 2.5x / 3x netherite" are 18.0 / 22.5 / 27.0.
 */
public final class ModTiers {
	/** 钛镐: 6767 uses, 2x netherite mining speed. */
	public static final Tier TITANIUM_PICKAXE = new TitaniumTier(6767, 18.0F);
	/** 钛斧: 5555 uses, 2.5x netherite mining speed. */
	public static final Tier TITANIUM_AXE = new TitaniumTier(5555, 22.5F);
	/** 钛剑 is not a digging tool, so it keeps the vanilla sword speed. */
	public static final Tier TITANIUM_SWORD = new TitaniumTier(8848, 1.0F);

	/** 蓝曜镐: 666666 uses, 3x netherite mining speed. */
	public static final Tier CYANISIDIAN_PICKAXE = new CyanisidianTier(666666, 27.0F);
	/** 蓝曜斧: 666666 uses, base speed equal to the netherite axe (the built-in Efficiency 5 is an attribute). */
	public static final Tier CYANISIDIAN_AXE = new CyanisidianTier(666666, 9.0F);
	/** 蓝曜剑: 1,000,000 uses. */
	public static final Tier CYANISIDIAN_SWORD = new CyanisidianTier(1000000, 1.0F);

	private ModTiers() {
	}

	private abstract static class ModTier implements Tier {
		private final int uses;
		private final float speed;

		private ModTier(int uses, float speed) {
			this.uses = uses;
			this.speed = speed;
		}

		@Override
		public int getUses() {
			return this.uses;
		}

		@Override
		public float getSpeed() {
			return this.speed;
		}

		@Override
		public float getAttackDamageBonus() {
			// Attack damage is given explicitly when the item attributes are built.
			return 0.0F;
		}

		@Override
		public int getEnchantmentValue() {
			return 15;
		}
	}

	private static final class TitaniumTier extends ModTier {
		private TitaniumTier(int uses, float speed) {
			super(uses, speed);
		}

		@Override
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return ModTags.INCORRECT_FOR_TITANIUM_TOOL;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(ModItems.TITANIUM_PLATE);
		}
	}

	private static final class CyanisidianTier extends ModTier {
		private CyanisidianTier(int uses, float speed) {
			super(uses, speed);
		}

		@Override
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return ModTags.INCORRECT_FOR_CYANISIDIAN_TOOL;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(ModItems.CYANISIDIAN_PLATE);
		}
	}
}
