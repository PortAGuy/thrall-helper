package com.portaguy.trackers;

import com.portaguy.*;
import com.portaguy.infoboxes.ChargeInfobox;
import com.portaguy.overlays.ChargeOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarPlayerID;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.config.Keybind;

import javax.inject.Inject;

public class ChargeTracker extends SpellTracker {
  @Inject
  protected ChargeOverlay overlay;

  @Inject
  protected ChargeInfobox infobox;

  @Inject
  public ChargeTracker() {
    super(Spellbook.STANDARD, false);
  }

  @Subscribe
  @Override
  protected void onVarbitChanged(VarbitChanged event) {
    if (event.getVarpId() == VarPlayerID.MAGEARENA_CHARGE) {
      if (event.getValue() > 0 && !active) {
        start();
      } else if (event.getValue() == 0 && active) {
        stop();
      }
    }
  }

  @Override
  protected boolean onlyOnSpellbook() {
    return config.chargeOnlyStandard();
  }

  @Override
  protected boolean isSpellTracked() {
    return config.chargeEnabled();
  }

  @Override
  protected Notification getNotification() {
    return config.chargeShouldNotify();
  }

  @Override
  protected String getNotifyPattern() {
    return config.chargeNotifyRegex();
  }

  @Override
  protected String getRemovePattern() {
    return config.chargeRemoveRegex();
  }

  @Override
  protected boolean onGameMessageOnly() {
    return config.chargeMatchGameMessagesOnly();
  }

  @Override
  protected String getCustomMessage() {
    return config.chargeCustomText();
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
    return config.chargeHideReminderHotkey();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.chargeReminderStyle();
  }
}