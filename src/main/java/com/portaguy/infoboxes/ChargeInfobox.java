package com.portaguy.infoboxes;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderInfobox;
import com.portaguy.SpellReminderPlugin;
import com.portaguy.trackers.ChargeTracker;
import net.runelite.api.SpriteID;

import javax.inject.Inject;

public class ChargeInfobox extends SpellReminderInfobox {
  @Inject
  public ChargeInfobox(SpellReminderPlugin plugin,
                       SpellReminderConfig config,
                       ChargeTracker tracker) {
    super(plugin, config, tracker);
  }

  @Override
  protected int getSpriteId() {
    return SpriteID.SPELL_CHARGE;
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.chargeTimeoutSeconds();
  }
}