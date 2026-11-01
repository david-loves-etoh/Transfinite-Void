package io.transfinite_void.effect;

import io.transfinite_void.TransfiniteVoid;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.function.BiConsumer;

/**
 * 飞升 (Ascension).
 * <p>
 * For level {@code n} (amplifier {@code n - 1}):
 * <ul>
 *     <li>{@code n^2} extra max health;</li>
 *     <li>{@code n%} extra attack damage;</li>
 *     <li>{@code n^2} health restored every 15 seconds.</li>
 * </ul>
 * Vanilla only scales effect attribute modifiers linearly ({@code amount * (amplifier + 1)}), so the two modifiers are
 * installed by hand in {@link #addAttributeModifiers(AttributeMap, int)} / {@link #removeAttributeModifiers(AttributeMap)}.
 * {@link #createModifiers(int, BiConsumer)} is overridden as well so potion tooltips list the right values.
 */
public class AscensionEffect extends MobEffect {
	/** 15 seconds between two heals. */
	public static final int HEAL_INTERVAL = 300;

	private static final ResourceLocation MAX_HEALTH_ID = TransfiniteVoid.id("ascension_max_health");
	private static final ResourceLocation ATTACK_DAMAGE_ID = TransfiniteVoid.id("ascension_attack_damage");

	public AscensionEffect() {
		super(MobEffectCategory.BENEFICIAL, 0xFFD54A);
	}

	/** @param amplifier {@code level - 1} */
	private static double healthBonus(int amplifier) {
		double level = amplifier + 1;
		return level * level;
	}

	private static double attackBonus(int amplifier) {
		return (amplifier + 1) / 100.0D;
	}

	@Override
	public void createModifiers(int amplifier, BiConsumer<Holder<Attribute>, AttributeModifier> output) {
		output.accept(Attributes.MAX_HEALTH, new AttributeModifier(
				MAX_HEALTH_ID, healthBonus(amplifier), AttributeModifier.Operation.ADD_VALUE));
		output.accept(Attributes.ATTACK_DAMAGE, new AttributeModifier(
				ATTACK_DAMAGE_ID, attackBonus(amplifier), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
	}

	@Override
	public void addAttributeModifiers(AttributeMap attributeMap, int amplifier) {
		apply(attributeMap, Attributes.MAX_HEALTH, new AttributeModifier(
				MAX_HEALTH_ID, healthBonus(amplifier), AttributeModifier.Operation.ADD_VALUE));
		apply(attributeMap, Attributes.ATTACK_DAMAGE, new AttributeModifier(
				ATTACK_DAMAGE_ID, attackBonus(amplifier), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
	}

	@Override
	public void removeAttributeModifiers(AttributeMap attributeMap) {
		clear(attributeMap, Attributes.MAX_HEALTH, MAX_HEALTH_ID);
		clear(attributeMap, Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_ID);
	}

	private static void apply(AttributeMap attributeMap, Holder<Attribute> attribute, AttributeModifier modifier) {
		AttributeInstance instance = attributeMap.getInstance(attribute);

		if (instance != null) {
			instance.removeModifier(modifier.id());
			instance.addPermanentModifier(modifier);
		}
	}

	private static void clear(AttributeMap attributeMap, Holder<Attribute> attribute, ResourceLocation id) {
		AttributeInstance instance = attributeMap.getInstance(attribute);

		if (instance != null) {
			instance.removeModifier(id);
		}
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return duration % HEAL_INTERVAL == 0;
	}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		entity.heal((float) healthBonus(amplifier));

		return true;
	}
}
