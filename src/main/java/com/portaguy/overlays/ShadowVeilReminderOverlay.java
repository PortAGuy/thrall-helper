package com.portaguy.overlays;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellReminderStyle;
import com.portaguy.trackers.ShadowVeilTracker;
import net.runelite.api.Client;

import javax.inject.Inject;
import java.awt.*;

public class ShadowVeilReminderOverlay extends SpellReminderOverlay {
  @Inject
  public ShadowVeilReminderOverlay(SpellReminderConfig config, Client client, ShadowVeilTracker tracker) {
    super(config, client, tracker);
  }

  @Override
  protected String getLongText() {
    return "You need to cast Shadow Veil!";
  }

  @Override
  protected String getShortText() {
    return "Veil";
  }

  @Override
  protected String getCustomText() {
    return config.shadowVeilCustomText();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.shadowVeilReminderStyle();
  }

  @Override
  protected boolean shouldFlash() {
    return config.shadowVeilShouldFlash();
  }

  @Override
  protected Color getColor() {
    return config.shadowVeilColor();
  }

  @Override
  protected Color getFlashColor() {
    return config.shadowVeilFlashColor();
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.shadowVeilTimeoutSeconds();
  }
}