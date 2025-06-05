package com.portaguy.infoboxes;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderInfobox;
import com.portaguy.SpellReminderPlugin;
import com.portaguy.trackers.VileVigourTracker;
import net.runelite.api.SpriteID;

import javax.inject.Inject;

public class VileVigourReminderInfobox extends SpellReminderInfobox {
  @Inject
  public VileVigourReminderInfobox(SpellReminderPlugin plugin,
                                   SpellReminderConfig config,
                                   VileVigourTracker tracker) {
    super(plugin, config, tracker);
  }

  @Override
  protected int getSpriteId() {
    return SpriteID.SPELL_VILE_VIGOUR;
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.vileVigourTimeoutSeconds();
  }
}