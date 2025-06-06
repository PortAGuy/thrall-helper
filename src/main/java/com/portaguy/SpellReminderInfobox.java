package com.portaguy;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.ui.overlay.infobox.InfoBox;

import java.awt.*;

@Slf4j
public abstract class SpellReminderInfobox extends InfoBox {
  protected final SpellReminderConfig config;
  protected final SpellTracker tracker;
  @Setter
  private Long startTime;

  public SpellReminderInfobox(SpellReminderPlugin plugin, SpellReminderConfig config, SpellTracker tracker) {
    super(null, plugin);
    this.config = config;
    this.tracker = tracker;
    this.startTime = null;
  }

  @Override
  public String getText() {
    return null;
  }

  @Override
  public Color getTextColor() {
    return null;
  }

  @Override
  public String getTooltip() {
    return tracker.getCustomMessage();
  }

  @Override
  public boolean render() {
    return !isExpired();
  }

  protected boolean isExpired() {
    if (startTime == null) {
      return true;
    }

    final long timeoutMillis = getTimeoutSeconds() * 1000L;
    final long elapsedMillis = System.currentTimeMillis() - startTime;

    return elapsedMillis > timeoutMillis;
  }

  protected abstract int getSpriteId();

  protected abstract int getTimeoutSeconds();
}