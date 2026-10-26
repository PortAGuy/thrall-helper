package com.portaguy;

import net.runelite.api.Client;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.TextComponent;

import javax.inject.Inject;
import java.awt.*;

/**
 * Draws the remaining duration (top) and time until the next cast (bottom) on the
 * thrall and Death Charge icons in the Arceuus spellbook.
 */
public class SpellbookTimerOverlay extends Overlay {
  private static final int[] THRALL_SPELLS = {
      InterfaceID.MagicSpellbook.RESURRECT_LESSER_GHOST,
      InterfaceID.MagicSpellbook.RESURRECT_LESSER_SKELETON,
      InterfaceID.MagicSpellbook.RESURRECT_LESSER_ZOMBIE,
      InterfaceID.MagicSpellbook.RESURRECT_SUPERIOR_GHOST,
      InterfaceID.MagicSpellbook.RESURRECT_SUPERIOR_SKELETON,
      InterfaceID.MagicSpellbook.RESURRECT_SUPERIOR_ZOMBIE,
      InterfaceID.MagicSpellbook.RESURRECT_GREATER_GHOST,
      InterfaceID.MagicSpellbook.RESURRECT_GREATER_SKELETON,
      InterfaceID.MagicSpellbook.RESURRECT_GREATER_ZOMBIE,
  };

  private static final Color DURATION_COLOR = Color.GREEN;
  private static final Color COOLDOWN_COLOR = Color.ORANGE;

  private final Client client;
  private final SpellReminderConfig config;
  private final SpellbookTimers timers;
  private final TextComponent text = new TextComponent();

  @Inject
  SpellbookTimerOverlay(Client client, SpellReminderConfig config, SpellbookTimers timers) {
    this.client = client;
    this.config = config;
    this.timers = timers;
    setPosition(OverlayPosition.DYNAMIC);
    setLayer(OverlayLayer.ABOVE_WIDGETS);
  }

  @Override
  public Dimension render(Graphics2D graphics) {
    final boolean thrall = config.thrallSpellbookTimers();
    final boolean deathCharge = config.deathChargeSpellbookTimers();
    if (!thrall && !deathCharge) {
      return null;
    }

    graphics.setFont(FontManager.getRunescapeSmallFont());

    if (thrall) {
      final int up = timers.thrallTicksLeft();
      final int cooldown = timers.thrallCooldownTicksLeft();
      if (up > 0 || cooldown > 0) {
        for (int spell : THRALL_SPELLS) {
          renderTimers(graphics, client.getWidget(spell), up, cooldown);
        }
      }
    }

    if (deathCharge) {
      renderTimers(graphics, client.getWidget(InterfaceID.MagicSpellbook.DEATH_CHARGE),
          timers.deathChargeTicksLeft(), timers.deathChargeCooldownTicksLeft());
    }

    return null;
  }

  private void renderTimers(Graphics2D graphics, Widget icon, int durationTicks, int cooldownTicks) {
    if (icon == null || icon.isHidden()) {
      return;
    }
    final Rectangle bounds = icon.getBounds();
    if (bounds == null || bounds.isEmpty()) {
      return;
    }

    final FontMetrics metrics = graphics.getFontMetrics();
    if (durationTicks > 0) {
      drawCentered(graphics, metrics, SpellbookTimers.format(durationTicks), bounds,
          bounds.y + metrics.getAscent(), DURATION_COLOR);
    }
    if (cooldownTicks > 0) {
      drawCentered(graphics, metrics, SpellbookTimers.format(cooldownTicks), bounds,
          bounds.y + bounds.height - 1, COOLDOWN_COLOR);
    }
  }

  private void drawCentered(Graphics2D graphics, FontMetrics metrics, String value, Rectangle bounds,
                            int baselineY, Color color) {
    final int x = bounds.x + (bounds.width - metrics.stringWidth(value)) / 2;
    text.setText(value);
    text.setColor(color);
    text.setPosition(new Point(x, baselineY));
    text.render(graphics);
  }
}
