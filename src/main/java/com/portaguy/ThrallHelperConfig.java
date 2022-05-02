package com.portaguy;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;
import net.runelite.client.config.Units;

@ConfigGroup("thrallhelper")
public interface ThrallHelperConfig extends Config
{
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
		description = "Makes the reminder box flash.",
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
}
