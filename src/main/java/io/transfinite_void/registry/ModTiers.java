package io.transfinite_void.registry;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

/**
 * Tool tiers of the titanium set.
 * <p>
 * Mining level 5 is expressed through {@link ModTags#INCORRECT_FOR_TITANIUM_TOOL}: the tag is empty, so no block can
 * deny drops to a titanium tool (vanilla's netherite tier uses the same trick for level 4).
 * <p>
 * Vanilla netherite pickaxes/axes run at speed 9.0, so "2x netherite" is 18.0 and "2.5x netherite" is 22.5. Every
 * tool needs its own tier because {@code TieredItem} takes both the mining speed and the durability from it.
 */
public final class ModTiers {
	/** 钛镐: 6767 uses, 2x netherite mining speed. */
	public static final Tier TITANIUM_PICKAXE = new TitaniumTier(6767, 18.0F);
	/** 钛斧: 5555 uses, 2.5x netherite mining speed. */
	public static final Tier TITANIUM_AXE = new TitaniumTier(5555, 22.5F);
	/** 钛剑 is not a digging tool, so it keeps the vanilla sword speed. */
	public static final Tier TITANIUM_SWORD = new TitaniumTier(8848, 1.0F);

	private ModTiers() {
	}

	private static final class TitaniumTier implements Tier {
		private final int uses;
		private final float speed;

		private TitaniumTier(int uses, float speed) {
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
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return ModTags.INCORRECT_FOR_TITANIUM_TOOL;
		}

		@Override
		public int getEnchantmentValue() {
			return 15;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(ModItems.TITANIUM_PLATE);
		}
	}
}
