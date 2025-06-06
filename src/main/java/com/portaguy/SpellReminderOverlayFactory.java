package com.portaguy;

import com.google.inject.Inject;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.HashMap;
import java.util.Map;

public class SpellReminderOverlayFactory {
  private final OverlayManager overlayManager;
  private final Map<SpellTracker, SpellReminderOverlay> activeOverlays;

  @Inject
  public SpellReminderOverlayFactory(OverlayManager overlayManager) {
    this.overlayManager = overlayManager;
    this.activeOverlays = new HashMap<>();
  }

  public void createOverlay(SpellTracker tracker) {
    if (tracker == null || !tracker.isSpellTracked()) {
      return;
    }

    SpellReminderOverlay overlay = tracker.getOverlay();
    if (overlay == null) {
      return;
    }

    overlay.setStartTime(System.currentTimeMillis());
    activeOverlays.put(tracker, overlay);
    overlayManager.add(overlay);
  }

  public void removeOverlay(SpellTracker tracker) {
    if (tracker == null) {
      return;
    }

    SpellReminderOverlay overlay = activeOverlays.remove(tracker);
    if (overlay != null) {
      overlayManager.remove(overlay);
    }
  }

  public void removeAllOverlays() {
    activeOverlays.values().forEach(overlayManager::remove);
    activeOverlays.clear();
  }
}