package com.portaguy.infoboxes;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderInfobox;
import com.portaguy.SpellReminderPlugin;
import com.portaguy.trackers.CorruptionTracker;
import net.runelite.api.gameval.SpriteID;

import javax.inject.Inject;

public class CorruptionInfobox extends SpellReminderInfobox {
  @Inject
  public CorruptionInfobox(SpellReminderPlugin plugin,
                           SpellReminderConfig config,
                           CorruptionTracker tracker) {
    super(plugin, config, tracker);
  }

  @Override
  protected int getSpriteId() {
    return SpriteID.MagicNecroOn.GREATER_CORRUPTION;
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.corruptionTimeoutSeconds();
  }
}