package io.transfinite_void.item;

import java.util.List;

/** Armour that grants continuous effects while worn. The same list drives the tooltip and the server tick. */
public interface WornEffectsItem {
	List<WornEffect> wornEffects();
}
