package com.portaguy;

import com.google.inject.Inject;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;

import java.util.HashMap;
import java.util.Map;

public class SpellReminderInfoboxFactory {
  private final Map<SpellTracker, SpellReminderInfobox> activeInfoboxes;

  @Inject
  private final InfoBoxManager infoBoxManager;

  @Inject
  private final SpriteManager spriteManager;

  @Inject
  public SpellReminderInfoboxFactory(InfoBoxManager infoBoxManager, SpriteManager spriteManager) {
    this.infoBoxManager = infoBoxManager;
    this.spriteManager = spriteManager;
    this.activeInfoboxes = new HashMap<>();
  }

  public void createInfobox(SpellTracker tracker) {
    if (tracker == null || !tracker.isSpellTracked() || tracker.getReminderStyle() != SpellReminderStyle.INFOBOX) {
      return;
    }

    SpellReminderInfobox infobox = tracker.getInfobox();
    if (infobox == null) {
      return;
    }

    if (activeInfoboxes.containsKey(tracker)) {
      return;
    }

    infobox.setStartTime(System.currentTimeMillis());
    spriteManager.getSpriteAsync(infobox.getSpriteId(), 0, infobox::setImage);
    activeInfoboxes.put(tracker, infobox);
    infoBoxManager.addInfoBox(infobox);
  }

  public void removeInfobox(SpellTracker tracker) {
    if (tracker == null) {
      return;
    }

    SpellReminderInfobox infobox = activeInfoboxes.remove(tracker);
    if (infobox != null) {
      infoBoxManager.removeInfoBox(infobox);
    }
  }

  public void removeAllInfoboxes() {
    activeInfoboxes.values().forEach(infoBoxManager::removeInfoBox);
    activeInfoboxes.clear();
  }

  public boolean isInfoboxActive(SpellTracker tracker) {
    return activeInfoboxes.containsKey(tracker);
  }
}