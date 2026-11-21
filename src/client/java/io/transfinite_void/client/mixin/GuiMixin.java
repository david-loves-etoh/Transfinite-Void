package io.transfinite_void.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Replaces the vanilla hearts / armour icons with a single bar once the values no longer fit the vanilla rows:
 * <ul>
 *   <li>max health &gt; 40 (more than two rows of hearts) — red bar, {@code current/max} written on it;</li>
 *   <li>armour &gt; 20 (more than one row of icons) — grey bar with the armour value on it.</li>
 * </ul>
 * Both bars sit exactly where the vanilla rows are drawn (x = width/2 - 91, hearts at height - 39, armour one row
 * above at height - 49), use the vanilla heart / armour sprite as their left icon, and fill from left to right.
 */
@Mixin(Gui.class)
public abstract class GuiMixin {
	private static final ResourceLocation HEART_SPRITE = ResourceLocation.withDefaultNamespace("hud/heart/full");
	private static final ResourceLocation ARMOR_SPRITE = ResourceLocation.withDefaultNamespace("hud/armor_full");

	private static final int ICON_SIZE = 9;
	/** Icon (9) plus a 2 px gap before the bar. */
	private static final int BAR_OFFSET = 11;
	private static final int BAR_WIDTH = 70;
	private static final int BAR_HEIGHT = 9;
	private static final int HEALTH_ROW_OFFSET = 39;
	private static final int ARMOR_ROW_OFFSET = 49;
	private static final float HEALTH_THRESHOLD = 40.0F;
	private static final int ARMOR_THRESHOLD = 20;

	private static final int COLOUR_EMPTY = 0xFF000000;
	private static final int COLOUR_HEALTH = 0xFFD32F2F;
	private static final int COLOUR_ARMOR = 0xFFB0B0B0;
	private static final int COLOUR_TEXT = 0xFFFFFFFF;

	@Inject(method = "renderHearts", at = @At("HEAD"), cancellable = true)
	private void transfiniteVoid$renderHealthBar(GuiGraphics graphics, Player player, int a, int b, int c, int d,
			float e, int f, int g, int h, boolean bl, CallbackInfo ci) {
		float maxHealth = player.getMaxHealth();

		if (maxHealth <= HEALTH_THRESHOLD) {
			return;
		}

		ci.cancel();

		String text = Math.round(player.getHealth()) + "/" + Math.round(maxHealth);
		drawBar(graphics, HEART_SPRITE, graphics.guiWidth() / 2 - 91, graphics.guiHeight() - HEALTH_ROW_OFFSET,
				player.getHealth() / maxHealth, COLOUR_HEALTH, text);
	}

	@Inject(method = "renderArmor", at = @At("HEAD"), cancellable = true)
	private static void transfiniteVoid$renderArmorBar(GuiGraphics graphics, Player player, int a, int b, int c,
			int d, CallbackInfo ci) {
		int armor = player.getArmorValue();

		if (armor <= ARMOR_THRESHOLD) {
			return;
		}

		ci.cancel();

		// The armour bar is always drawn completely filled; the value itself is written on the bar.
		drawBar(graphics, ARMOR_SPRITE, graphics.guiWidth() / 2 - 91, graphics.guiHeight() - ARMOR_ROW_OFFSET,
				1.0F, COLOUR_ARMOR, String.valueOf(armor));
	}

	private static void drawBar(GuiGraphics graphics, ResourceLocation icon, int x, int y, float ratio, int colour,
			String text) {
		int barX = x + BAR_OFFSET;

		graphics.blitSprite(icon, x, y, ICON_SIZE, ICON_SIZE);
		graphics.fill(barX, y, barX + BAR_WIDTH, y + BAR_HEIGHT, COLOUR_EMPTY);

		int filled = Math.round(BAR_WIDTH * Mth.clamp(ratio, 0.0F, 1.0F));

		if (filled > 0) {
			graphics.fill(barX, y, barX + filled, y + BAR_HEIGHT, colour);
		}

		Font font = Minecraft.getInstance().font;
		graphics.drawCenteredString(font, text, barX + BAR_WIDTH / 2, y + 1, COLOUR_TEXT);
	}
}
