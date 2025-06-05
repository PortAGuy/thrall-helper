package com.portaguy.trackers;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellTracker;
import com.portaguy.Spellbook;
import com.portaguy.overlays.MarkOfDarknessReminderOverlay;
import net.runelite.api.Skill;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.util.Text;

import javax.inject.Inject;

public class MarkOfDarknessTracker extends SpellTracker {
  private static final String MARK_PLACED_MESSAGE = "you have placed a mark of darkness upon yourself.";
  private static final String MARK_FADED_MESSAGE = "your mark of darkness has faded away.";

  @Inject
  protected MarkOfDarknessReminderOverlay overlay;

  @Inject
  protected SpellReminderConfig config;

  @Inject
  public MarkOfDarknessTracker() {
    super(Spellbook.ARCEUUS, true);
  }

  @Subscribe
  @Override
  protected void onChatMessage(ChatMessage event) {
    final int magicLevel = client.getBoostedSkillLevel(Skill.MAGIC);

    String message = Text.standardize(event.getMessage());
    if (message.equals(MARK_PLACED_MESSAGE)) {
      start(magicLevel);
    } else if (message.equals(MARK_FADED_MESSAGE)) {
      stop();
    }
  }

  @Override
  protected boolean onlyOnSpellbook() {
    return config.markOfDarknessOnlyArceuus();
  }

  @Override
  protected boolean isSpellTracked() {
    return config.markOfDarknessEnabled();
  }

  @Override
  protected Notification getNotification() {
    return config.markOfDarknessShouldNotify();
  }

  @Override
  protected String getNotifyPattern() {
    return config.markOfDarknessNotifyRegex();
  }

  @Override
  protected String getRemovePattern() {
    return config.markOfDarknessRemoveRegex();
  }

  @Override
  protected boolean onGameMessageOnly() { return config.markOfDarknessMatchGameMessagesOnly(); }

  @Override
  protected String getCustomMessage() {
    return config.markOfDarknessCustomText();
  }

  @Override
  protected SpellReminderOverlay getOverlay() {
    return overlay;
  }
}