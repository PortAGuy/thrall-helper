package com.portaguy.overlays;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellReminderStyle;
import com.portaguy.trackers.ChargeTracker;
import net.runelite.api.Client;

import javax.inject.Inject;
import java.awt.*;

public class ChargeReminderOverlay extends SpellReminderOverlay {
  @Inject
  public ChargeReminderOverlay(SpellReminderConfig config, Client client, ChargeTracker tracker) {
    super(config, client, tracker);
  }

  @Override
  protected String getLongText() {
    return "You need to cast Charge!";
  }

  @Override
  protected String getShortText() {
    return "Charge";
  }

  @Override
  protected String getCustomText() {
    return config.chargeCustomText();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.chargeReminderStyle();
  }

  @Override
  protected boolean shouldFlash() {
    return config.chargeShouldFlash();
  }

  @Override
  protected Color getColor() {
    return config.chargeColor();
  }

  @Override
  protected Color getFlashColor() {
    return config.chargeFlashColor();
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.chargeTimeoutSeconds();
  }
}