package com.portaguy.trackers;

import com.portaguy.*;
import com.portaguy.infoboxes.WardOfArceuusInfobox;
import com.portaguy.overlays.WardOfArceuusOverlay;
import net.runelite.api.ChatMessageType;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.util.Text;

import javax.inject.Inject;

public class WardOfArceuusTracker extends SpellTracker {
  private static final String WARD_EXPIRED_MESSAGE = "your ward of arceuus has expired.";

  @Inject
  protected WardOfArceuusOverlay overlay;

  @Inject
  protected WardOfArceuusInfobox infobox;

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
    return config.wardOfArceuusNotifyRegex();
  }

  @Override
  protected String getRemovePattern() {
    return config.wardOfArceuusRemoveRegex();
  }

  @Override
  protected boolean onGameMessageOnly() {
    return config.wardOfArceuusMatchGameMessagesOnly();
  }

  @Override
  protected String getCustomMessage() {
    return config.wardOfArceuusCustomText();
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
    return config.wardOfArceuusHideReminderHotkey();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.wardOfArceuusReminderStyle();
  }

  @Override
  public String getReminderStyleConfigKey() {
    return "wardOfArceuusReminderStyle";
  }
}