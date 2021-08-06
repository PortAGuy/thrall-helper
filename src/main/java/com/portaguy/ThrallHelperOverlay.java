package com.portaguy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;

public class ThrallHelperOverlay extends OverlayPanel
{
	// Grabbed this color from the Don't Eat It plugin hub plugin
	private static final Color DANGER = new Color(255, 0, 0, 150);

	private final ThrallHelperConfig config;

	@Inject
	private ThrallHelperOverlay(ThrallHelperConfig config)
	{
		this.config = config;

		panelComponent.getChildren().add((LineComponent.builder())
			.left("You need to summon a thrall!")
			.build());

		panelComponent.setBackgroundColor(DANGER);

		setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
		setClearChildren(false);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		return super.render(graphics);
	}
}
