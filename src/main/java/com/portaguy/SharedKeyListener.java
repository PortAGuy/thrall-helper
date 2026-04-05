package com.portaguy;

import lombok.RequiredArgsConstructor;
import net.runelite.client.config.Keybind;
import net.runelite.client.input.KeyListener;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SharedKeyListener implements KeyListener {
  private final List<SpellTracker> trackers;
  private final SpellReminderOverlayFactory overlayFactory;
  private final SpellReminderInfoboxFactory infoboxFactory;

  // Hold the set of actively pressed SpellTracker hotkeys
  private final Set<SpellTracker> pressedTrackers = new HashSet<>();
  // Whether we should consume the keypress
  private boolean shouldConsume = false;

  private static boolean isNonModifierKey(KeyEvent e) {
    return Keybind.getModifierForKeyCode(e.getKeyCode()) == null;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // Gather the list of configured keybinds that match the key press
    List<SpellTracker> matchingTrackers = trackers.stream()
            .filter(tracker -> Optional.ofNullable(tracker.getHideReminderHotkey())
                    .map(keybind -> keybind.matches(e))
                    .orElse(false))
            .collect(Collectors.toList());

    // Add them to the list of pressed trackers and remove the overlays/infoboxes
    matchingTrackers.stream()
            .filter(pressedTrackers::add)
            .forEach(tracker -> {
              overlayFactory.removeOverlay(tracker);
              infoboxFactory.removeInfobox(tracker);
            });

    if (matchingTrackers.isEmpty()) {
      return;
    }

    if (isNonModifierKey(e)) {
      shouldConsume = true;
      e.consume();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    boolean didRemove = pressedTrackers.removeIf(tracker ->
            Optional.ofNullable(tracker.getHideReminderHotkey())
                    .map(keybind -> keybind.matches(e))
                    .orElse(false));

    // Reset shouldConsume if we released a hotkey press and have no active pressed trackers
    if (didRemove && pressedTrackers.isEmpty()) {
      shouldConsume = false;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (shouldConsume) {
      e.consume();
    }
  }

  @Override
  public void focusLost() {
    shouldConsume = false;
    pressedTrackers.clear();
  }
}
