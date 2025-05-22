package com.portaguy;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.InfoBox;

import java.awt.*;

public class ThrallHelperInfobox extends InfoBox {

  public ThrallHelperInfobox(Plugin plugin) {
    super(null, plugin);
  }

  @Override
  public String getText() {
    return null;
  }

  @Override
  public Color getTextColor() {
    return null;
  }

  @Override
  public String getTooltip() {
    return "You need to summon a thrall!";
  }
}
