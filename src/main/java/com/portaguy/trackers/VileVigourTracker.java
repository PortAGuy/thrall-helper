package com.portaguy.trackers;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellTracker;
import com.portaguy.overlays.VileVigourReminderOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

public class VileVigourTracker extends SpellTracker {
  @Inject
  protected VileVigourReminderOverlay overlay;

  @Inject
  protected SpellReminderConfig config;

  @Inject
  public VileVigourTracker() {
    super(false);
  }

  @Subscribe
  @Override
  protected void onVarbitChanged(VarbitChanged event) {
    if (event.getVarbitId() == VarbitID.ARCEUUS_VILE_VIGOUR_COOLDOWN) {
      if (event.getValue() == 1 && !active) {
        start();
      } else if (event.getValue() == 0 && active) {
        stop();
      }
    }
  }

  @Override
  protected boolean isSpellTracked() {
    return config.vileVigourEnabled();
  }

  @Override
  protected Notification getCustomNotification() {
    return config.vileVigourShouldNotify();
  }

  @Override
  protected String getCustomMessage() {
    return config.vileVigourCustomText();
  }

  @Override
  protected SpellReminderOverlay getOverlay() {
    return overlay;
  }
}
