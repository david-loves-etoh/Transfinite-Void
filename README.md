# Transfinite Void

A Fabric mod for Minecraft 1.21.1 (mojmap, Java 21).

## Titanium content (钛)

| Content | Notes |
|---|---|
| 钛矿 `titanium_ore` | Mining level 4 (needs a netherite pickaxe, it is in `#minecraft:incorrect_for_diamond_tool`). Drops titanium directly, 3-7 XP. **No world generation yet** — creative tab only, so the ore is currently unobtainable in survival. The block, loot table and tags are already in place; add a configured/placed feature plus a biome modifier when you want it to generate. |
| 钛 `titanium` | Base material. |
| 钛板 `titanium_plate` | 2 titanium -> 1 plate. Crafting material of all titanium gear. |
| 钛棍 `titanium_stick` | 2 plates -> 2 rods. Handle of the pickaxe / axe / sword. |
| 钛镐 `titanium_pickaxe` | Mining level 5 (`#transfinite_void:incorrect_for_titanium_tool`, empty tag), 6767 durability, 18.0 mining speed (2x netherite's 9.0), built-in Fortune III. |
| 钛斧 `titanium_axe` | Mining level 5, 5555 durability, 22.5 mining speed (2.5x netherite), built-in Silk Touch, scrapes waxed copper by one oxidation step **without** removing the wax. |
| 钛剑 `titanium_sword` | 10 attack damage, 8848 durability, Mending repair x2, applies Bleeding III for 20 s on hit. |
| 钛头盔 `titanium_helmet` | +12 armour, +2 toughness, 114514 durability, Water Breathing I + Health Boost II while worn. |
| 钛胸甲 `titanium_chestplate` | +15 armour, +3 toughness, 214719 durability, Regeneration II + Resistance II + Saturation I while worn. |
| 钛护腿 `titanium_leggings` | +13 armour, +2.5 toughness, 188888 durability, Ascension I + Jump Boost II while worn. |
| 钛靴子 `titanium_boots` | +11 armour, +1.5 toughness, 99999 durability (not specified in the design), Speed II while worn. |
| 飞升 I-X 药水 | Creative-only potions, 3 minutes each. |
| 新的开始 / A New Beginning | Granted when a player wears the full titanium set. |

## Effects

* **流血 / Bleeding** — `2^(level-1)` damage every 3 s (level III = 4 HP per tick). Uses the custom damage type
  `transfinite_void:bleeding` (bypasses armour, own death message).
* **飞升 / Ascension** — for level `n`: `+n^2` max health, `+n%` attack damage, heals `n^2` every 15 s.

## Implementation notes

* Built-in Fortune III / Silk Touch are stored as real `enchantments` components, baked onto the stack when the tool
  is used (the block-break hook runs before the loot table rolls). Enchantment components cannot be created before a
  registry is available, which is why this happens at use time rather than at item registration.
* Mending x2 is a small `EnchantmentHelper#modifyDurabilityToRepairFromXp` mixin.
* Armour effects are applied from a server tick, so they also work when armour is equipped by dispensers / `/item`.
* Textures are generated pixel-art placeholders (the armour layers reuse vanilla's alpha mask, repainted in the
  titanium palette) — replace them freely.
