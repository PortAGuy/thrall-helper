package com.portaguy.trackers;

import com.portaguy.SpellReminderConfig;
import com.portaguy.SpellReminderOverlay;
import com.portaguy.SpellTracker;
import com.portaguy.Spellbook;
import com.portaguy.overlays.VileVigourReminderOverlay;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.time.Instant;

public class VileVigourTracker extends SpellTracker {
  private Instant lastStop = null;

  @Inject
  protected VileVigourReminderOverlay overlay;

  @Inject
  protected SpellReminderConfig config;

  @Inject
  public VileVigourTracker() {
    super(Spellbook.ARCEUUS, false);
  }

  @Override
  @Subscribe
  protected void onVarbitChanged(VarbitChanged event) {
    if (event.getVarbitId() == VarbitID.ARCEUUS_VILE_VIGOUR_COOLDOWN) {
      if (event.getValue() == 1 && !active) {
        start();
      } else if (event.getValue() == 0 && active) {
        lastStop = Instant.now();
        if (isBelowThreshold()) {
          stop();
        } else {
          reset();
        }
      }
    }
  }

  @Override
  @Subscribe(priority = 1)
  protected void onGameTick(GameTick ignored) {
    if (active && client.getTickCount() == finalTick) {
      stop();
    } else if (isExpired() || client.getTickCount() > finalTick) {
      reset();
    }

    if (lastStop != null) {
      Instant timeout = lastStop.plusSeconds(config.vileVigourThresholdTimeout());
      if (Instant.now().isBefore(timeout) && isBelowThreshold()) {
        stop(); // Reset IsExpired to trigger a notification
        lastStop = null;
      }
    }
  }

  private boolean isBelowThreshold() {
    final int runEnergy = client.getEnergy() / 100;
    return runEnergy < config.vileVigourRunThreshold();
  }

  @Override
  protected boolean onlyOnSpellbook() {
    return config.vileVigourOnlyArceuus();
  }

  @Override
  protected boolean isSpellTracked() {
    return config.vileVigourEnabled();
  }

  @Override
  protected Notification getNotification() {
    return config.vileVigourShouldNotify();
  }

  @Override
  protected String getNotifyPattern() {
    return config.vileVigourNotifyRegex();
  }

  @Override
  protected String getRemovePattern() {
    return config.vileVigourRemoveRegex();
  }

  @Override
  protected boolean onGameMessageOnly() {
    return config.vileVigourMatchGameMessagesOnly();
  }

  @Override
  protected String getCustomMessage() {
    return config.vileVigourCustomText();
  }

  @Override
  protected SpellReminderOverlay getOverlay() {
    return overlay;
  }

  @Override
  protected Keybind getHideReminderHotkey() {
    return config.vileVigourHideReminderHotkey();
  }
}
