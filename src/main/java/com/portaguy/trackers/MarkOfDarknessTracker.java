package com.portaguy.trackers;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellTracker;
import com.portaguy.overlays.MarkOfDarknessReminderOverlay;
import net.runelite.api.Skill;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

public class MarkOfDarknessTracker extends SpellTracker {
  private static final String MARK_PLACED_MESSAGE = "<col=a53fff>You have placed a Mark of Darkness upon yourself.</col>";
  private static final String MARK_FADED_MESSAGE = "<col=a53fff>Your Mark of Darkness has faded away.</col>";

  @Inject
  protected MarkOfDarknessReminderOverlay overlay;

  @Inject
  protected SpellReminderConfig config;

  @Inject
  public MarkOfDarknessTracker() {
    super(true);
  }

  @Subscribe
  @Override
  protected void onChatMessage(ChatMessage event) {
    int magicLevel = client.getBoostedSkillLevel(Skill.MAGIC);
    if (event.getMessage().equals(MARK_PLACED_MESSAGE)) {
      start(magicLevel);
    } else if (event.getMessage().equals(MARK_FADED_MESSAGE)) {
      stop();
    }
  }

  @Override
  protected boolean isSpellTracked() {
    return config.markOfDarknessEnabled();
  }

  @Override
  protected boolean shouldNotify() {
    return config.markOfDarknessShouldNotify();
  }

  @Override
  protected String getCustomMessage() {
    return config.markOfDarknessCustomText();
  }

  @Override
  protected SpellReminderOverlay getOverlay() {
    return overlay;
  }
}