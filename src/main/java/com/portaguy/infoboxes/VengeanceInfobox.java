package com.portaguy.infoboxes;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderInfobox;
import com.portaguy.SpellReminderPlugin;
import com.portaguy.trackers.VengeanceTracker;
import net.runelite.api.SpriteID;

import javax.inject.Inject;

public class VengeanceInfobox extends SpellReminderInfobox {
  @Inject
  public VengeanceInfobox(SpellReminderPlugin plugin,
                          SpellReminderConfig config,
                          VengeanceTracker tracker) {
    super(plugin, config, tracker);
  }

  @Override
  protected int getSpriteId() {
    return SpriteID.SPELL_VENGEANCE;
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.vengeanceTimeoutSeconds();
  }
}