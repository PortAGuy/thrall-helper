package com.portaguy.overlays;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellReminderStyle;
import com.portaguy.trackers.ThrallTracker;
import net.runelite.api.Client;

import javax.inject.Inject;
import java.awt.*;

public class ThrallReminderOverlay extends SpellReminderOverlay {
  @Inject
  public ThrallReminderOverlay(SpellReminderConfig config, Client client, ThrallTracker tracker) {
    super(config, client, tracker);
  }

  @Override
  protected String getLongText() {
    return "You need to summon a thrall!";
  }

  @Override
  protected String getShortText() {
    return "Thrall";
  }

  @Override
  protected String getCustomText() {
    return config.customText();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.reminderStyle();
  }

  @Override
  protected boolean shouldFlash() {
    return config.shouldFlash();
  }

  @Override
  protected Color getColor() {
    return config.color();
  }

  @Override
  protected Color getFlashColor() {
    return config.flashColor();
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.thrallTimeoutSeconds();
  }
}