# Transfinite Void

A Fabric mod for Minecraft 1.21.1 (mojmap, Java 21).

## Titanium content (钛)

| Content | Notes |
|---|---|
| 钛矿 `titanium_ore` | Mining level 4 (needs a netherite pickaxe, it is in `#minecraft:incorrect_for_diamond_tool`). Drops titanium directly, 3-7 XP. **No world generation yet** — creative tab only, so the ore is currently unobtainable in survival. The block, loot table and tags are already in place; add a configured/placed feature plus a biome modifier when you want it to generate. |
| 钛 `titanium` | Base material (the ingot form). |
| 钛块 `titanium_block` | Mining level 4 like the ore. 9 titanium <-> 1 block (crafting table, both directions). |
| 钛板 `titanium_plate` | 2 titanium -> 1 plate. Crafting material of all titanium gear. |
| 钛棍 `titanium_stick` | 2 plates -> 2 rods. Handle of the pickaxe / axe / sword. |
| 钛镐 `titanium_pickaxe` | Mining level 5 (`#transfinite_void:incorrect_for_titanium_tool`, empty tag), 6767 durability, 18.0 mining speed (2x netherite's 9.0), built-in Fortune III. |
| 钛斧 `titanium_axe` | Mining level 5, 5555 durability, 22.5 mining speed (2.5x netherite), built-in Silk Touch, scrapes waxed copper by one oxidation step **without** removing the wax. |
| 钛剑 `titanium_sword` | 10 attack damage, 8848 durability, Mending repair x2, applies Bleeding III for 20 s on hit. |
| 钛头盔 `titanium_helmet` | +12 armour, +2 toughness, 114514 durability, Water Breathing I + Health Boost II while worn. |
| 钛胸甲 `titanium_chestplate` | +15 armour, +3 toughness, 214719 durability, Regeneration II + Resistance II + Saturation I while worn. |
| 钛护腿 `titanium_leggings` | +13 armour, +2.5 toughness, 188888 durability, Ascension I + Jump Boost II while worn. |
| 钛靴子 `titanium_boots` | +11 armour, +1.5 toughness, 99999 durability (not specified in the design), Speed II while worn. |
| 飞升 I-X 药水 | Registered as vanilla potions (`transfinite_void:ascension_1` … `_10`, 3 minutes each). Vanilla's *Food & Drinks* creative tab generates one `minecraft:potion`, `minecraft:splash_potion`, `minecraft:lingering_potion` entry and one tipped arrow per registered potion, so they appear there and only there. Item names come from the `item.minecraft.{,splash_,lingering_}potion.effect.ascension_N` / `item.minecraft.tipped_arrow.effect.ascension_N` keys. |
| 万神殿 `pantheon` | Right-click an altar block to summon its master with Ascension: iron block → iron golem (X, 10:00), wither skeleton skull → wither (V, 10:00), dragon head → ender dragon (VI, 6:06). Not consumed by default (`PantheonItem.CONSUME_ON_USE`). |
| 新的开始 / A New Beginning | Granted once every titanium piece is in the player's inventory (armour slots included). |

## Cyanisidian content (蓝曜石)

| Content | Notes |
|---|---|
| 蓝曜石矿 `cyanisidian_ore` | Mining level 5 (it is in `#needs_diamond_tool` + `#incorrect_for_diamond_tool` + `#incorrect_for_netherite_tool`, so only the empty-tag titanium/cyanisidian tiers take drops). Hardness 165 = 3x reinforced deepslate (55), blast resistance 3600. Drops cyanisidian, 5-12 XP. **No world generation**, creative tab only. |
| 蓝曜石 `cyanisidian` | Gem material (the old ruby, in blue). |
| 蓝曜核心 `cyanisidian_core` | 4 redstone blocks (corners) + 5 cyanisidian (plus). |
| 蓝曜块 `cyanisidian_block` | 4 cyanisidian -> 1 block, and 1 block -> 4 cyanisidian (crafting table, both ways). Same mining level as the ore. |
| 蓝曜板 `cyanisidian_plate` | 2 blocks -> 3 plates. |
| 蓝曜棍 `cyanisidian_rod` | 1 cyanisidian + 1 plate. |
| 蓝曜镐 `cyanisidian_pickaxe` | Mining level 6, 666666 durability, 27.0 mining speed (3x netherite), built-in Fortune V. |
| 蓝曜斧 `cyanisidian_axe` | Mining level 6, 666666 durability, netherite axe base speed + built-in Efficiency V (`mining_efficiency` +26, exactly what the enchantment grants), chain-mines connected logs (`#minecraft:logs` only, max 64 blocks, 1 durability per 4 logs), no Silk Touch. |
| 蓝曜剑 `cyanisidian_sword` | 18 attack damage, 1,000,000 durability; held: +0.1 movement speed, +0.1 jump strength, Ascension II, Strength II. |
| 蓝曜头盔 `cyanisidian_helmet` | +15 armour, +2 toughness, +1 knockback resistance, +1 max health, 1,000,000 durability, built-in Unbreaking V. |
| 蓝曜胸甲 `cyanisidian_chestplate` | +20 armour, +3 toughness, +1.5 knockback resistance, +2 max health, 2,000,000 durability, Unbreaking V, Ascension III + Health Boost III while worn. |
| 蓝曜护腿 `cyanisidian_leggings` | +17 armour, +2.5 toughness, +1.2 knockback resistance, +1 max health, 1,000,000 durability, Unbreaking V. |
| 蓝曜靴子 `cyanisidian_boots` | +10 armour, +2 toughness, +1 knockback resistance, +0.4 movement speed, +0.2 jump strength, 1,000,000 durability, Unbreaking V. |

## Stardust content (星陨)

| Content | Notes |
|---|---|
| 星陨矿 `stardust_ore` | Mining level 6 (vanilla tiers **and** the titanium tier are listed in its incorrect tags; the cyanisidian tier's empty tag allows it). Hardness/blast 165 / 3600 (not specified, same as the cyanisidian ore). Drops stardust dust, 8-20 XP. **No world generation**, creative tab only. |
| 星陨粒 `stardust_dust` | 9 -> 1 ingot. |
| 星陨锭 `stardust_ingot` | 9 -> 1 block (and 1 block -> 9 ingots, matching the cyanisidian block). |
| 星陨块 `stardust_block` | Same mining level as the ore. |
| 星陨镐 `stardust_pickaxe` | Mining level 7, 99999 durability, 54.0 mining speed (2x the cyanisidian pickaxe). |
| 星陨斧 `stardust_axe` | Mining level 7, 199999 durability, 70.0 mining speed (2x the cyanisidian axe's effective 35). |
| 星陨剑 `stardust_sword` | 30 attack damage, 299999 durability, attack speed 4.8/s (a third of a netherite sword's cooldown), +10 true damage per hit, 10% chance to chain a double-damage follow-up hit (each follow-up can chain again). |
| 星陨拳剑 `stardust_fist_sword` | Unbreakable. Shapeless: stardust axe + pickaxe + sword + 3 stardust ingots. 42 attack damage, attack speed 4.8/s. Right-click: three guaranteed 20-damage hits on every hostile mob (`Enemy`) inside a 6x6x6 cube around the player, each hit with the same recursive 10% double-damage combo. No cooldown. Sprite is a katar silhouette (ProjectE red-matter katar as shape reference) painted in the stardust blue/gold palette. |
| 星陨头盔 `stardust_helmet` | Unbreakable. +25 armour, +5 toughness, +5 max health, +3 magic/true defence. Worn: Water Breathing III, Haste III. |
| 星陨胸甲 `stardust_chestplate` | Unbreakable. +50 armour, +9 toughness, +10 max health, +6 magic/true defence. Worn: Hero of the Village III, Ascension V, Health Boost III, Regeneration III. |
| 星陨护腿 `stardust_leggings` | Unbreakable. +30 armour, +6 toughness, +8 max health, +4 magic/true defence. Worn: Jump Boost IV, Slow Falling. |
| 星陨靴子 `stardust_boots` | Unbreakable. +20 armour, +4 toughness, +15 max health, +1 magic/true defence. Worn: Speed V. |

Textures use a hand-built deep-blue/yellow material with a horizontal motion-blur smear (`stardust_pattern` in the
generator): the block is the raw material, the ore is that material in vanilla stone, tools/armour are vanilla iron
shapes painted with it (wood handles kept).

## Creative tabs

`transfinite_void:materials` (超限虚空-材料) holds every ore / ingot / block / plate / rod / core / dust;
`transfinite_void:equipment` (超限虚空-装备) holds all tools, weapons, armour and the two hand-held artefacts
(万神殿 `pantheon`, 高维怀表 `pocket_watch`). Potions are not in either tab — vanilla's *Food & Drinks* tab generates
them from the potion registry.

## Artefacts

| Item | Notes |
|---|---|
| 高维怀表 `pocket_watch` | Unbreakable. Right-click freezes time (every non-player entity on the server skips its tick and the daylight cycle is switched off), right-click again resumes it. Also resumes automatically when the holder dies or disconnects. No recipe — creative only. |

## Effects

* **流血 / Bleeding** — `2^(level-1)` damage every 3 s (level III = 4 HP per tick). Uses the custom damage type
  `transfinite_void:bleeding` (bypasses armour, own death message). Registered as potions `transfinite_void:bleeding_1`
  … `_10` (20 s each), which makes vanilla's *Food & Drinks* tab generate the potion, splash, lingering and tipped
  arrow entries automatically.
* **飞升 / Ascension** — for level `n`: `+n^2` max health, `+n%` attack damage, heals `n^2` every 15 s.

## Tooltip format (all gear)

Every tool, weapon and armour piece renders its block through `GearTooltip`, in this exact order:

```
无法破坏                                  (only unbreakable items)
在主手时： / 戴在头上时： / 穿在身上时： / 穿在腿上时： / 穿在脚上时：
 +N 护甲值  /  +N 盔甲韧性  /  +N 最大生命值  /  +N 魔法/真实伤害减免  /  +N 击退减免 …
 30 攻击伤害 / 4.8 攻击速度                (vanilla attribute "equals" lines, dark green)
 时运 III / 精准采集 / 效率 V / 耐久 V      (built-in enchantments, vanilla enchantment format)
 连锁挖掘 … / 经验修补效率 x2 …             (ability lines, gray)
 水下呼吸 III / 急迫 III                   (continuous effects, level I prints without a numeral)
```

The vanilla attribute tooltip is switched off for mod gear (`attributes(...).withTooltip(false)`) so the lines above are
the only ones shown. `WornEffectsItem` / `HeldEffectsItem` hold the effect lists on the item itself, and the single
`GearEffectHandler` tick reads them — tooltip and actual behaviour cannot drift apart.

## Implementation notes

* Built-in Fortune III / Silk Touch are stored as real `enchantments` components, baked onto the stack when the tool
  is used (the block-break hook runs before the loot table rolls). Enchantment components cannot be created before a
  registry is available, which is why this happens at use time rather than at item registration.
* Mending x2 is a small `EnchantmentHelper#modifyDurabilityToRepairFromXp` mixin.
* "Built-in" enchantments beyond what vanilla normally allows (Fortune V, Unbreaking V) are stored as real
  `enchantments` components and baked in from `Item#inventoryTick` (plus `mineBlock` for the pickaxe), so vanilla loot,
  anvil and durability code paths see them. Efficiency V on the cyanisidian axe is instead a
  `minecraft:player.mining_efficiency` attribute modifier; the cyanisidian tiers use an empty
  `#transfinite_void:incorrect_for_cyanisidian_tool` tag for mining level 6.
* Armour effects are applied from a server tick, so they also work when armour is equipped by dispensers / `/item`.
* Mod icon: `assets/transfinite_void/icon.png` (256x256) is composed from the project banner — the 915x143 banner's
  single-line wordmark plus tagline are placed on a rendered starfield (nebula clouds, milky-way band, glare stars),
  because the icon slot in Mod Menu is square.
* Damage types: `transfinite_void:bleeding` (bypasses armour) and `transfinite_void:true_damage` (bypasses armour,
  effects, enchantments, resistance and the invulnerability window). The stardust sword's "magic &amp; true defense"
  reduction is a `LivingEntity#hurt` mixin that subtracts the summed armour value from magic damage and true damage;
  a thread-local guard re-enters `hurt` once with the reduced amount.
* 高维怀表's time stop is a `Entity#tick` mixin: `TimeStop` keeps the owner plus the previous `daylightCycle` gamerule
  value and `EntityMixin` cancels the tick of every non-player entity on the server while it is active.
* Effect HUD icons live at `assets/transfinite_void/textures/mob_effect/ascension.png` and `bleeding.png` (18x18).
  1.21 resolves the sprite from the effect's registry key, and the vanilla `mob_effects` atlas is a directory source, so
  a plain PNG in the mod namespace is enough.
* Textures are titanium-palette recolours of the vanilla iron item sprites (same geometry, wood handles kept) and of
  the vanilla iron armour layers; the ore reuses vanilla iron ore's pigment mask on the vanilla stone base. The plate
  is hand drawn in the same seven-shade ramp.