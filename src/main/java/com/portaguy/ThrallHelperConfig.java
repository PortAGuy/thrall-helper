package com.portaguy;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Units;

@ConfigGroup("thrallhelper")
public interface ThrallHelperConfig extends Config
{
	@ConfigItem(
		keyName = "shouldNotify",
		name = "Notify when thrall expires",
		description = "This will send a notification when the thrall needs to be summoned"
	)
	default boolean shouldNotify()
	{
		return true;
	}

	@ConfigItem(
		keyName = "thrallTimeout",
		name = "Timeout Thrall Box",
		description = "The duration of time before the thrall box disappears"
	)
	@Units(Units.MINUTES)
	default int thrallTimeout()
	{
		return 5;
	}

}
