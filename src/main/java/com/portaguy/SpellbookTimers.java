package com.portaguy;

import com.portaguy.trackers.ThrallTracker;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Tracks how long thralls and Death Charge stay up, and when each can be cast again,
 * for the timers drawn on the spellbook icons.
 */
@Singleton
public class SpellbookTimers {
  // Cooldowns match RuneLite's Timers & Buffs plugin.
  static final int THRALL_COOLDOWN_TICKS = 17;
  static final int DEATH_CHARGE_COOLDOWN_TICKS = 100; // 60 seconds

  private static final int NOT_RUNNING = -1;

  private final Client client;

  // The plugin's registered tracker (trackers aren't singletons, so it is handed over
  // rather than injected).
  @Setter
  private ThrallTracker thrallTracker;

  private int thrallCooldownEnd = NOT_RUNNING;
  private int deathChargeActiveEnd = NOT_RUNNING;
  private int deathChargeCooldownEnd = NOT_RUNNING;

  @Inject
  SpellbookTimers(Client client) {
    this.client = client;
  }

  @Subscribe
  public void onVarbitChanged(VarbitChanged event) {
    final int tick = client.getTickCount();
    switch (event.getVarbitId()) {
      case VarbitID.ARCEUUS_RESURRECTION_COOLDOWN:
        thrallCooldownEnd = event.getValue() == 1 ? tick + THRALL_COOLDOWN_TICKS : NOT_RUNNING;
        break;
      case VarbitID.ARCEUUS_DEATH_CHARGE_ACTIVE:
        // Death Charge lasts one tick per real Magic level.
        deathChargeActiveEnd = event.getValue() == 1
            ? tick + client.getRealSkillLevel(Skill.MAGIC) : NOT_RUNNING;
        break;
      case VarbitID.ARCEUUS_DEATH_CHARGE_COOLDOWN:
        deathChargeCooldownEnd = event.getValue() == 1 ? tick + DEATH_CHARGE_COOLDOWN_TICKS : NOT_RUNNING;
        break;
      default:
        break;
    }
  }

  public void reset() {
    thrallCooldownEnd = NOT_RUNNING;
    deathChargeActiveEnd = NOT_RUNNING;
    deathChargeCooldownEnd = NOT_RUNNING;
  }

  /** Ticks until the current thrall despawns, or 0 if none is up. */
  public int thrallTicksLeft() {
    if (thrallTracker == null || !thrallTracker.isActive() || thrallTracker.getFinalTick() == Integer.MAX_VALUE) {
      return 0;
    }
    return remaining(thrallTracker.getFinalTick());
  }

  public int thrallCooldownTicksLeft() {
    return remaining(thrallCooldownEnd);
  }

  public int deathChargeTicksLeft() {
    return remaining(deathChargeActiveEnd);
  }

  public int deathChargeCooldownTicksLeft() {
    return remaining(deathChargeCooldownEnd);
  }

  private int remaining(int endTick) {
    return endTick == NOT_RUNNING ? 0 : Math.max(0, endTick - client.getTickCount());
  }

  /** Formats game ticks as seconds ("42s") or minutes ("1:05"), rounding up. */
  static String format(int ticks) {
    final int seconds = (ticks * 6 + 9) / 10; // ticks * 0.6, rounded up
    return seconds >= 60 ? String.format("%d:%02d", seconds / 60, seconds % 60) : seconds + "s";
  }
}
