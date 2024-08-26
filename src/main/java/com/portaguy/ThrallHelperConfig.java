package com.portaguy;

import java.awt.Color;
import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.Units;

@ConfigGroup("thrallhelper")
public interface ThrallHelperConfig extends Config
{
	String GROUP = "thrallhelper";
	@ConfigItem(
		keyName = "shouldNotify",
		name = "Notify when thrall expires",
		description = "Sends a notification once the thrall needs to be summoned.",
		position = 1
	)
	default boolean shouldNotify()
	{
		return true;
	}

	@ConfigItem(
		keyName = "thrallTimeoutSeconds",
		name = "Timeout Thrall Box",
		description = "The duration in seconds before the thrall box disappears.",
		position = 2
	)
	@Units(Units.SECONDS)
	default int thrallTimeoutSeconds()
	{
		return 120;
	}

	@ConfigItem(
		keyName = "shouldFlash",
		name = "Flash the Reminder Box",
		description = "Makes the reminder box flash between the defined colors.",
		position = 3
	)
	default boolean shouldFlash() { return false; }

	@ConfigItem(
		keyName = "onlyArceuus",
		name = "Only on Arceuus Spellbook",
		description = "Only display the reminder box when on the Arceuus spellbook.",
		position = 4
	)
	default boolean onlyArceuus() { return false; }

	@ConfigItem(
			keyName = "hideReminderHotkey",
			name = "Hide Reminder Hotkey",
			description = "Use this hotkey to hide the reminder box.",
			position = 5
	)
	default Keybind hideReminderHotkey()
	{
		return Keybind.NOT_SET;
	}

	@Alpha
	@ConfigItem(
			keyName = "flashColor1",
			name = "Flash Color #1",
			description = "The first color to flash between, also controls the non-flashing color.",
			position = 6
	)
	default Color flashColor1() { return new Color(255, 0, 0, 150); }

	@Alpha
	@ConfigItem(
			keyName = "flashColor2",
			name = "Flash Color #2",
			description = "The second color to flash between.",
			position = 7
	)
	default Color flashColor2() { return new Color(70, 61, 50, 150); }

	@ConfigItem(
			keyName= "reminderRegex",
			name = "Remind on Regex",
			description = "Displays the reminder upon a chat message matching the regex",
			position = 8
	)
	default String reminderRegex() { return ""; }

	@ConfigItem(
			keyName = "hiderRegex",
			name = "Hide on Regex",
			description = "Hides the reminder (if active) upon a chat message matching the regex",
			position = 9
	)
	default String hiderRegex() { return ""; }

	@ConfigItem(
			keyName = "matchGameMessagesOnly",
			name = "Only match game messages",
			description = "Only attempt to match game messages with the regex.",
			position = 10
	)
	default boolean matchGameMessagesOnly() { return false; }

	@ConfigItem(
			keyName = "reminderStyle",
			name = "Reminder style",
			description = "Changes the style of the reminder box",
			position = 11
	)

	default ThrallHelperStyle reminderStyle() { return ThrallHelperStyle.LONG_TEXT; }
}
