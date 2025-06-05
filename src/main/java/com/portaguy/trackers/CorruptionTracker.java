package com.portaguy.trackers;

import com.portaguy.*;
import com.portaguy.infoboxes.CorruptionReminderInfobox;
import com.portaguy.overlays.CorruptionReminderOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.config.Keybind;

import javax.inject.Inject;

public class CorruptionTracker extends SpellTracker {
  @Inject
  protected CorruptionReminderOverlay overlay;

  @Inject
  protected CorruptionReminderInfobox infobox;

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
  protected boolean onGameMessageOnly() {
    return config.corruptionMatchGameMessagesOnly();
  }

  @Override
  protected String getCustomMessage() {
    return config.corruptionCustomText();
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
    return config.corruptionHideReminderHotkey();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.corruptionReminderStyle();
  }
}