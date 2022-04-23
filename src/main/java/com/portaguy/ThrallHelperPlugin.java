package com.portaguy;

import com.google.inject.Provides;
import java.time.Duration;
import java.time.Instant;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.HotkeyListener;

@Slf4j
@PluginDescriptor(
	name = "Thrall Helper"
)
public class ThrallHelperPlugin extends Plugin
{
	private static final String RESURRECT_THRALL_MESSAGE_START = ">You resurrect a ";
	private static final String RESURRECT_THRALL_MESSAGE_END = " thrall.</col>";
	private static final String RESURRECT_THRALL_DISAPPEAR_MESSAGE_START = ">Your ";
	private static final String RESURRECT_THRALL_DISAPPEAR_MESSAGE_END = " thrall returns to the grave.</col>";
	private Instant last_thrall_summoned;

	@Inject
	private Notifier notifier;

	@Inject
	private ThrallHelperOverlay overlay;

	@Inject
	private ThrallHelperConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	KeyManager keyManager;

	private final HotkeyListener muteReminderHotkeyListener = new HotkeyListener(() -> config.muteReminderHotkey())
	{
		@Override
		public void hotkeyPressed()
		{
			overlayManager.remove(overlay);
			last_thrall_summoned = null;
		}
	};

	@Override
	protected void startUp() throws Exception
	{
		keyManager.registerKeyListener(muteReminderHotkeyListener);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (last_thrall_summoned != null)
		{
			final Duration thrall_timeout = Duration.ofMinutes(config.thrallTimeout() + 1);
			final Duration since_summon = Duration.between(last_thrall_summoned, Instant.now());

			if (since_summon.compareTo(thrall_timeout) >= 0)
			{
				overlayManager.remove(overlay);
				last_thrall_summoned = null;
			}
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		final String message = event.getMessage();

		if (message.contains(RESURRECT_THRALL_MESSAGE_START) && message.endsWith(RESURRECT_THRALL_MESSAGE_END))
		{
			overlayManager.remove(overlay);
			last_thrall_summoned = Instant.now();
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
