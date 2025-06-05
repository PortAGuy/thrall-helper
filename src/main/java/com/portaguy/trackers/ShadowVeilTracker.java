package com.portaguy.trackers;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellTracker;
import com.portaguy.Spellbook;
import com.portaguy.overlays.ShadowVeilReminderOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

public class ShadowVeilTracker extends SpellTracker {
  @Inject
  protected ShadowVeilReminderOverlay overlay;

  @Inject
  protected SpellReminderConfig config;

  @Inject
  public ShadowVeilTracker() {
    super(Spellbook.ARCEUUS, false);
  }

  @Subscribe
  @Override
  protected void onVarbitChanged(VarbitChanged event) {
    if (event.getVarbitId() == VarbitID.ARCEUUS_SHADOW_VEIL_ACTIVE) {
      if (event.getValue() == 1 && !active) {
        start();
      } else if (event.getValue() == 0 && active) {
        stop();
      }
    }
  }

  @Override
  protected boolean onlyOnSpellbook() {
    return config.shadowVeilOnlyArceuus();
  }

  @Override
  protected boolean isSpellTracked() {
    return config.shadowVeilEnabled();
  }

  @Override
  protected Notification getCustomNotification() {
    return config.shadowVeilShouldNotify();
  }

  @Override
  protected String getCustomMessage() {
    return config.shadowVeilCustomText();
  }

  @Override
  protected SpellReminderOverlay getOverlay() {
    return overlay;
  }
}