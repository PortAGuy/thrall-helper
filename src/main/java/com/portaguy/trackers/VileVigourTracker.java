package com.portaguy.trackers;

import com.portaguy.*;
import com.portaguy.infoboxes.VileVigourInfobox;
import com.portaguy.overlays.VileVigourOverlay;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.time.Instant;

/***
 * Vile Vigour is a special case for spell tracking as you might not want to cast
 * it immediately after the cooldown ends, since it depends on your run energy being low.
 * <br/>
 * The cases that we want to handle are:
 * <ul>
 *   <li>If it comes off cooldown and you're below the run energy threshold, you get a notification</li>
 *   <li>If it comes off cooldown and you're not below the run energy threshold, you do not get a notification</li>
 *   <li>If you've casted it X seconds ago and you hit the run energy threshold, you get a notification</li>
 * </ul>
 */
public class VileVigourTracker extends SpellTracker {
  private Instant cooldownEndTime = null;

  @Inject
  protected VileVigourOverlay overlay;

  @Inject
  protected VileVigourInfobox infobox;

  @Inject
  public VileVigourTracker() {
    super(Spellbook.ARCEUUS, false);
  }

  @Override
  @Subscribe
  protected void onVarbitChanged(VarbitChanged event) {
    if (event.getVarbitId() != VarbitID.ARCEUUS_VILE_VIGOUR_COOLDOWN) {
      return;
    }

    if (event.getValue() == 1 && !active) {
      start();
    } else if (event.getValue() == 0 && active) {
      cooldownEndTime = Instant.now();
      checkAndNotify();
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

    if (cooldownEndTime != null) {
      Instant thresholdTime = cooldownEndTime.plusSeconds(config.vileVigourThresholdTimeout());
      if (Instant.now().isAfter(thresholdTime)) {
        cooldownEndTime = null;
      } else if (isBelowThreshold()) {
        stop();
        cooldownEndTime = null;
      }
    }
  }

  private void checkAndNotify() {
    if (isBelowThreshold()) {
      stop();
    } else {
      reset();
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
  protected SpellReminderInfobox getInfobox() {
    return infobox;
  }

  @Override
  protected Keybind getHideReminderHotkey() {
    return config.vileVigourHideReminderHotkey();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.vileVigourReminderStyle();
  }

  @Override
  public String getReminderStyleConfigKey() {
    return "vileVigourReminderStyle";
  }
}
