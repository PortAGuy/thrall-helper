package com.portaguy.trackers;

import com.portaguy.*;
import com.portaguy.infoboxes.ThrallInfobox;
import com.portaguy.overlays.ThrallReminderOverlay;
import net.runelite.api.Skill;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

public class ThrallTracker extends SpellTracker {
  @Inject
  protected ThrallReminderOverlay overlay;

  @Inject
  protected ThrallInfobox infobox;

  @Inject
  public ThrallTracker() {
    super(Spellbook.ARCEUUS, false);
  }

  @Subscribe
  @Override
  protected void onVarbitChanged(VarbitChanged event) {
    // We use ARCEUUS_RESURRECTION_COOLDOWN instead of ARCEUUS_RESURRECTION_ACTIVE
    // so that the reminder is cleared immediately upon casting a new thrall.
    if (event.getVarbitId() != VarbitID.ARCEUUS_RESURRECTION_COOLDOWN) {
      return;
    }

    if (event.getValue() == 1) {
      // Thralls last as long as your magic level in game ticks.
      // This timer can be extended 50% or 100% depending on CA completions.
      int ticks = client.getBoostedSkillLevel(Skill.MAGIC);
      if (client.getVarbitValue(VarbitID.CA_TIER_STATUS_GRANDMASTER) == 2) {
        ticks += ticks;
      } else if (client.getVarbitValue(VarbitID.CA_TIER_STATUS_MASTER) == 2) {
        ticks += ticks / 2;
      }

      // Thralls take 4 ticks from summon to become active
      start(ticks + 4);
    }
  }

  @Override
  protected boolean onlyOnSpellbook() {
    return config.onlyArceuus();
  }

  @Override
  protected boolean isSpellTracked() {
    return config.thrallEnabled();
  }

  @Override
  protected Notification getNotification() {
    return config.shouldNotify();
  }

  @Override
  protected String getNotifyPattern() {
    return config.notifyRegex();
  }

  @Override
  protected String getRemovePattern() {
    return config.removeRegex();
  }

  @Override
  protected boolean onGameMessageOnly() {
    return config.matchGameMessagesOnly();
  }

  @Override
  protected String getCustomMessage() {
    return config.customText();
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
    return config.hideReminderHotkey();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.reminderStyle();
  }
}