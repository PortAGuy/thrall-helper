package com.portaguy.infoboxes;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderInfobox;
import com.portaguy.SpellReminderPlugin;
import com.portaguy.trackers.ThrallTracker;

import javax.inject.Inject;

public class ThrallInfobox extends SpellReminderInfobox {
  private static final int SPRITE_SPELL_RESURRECT_GREATER_GHOST = 2979;

  @Inject
  public ThrallInfobox(SpellReminderPlugin plugin,
                       SpellReminderConfig config,
                       ThrallTracker tracker) {
    super(plugin, config, tracker);
  }

  @Override
  protected int getSpriteId() {
    return SPRITE_SPELL_RESURRECT_GREATER_GHOST;
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.thrallTimeoutSeconds();
  }
}