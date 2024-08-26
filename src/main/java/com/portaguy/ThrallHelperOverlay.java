package com.portaguy;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

public class ThrallHelperOverlay extends OverlayPanel
{

	private final ThrallHelperConfig config;
	private final Client client;

	private final String LONG_TEXT = "You need to summon a thrall!";
	private final String SHORT_TEXT = "Thrall";

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

		switch (config.reminderStyle()) {
			case LONG_TEXT:
				panelComponent.getChildren().add((LineComponent.builder())
						.left(LONG_TEXT)
						.build());

				panelComponent.setPreferredSize(new Dimension(graphics.getFontMetrics().stringWidth(LONG_TEXT) - 20, 0));
				break;
			case SHORT_TEXT:
				panelComponent.getChildren().add((LineComponent.builder())
						.left(SHORT_TEXT)
						.build());
				panelComponent.setPreferredSize(new Dimension(graphics.getFontMetrics().stringWidth(SHORT_TEXT) + 10, 0));
				break;
		}

		if (config.shouldFlash()) {
			if (client.getGameCycle() % 40 >= 20)
			{
				panelComponent.setBackgroundColor(config.flashColor1());
			} else
			{
				panelComponent.setBackgroundColor(config.flashColor2());
			}
		} else {
			panelComponent.setBackgroundColor(config.flashColor1());
		}

		setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
		return panelComponent.render(graphics);
	}
}
