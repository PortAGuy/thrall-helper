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

import javax.inject.Inject;

@Slf4j
public abstract class SpellTracker {
  @Inject
  protected Client client;

  @Getter
  protected boolean active = false;

  @Getter
  protected boolean expired = false;

  protected int startTick;
  protected int finalTick;
  protected boolean removedOnDeath;

  public SpellTracker(boolean removedOnDeath) {
    this.removedOnDeath = removedOnDeath;
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
   * Checks if this spell should be tracked based on config settings
   *
   * @return true if the spell should be tracked, false otherwise
   */
  protected abstract boolean isSpellTracked();

  /**
   * Checks if this spell should show notifications based on config settings
   *
   * @return The custom notification for this spell
   */
  protected abstract Notification getCustomNotification();

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