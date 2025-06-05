package com.portaguy.trackers;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellTracker;
import com.portaguy.Spellbook;
import com.portaguy.overlays.CorruptionReminderOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

public class CorruptionTracker extends SpellTracker {
  @Inject
  protected CorruptionReminderOverlay overlay;

  @Inject
  protected SpellReminderConfig config;

  @Inject
  public CorruptionTracker() {
    super(Spellbook.ARCEUUS, false);
  }

  @Subscribe
  @Override
  protected void onVarbitChanged(VarbitChanged event) {
    if (event.getVarbitId() == VarbitID.ARCEUUS_CORRUPTION_COOLDOWN) {
      if (event.getValue() == 1 && !active) {
        start();
      } else if (event.getValue() == 0 && active) {
        stop();
      }
    }
  }

  @Override
  protected boolean onlyOnSpellbook() {
    return config.corruptionOnlyArceuus();
  }

  @Override
  protected boolean isSpellTracked() {
    return config.corruptionEnabled();
  }

  @Override
  protected Notification getNotification() {
    return config.corruptionShouldNotify();
  }

  @Override
  protected String getNotifyPattern() {
    return config.corruptionNotifyRegex();
  }

  @Override
  protected String getRemovePattern() {
    return config.corruptionRemoveRegex();
  }

  @Override
  protected String getCustomMessage() {
    return config.corruptionCustomText();
  }

  @Override
  protected SpellReminderOverlay getOverlay() {
    return overlay;
  }
}