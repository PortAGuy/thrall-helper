package com.portaguy.trackers;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellTracker;
import com.portaguy.overlays.ChargeReminderOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarPlayerID;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

public class ChargeTracker extends SpellTracker {
  @Inject
  protected SpellReminderConfig config;

  @Inject
  protected ChargeReminderOverlay overlay;

  @Inject
  public ChargeTracker() {
    super(false);
  }

  @Subscribe
  protected void onVarbitChanged(VarbitChanged event) {
    if (event.getVarpId() == VarPlayerID.MAGEARENA_CHARGE) {
      if (event.getValue() == 1 && !active) {
        start();
      } else if (event.getValue() == 0 && active) {
        stop();
      }
    }
  }

  @Override
  protected boolean isSpellTracked() {
    return config.chargeEnabled();
  }

  @Override
  protected boolean shouldNotify() {
    return config.chargeShouldNotify();
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