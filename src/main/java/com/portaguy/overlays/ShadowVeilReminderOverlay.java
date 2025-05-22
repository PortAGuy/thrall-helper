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
  protected Color getFlashColor1() {
    return config.shadowVeilFlashColor1();
  }

  @Override
  protected Color getFlashColor2() {
    return config.shadowVeilFlashColor2();
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.shadowVeilTimeoutSeconds();
  }
}