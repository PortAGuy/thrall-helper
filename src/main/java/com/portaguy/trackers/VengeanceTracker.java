package com.portaguy.trackers;

import com.portaguy.*;
import com.portaguy.infoboxes.VengeanceInfobox;
import com.portaguy.overlays.VengeanceOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

public class VengeanceTracker extends SpellTracker {
  @Inject
  protected VengeanceOverlay overlay;

  @Inject
  protected VengeanceInfobox infobox;

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
  protected boolean onGameMessageOnly() {
    return config.vengeanceMatchGameMessagesOnly();
  }

  @Override
  protected String getCustomMessage() {
    return config.vengeanceCustomText();
  }

  @Override
  protected SpellReminderOverlay getOverlay() {
    return overlay;
  }

  @Override
  protected SpellReminderInfobox getInfobox() {
    return infobox;
  }

  @Override
  protected Keybind getHideReminderHotkey() {
    return config.vengeanceHideReminderHotkey();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.vengeanceReminderStyle();
  }
}