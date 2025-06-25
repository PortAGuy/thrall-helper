package com.portaguy;

import lombok.NonNull;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.annotation.Nullable;
import java.awt.*;

public abstract class SpellReminderOverlay extends OverlayPanel {
  protected final SpellReminderConfig config;
  protected final Client client;
  protected final SpellTracker tracker;
  @Setter
  private Long startTime;

  protected SpellReminderOverlay(SpellReminderConfig config, Client client, SpellTracker tracker) {
    this.config = config;
    this.client = client;
    this.tracker = tracker;
    this.startTime = null;
    setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
  }

  @Override
  public Dimension render(Graphics2D graphics) {
    if (isExpired()) {
      return null;
    }

    final int padding = getTextPadding();
    final String displayText = getDisplayText();
    if (displayText == null) {
      return null;
    }

    panelComponent.getChildren().clear();
    panelComponent.getChildren().add(LineComponent.builder()
        .left(displayText)
        .build());

    panelComponent.setPreferredSize(getTextWidth(graphics, displayText, padding));
    if (shouldFlash() && client.getGameCycle() % 40 >= 20) {
      panelComponent.setBackgroundColor(getFlashColor());
    } else {
      panelComponent.setBackgroundColor(getColor());
    }

    if (getReminderStyle() == SpellReminderStyle.CUSTOM_TEXT) {
      return super.render(graphics);
    } else {
      return panelComponent.render(graphics);
    }
  }

  @Nullable
  private String getDisplayText() {
    switch (getReminderStyle()) {
      case LONG_TEXT:
        return getLongText();
      case SHORT_TEXT:
        return getShortText();
      case CUSTOM_TEXT:
        return getCustomText();
      default:
        return null;
    }
  }

  private int getTextPadding() {
    switch (getReminderStyle()) {
      case LONG_TEXT:
      case CUSTOM_TEXT:
        return -20;
      case SHORT_TEXT:
        return 10;
      default:
        return 0;
    }
  }

  @NonNull
  private Dimension getTextWidth(Graphics2D graphics, String string, int offset) {
    FontMetrics fontMetrics = graphics.getFontMetrics();
    int stringWidth = fontMetrics.stringWidth(string);
    return new Dimension(stringWidth + offset, 0);
  }

  protected boolean isExpired() {
    if (startTime == null) {
      return true;
    }

    final long timeoutMillis = getTimeoutSeconds() * 1000L;
    final long elapsedMillis = System.currentTimeMillis() - startTime;

    return elapsedMillis > timeoutMillis;
  }

  protected abstract String getLongText();

  protected abstract String getShortText();

  protected abstract String getCustomText();

  protected abstract SpellReminderStyle getReminderStyle();

  protected abstract boolean shouldFlash();

  protected abstract Color getColor();

  protected abstract Color getFlashColor();

  protected abstract int getTimeoutSeconds();
}