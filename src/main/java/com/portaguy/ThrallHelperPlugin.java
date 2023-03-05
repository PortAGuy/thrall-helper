package com.portaguy;

import com.google.inject.Provides;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
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
	private static final String SPELL_TARGET_REGEX = "<col=00ff00>Resurrect (Greater|Superior|Lesser) (Skeleton|Ghost|Zombie)</col>";
	private static final Pattern SPELL_TARGET_PATTERN = Pattern.compile(SPELL_TARGET_REGEX);
	private static final int SPELLBOOK_VARBIT = 4070;
	private static final int ARCEUUS_SPELLBOOK = 3;
	private static final Set<Integer> activeSpellSpriteIds = new HashSet<>(Arrays.asList(
			// Ghost, Skeleton, Zombie
			2980, 2982, 2984, 	// Greater
			2979, 2981, 2983,	// Superior
			1270, 1271, 1300	// Lesser
	));

	private Instant last_thrall_expiry;
	private boolean isSpellClicked = false;
	private Pattern reminderRegex;
	private Pattern hiderRegex;

	@Inject
	private Notifier notifier;

	@Inject
	private ThrallHelperOverlay overlay;

	@Inject
	private ThrallHelperConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private Client client;

	@Inject
	KeyManager keyManager;

	private final HotkeyListener hideReminderHotkeyListener = new HotkeyListener(() -> config.hideReminderHotkey())
	{
		@Override
		public void hotkeyPressed()
		{
			overlayManager.remove(overlay);
			last_thrall_expiry = null;
		}
	};

	@Override
	protected void startUp() throws Exception
	{
		keyManager.registerKeyListener(hideReminderHotkeyListener);
		if (!config.reminderRegex().isEmpty())
		{
			reminderRegex = compilePattern(config.reminderRegex());
		} else {
			reminderRegex = null;
		}

		if (!config.hiderRegex().isEmpty())
		{
			hiderRegex = compilePattern(config.hiderRegex());
		} else {
			hiderRegex = null;
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		keyManager.unregisterKeyListener(hideReminderHotkeyListener);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!ThrallHelperConfig.GROUP.equals(event.getGroup())) return;

		if (event.getKey().equals("reminderRegex"))
		{
			if (event.getNewValue() != null && !event.getNewValue().isEmpty())
			{
				reminderRegex = compilePattern(event.getNewValue());
				return;
			} else {
				reminderRegex = null;
			}

		}

		if (event.getKey().equals("hiderRegex"))
		{
			if (event.getNewValue() != null && !event.getNewValue().isEmpty())
			{
				hiderRegex = compilePattern(event.getNewValue());
			} else {
				hiderRegex = null;
			}

		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (last_thrall_expiry != null)
		{
			final Duration thrall_overlay_timeout = Duration.ofSeconds(config.thrallTimeoutSeconds());
			final Duration since_thrall_expiry = Duration.between(last_thrall_expiry, Instant.now());

			if (since_thrall_expiry.compareTo(thrall_overlay_timeout) >= 0)
			{
				overlayManager.remove(overlay);
				last_thrall_expiry = null;
			}
		}
		if (!(client.getVarbitValue(SPELLBOOK_VARBIT) == ARCEUUS_SPELLBOOK) && config.onlyArceuus()) {
			overlayManager.remove(overlay);
			last_thrall_expiry = null;
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		final String message = event.getMessage();
		if (reminderRegex != null)
		{
			Matcher reminderMatcher = reminderRegex.matcher(message);
			if (reminderMatcher.matches())
			{
				overlayManager.add(overlay);
				last_thrall_expiry = Instant.now();
				if (config.shouldNotify())
				{
					notifier.notify("You need to summon a thrall!");
				}
			}
		}

		if (hiderRegex != null)
		{
			Matcher hiderMatcher = hiderRegex.matcher(message);
			if (hiderMatcher.matches())
			{
				overlayManager.remove(overlay);
				isSpellClicked = false;
			}
		}

		if (message.contains(RESURRECT_THRALL_MESSAGE_START) && message.endsWith(RESURRECT_THRALL_MESSAGE_END))
		{
			overlayManager.remove(overlay);
			isSpellClicked = false;
		}
		if (message.contains(RESURRECT_THRALL_DISAPPEAR_MESSAGE_START) && message.endsWith((RESURRECT_THRALL_DISAPPEAR_MESSAGE_END)))
		{
			// If the spell has been cast there is no need to notify
			if (!isSpellClicked)
			{
				overlayManager.add(overlay);
				last_thrall_expiry = Instant.now();
				if (config.shouldNotify())
				{
					notifier.notify("You need to summon a thrall!");
				}
			}
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		// Check the menu option clicked is one of the resurrection spells
		Matcher matcher = SPELL_TARGET_PATTERN.matcher(event.getMenuTarget());
		if (!matcher.matches())
		{
			return;
		}
		// If the user doesn't have the book then they can't cast the spell
		if (!hasBookOfTheDead())
		{
			return;
		}
		Widget widget = event.getWidget();
		if (widget == null)
		{
			return;
		}
		// In the 10-second cool down where the spell can't recast the opacity changes from 0 to 150
		if (activeSpellSpriteIds.contains(widget.getSpriteId()) && widget.getOpacity() == 0)
		{
			isSpellClicked = true;
		}
	}

	private Pattern compilePattern(String pattern)
	{
		try
		{
			return Pattern.compile(pattern);
		}
		catch (PatternSyntaxException e)
		{
			return null;
		}
	}

	private boolean hasBookOfTheDead()
	{
		ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
		ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);
		if (inventory == null || equipment == null)
		{
			return false;
		}
		return inventory.contains(ItemID.BOOK_OF_THE_DEAD) || equipment.contains(ItemID.BOOK_OF_THE_DEAD);
	}

	@Provides
	ThrallHelperConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ThrallHelperConfig.class);
	}
}
