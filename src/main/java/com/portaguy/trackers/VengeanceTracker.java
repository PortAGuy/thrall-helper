package com.portaguy.trackers;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellTracker;
import com.portaguy.Spellbook;
import com.portaguy.overlays.VengeanceReminderOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

public class VengeanceTracker extends SpellTracker {
  @Inject
  protected VengeanceReminderOverlay overlay;

  @Inject
  protected SpellReminderConfig config;

  @Inject
  public VengeanceTracker() {
    super(Spellbook.LUNAR, false);
  }

  @Subscribe
  @Override
  protected void onVarbitChanged(VarbitChanged event) {
    // 0 when there is no active vengeance to rebound
    int rebound = client.getVarbitValue(VarbitID.VENGEANCE_REBOUND);
    // 1 when vengeance is on cooldown
    int cooldown = client.getVarbitValue(VarbitID.VENGEANCE_TIMELIMIT);
    if (event.getVarbitId() == VarbitID.VENGEANCE_TIMELIMIT) {
      if (event.getValue() == 1 && !active) {
        start();
      } else if (event.getValue() == 0 && rebound == 0 && active) {
        stop();
      }
    } else if (event.getVarbitId() == VarbitID.VENGEANCE_REBOUND) {
      if (event.getValue() == 0 && cooldown == 0 && active) {
        stop();
      }
    }
  }

  @Override
  protected boolean onlyOnSpellbook() {
    return config.vengeanceOnlyLunar();
  }

  @Override
  protected boolean isSpellTracked() {
    return config.vengeanceEnabled();
  }

  @Override
  protected Notification getNotification() {
    return config.vengeanceShouldNotify();
  }

  @Override
  protected String getNotifyPattern() {
    return config.vengeanceNotifyRegex();
  }

  @Override
  protected String getRemovePattern() {
    return config.vengeanceRemoveRegex();
  }

  @Override
  protected String getCustomMessage() {
    return config.vengeanceCustomText();
  }

  @Override
  protected SpellReminderOverlay getOverlay() {
    return overlay;
  }
}