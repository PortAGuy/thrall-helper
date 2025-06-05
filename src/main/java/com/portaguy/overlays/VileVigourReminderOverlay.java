package com.portaguy.overlays;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellReminderStyle;
import com.portaguy.trackers.VileVigourTracker;
import net.runelite.api.Client;

import javax.inject.Inject;
import java.awt.*;

public class VileVigourReminderOverlay extends SpellReminderOverlay {
  @Inject
  public VileVigourReminderOverlay(SpellReminderConfig config, Client client, VileVigourTracker tracker) {
    super(config, client, tracker);
  }

  @Override
  protected String getLongText() {
    return "You need to cast Vile Vigour!";
  }

  @Override
  protected String getShortText() {
    return "Vile";
  }

  @Override
  protected String getCustomText() {
    return config.vileVigourCustomText();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.vileVigourReminderStyle();
  }

  @Override
  protected boolean shouldFlash() {
    return config.vileVigourShouldFlash();
  }

  @Override
  protected Color getColor() {
    return config.vileVigourColor();
  }

  @Override
  protected Color getFlashColor() {
    return config.vileVigourFlashColor();
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.vileVigourTimeoutSeconds();
  }
}
