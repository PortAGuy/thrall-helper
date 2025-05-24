package com.portaguy.trackers;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellTracker;
import com.portaguy.overlays.DeathChargeReminderOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

public class DeathChargeTracker extends SpellTracker {
  @Inject
  protected SpellReminderConfig config;

  @Inject
  protected DeathChargeReminderOverlay overlay;

  @Inject
  public DeathChargeTracker() {
    super(false);
  }

  @Subscribe
  protected void onVarbitChanged(VarbitChanged event) {
    if (event.getVarbitId() == VarbitID.ARCEUUS_DEATH_CHARGE_COOLDOWN) {
      if (event.getValue() == 1 && !active) {
        start();
      } else if (event.getValue() == 0 && active) {
        stop();
      }
    }
  }

  @Override
  protected boolean isSpellTracked() {
    return config.deathChargeEnabled();
  }

  @Override
  protected Notification getCustomNotification() {
    return config.deathChargeShouldNotify();
  }

  @Override
  protected String getCustomMessage() {
    return config.deathChargeCustomText();
  }

  @Override
  protected SpellReminderOverlay getOverlay() {
    return overlay;
  }
}
