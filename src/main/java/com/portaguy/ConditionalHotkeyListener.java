package com.portaguy;

import net.runelite.client.config.Keybind;
import net.runelite.client.util.HotkeyListener;

import java.awt.event.KeyEvent;
import java.util.function.Supplier;

/**
 * A {@link HotkeyListener} variant whose hotkey action returns a boolean.
 * The key event is only consumed when {@link #onHotkeyPressed()} returns {@code true},
 * allowing other listeners bound to the same key to still fire when this one does nothing.
 *
 * <p>Note: Java's type system prevents naming this method {@code hotkeyPressed()} since
 * the parent declares it as {@code void}; override {@link #onHotkeyPressed()} instead.</p>
 */
public abstract class ConditionalHotkeyListener extends HotkeyListener
{
  private final Supplier<Keybind> keybind;
  private boolean isPressed;
  private boolean isConsumingTyped;

  protected ConditionalHotkeyListener(Supplier<Keybind> keybind)
  {
    super(keybind);
    this.keybind = keybind;
  }

  /**
   * Called when the configured hotkey is pressed.
   *
   * @return {@code true} if the hotkey was acted upon and the key event should be consumed;
   *         {@code false} if nothing was done and the event should propagate to other listeners.
   */
  protected abstract boolean onHotkeyPressed();

  @Override
  public final void hotkeyPressed()
  {
    // Intentional no-op: logic is driven by onHotkeyPressed() in keyPressed() below.
  }

  @Override
  public void keyPressed(KeyEvent e)
  {
    if (keybind.get().matches(e))
    {
      boolean wasPressed = isPressed;
      isPressed = true;
      if (!wasPressed)
      {
        boolean consumed = onHotkeyPressed();
        if (consumed && Keybind.getModifierForKeyCode(e.getKeyCode()) == null)
        {
          isConsumingTyped = true;
          e.consume();
        }
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e)
  {
    if (keybind.get().matches(e))
    {
      isPressed = false;
      isConsumingTyped = false;
    }
  }

  @Override
  public void keyTyped(KeyEvent e)
  {
    if (isConsumingTyped)
    {
      e.consume();
    }
  }
}
