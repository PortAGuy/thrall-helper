package com.portaguy.infoboxes;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderInfobox;
import com.portaguy.SpellReminderPlugin;
import com.portaguy.trackers.MagicImbueTracker;
import net.runelite.api.gameval.SpriteID;

import javax.inject.Inject;

public class MagicImbueInfobox extends SpellReminderInfobox {
  @Inject
  public MagicImbueInfobox(SpellReminderPlugin plugin,
                           SpellReminderConfig config,
                           MagicImbueTracker tracker) {
    super(plugin, config, tracker);
  }

  @Override
  protected int getSpriteId() {
    return SpriteID.LunarMagicOn.MAGIC_IMBUE;
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.magicImbueTimeoutSeconds();
  }
}