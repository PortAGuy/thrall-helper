package com.portaguy.trackers;

import com.portaguy.*;
import com.portaguy.infoboxes.DeathChargeInfobox;
import com.portaguy.overlays.DeathChargeOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarPlayerID;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;

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

  // Special attack energy is stored as 0-1000.
  private static final int FULL_SPEC = 1000;

  // Death Charge came off cooldown while spec was full, so the reminder is held back until
  // spec drops: a charge used at full spec restores nothing.
  private boolean waitingForSpec;

  @Subscribe
  @Override
  protected void onVarbitChanged(VarbitChanged event) {
    if (event.getVarbitId() == VarbitID.ARCEUUS_DEATH_CHARGE_COOLDOWN) {
      if (event.getValue() == 1 && !active) {
        waitingForSpec = false;
        start();
      } else if (event.getValue() == 0 && active) {
        if (config.deathChargeHideAtFullSpec() && client.getVarpValue(VarPlayerID.SA_ENERGY) >= FULL_SPEC) {
          waitingForSpec = true;
        } else {
          stop();
        }
      }
    } else if (event.getVarpId() == VarPlayerID.SA_ENERGY && waitingForSpec) {
      if (event.getValue() < FULL_SPEC || !config.deathChargeHideAtFullSpec()) {
        waitingForSpec = false;
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

  @Override
  public String getReminderStyleConfigKey() {
    return "deathChargeReminderStyle";
  }
}
