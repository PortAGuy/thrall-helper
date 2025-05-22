package com.portaguy.overlays;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellReminderStyle;
import com.portaguy.trackers.WardOfArceuusTracker;
import net.runelite.api.Client;

import javax.inject.Inject;
import java.awt.*;

public class WardOfArceuusReminderOverlay extends SpellReminderOverlay {
  @Inject
  public WardOfArceuusReminderOverlay(SpellReminderConfig config, Client client, WardOfArceuusTracker tracker) {
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
  protected Color getFlashColor1() {
    return config.wardOfArceuusFlashColor1();
  }

  @Override
  protected Color getFlashColor2() {
    return config.wardOfArceuusFlashColor2();
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.wardOfArceuusTimeoutSeconds();
  }
}