package com.portaguy;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

public class ThrallHelperOverlay extends OverlayPanel
{
	// Grabbed this color from the Don't Eat It plugin hub plugin
	private static final Color DANGER = new Color(255, 0, 0, 150);

	private final ThrallHelperConfig config;
	private final Client client;

	@Inject
	private ThrallHelperOverlay(ThrallHelperConfig config, Client client)
	{
		this.config = config;
		this.client = client;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		panelComponent.getChildren().clear();

		panelComponent.getChildren().add((LineComponent.builder())
			.left("You need to summon a thrall!")
			.build());

		if (config.shouldFlash()) {
			if (client.getGameCycle() % 40 >= 20)
			{
				panelComponent.setBackgroundColor(getPreferredColor());
			} else
			{
				panelComponent.setBackgroundColor(DANGER);
			}
		} else {
			panelComponent.setBackgroundColor(DANGER);
		}

		setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
		return panelComponent.render(graphics);
	}
}
