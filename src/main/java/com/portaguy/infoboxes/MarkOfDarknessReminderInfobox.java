package com.portaguy.infoboxes;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderInfobox;
import com.portaguy.SpellReminderPlugin;
import com.portaguy.trackers.MarkOfDarknessTracker;
import net.runelite.api.SpriteID;

import javax.inject.Inject;

public class MarkOfDarknessReminderInfobox extends SpellReminderInfobox {
  @Inject
  public MarkOfDarknessReminderInfobox(SpellReminderPlugin plugin,
                                       SpellReminderConfig config,
                                       MarkOfDarknessTracker tracker) {
    super(plugin, config, tracker);
  }

  @Override
  protected int getSpriteId() {
    return SpriteID.SPELL_MARK_OF_DARKNESS;
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.markOfDarknessTimeoutSeconds();
  }
}