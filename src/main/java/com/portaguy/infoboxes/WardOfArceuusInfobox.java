package com.portaguy.infoboxes;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderInfobox;
import com.portaguy.SpellReminderPlugin;
import com.portaguy.trackers.WardOfArceuusTracker;
import net.runelite.api.SpriteID;

import javax.inject.Inject;

public class WardOfArceuusInfobox extends SpellReminderInfobox {
  @Inject
  public WardOfArceuusInfobox(SpellReminderPlugin plugin,
                              SpellReminderConfig config,
                              WardOfArceuusTracker tracker) {
    super(plugin, config, tracker);
  }

  @Override
  protected int getSpriteId() {
    return SpriteID.SPELL_WARD_OF_ARCEUUS;
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.wardOfArceuusTimeoutSeconds();
  }
}