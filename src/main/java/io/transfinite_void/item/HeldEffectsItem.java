package io.transfinite_void.item;

import java.util.List;

/** A weapon that grants continuous effects while held in the main hand. */
public interface HeldEffectsItem {
	List<WornEffect> heldEffects();
}
