package com.portaguy.infoboxes;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderInfobox;
import com.portaguy.SpellReminderPlugin;
import com.portaguy.trackers.MarkOfDarknessTracker;
import net.runelite.api.gameval.SpriteID;

import javax.inject.Inject;

public class MarkOfDarknessInfobox extends SpellReminderInfobox {
  @Inject
  public MarkOfDarknessInfobox(SpellReminderPlugin plugin,
                               SpellReminderConfig config,
                               MarkOfDarknessTracker tracker) {
    super(plugin, config, tracker);
  }

  @Override
  protected int getSpriteId() {
    return SpriteID.MagicNecroOn.MARK_OF_DARKNESS;
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.markOfDarknessTimeoutSeconds();
  }
}