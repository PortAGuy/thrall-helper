package com.portaguy.trackers;

import com.portaguy.*;
import com.portaguy.infoboxes.DeathChargeInfobox;
import com.portaguy.overlays.DeathChargeOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.config.Keybind;

import javax.inject.Inject;

public class DeathChargeTracker extends SpellTracker {
  @Inject
  protected DeathChargeOverlay overlay;

  @Inject
  protected DeathChargeInfobox infobox;

  @Inject
  public DeathChargeTracker() {
    super(Spellbook.ARCEUUS, false);
  }

  @Subscribe
  @Override
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
  protected boolean onlyOnSpellbook() {
    return config.deathChargeOnlyArceuus();
  }

  @Override
  protected boolean isSpellTracked() {
    return config.deathChargeEnabled();
  }

  @Override
  protected Notification getNotification() {
    return config.deathChargeShouldNotify();
  }

  @Override
  protected String getNotifyPattern() {
    return config.deathChargeNotifyRegex();
  }

  @Override
  protected String getRemovePattern() {
    return config.deathChargeRemoveRegex();
  }

  @Override
  protected boolean onGameMessageOnly() {
    return config.deathChargeMatchGameMessagesOnly();
  }

  @Override
  protected String getCustomMessage() {
    return config.deathChargeCustomText();
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
    return config.deathChargeHideReminderHotkey();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.deathChargeReminderStyle();
  }
}
