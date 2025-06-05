package com.portaguy.trackers;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellTracker;
import com.portaguy.Spellbook;
import com.portaguy.overlays.ChargeReminderOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarPlayerID;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.config.Keybind;

import javax.inject.Inject;

public class ChargeTracker extends SpellTracker {
  @Inject
  protected ChargeReminderOverlay overlay;

  @Inject
  protected SpellReminderConfig config;

  @Inject
  public ChargeTracker() {
    super(Spellbook.STANDARD, false);
  }

  @Subscribe
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
  protected Keybind getHideReminderHotkey() {
    return config.chargeHideReminderHotkey();
  }
}