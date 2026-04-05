package com.portaguy.overlays;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellReminderStyle;
import com.portaguy.trackers.MagicImbueTracker;
import net.runelite.api.Client;

import javax.inject.Inject;
import java.awt.*;

public class MagicImbueOverlay extends SpellReminderOverlay {
  @Inject
  public MagicImbueOverlay(SpellReminderConfig config, Client client, MagicImbueTracker tracker) {
    super(config, client, tracker);
  }

  @Override
  protected String getLongText() {
    return "You need to cast Magic Imbue!";
  }

  @Override
  protected String getShortText() {
    return "Magic Imbue";
  }

  @Override
  protected String getCustomText() {
    return config.magicImbueCustomText();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.magicImbueReminderStyle();
  }

  @Override
  protected boolean shouldFlash() {
    return config.magicImbueShouldFlash();
  }

  @Override
  protected Color getColor() {
    return config.magicImbueColor();
  }

  @Override
  protected Color getFlashColor() {
    return config.magicImbueFlashColor();
  }

  @Override
  protected int getTimeoutSeconds() {
    return config.magicImbueTimeoutSeconds();
  }
}