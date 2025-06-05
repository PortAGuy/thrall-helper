package com.portaguy;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.ActorDeath;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.VarbitChanged;
import net.runelite.client.config.Notification;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;

import javax.inject.Inject;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Slf4j
@Getter
public abstract class SpellTracker {
  protected Spellbook spellbook;
  protected boolean removedOnDeath;
  protected Pattern notifyMessage;
  protected Pattern removeMessage;

  protected boolean active;
  protected boolean expired;
  protected int startTick;
  protected int finalTick;

  @Inject
  protected Client client;

  @Inject
  protected SpellReminderConfig config;

  public SpellTracker(Spellbook spellbook, boolean removedOnDeath) {
    this.spellbook = spellbook;
    this.removedOnDeath = removedOnDeath;
    this.notifyMessage = Pattern.compile("");
    this.removeMessage = Pattern.compile("");

    this.active = false;
    this.expired = false;
    this.startTick = -1;
    this.finalTick = Integer.MAX_VALUE;
  }

  public void start() {
    active = true;
    startTick = client.getTickCount();
    finalTick = Integer.MAX_VALUE;
  }

  public void start(int maxDuration) {
    active = true;
    startTick = client.getTickCount();
    finalTick = startTick + maxDuration;
  }

  public void stop() {
    this.active = false;
    this.expired = true;
  }

  public void initializePatterns() {
    final String notify = getNotifyPattern();
    try {
      notifyMessage = Pattern.compile(notify);
    } catch (PatternSyntaxException exception) {
      notifyMessage = Pattern.compile("");
    }

    final String remove = getRemovePattern();
    try {
      removeMessage = Pattern.compile(remove);
    } catch (PatternSyntaxException exception) {
      removeMessage = Pattern.compile("");
    }
  }

  @Subscribe
  protected void onConfigChanged(ConfigChanged event) {
    if (!event.getGroup().equals(SpellReminderConfig.GROUP)) {
      return;
    }

    initializePatterns();
  }

  @Subscribe
  protected void onActorDeath(ActorDeath event) {
    Player player = client.getLocalPlayer();
    if (player == null) {
      return;
    }

    if (event.getActor() instanceof Player) {
      Player actor = (Player) event.getActor();
      if (!actor.equals(player)) {
        return;
      }

      if (removedOnDeath && active) {
        stop();
      }
    }
  }

  @Subscribe
  protected void onVarbitChanged(VarbitChanged event) {
  }

  @Subscribe
  protected void onChatMessage(ChatMessage event) {
  }

  // Run this after the plugin's main game tick to reset a tracker
  @Subscribe(priority = -1)
  protected void onGameTick(GameTick ignored) {
    if (isExpired() || client.getTickCount() > finalTick) {
      active = false;
      expired = false;
      startTick = 0;
    }
  }

  /**
   * Checks if this spell should show notifications only when its spellbook is
   * active
   *
   * @return true if the spell should be tracked only on its spellbook, false
   *         otherwise
   */
  protected abstract boolean onlyOnSpellbook();

  /**
   * Checks if this spell should be tracked based on config settings
   *
   * @return true if the spell should be tracked, false otherwise
   */
  protected abstract boolean isSpellTracked();

  /**
   * Gets the custom regex pattern to notify about the tracker
   *
   * @return The custom remove notify expression
   */
  protected abstract String getNotifyPattern();

  /**
   * Gets the custom regex pattern to remove the tracker's overlays
   *
   * @return The custom remove regular expression
   */
  protected abstract String getRemovePattern();

  /**
   * Checks if this spell should show notifications based on config settings
   *
   * @return The custom notification for this spell
   */
  protected abstract Notification getNotification();

  /**
   * Gets the custom message to display when the spell expires
   *
   * @return The custom message for this spell
   */
  protected abstract String getCustomMessage();

  /**
   * Gets the overlay to display when the spell expires
   *
   * @return The custom overlay for this spell
   */
  protected abstract SpellReminderOverlay getOverlay();
}