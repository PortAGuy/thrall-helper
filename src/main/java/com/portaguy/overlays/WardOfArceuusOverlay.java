package com.portaguy.overlays;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellReminderStyle;
import com.portaguy.trackers.WardOfArceuusTracker;
import net.runelite.api.Client;

import javax.inject.Inject;
import java.awt.*;

public class WardOfArceuusOverlay extends SpellReminderOverlay {
  @Inject
  public WardOfArceuusOverlay(SpellReminderConfig config, Client client, WardOfArceuusTracker tracker) {
    super(config, client, tracker);
  }

  @Override
  protected String getLongText() {
    return "You need to cast Ward of Arceuus!";
  }

  @Override
  protected String getShortText() {
    return "Ward";
  }

  @Override
  protected String getCustomText() {
    return config.wardOfArceuusCustomText();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.wardOfArceuusReminderStyle();
  }

  @Override
  protected boolean shouldFlash() {
    return config.wardOfArceuusShouldFlash();
  }

  @Override
  protected Color getColor() {
    return config.wardOfArceuusColor();
  }

  @Override
  protected Color getFlashColor() {
    return config.wardOfArceuusFlashColor();
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.wardOfArceuusTimeoutSeconds();
  }
}