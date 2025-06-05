package com.portaguy.trackers;

import com.portaguy.*;
import com.portaguy.infoboxes.ShadowVeilReminderInfobox;
import com.portaguy.overlays.ShadowVeilReminderOverlay;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.config.Keybind;

import javax.inject.Inject;

public class ShadowVeilTracker extends SpellTracker {
  @Inject
  protected ShadowVeilReminderOverlay overlay;

  @Inject
  protected ShadowVeilReminderInfobox infobox;

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
  protected Notification getNotification() {
    return config.shadowVeilShouldNotify();
  }

  @Override
  protected String getNotifyPattern() {
    return config.shadowVeilNotifyRegex();
  }

  @Override
  protected String getRemovePattern() {
    return config.shadowVeilRemoveRegex();
  }

  @Override
  protected boolean onGameMessageOnly() {
    return config.shadowVeilMatchGameMessagesOnly();
  }

  @Override
  protected String getCustomMessage() {
    return config.shadowVeilCustomText();
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
    return config.shadowVeilHideReminderHotkey();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.shadowVeilReminderStyle();
  }
}