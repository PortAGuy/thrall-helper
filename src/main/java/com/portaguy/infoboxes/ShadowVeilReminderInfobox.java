package com.portaguy.infoboxes;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderInfobox;
import com.portaguy.SpellReminderPlugin;
import com.portaguy.trackers.ShadowVeilTracker;
import net.runelite.api.SpriteID;

import javax.inject.Inject;

public class ShadowVeilReminderInfobox extends SpellReminderInfobox {
  @Inject
  public ShadowVeilReminderInfobox(SpellReminderPlugin plugin,
                                   SpellReminderConfig config,
                                   ShadowVeilTracker tracker) {
    super(plugin, config, tracker);
  }

  @Override
  protected int getSpriteId() {
    return SpriteID.SPELL_SHADOW_VEIL;
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.shadowVeilTimeoutSeconds();
  }
}