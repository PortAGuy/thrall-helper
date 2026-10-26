package com.portaguy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SpellbookTimersTest
{
	@Test
	public void formatsTicksAsRoundedUpSeconds()
	{
		assertEquals("1s", SpellbookTimers.format(1));
		assertEquals("11s", SpellbookTimers.format(SpellbookTimers.THRALL_COOLDOWN_TICKS));
		assertEquals("59s", SpellbookTimers.format(98));
	}

	@Test
	public void formatsMinuteAndOverAsMinutesAndSeconds()
	{
		assertEquals("1:00", SpellbookTimers.format(SpellbookTimers.DEATH_CHARGE_COOLDOWN_TICKS));
		assertEquals("1:59", SpellbookTimers.format(198));
	}
}
