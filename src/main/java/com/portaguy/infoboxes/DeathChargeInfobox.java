package com.portaguy.infoboxes;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderInfobox;
import com.portaguy.SpellReminderPlugin;
import com.portaguy.trackers.DeathChargeTracker;
import net.runelite.api.SpriteID;

import javax.inject.Inject;

public class DeathChargeInfobox extends SpellReminderInfobox {
  @Inject
  public DeathChargeInfobox(SpellReminderPlugin plugin,
                            SpellReminderConfig config,
                            DeathChargeTracker tracker) {
    super(plugin, config, tracker);
  }

  @Override
  protected int getSpriteId() {
    return SpriteID.SPELL_DEATH_CHARGE;
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.deathChargeTimeoutSeconds();
  }
}