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
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.HotkeyListener;

@Slf4j
@PluginDescriptor(
	name = "Spell Helper"
)
public class SpellHelperPlugin extends Plugin
{
	private static final String RESURRECT_THRALL_MESSAGE_START = ">You resurrect a ";
	private static final String RESURRECT_THRALL_MESSAGE_END = " thrall.</col>";
	private static final String RESURRECT_THRALL_DISAPPEAR_MESSAGE_START = ">Your ";
	private static final String RESURRECT_THRALL_DISAPPEAR_MESSAGE_END = " thrall returns to the grave.</col>";
	private static final int SPELL_RESURRECT_GREATER_GHOST = 2979;
	private static final String SPELL_TARGET_REGEX = "<col=00ff00>Resurrect (Greater|Superior|Lesser) (Skeleton|Ghost|Zombie)</col>";
	private static final Pattern SPELL_TARGET_PATTERN = Pattern.compile(SPELL_TARGET_REGEX);
	private static final int SPELLBOOK_VARBIT = 4070;
	private static final int SUMMON_ANIMATION = 8973;
	private static final int[] SUMMON_GRAPHICS = {1873, 1874, 1875};
 	private static final int ARCEUUS_SPELLBOOK = 3;
	private static final Set<Integer> activeSpellSpriteIds = new HashSet<>(Arrays.asList(
			// Ghost, Skeleton, Zombie
			2980, 2982, 2984, 	// Greater
			2979, 2981, 2983,	// Superior
			1270, 1271, 1300	// Lesser
	));

	private ThrallHelperInfobox infobox;
	private Instant lastThrallExpiry;
	private boolean isSpellClicked = false;
	private Pattern reminderRegex;
	private Pattern hiderRegex;

	@Inject
	private Notifier notifier;

	@Inject
	private ThrallHelperOverlay overlay;

	@Inject
	private SpellHelperConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private InfoBoxManager infoBoxManager;

	@Inject
	private SpriteManager spriteManager;

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
			infoBoxManager.removeInfoBox(infobox);
			lastThrallExpiry = null;
		}
	};

	@Override
	protected void startUp() throws Exception
	{
        infobox = new ThrallHelperInfobox(this);
		spriteManager.getSpriteAsync(SPELL_RESURRECT_GREATER_GHOST, 0, infobox::setImage);
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
		infoBoxManager.removeInfoBox(infobox);
		keyManager.unregisterKeyListener(hideReminderHotkeyListener);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!SpellHelperConfig.GROUP.equals(event.getGroup())) return;

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

		if (event.getKey().equals("reminderStyle"))
		{
			if(event.getNewValue().equals("INFOBOX"))
			{
				if (overlayManager.anyMatch(entry -> entry instanceof ThrallHelperOverlay))
				{
					overlayManager.remove(overlay);
					infoBoxManager.addInfoBox(infobox);
				}
			} else {
				if (infoBoxManager.getInfoBoxes().contains(infobox))
				{
					overlayManager.add(overlay);
					infoBoxManager.removeInfoBox(infobox);
				}
			}
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (isSpellClicked && (client.getLocalPlayer().getAnimation() == SUMMON_ANIMATION) || checkGraphic())
		{
			overlayManager.remove(overlay);
			infoBoxManager.removeInfoBox(infobox);
		}

		if (lastThrallExpiry != null)
		{
			final Duration thrallOverlayTimeout = Duration.ofSeconds(config.thrallTimeoutSeconds());
			final Duration sinceThrallExpiry = Duration.between(lastThrallExpiry, Instant.now());

			if (sinceThrallExpiry.compareTo(thrallOverlayTimeout) >= 0)
			{
				overlayManager.remove(overlay);
				infoBoxManager.removeInfoBox(infobox);
				lastThrallExpiry = null;
			}
		}
		if (!(client.getVarbitValue(SPELLBOOK_VARBIT) == ARCEUUS_SPELLBOOK) && config.onlyArceuus())
		{
			overlayManager.remove(overlay);
			infoBoxManager.removeInfoBox(infobox);
			lastThrallExpiry = null;
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		final String message = event.getMessage();
		if (reminderRegex != null && (!config.matchGameMessagesOnly() || event.getType() == ChatMessageType.GAMEMESSAGE))
		{
			Matcher reminderMatcher = reminderRegex.matcher(message);
			if (reminderMatcher.matches())
			{
				if (config.reminderStyle() == SpellHelperStyle.INFOBOX) {
					if (!infoBoxManager.getInfoBoxes().contains(infobox)) {
						infoBoxManager.addInfoBox(infobox);
					}
				} else {
					overlayManager.add(overlay);
				}
				lastThrallExpiry = Instant.now();
				if (config.shouldNotify())
				{
					notifier.notify("You need to summon a thrall!");
				}
			}
		}

		if (hiderRegex != null && (!config.matchGameMessagesOnly() || event.getType() == ChatMessageType.GAMEMESSAGE))
		{
			Matcher hiderMatcher = hiderRegex.matcher(message);
			if (hiderMatcher.matches())
			{
				overlayManager.remove(overlay);
				infoBoxManager.removeInfoBox(infobox);
				isSpellClicked = false;
			}
		}

		if (message.contains(RESURRECT_THRALL_MESSAGE_START) && message.endsWith(RESURRECT_THRALL_MESSAGE_END))
		{
			overlayManager.remove(overlay);
			infoBoxManager.removeInfoBox(infobox);
			isSpellClicked = false;
		}
		if (message.contains(RESURRECT_THRALL_DISAPPEAR_MESSAGE_START) && message.endsWith((RESURRECT_THRALL_DISAPPEAR_MESSAGE_END)))
		{
			// If the spell has been cast there is no need to notify
			if (!isSpellClicked)
			{
				if (!config.onlyArceuus() || (config.onlyArceuus() && client.getVarbitValue(SPELLBOOK_VARBIT) == ARCEUUS_SPELLBOOK)) {
					if (config.reminderStyle() == SpellHelperStyle.INFOBOX) {
						if (!infoBoxManager.getInfoBoxes().contains(infobox)) {
							infoBoxManager.addInfoBox(infobox);
						}
					} else {
						overlayManager.add(overlay);
					}
					lastThrallExpiry = Instant.now();
					if (config.shouldNotify())
					{
						notifier.notify("You need to summon a thrall!");
					}
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

	private boolean checkGraphic()
	{
		for (int i : SUMMON_GRAPHICS) {
			if (client.getLocalPlayer().hasSpotAnim(i)) {
				return true;
			}
		}
		return false;
	}

	@Provides
	SpellHelperConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(SpellHelperConfig.class);
	}
}
