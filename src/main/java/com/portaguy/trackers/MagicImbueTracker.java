package com.portaguy.trackers;

import com.portaguy.*;
import com.portaguy.infoboxes.MagicImbueInfobox;
import com.portaguy.overlays.MagicImbueOverlay;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GameState;
import net.runelite.api.WorldView;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import java.util.Set;

@Slf4j
public class MagicImbueTracker extends SpellTracker {
  @Inject
  protected MagicImbueOverlay overlay;

  @Inject
  protected MagicImbueInfobox infobox;

  private boolean wasImbueActive = false;

  private static final int AIR_ALTAR_REGION = 11339;
  private static final int WATER_ALTAR_REGION = 10827;
  private static final int EARTH_ALTAR_REGION = 10571;
  private static final int FIRE_ALTAR_REGION = 10315;
  private static final int COSMIC_ALTAR_REGION = 8523;
  private static final Set<Integer> ALTAR_REGIONS = Set.of(
      AIR_ALTAR_REGION,
      WATER_ALTAR_REGION,
      EARTH_ALTAR_REGION,
      FIRE_ALTAR_REGION,
      COSMIC_ALTAR_REGION);

  @Inject
  public MagicImbueTracker() {
    super(Spellbook.LUNAR, false);
  }

  @Subscribe
  @Override
  protected void onVarbitChanged(VarbitChanged event) {
    if (event.getVarbitId() == VarbitID.MAGIC_IMBUE_ACTIVE) {
      if (event.getValue() == 2 && !active) {
        start();
        wasImbueActive = true;
      }
    }
  }

  @Subscribe
  @Override
  protected void onGameStateChanged(GameStateChanged event) {
    if (event.getGameState() == GameState.LOGGING_IN) {
      wasImbueActive = false;
    }

    if (event.getGameState() == GameState.LOGGED_IN) {
      boolean isInAltar = isInAltarRegion();
      boolean isImbueActive = client.getVarbitValue(VarbitID.MAGIC_IMBUE_ACTIVE) > 0;
      if (isInAltar && !isImbueActive && wasImbueActive) {
        stop();
      }
    }
  }

  private boolean isInAltarRegion() {
    WorldView wv = client.getTopLevelWorldView();
    if (wv == null) {
      return false;
    }

    for (int region : wv.getMapRegions()) {
      if (ALTAR_REGIONS.contains(region)) {
        return true;
      }
    }

    return false;
  }

  @Override
  protected boolean onlyOnSpellbook() {
    return config.magicImbueOnlyLunar();
  }

  @Override
  protected boolean isSpellTracked() {
    return config.magicImbueEnabled();
  }

  @Override
  protected Notification getNotification() {
    return config.magicImbueShouldNotify();
  }

  @Override
  protected String getNotifyPattern() {
    return config.magicImbueNotifyRegex();
  }

  @Override
  protected String getRemovePattern() {
    return config.magicImbueRemoveRegex();
  }

  @Override
  protected boolean onGameMessageOnly() {
    return config.magicImbueMatchGameMessagesOnly();
  }

  @Override
  protected String getCustomMessage() {
    return config.magicImbueCustomText();
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
    return config.magicImbueHideReminderHotkey();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.magicImbueReminderStyle();
  }

  @Override
  public String getReminderStyleConfigKey() {
    return "magicImbueReminderStyle";
  }
}
