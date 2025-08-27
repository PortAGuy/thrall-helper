package com.portaguy.trackers;

import com.portaguy.*;
import com.portaguy.infoboxes.MarkOfDarknessInfobox;
import com.portaguy.overlays.MarkOfDarknessOverlay;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.Skill;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.gameval.InventoryID;
import net.runelite.api.gameval.ItemID;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.util.Text;

import javax.inject.Inject;

public class MarkOfDarknessTracker extends SpellTracker {
  private static final String MARK_PLACED_MESSAGE = "you have placed a mark of darkness upon yourself.";
  private static final String MARK_FADED_MESSAGE = "your mark of darkness has faded away.";

  @Inject
  protected MarkOfDarknessOverlay overlay;

  @Inject
  protected MarkOfDarknessInfobox infobox;

  @Inject
  public MarkOfDarknessTracker() {
    super(Spellbook.ARCEUUS, true);
  }

  @Subscribe
  @Override
  protected void onChatMessage(ChatMessage event) {
    String message = Text.standardize(event.getMessage());
    if (message.equals(MARK_PLACED_MESSAGE)) {
      int ticks = client.getRealSkillLevel(Skill.MAGIC) * 3;
      if (isPurgingStaffEquipped()) {
        ticks *= 5;
      }
      start(ticks);
    } else if (message.equals(MARK_FADED_MESSAGE)) {
      stop();
    }
  }

  private boolean isPurgingStaffEquipped() {
    final ItemContainer container = client.getItemContainer(InventoryID.WORN);
    if (container == null) {
      return false;
    }

    final Item weapon = container.getItem(EquipmentInventorySlot.WEAPON.getSlotIdx());
    if (weapon == null) {
      return false;
    }

    return weapon.getId() == ItemID.PURGING_STAFF;
  }

  @Override
  protected boolean onlyOnSpellbook() {
    return config.markOfDarknessOnlyArceuus();
  }

  @Override
  protected boolean isSpellTracked() {
    return config.markOfDarknessEnabled();
  }

  @Override
  protected Notification getNotification() {
    return config.markOfDarknessShouldNotify();
  }

  @Override
  protected String getNotifyPattern() {
    return config.markOfDarknessNotifyRegex();
  }

  @Override
  protected String getRemovePattern() {
    return config.markOfDarknessRemoveRegex();
  }

  @Override
  protected boolean onGameMessageOnly() {
    return config.markOfDarknessMatchGameMessagesOnly();
  }

  @Override
  protected String getCustomMessage() {
    return config.markOfDarknessCustomText();
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
    return config.markOfDarknessHideReminderHotkey();
  }

  @Override
  protected SpellReminderStyle getReminderStyle() {
    return config.markOfDarknessReminderStyle();
  }

  @Override
  public String getReminderStyleConfigKey() {
    return "markOfDarknessReminderStyle";
  }
}