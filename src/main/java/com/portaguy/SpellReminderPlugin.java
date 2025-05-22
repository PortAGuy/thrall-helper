package com.portaguy;

import com.google.inject.Provides;
import com.portaguy.trackers.*;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.HotkeyListener;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@PluginDescriptor(name = "Spell Reminder")
public class SpellReminderPlugin extends Plugin {
  protected ThrallHelperInfobox infobox;
  private final List<SpellTracker> spellTrackers = new ArrayList<>();
  private static final int RESURRECT_GREATER_GHOST_SPRITE_ID = 2979;

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
  protected Client client;

  @Inject
  protected EventBus eventBus;

  @Inject
  protected Notifier notifier;

  @Inject
  protected InfoBoxManager infoBoxManager;

  @Inject
  protected SpriteManager spriteManager;

  @Inject
  protected KeyManager keyManager;

  @Inject
  protected SpellReminderConfig config;

  @Inject
  protected SpellReminderOverlayFactory overlayFactory;

  @Provides
  SpellReminderConfig provideConfig(ConfigManager configManager) {
    return configManager.getConfig(SpellReminderConfig.class);
  }

  private final HotkeyListener hideReminderHotkeyListener = new HotkeyListener(() -> config.hideReminderHotkey()) {
    @Override
    public void hotkeyPressed() {
      overlayFactory.removeAllOverlays();
      infoBoxManager.removeInfoBox(infobox);
    }
  };

  @Override
  protected void startUp() {
    infobox = new ThrallHelperInfobox(this);
    spriteManager.getSpriteAsync(RESURRECT_GREATER_GHOST_SPRITE_ID, 0, infobox::setImage);

    spellTrackers.add(thrallTracker);
    spellTrackers.add(deathChargeTracker);
    spellTrackers.add(markOfDarknessTracker);
    spellTrackers.add(wardOfArceuusTracker);
    spellTrackers.add(shadowVeilTracker);
    spellTrackers.add(corruptionTracker);
    spellTrackers.add(chargeTracker);
    spellTrackers.add(vengeanceTracker);

    for (SpellTracker tracker : spellTrackers) {
      eventBus.register(tracker);
    }

    keyManager.registerKeyListener(hideReminderHotkeyListener);
  }

  @Override
  protected void shutDown() {
    for (SpellTracker tracker : spellTrackers) {
      eventBus.unregister(tracker);
    }

    overlayFactory.removeAllOverlays();
    infoBoxManager.removeInfoBox(infobox);
    keyManager.unregisterKeyListener(hideReminderHotkeyListener);
  }

  @Subscribe
  protected void onGameTick(GameTick ignored) {
    for (SpellTracker tracker : spellTrackers) {
      if (!tracker.isSpellTracked()) {
        continue;
      }

      if (tracker.isExpired()) {
        overlayFactory.createOverlay(tracker);
        if (tracker.shouldNotify()) {
          notifier.notify(tracker.getCustomMessage());
        }
      }

      if (tracker.isActive()) {
        overlayFactory.removeOverlay(tracker);
      }
    }
  }
}
