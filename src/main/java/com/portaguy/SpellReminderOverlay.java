package com.portaguy;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

import java.awt.*;

public abstract class SpellReminderOverlay extends OverlayPanel {
  protected final SpellReminderConfig config;
  protected final Client client;
  protected final SpellTracker tracker;
  protected long startTime;

  protected SpellReminderOverlay(SpellReminderConfig config, Client client, SpellTracker tracker) {
    this.config = config;
    this.client = client;
    this.tracker = tracker;
    this.startTime = System.currentTimeMillis();
    setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
  }

  @Override
  public Dimension render(Graphics2D graphics) {
    if (isExpired()) {
      return null;
    }

    panelComponent.getChildren().clear();

    switch (getReminderStyle()) {
      case LONG_TEXT:
        panelComponent.getChildren().add(LineComponent.builder()
            .left(getLongText())
            .build());
        panelComponent.setPreferredSize(new Dimension(
            graphics.getFontMetrics().stringWidth(getLongText()) - 20, 0));
        break;
      case SHORT_TEXT:
        panelComponent.getChildren().add(LineComponent.builder()
            .left(getShortText())
            .build());
        panelComponent.setPreferredSize(new Dimension(
            graphics.getFontMetrics().stringWidth(getShortText()) + 10, 0));
        break;
      case CUSTOM_TEXT:
        panelComponent.getChildren().add(LineComponent.builder()
            .left(getCustomText())
            .build());
        panelComponent.setPreferredSize(new Dimension(
            graphics.getFontMetrics().stringWidth(getCustomText()) - 20, 0));
        break;
    }

    if (shouldFlash()) {
      if (client.getGameCycle() % 40 >= 20) {
        panelComponent.setBackgroundColor(getFlashColor1());
      } else {
        panelComponent.setBackgroundColor(getFlashColor2());
      }
    } else {
      panelComponent.setBackgroundColor(getFlashColor1());
    }

    if (getReminderStyle() == SpellReminderStyle.CUSTOM_TEXT) {
      return super.render(graphics);
    } else {
      return panelComponent.render(graphics);
    }
  }

  protected boolean isExpired() {
    return System.currentTimeMillis() - startTime > getTimeoutSeconds() * 1000L;
  }

  protected abstract String getLongText();

  protected abstract String getShortText();

  protected abstract String getCustomText();

  protected abstract SpellReminderStyle getReminderStyle();

  protected abstract boolean shouldFlash();

  protected abstract Color getFlashColor1();

  protected abstract Color getFlashColor2();

  protected abstract int getTimeoutSeconds();
}