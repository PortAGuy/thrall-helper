package com.portaguy;

import com.google.inject.Provides;
import com.portaguy.trackers.*;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.Notifier;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.HotkeyListener;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

@Slf4j
@PluginDescriptor(
  name = "Spell Reminder",
  description = "Shows a box that reminds you to recast a spell",
  tags = {
      "spellbook",
      "thrall",
      "death charge",
      "mark of darkness",
      "ward of arceuus",
      "shadow veil",
      "corruption",
      "charge",
      "vile vigour",
      "vengeance"
  },
  configName = "ThrallHelperPlugin"
)
public class SpellReminderPlugin extends Plugin {
  private final List<SpellTracker> spellTrackers = new ArrayList<>();
  private final List<HotkeyListener> hotkeyListeners = new ArrayList<>();

  @Inject
  protected ThrallTracker thrallTracker;

  @Inject
  protected DeathChargeTracker deathChargeTracker;

  @Inject
  protected MarkOfDarknessTracker markOfDarknessTracker;

  @Inject
  protected WardOfArceuusTracker wardOfArceuusTracker;

  @Inject
  protected ShadowVeilTracker shadowVeilTracker;

  @Inject
  protected CorruptionTracker corruptionTracker;

  @Inject
  protected ChargeTracker chargeTracker;

  @Inject
  protected VengeanceTracker vengeanceTracker;

  @Inject
  protected VileVigourTracker vileVigourTracker;

  @Inject
  protected Client client;

  @Inject
  protected EventBus eventBus;

  @Inject
  protected Notifier notifier;

  @Inject
  protected KeyManager keyManager;

  @Inject
  protected ConfigManager configManager;

  @Inject
  protected ChatMessageManager chatMessageManager;

  @Inject
  protected SpellReminderConfig config;

  @Inject
  protected SpellReminderOverlayFactory overlayFactory;

  @Inject
  protected SpellReminderInfoboxFactory infoboxFactory;

  @Provides
  SpellReminderConfig provideConfig(ConfigManager configManager) {
    return configManager.getConfig(SpellReminderConfig.class);
  }

  @Override
  protected void startUp() {
    spellTrackers.add(thrallTracker);
    spellTrackers.add(deathChargeTracker);
    spellTrackers.add(markOfDarknessTracker);
    spellTrackers.add(wardOfArceuusTracker);
    spellTrackers.add(shadowVeilTracker);
    spellTrackers.add(corruptionTracker);
    spellTrackers.add(chargeTracker);
    spellTrackers.add(vengeanceTracker);
    spellTrackers.add(vileVigourTracker);

    for (SpellTracker tracker : spellTrackers) {
      tracker.initializePatterns();
      eventBus.register(tracker);

      HotkeyListener listener = new HotkeyListener(tracker::getHideReminderHotkey) {
        @Override
        public void hotkeyPressed() {
          overlayFactory.removeOverlay(tracker);
          infoboxFactory.removeInfobox(tracker);
        }
      };
      keyManager.registerKeyListener(listener);

      hotkeyListeners.add(listener);
    }
  }

  @Override
  protected void shutDown() {
    for (SpellTracker tracker : spellTrackers) {
      eventBus.unregister(tracker);
    }

    for (HotkeyListener listener : hotkeyListeners) {
      keyManager.unregisterKeyListener(listener);
    }

    hotkeyListeners.clear();
    overlayFactory.removeAllOverlays();
    infoboxFactory.removeAllInfoboxes();
  }

  @Subscribe
  protected void onGameTick(GameTick ignored) {
    for (SpellTracker tracker : spellTrackers) {
      if (!tracker.isSpellTracked()) {
        continue;
      }

      if (tracker.isExpired()) {
        final int spellbookVarbit = client.getVarbitValue(VarbitID.SPELLBOOK);

        Spellbook spellbook = Spellbook.fromVarbit(spellbookVarbit);
        if (tracker.onlyOnSpellbook() && tracker.getSpellbook() != spellbook) {
          return;
        }

        if (tracker.getReminderStyle() == SpellReminderStyle.INFOBOX) {
          infoboxFactory.createInfobox(tracker);
        } else {
          overlayFactory.createOverlay(tracker);
        }

        if (tracker.getNotification().isEnabled()) {
          notifier.notify(tracker.getNotification(), tracker.getCustomMessage());
        }
      }

      if (tracker.isActive()) {
        overlayFactory.removeOverlay(tracker);
        infoboxFactory.removeInfobox(tracker);
      }
    }
  }

  @Subscribe
  protected void onChatMessage(ChatMessage event) {
    final String message = event.getMessage();
    final ChatMessageType type = event.getType();

    for (SpellTracker tracker : spellTrackers) {
      if (tracker.onGameMessageOnly() && type != ChatMessageType.GAMEMESSAGE) {
        continue;
      }

      Matcher notifyMatcher = tracker.notifyMessage.matcher(message);
      if (notifyMatcher.matches()) {
        if (tracker.getReminderStyle() == SpellReminderStyle.INFOBOX) {
          infoboxFactory.createInfobox(tracker);
        } else {
          overlayFactory.createOverlay(tracker);
        }

        if (tracker.getNotification().isEnabled()) {
          notifier.notify(tracker.getNotification(), tracker.getCustomMessage());
        }
      }

      Matcher removeMatcher = tracker.removeMessage.matcher(message);
      if (removeMatcher.matches()) {
        overlayFactory.removeOverlay(tracker);
        infoboxFactory.removeInfobox(tracker);
      }
    }
  }

  @Subscribe
  protected void onGameStateChanged(GameStateChanged gameStateChanged) {
    if (gameStateChanged.getGameState() != GameState.LOGGED_IN) {
	  return;
	}

	if (!config.thrallHelperToSpellReminderUpdate()) {
      chatMessageManager.queue(QueuedMessage.builder()
        .type(ChatMessageType.GAMEMESSAGE)
        .runeLiteFormattedMessage(SpellReminderConfig.thrallHelperToSpellReminderUpdateText).build());
      configManager.setConfiguration(SpellReminderConfig.GROUP, "thrallHelperToSpellReminderUpdate", true);
	}
  }
}
