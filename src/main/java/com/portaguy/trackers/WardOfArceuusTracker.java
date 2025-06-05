package com.portaguy.trackers;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellTracker;
import com.portaguy.Spellbook;
import com.portaguy.overlays.WardOfArceuusReminderOverlay;
import net.runelite.api.ChatMessageType;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.util.Text;

import javax.inject.Inject;

public class WardOfArceuusTracker extends SpellTracker {
  private static final String WARD_EXPIRED_MESSAGE = "your ward of arceuus has expired.";

  @Inject
  protected WardOfArceuusReminderOverlay overlay;

  @Inject
  protected SpellReminderConfig config;

  @Inject
  public WardOfArceuusTracker() {
    super(Spellbook.ARCEUUS, true);
  }

  @Subscribe
  @Override
  protected void onVarbitChanged(VarbitChanged event) {
    if (event.getVarbitId() == VarbitID.ARCEUUS_WARD_COOLDOWN) {
      if (event.getValue() == 1 && !active) {
        start();
      }
    }
  }

  @Subscribe
  @Override
  protected void onChatMessage(ChatMessage event) {
    String message = Text.standardize(event.getMessage());
    if (event.getType() != ChatMessageType.GAMEMESSAGE) {
      return;
    }

    if (message.equals(WARD_EXPIRED_MESSAGE)) {
      stop();
    }
  }

  @Override
  protected boolean onlyOnSpellbook() {
    return config.wardOfArceuusOnlyArceuus();
  }

  @Override
  protected boolean isSpellTracked() {
    return config.wardOfArceuusEnabled();
  }

  @Override
  protected Notification getNotification() {
    return config.wardOfArceuusShouldNotify();
  }

  @Override
  protected String getNotifyPattern() {
    return config.wardOfArceuusReminderRegex();
  }

  @Override
  protected String getRemovePattern() {
    return config.wardOfArceuusHiderRegex();
  }

  @Override
  protected String getCustomMessage() {
    return config.wardOfArceuusCustomText();
  }

  @Override
  protected SpellReminderOverlay getOverlay() {
    return overlay;
  }
}