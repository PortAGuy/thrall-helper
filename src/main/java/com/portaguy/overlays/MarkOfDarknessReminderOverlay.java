package com.portaguy.overlays;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellReminderStyle;
import com.portaguy.trackers.MarkOfDarknessTracker;
import net.runelite.api.Client;

import javax.inject.Inject;
import java.awt.*;

public class MarkOfDarknessReminderOverlay extends SpellReminderOverlay {
  @Inject
  public MarkOfDarknessReminderOverlay(SpellReminderConfig config, Client client, MarkOfDarknessTracker tracker) {
    super(config, client, tracker);
  }

  @Override
  protected String getLongText() {
    return "You need to cast Mark of Darkness!";
  }

  @Override
  protected String getShortText() {
    return "Mark";
  }

  @Override
  protected String getCustomText() {
    return config.markOfDarknessCustomText();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.markOfDarknessReminderStyle();
  }

  @Override
  protected boolean shouldFlash() {
    return config.markOfDarknessShouldFlash();
  }

  @Override
  protected Color getFlashColor1() {
    return config.markOfDarknessFlashColor1();
  }

  @Override
  protected Color getFlashColor2() {
    return config.markOfDarknessFlashColor2();
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.markOfDarknessTimeoutSeconds();
  }
}