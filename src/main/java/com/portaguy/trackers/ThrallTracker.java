package com.portaguy.trackers;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellTracker;
import com.portaguy.overlays.ThrallReminderOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

public class ThrallTracker extends SpellTracker {
  @Inject
  protected ThrallReminderOverlay overlay;

  @Inject
  protected SpellReminderConfig config;

  @Inject
  public ThrallTracker() {
    super(false);
  }

  @Subscribe
  @Override
  protected void onVarbitChanged(VarbitChanged event) {
    if (event.getVarbitId() == VarbitID.ARCEUUS_RESURRECTION_ACTIVE) {
      if (event.getValue() == 1 && !active) {
        start();
      } else if (event.getValue() == 0 && active) {
        stop();
      }
    }
  }

  @Override
  protected boolean isSpellTracked() {
    return config.thrallEnabled();
  }

  @Override
  protected boolean shouldNotify() {
    return config.shouldNotify();
  }

  @Override
  protected String getCustomMessage() {
    return config.customText();
  }

  @Override
  protected SpellReminderOverlay getOverlay() {
    return overlay;
  }
}