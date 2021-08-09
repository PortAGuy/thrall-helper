package com.portaguy;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Thrall Helper"
)
public class ThrallHelperPlugin extends Plugin
{
	// Grabbed these from the timers plugin
	private static final String RESURRECT_THRALL_MESSAGE_START = ">You resurrect a ";
	private static final String RESURRECT_THRALL_MESSAGE_END = " thrall.</col>";
	private static final String RESURRECT_THRALL_DISAPPEAR_MESSAGE_START = ">Your ";
	private static final String RESURRECT_THRALL_DISAPPEAR_MESSAGE_END = " thrall returns to the grave.</col>";

	@Inject
	private Client client;

	@Inject
	private Notifier notifier;

	@Inject
	private ThrallHelperOverlay overlay;

	@Inject
	private ThrallHelperConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		final String message = event.getMessage();

		if (message.contains(RESURRECT_THRALL_MESSAGE_START) && message.endsWith(RESURRECT_THRALL_MESSAGE_END))
		{
			overlayManager.remove(overlay);
		}
		if (message.contains(RESURRECT_THRALL_DISAPPEAR_MESSAGE_START) && message.endsWith((RESURRECT_THRALL_DISAPPEAR_MESSAGE_END)))
		{
			overlayManager.add(overlay);
			if (config.shouldNotify())
			{
				notifier.notify("You need to summon a thrall!");
			}
		}

	}

	@Provides
	ThrallHelperConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ThrallHelperConfig.class);
	}
}
