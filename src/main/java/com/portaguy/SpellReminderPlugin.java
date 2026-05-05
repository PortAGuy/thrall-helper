package com.portaguy;

import com.google.inject.Provides;
import com.portaguy.trackers.*;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.WorldView;
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
import net.runelite.client.events.ConfigChanged;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
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
  // ignore regions where summoning a thrall early is not useful (whisperer & sotetseg maze)
  private static final int[] INSTANCE_RESET_IGNORED_REGIONS = { 10595, 13379 };

  private SharedKeyListener keyListener;
  private final List<SpellTracker> spellTrackers = new ArrayList<>();
  private int[] previousRegions = null;
  private boolean wasInInstance = false;

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
  protected MagicImbueTracker magicImbueTracker;

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
    spellTrackers.add(magicImbueTracker);

    for (SpellTracker tracker : spellTrackers) {
      tracker.initializePatterns();
      eventBus.register(tracker);
    }

    keyListener = new SharedKeyListener(
      spellTrackers, overlayFactory, infoboxFactory);
    keyManager.registerKeyListener(keyListener);
  }

  @Override
  protected void shutDown() {
    keyManager.unregisterKeyListener(keyListener);

    for (SpellTracker tracker : spellTrackers) {
      eventBus.unregister(tracker);
    }
    spellTrackers.clear();

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
          notifier.notify(tracker.getNotification(), "Spell Reminder: " + tracker.getCustomMessage());
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

    // Moons of Peril sends an empty chat message.
    // Empty chat messages should not match on empty regex configs.
    if (message.isEmpty()) {
      return;
    }

    for (SpellTracker tracker : spellTrackers) {
      if (!tracker.isSpellTracked()) {
        continue;
      }

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
          notifier.notify(tracker.getNotification(), "Spell Reminder: " + tracker.getCustomMessage());
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

    resetThrallTimerIfEnteringANewInstance();
  }

  private void resetThrallTimerIfEnteringANewInstance() {
    WorldView wv = client.getTopLevelWorldView();
    if (wv == null)
    {
      return;
    }

    boolean inInstance = wv.isInstance();
    int[] regions = wv.getMapRegions();

    boolean isActive = thrallTracker.isActive();
    boolean hasSavedTimer = thrallTracker.hasResumeData();

    if (isActive || hasSavedTimer)
    {
      boolean enteredFromNonInstance = !wasInInstance;
      boolean changedInstance = wasInInstance && !regionsMatch(previousRegions, regions);

      if (enteredFromNonInstance || changedInstance)
      {
        if (regionsMatch(regions, thrallTracker.getSummonRegions()))
        {
          if (hasSavedTimer)
          {
            thrallTracker.resumeCountdown();
          }
        }
        else if (inInstance && !isInstanceResetIgnored(regions))
        {
          if (isActive)
          {
            thrallTracker.saveForResume(thrallTracker.getFinalTick());
            if (config.thrallNotifyOnInstanceEnter())
            {
              thrallTracker.stop();
            }
          }
        }
      }
    }

    wasInInstance = inInstance;
    previousRegions = inInstance ? regions.clone() : null;
  }

  private boolean regionsMatch(int[] a, int[] b)
  {
    if (a == null || b == null)
    {
      return false;
    }
    int[] sortedA = a.clone();
    int[] sortedB = b.clone();
    Arrays.sort(sortedA);
    Arrays.sort(sortedB);
    return Arrays.equals(sortedA, sortedB);
  }

  private boolean isInstanceResetIgnored(int[] regions)
  {
    for (int region : regions)
    {
      for (int ignored : INSTANCE_RESET_IGNORED_REGIONS)
      {
        if (region == ignored)
        {
          return true;
        }
      }
    }
    return false;
  }

  @Subscribe
  protected void onConfigChanged(ConfigChanged event) {
    if (!event.getGroup().equals(SpellReminderConfig.GROUP)) {
      return;
    }

    for (SpellTracker tracker : spellTrackers) {
      if (!event.getKey().equals(tracker.getReminderStyleConfigKey())) {
        continue;
      }

      boolean overlayWasActive = overlayFactory.isOverlayActive(tracker);
      boolean infoboxWasActive = infoboxFactory.isInfoboxActive(tracker);
      boolean wasActive = overlayWasActive || infoboxWasActive;

      overlayFactory.removeOverlay(tracker);
      infoboxFactory.removeInfobox(tracker);

      // Don't recreate an overlay or infobox if it wasn't active
      if (!wasActive) {
        continue;
      }

      if (tracker.getReminderStyle() == SpellReminderStyle.INFOBOX) {
          infoboxFactory.createInfobox(tracker);
      } else {
          overlayFactory.createOverlay(tracker);
      }
    }
  }
}
