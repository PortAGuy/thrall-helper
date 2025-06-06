package com.portaguy.overlays;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellReminderStyle;
import com.portaguy.trackers.CorruptionTracker;
import net.runelite.api.Client;

import javax.inject.Inject;
import java.awt.*;

public class CorruptionOverlay extends SpellReminderOverlay {
  @Inject
  public CorruptionOverlay(SpellReminderConfig config, Client client, CorruptionTracker tracker) {
    super(config, client, tracker);
  }

  @Override
  protected String getLongText() {
    return "You need to cast Corruption!";
  }

  @Override
  protected String getShortText() {
    return "Corrupt";
  }

  @Override
  protected String getCustomText() {
    return config.corruptionCustomText();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.corruptionReminderStyle();
  }

  @Override
  protected boolean shouldFlash() {
    return config.corruptionShouldFlash();
  }

  @Override
  protected Color getColor() {
    return config.corruptionColor();
  }

  @Override
  protected Color getFlashColor() {
    return config.corruptionFlashColor();
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.corruptionTimeoutSeconds();
  }
}