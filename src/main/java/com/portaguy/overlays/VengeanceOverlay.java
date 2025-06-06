package com.portaguy.overlays;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellReminderStyle;
import com.portaguy.trackers.VengeanceTracker;
import net.runelite.api.Client;

import javax.inject.Inject;
import java.awt.*;

public class VengeanceOverlay extends SpellReminderOverlay {
  @Inject
  public VengeanceOverlay(SpellReminderConfig config, Client client, VengeanceTracker tracker) {
    super(config, client, tracker);
  }

  @Override
  protected String getLongText() {
    return "You need to cast Vengeance!";
  }

  @Override
  protected String getShortText() {
    return "Veng";
  }

  @Override
  protected String getCustomText() {
    return config.vengeanceCustomText();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.vengeanceReminderStyle();
  }

  @Override
  protected boolean shouldFlash() {
    return config.vengeanceShouldFlash();
  }

  @Override
  protected Color getColor() {
    return config.vengeanceColor();
  }

  @Override
  protected Color getFlashColor() {
    return config.vengeanceFlashColor();
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.vengeanceTimeoutSeconds();
  }
}