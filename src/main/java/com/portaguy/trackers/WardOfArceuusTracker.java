package com.portaguy.trackers;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellTracker;
import com.portaguy.overlays.WardOfArceuusReminderOverlay;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

public class WardOfArceuusTracker extends SpellTracker {
  private static final String WARD_EXPIRED_MESSAGE = "<col=0000b2>Your Ward of Arceuus has expired.</col>";

  @Inject
  protected WardOfArceuusReminderOverlay overlay;

  @Inject
  protected SpellReminderConfig config;

  @Inject
  public WardOfArceuusTracker() {
    super(true);
  }

  @Subscribe
  @Override
  protected void onVarbitChanged(VarbitChanged event) {
    if (event.getVarbitId() == VarbitID.ARCEUUS_WARD_COOLDOWN) {
      if (event.getValue() == 1 && !active) {
        start();
      } else if (event.getValue() == 0 && active) {
        stop();
      }
    }
  }

  @Subscribe
  @Override
  protected void onChatMessage(ChatMessage event) {
    if (event.getMessage().equals(WARD_EXPIRED_MESSAGE)) {
      stop();
    }
  }

  @Override
  protected boolean isSpellTracked() {
    return config.wardOfArceuusEnabled();
  }

  @Override
  protected boolean shouldNotify() {
    return config.wardOfArceuusShouldNotify();
  }

  @Override
  protected String getCustomMessage() {
    return config.wardOfArceuusCustomText();
  }

  @Override
  protected SpellReminderOverlay getOverlay() {
    return overlay;
  }
}