package com.portaguy;

import net.runelite.client.config.*;

import java.awt.*;

@ConfigGroup(SpellReminderConfig.GROUP)
public interface SpellReminderConfig extends Config {
  String GROUP = "thrallhelper";

  // Sections

  @ConfigSection(
      name = "Thralls",
      description = "Thrall Reminder Settings",
      position = 0,
      closedByDefault = true
  )
  String THRALL_SECTION = "thrallSection";

  @ConfigSection(
      name = "Death Charge",
      description = "Death Charge Reminder Settings",
      position = 1,
      closedByDefault = true
  )
  String DEATH_CHARGE_SECTION = "deathChargeSection";

  @ConfigSection(
      name = "Mark of Darkness",
      description = "Mark of Darkness Reminder Settings",
      position = 2,
      closedByDefault = true
  )
  String MARK_OF_DARKNESS_SECTION = "markOfDarknessSection";

  @ConfigSection(
      name = "Ward of Arceuus",
      description = "Ward of Arceuus Reminder Settings",
      position = 3,
      closedByDefault = true
  )
  String WARD_OF_ARCEUUS_SECTION = "wardOfArceuusSection";

  @ConfigSection(
      name = "Shadow Veil",
      description = "Shadow Veil Reminder Settings",
      position = 4,
      closedByDefault = true
  )
  String SHADOW_VEIL_SECTION = "shadowVeilSection";

  @ConfigSection(
      name = "Corruption",
      description = "Corruption Reminder Settings",
      position = 5,
      closedByDefault = true
  )
  String CORRUPTION_SECTION = "corruptionSection";

  @ConfigSection(
      name = "Charge",
      description = "Charge Reminder Settings",
      position = 7,
      closedByDefault = true
  )
  String CHARGE_SECTION = "chargeSection";

  @ConfigSection(
      name = "Vengeance",
      description = "Vengeance Reminder Settings",
      position = 8,
      closedByDefault = true
  )
  String VENGEANCE_SECTION = "vengeanceSection";

  @ConfigSection(
      name = "Vile Vigour",
      description = "Vile Vigour Reminder Settings",
      position = 9,
      closedByDefault = true
  )
  String VILE_VIGOUR_SECTION = "vileVigourSection";

  /*
   * Thralls
   * Note: These do not have a "thrall" keyName as they were created when the
   * plugin
   * was simply "Thrall Helper" and have been preserved to keep user settings.
   */

  @ConfigItem(
      keyName = "thrallEnabled",
      name = "Thrall Enabled",
      description = "Enables reminders for Thralls.",
      position = 0,
      section = THRALL_SECTION
  )
  default boolean thrallEnabled() {
    return true;
  }

  @ConfigItem(
      keyName = "shouldNotify",
      name = "Notification on Reminder",
      description = "Sends a notification once the thrall needs to be summoned.",
      position = 1,
      section = THRALL_SECTION
  )
  default Notification shouldNotify() {
    return Notification.ON;
  }

  @ConfigItem(
      keyName = "thrallTimeoutSeconds",
      name = "Reminder Box Timeout",
      description = "The duration in seconds before the reminder box disappears.",
      position = 2,
      section = THRALL_SECTION
  )
  @Units(Units.SECONDS)
  default int thrallTimeoutSeconds() {
    return 120;
  }

  @ConfigItem(
      keyName = "onlyArceuus",
      name = "Only on Arceuus Spellbook",
      description = "Only display the reminder box when on the Arceuus spellbook.",
      position = 3,
      section = THRALL_SECTION
  )
  default boolean onlyArceuus() {
    return false;
  }

  @ConfigItem(
      keyName = "hideReminderHotkey",
      name = "Hide Reminder Hotkey",
      description = "Sets a hotkey to instantly hide the reminder box.",
      position = 4,
      section = THRALL_SECTION
  )
  default Keybind hideReminderHotkey() {
    return Keybind.NOT_SET;
  }

  @ConfigItem(
      keyName = "reminderStyle",
      name = "Reminder style",
      description = "Changes the style of the reminder box",
      position = 5,
      section = THRALL_SECTION
  )
  default SpellReminderStyle reminderStyle() {
    return SpellReminderStyle.LONG_TEXT;
  }

  @ConfigItem(
      keyName = "customText",
      name = "Custom Text",
      description = "Changes the text in the reminder box if the style is set to custom text",
      position = 6,
      section = THRALL_SECTION
  )
  default String customText() {
    return "You need to summon a thrall!";
  }

  @ConfigItem(
      keyName = "shouldFlash",
      name = "Flash the Reminder Box",
      description = "Makes the reminder box flash between the defined colors.",
      position = 7,
      section = THRALL_SECTION
  )
  default boolean shouldFlash() {
    return false;
  }

  @Alpha
  @ConfigItem(
      keyName = "flashColor1",
      name = "Flash Color #1",
      description = "The first color to flash between, also controls the non-flashing color.",
      position = 8,
      section = THRALL_SECTION
  )
  default Color flashColor1() {
    return new Color(255, 0, 0, 150);
  }

  @Alpha
  @ConfigItem(
      keyName = "flashColor2",
      name = "Flash Color #2",
      description = "The second color to flash between.",
      position = 9,
      section = THRALL_SECTION
  )
  default Color flashColor2() {
    return new Color(70, 61, 50, 150);
  }

  @ConfigItem(
      keyName = "reminderRegex",
      name = "Remind on Regex",
      description = "Displays the reminder box upon a chat message matching the regex.",
      position = 10,
      section = THRALL_SECTION
  )
  default String reminderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "hiderRegex",
      name = "Hide on Regex",
      description = "Hides the reminder (if active) upon a chat message matching the regex.",
      position = 11,
      section = THRALL_SECTION
  )
  default String hiderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "matchGameMessagesOnly",
      name = "Only Match Game Messages",
      description = "Only attempt to match game messages with the regex.",
      position = 12,
      section = THRALL_SECTION
  )
  default boolean matchGameMessagesOnly() {
    return false;
  }

  // Death Charge

  @ConfigItem(
      keyName = "deathChargeEnabled",
      name = "Death Charge Enabled",
      description = "Enables reminders for Death Charge.",
      position = 0,
      section = DEATH_CHARGE_SECTION
  )
  default boolean deathChargeEnabled() {
    return true;
  }

  @ConfigItem(
      keyName = "deathChargeShouldNotify",
      name = "Notification on Reminder",
      description = "Sends a notification when Death Charge can be recast.",
      position = 1,
      section = DEATH_CHARGE_SECTION
  )
  default Notification deathChargeShouldNotify() {
    return Notification.ON;
  }

  @ConfigItem(
      keyName = "deathChargeTimeoutSeconds",
      name = "Reminder Box Timeout",
      description = "The duration in seconds before the reminder box disappears.",
      position = 2,
      section = DEATH_CHARGE_SECTION
  )
  @Units(Units.SECONDS)
  default int deathChargeTimeoutSeconds() {
    return 120;
  }

  @ConfigItem(
      keyName = "deathChargeOnlyArceuus",
      name = "Only on Arceuus Spellbook",
      description = "Only display the reminder box when on the Arceuus spellbook.",
      position = 3,
      section = DEATH_CHARGE_SECTION
  )
  default boolean deathChargeOnlyArceuus() {
    return false;
  }

  @ConfigItem(
      keyName = "deathChargeHideReminderHotkey",
      name = "Hide Reminder Hotkey",
      description = "Sets a hotkey to instantly hide the reminder box.",
      position = 4,
      section = DEATH_CHARGE_SECTION
  )
  default Keybind deathChargeHideReminderHotkey() {
    return Keybind.NOT_SET;
  }

  @ConfigItem(
      keyName = "deathChargeReminderStyle",
      name = "Reminder style",
      description = "Changes the style of the reminder box",
      position = 5,
      section = DEATH_CHARGE_SECTION
  )
  default SpellReminderStyle deathChargeReminderStyle() {
    return SpellReminderStyle.LONG_TEXT;
  }

  @ConfigItem(
      keyName = "deathChargeCustomText",
      name = "Custom Text",
      description = "Changes the text in the reminder box if the style is set to custom text",
      position = 6,
      section = DEATH_CHARGE_SECTION
  )
  default String deathChargeCustomText() {
    return "You need to death charge!";
  }

  @ConfigItem(
      keyName = "deathChargeShouldFlash",
      name = "Flash the Reminder Box",
      description = "Makes the reminder box flash between the defined colors.",
      position = 7,
      section = DEATH_CHARGE_SECTION
  )
  default boolean deathChargeShouldFlash() {
    return false;
  }

  @Alpha
  @ConfigItem(
      keyName = "deathChargeFlashColor1",
      name = "Flash Color #1",
      description = "The first color to flash between, also controls the non-flashing color.",
      position = 8,
      section = DEATH_CHARGE_SECTION
  )
  default Color deathChargeFlashColor1() {
    return new Color(255, 0, 0, 150);
  }

  @Alpha
  @ConfigItem(
      keyName = "deathChargeFlashColor2",
      name = "Flash Color #2",
      description = "The second color to flash between.",
      position = 9,
      section = DEATH_CHARGE_SECTION
  )
  default Color deathChargeFlashColor2() {
    return new Color(70, 61, 50, 150);
  }

  @ConfigItem(
      keyName = "deathChargeReminderRegex",
      name = "Remind on Regex",
      description = "Displays the reminder box upon a chat message matching the regex.",
      position = 10,
      section = DEATH_CHARGE_SECTION
  )
  default String deathChargeReminderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "deathChargeHiderRegex",
      name = "Hide on Regex",
      description = "Hides the reminder (if active) upon a chat message matching the regex.",
      position = 11,
      section = DEATH_CHARGE_SECTION
  )
  default String deathChargeHiderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "deathChargeMatchGameMessagesOnly",
      name = "Only Match Game Messages",
      description = "Only attempt to match game messages with the regex.",
      position = 12,
      section = DEATH_CHARGE_SECTION
  )
  default boolean deathChargeMatchGameMessagesOnly() {
    return false;
  }

  // Mark of Darkness

  @ConfigItem(
      keyName = "markOfDarknessEnabled",
      name = "Mark of Darkness Enabled",
      description = "Enables reminders for Mark of Darkness.",
      position = 0,
      section = MARK_OF_DARKNESS_SECTION
  )
  default boolean markOfDarknessEnabled() {
    return true;
  }

  @ConfigItem(
      keyName = "markOfDarknessShouldNotify",
      name = "Notification on Reminder",
      description = "Sends a notification when Mark of Darkness wears off.",
      position = 1,
      section = MARK_OF_DARKNESS_SECTION
  )
  default Notification markOfDarknessShouldNotify() {
    return Notification.ON;
  }

  @ConfigItem(
      keyName = "markOfDarknessTimeoutSeconds",
      name = "Reminder Box Timeout",
      description = "The duration in seconds before the reminder box disappears.",
      position = 2,
      section = MARK_OF_DARKNESS_SECTION
  )
  @Units(Units.SECONDS)
  default int markOfDarknessTimeoutSeconds() {
    return 120;
  }

  @ConfigItem(
      keyName = "markOfDarknessOnlyArceuus",
      name = "Only on Arceuus Spellbook",
      description = "Only display the reminder box when on the Arceuus spellbook.",
      position = 3,
      section = MARK_OF_DARKNESS_SECTION
  )
  default boolean markOfDarknessOnlyArceuus() {
    return false;
  }

  @ConfigItem(
      keyName = "markOfDarknessHideReminderHotkey",
      name = "Hide Reminder Hotkey",
      description = "Sets a hotkey to instantly hide the reminder box.",
      position = 4,
      section = MARK_OF_DARKNESS_SECTION
  )
  default Keybind markOfDarknessHideReminderHotkey() {
    return Keybind.NOT_SET;
  }

  @ConfigItem(
      keyName = "markOfDarknessReminderStyle",
      name = "Reminder style",
      description = "Changes the style of the reminder box",
      position = 5,
      section = MARK_OF_DARKNESS_SECTION
  )
  default SpellReminderStyle markOfDarknessReminderStyle() {
    return SpellReminderStyle.LONG_TEXT;
  }

  @ConfigItem(
      keyName = "markOfDarknessCustomText",
      name = "Custom Text",
      description = "Changes the text in the reminder box if the style is set to custom text",
      position = 6,
      section = MARK_OF_DARKNESS_SECTION
  )
  default String markOfDarknessCustomText() {
    return "You need to Mark of Darkness!";
  }

  @ConfigItem(
      keyName = "markOfDarknessShouldFlash",
      name = "Flash the Reminder Box",
      description = "Makes the reminder box flash between the defined colors.",
      position = 7,
      section = MARK_OF_DARKNESS_SECTION
  )
  default boolean markOfDarknessShouldFlash() {
    return false;
  }

  @Alpha
  @ConfigItem(
      keyName = "markOfDarknessFlashColor1",
      name = "Flash Color #1",
      description = "The first color to flash between, also controls the non-flashing color.",
      position = 8,
      section = MARK_OF_DARKNESS_SECTION
  )
  default Color markOfDarknessFlashColor1() {
    return new Color(255, 0, 0, 150);
  }

  @Alpha
  @ConfigItem(
      keyName = "markOfDarknessFlashColor2",
      name = "Flash Color #2",
      description = "The second color to flash between.",
      position = 9,
      section = MARK_OF_DARKNESS_SECTION
  )
  default Color markOfDarknessFlashColor2() {
    return new Color(70, 61, 50, 150);
  }

  @ConfigItem(
      keyName = "markOfDarknessReminderRegex",
      name = "Remind on Regex",
      description = "Displays the reminder box upon a chat message matching the regex.",
      position = 10,
      section = MARK_OF_DARKNESS_SECTION
  )
  default String markOfDarknessReminderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "markOfDarknessHiderRegex",
      name = "Hide on Regex",
      description = "Hides the reminder (if active) upon a chat message matching the regex.",
      position = 11,
      section = MARK_OF_DARKNESS_SECTION
  )
  default String markOfDarknessHiderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "markOfDarknessMatchGameMessagesOnly",
      name = "Only Match Game Messages",
      description = "Only attempt to match game messages with the regex.",
      position = 12,
      section = MARK_OF_DARKNESS_SECTION
  )
  default boolean markOfDarknessMatchGameMessagesOnly() {
    return false;
  }

  // Ward of Arceuus

  @ConfigItem(
      keyName = "wardOfArceuusEnabled",
      name = "Ward of Arceuus Enabled",
      description = "Enables reminders for Ward of ARceuus.",
      position = 0,
      section = WARD_OF_ARCEUUS_SECTION
  )
  default boolean wardOfArceuusEnabled() {
    return true;
  }

  @ConfigItem(
      keyName = "wardOfArceuusShouldNotify",
      name = "Notification on Reminder",
      description = "Sends a notification once Ward of Arceuus wears off.",
      position = 1,
      section = WARD_OF_ARCEUUS_SECTION
  )
  default Notification wardOfArceuusShouldNotify() {
    return Notification.ON;
  }

  @ConfigItem(
      keyName = "wardOfArceuusTimeoutSeconds",
      name = "Reminder Box Timeout",
      description = "The duration in seconds before the reminder box disappears.",
      position = 2,
      section = WARD_OF_ARCEUUS_SECTION
  )
  @Units(Units.SECONDS)
  default int wardOfArceuusTimeoutSeconds() {
    return 120;
  }

  @ConfigItem(
      keyName = "wardOfArceuusOnlyArceuus",
      name = "Only on Arceuus Spellbook",
      description = "Only display the reminder box when on the Arceuus spellbook.",
      position = 3,
      section = WARD_OF_ARCEUUS_SECTION
  )
  default boolean wardOfArceuusOnlyArceuus() {
    return false;
  }

  @ConfigItem(
      keyName = "wardOfArceuusHideReminderHotkey",
      name = "Hide Reminder Hotkey",
      description = "Sets a hotkey to instantly hide the reminder box.",
      position = 4,
      section = WARD_OF_ARCEUUS_SECTION
  )
  default Keybind wardOfArceuusHideReminderHotkey() {
    return Keybind.NOT_SET;
  }

  @ConfigItem(
      keyName = "wardOfArceuusReminderStyle",
      name = "Reminder style",
      description = "Changes the style of the reminder box",
      position = 5,
      section = WARD_OF_ARCEUUS_SECTION
  )
  default SpellReminderStyle wardOfArceuusReminderStyle() {
    return SpellReminderStyle.LONG_TEXT;
  }

  @ConfigItem(
      keyName = "wardOfArceuusCustomText",
      name = "Custom Text",
      description = "Changes the text in the reminder box if the style is set to custom text",
      position = 6,
      section = WARD_OF_ARCEUUS_SECTION
  )
  default String wardOfArceuusCustomText() {
    return "You need to Ward of Arceuus!";
  }

  @ConfigItem(
      keyName = "wardOfArceuusShouldFlash",
      name = "Flash the Reminder Box",
      description = "Makes the reminder box flash between the defined colors.",
      position = 7,
      section = WARD_OF_ARCEUUS_SECTION
  )
  default boolean wardOfArceuusShouldFlash() {
    return false;
  }

  @Alpha
  @ConfigItem(
      keyName = "wardOfArceuusFlashColor1",
      name = "Flash Color #1",
      description = "The first color to flash between, also controls the non-flashing color.",
      position = 8,
      section = WARD_OF_ARCEUUS_SECTION
  )
  default Color wardOfArceuusFlashColor1() {
    return new Color(255, 0, 0, 150);
  }

  @Alpha
  @ConfigItem(
      keyName = "wardOfArceuusFlashColor2",
      name = "Flash Color #2",
      description = "The second color to flash between.",
      position = 9,
      section = WARD_OF_ARCEUUS_SECTION
  )
  default Color wardOfArceuusFlashColor2() {
    return new Color(70, 61, 50, 150);
  }

  @ConfigItem(
      keyName = "wardOfArceuusReminderRegex",
      name = "Remind on Regex",
      description = "Displays the reminder box upon a chat message matching the regex.",
      position = 10,
      section = WARD_OF_ARCEUUS_SECTION
  )
  default String wardOfArceuusReminderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "wardOfArceuusHiderRegex",
      name = "Hide on Regex",
      description = "Hides the reminder (if active) upon a chat message matching the regex.",
      position = 11,
      section = WARD_OF_ARCEUUS_SECTION
  )
  default String wardOfArceuusHiderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "wardOfArceuusMatchGameMessagesOnly",
      name = "Only Match Game Messages",
      description = "Only attempt to match game messages with the regex.",
      position = 12,
      section = WARD_OF_ARCEUUS_SECTION
  )
  default boolean wardOfArceuusMatchGameMessagesOnly() {
    return false;
  }

  // Shadow Veil

  @ConfigItem(
      keyName = "shadowVeilEnabled",
      name = "Shadow Veil Enabled",
      description = "Enables reminders for Shadow Veil.",
      position = 0,
      section = SHADOW_VEIL_SECTION
  )
  default boolean shadowVeilEnabled() {
    return true;
  }

  @ConfigItem(
      keyName = "shadowVeilShouldNotify",
      name = "Notification on Reminder",
      description = "Sends a notification once Shadow Veil wears off.",
      position = 1,
      section = SHADOW_VEIL_SECTION
  )
  default Notification shadowVeilShouldNotify() {
    return Notification.ON;
  }

  @ConfigItem(
      keyName = "shadowVeilTimeoutSeconds",
      name = "Reminder Box Timeout",
      description = "The duration in seconds before the reminder box disappears.",
      position = 2,
      section = SHADOW_VEIL_SECTION
  )
  @Units(Units.SECONDS)
  default int shadowVeilTimeoutSeconds() {
    return 120;
  }

  @ConfigItem(
      keyName = "shadowVeilOnlyArceuus",
      name = "Only on Arceuus Spellbook",
      description = "Only display the reminder box when on the Arceuus spellbook.",
      position = 3,
      section = SHADOW_VEIL_SECTION
  )
  default boolean shadowVeilOnlyArceuus() {
    return false;
  }

  @ConfigItem(
      keyName = "shadowVeilHideReminderHotkey",
      name = "Hide Reminder Hotkey",
      description = "Sets a hotkey to instantly hide the reminder box.",
      position = 4,
      section = SHADOW_VEIL_SECTION
  )
  default Keybind shadowVeilHideReminderHotkey() {
    return Keybind.NOT_SET;
  }

  @ConfigItem(
      keyName = "shadowVeilReminderStyle",
      name = "Reminder style",
      description = "Changes the style of the reminder box",
      position = 5,
      section = SHADOW_VEIL_SECTION
  )
  default SpellReminderStyle shadowVeilReminderStyle() {
    return SpellReminderStyle.LONG_TEXT;
  }

  @ConfigItem(
      keyName = "shadowVeilCustomText",
      name = "Custom Text",
      description = "Changes the text in the reminder box if the style is set to custom text",
      position = 6,
      section = SHADOW_VEIL_SECTION
  )
  default String shadowVeilCustomText() {
    return "You need to Shadow Veil!";
  }

  @ConfigItem(
      keyName = "shadowVeilShouldFlash",
      name = "Flash the Reminder Box",
      description = "Makes the reminder box flash between the defined colors.",
      position = 7,
      section = SHADOW_VEIL_SECTION
  )
  default boolean shadowVeilShouldFlash() {
    return false;
  }

  @Alpha
  @ConfigItem(
      keyName = "shadowVeilFlashColor1",
      name = "Flash Color #1",
      description = "The first color to flash between, also controls the non-flashing color.",
      position = 8,
      section = SHADOW_VEIL_SECTION
  )
  default Color shadowVeilFlashColor1() {
    return new Color(255, 0, 0, 150);
  }

  @Alpha
  @ConfigItem(
      keyName = "shadowVeilFlashColor2",
      name = "Flash Color #2",
      description = "The second color to flash between.",
      position = 9,
      section = SHADOW_VEIL_SECTION
  )
  default Color shadowVeilFlashColor2() {
    return new Color(70, 61, 50, 150);
  }

  @ConfigItem(
      keyName = "shadowVeilReminderRegex",
      name = "Remind on Regex",
      description = "Displays the reminder box upon a chat message matching the regex.",
      position = 10,
      section = SHADOW_VEIL_SECTION
  )
  default String shadowVeilReminderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "shadowVeilHiderRegex",
      name = "Hide on Regex",
      description = "Hides the reminder (if active) upon a chat message matching the regex.",
      position = 11,
      section = SHADOW_VEIL_SECTION
  )
  default String shadowVeilHiderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "shadowVeilMatchGameMessagesOnly",
      name = "Only Match Game Messages",
      description = "Only attempt to match game messages with the regex.",
      position = 12,
      section = SHADOW_VEIL_SECTION
  )
  default boolean shadowVeilMatchGameMessagesOnly() {
    return false;
  }

  // Corruption

  @ConfigItem(
      keyName = "corruptionEnabled",
      name = "Corruption Enabled",
      description = "Enables reminders for Corruption.",
      position = 0,
      section = CORRUPTION_SECTION
  )
  default boolean corruptionEnabled() {
    return true;
  }

  @ConfigItem(
      keyName = "corruptionShouldNotify",
      name = "Notification on Reminder",
      description = "Sends a notification once Corruption should be recast.",
      position = 1,
      section = CORRUPTION_SECTION
  )
  default Notification corruptionShouldNotify() {
    return Notification.ON;
  }

  @ConfigItem(
      keyName = "corruptionTimeoutSeconds",
      name = "Reminder Box Timeout",
      description = "The duration in seconds before the reminder box disappears.",
      position = 2,
      section = CORRUPTION_SECTION
  )
  @Units(Units.SECONDS)
  default int corruptionTimeoutSeconds() {
    return 120;
  }

  @ConfigItem(
      keyName = "corruptionOnlyArceuus",
      name = "Only on Arceuus Spellbook",
      description = "Only display the reminder box when on the Arceuus spellbook.",
      position = 3,
      section = CORRUPTION_SECTION
  )
  default boolean corruptionOnlyArceuus() {
    return false;
  }

  @ConfigItem(
      keyName = "corruptionHideReminderHotkey",
      name = "Hide Reminder Hotkey",
      description = "Sets a hotkey to instantly hide the reminder box.",
      position = 4,
      section = CORRUPTION_SECTION
  )
  default Keybind corruptionHideReminderHotkey() {
    return Keybind.NOT_SET;
  }

  @ConfigItem(
      keyName = "corruptionReminderStyle",
      name = "Reminder style",
      description = "Changes the style of the reminder box",
      position = 5,
      section = CORRUPTION_SECTION
  )
  default SpellReminderStyle corruptionReminderStyle() {
    return SpellReminderStyle.LONG_TEXT;
  }

  @ConfigItem(
      keyName = "corruptionCustomText",
      name = "Custom Text",
      description = "Changes the text in the reminder box if the style is set to custom text",
      position = 6,
      section = CORRUPTION_SECTION
  )
  default String corruptionCustomText() {
    return "You need to cast a Corruption spell!";
  }

  @ConfigItem(
      keyName = "corruptionShouldFlash",
      name = "Flash the Reminder Box",
      description = "Makes the reminder box flash between the defined colors.",
      position = 7,
      section = CORRUPTION_SECTION
  )
  default boolean corruptionShouldFlash() {
    return false;
  }

  @Alpha
  @ConfigItem(
      keyName = "corruptionFlashColor1",
      name = "Flash Color #1",
      description = "The first color to flash between, also controls the non-flashing color.",
      position = 8,
      section = CORRUPTION_SECTION
  )
  default Color corruptionFlashColor1() {
    return new Color(255, 0, 0, 150);
  }

  @Alpha
  @ConfigItem(
      keyName = "corruptionFlashColor2",
      name = "Flash Color #2",
      description = "The second color to flash between.",
      position = 9,
      section = CORRUPTION_SECTION
  )
  default Color corruptionFlashColor2() {
    return new Color(70, 61, 50, 150);
  }

  @ConfigItem(
      keyName = "corruptionReminderRegex",
      name = "Remind on Regex",
      description = "Displays the reminder box upon a chat message matching the regex.",
      position = 10,
      section = CORRUPTION_SECTION
  )
  default String corruptionReminderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "corruptionHiderRegex",
      name = "Hide on Regex",
      description = "Hides the reminder (if active) upon a chat message matching the regex.",
      position = 11,
      section = CORRUPTION_SECTION
  )
  default String corruptionHiderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "corruptionMatchGameMessagesOnly",
      name = "Only Match Game Messages",
      description = "Only attempt to match game messages with the regex.",
      position = 12,
      section = CORRUPTION_SECTION
  )
  default boolean corruptionMatchGameMessagesOnly() {
    return false;
  }

  // Charge

  @ConfigItem(
      keyName = "chargeEnabled",
      name = "Charge Enabled",
      description = "Enables reminders for Charge.",
      position = 0,
      section = CHARGE_SECTION
  )
  default boolean chargeEnabled() {
    return true;
  }

  @ConfigItem(
      keyName = "chargeShouldNotify",
      name = "Notification on Reminder",
      description = "Sends a notification once Charge wears off.",
      position = 1,
      section = CHARGE_SECTION
  )
  default Notification chargeShouldNotify() {
    return Notification.ON;
  }

  @ConfigItem(
      keyName = "chargeTimeoutSeconds",
      name = "Reminder Box Timeout",
      description = "The duration in seconds before the reminder box disappears.",
      position = 2,
      section = CHARGE_SECTION
  )
  @Units(Units.SECONDS)
  default int chargeTimeoutSeconds() {
    return 120;
  }

  @ConfigItem(
      keyName = "chargeOnlyStandard",
      name = "Only on Standard Spellbook",
      description = "Only display the reminder box when on the Standard spellbook.",
      position = 3,
      section = CHARGE_SECTION
  )
  default boolean chargeOnlyStandard() {
    return false;
  }

  @ConfigItem(
      keyName = "chargeHideReminderHotkey",
      name = "Hide Reminder Hotkey",
      description = "Sets a hotkey to instantly hide the reminder box.",
      position = 4,
      section = CHARGE_SECTION
  )
  default Keybind chargeHideReminderHotkey() {
    return Keybind.NOT_SET;
  }

  @ConfigItem(
      keyName = "chargeReminderStyle",
      name = "Reminder style",
      description = "Changes the style of the reminder box",
      position = 5,
      section = CHARGE_SECTION
  )
  default SpellReminderStyle chargeReminderStyle() {
    return SpellReminderStyle.LONG_TEXT;
  }

  @ConfigItem(
      keyName = "chargeCustomText",
      name = "Custom Text",
      description = "Changes the text in the reminder box if the style is set to custom text",
      position = 6,
      section = CHARGE_SECTION
  )
  default String chargeCustomText() {
    return "You need to cast Charge!";
  }

  @ConfigItem(
      keyName = "chargeShouldFlash",
      name = "Flash the Reminder Box",
      description = "Makes the reminder box flash between the defined colors.",
      position = 7,
      section = CHARGE_SECTION
  )
  default boolean chargeShouldFlash() {
    return false;
  }

  @Alpha
  @ConfigItem(
      keyName = "chargeFlashColor1",
      name = "Flash Color #1",
      description = "The first color to flash between, also controls the non-flashing color.",
      position = 8,
      section = CHARGE_SECTION
  )
  default Color chargeFlashColor1() {
    return new Color(255, 0, 0, 150);
  }

  @Alpha
  @ConfigItem(
      keyName = "chargeFlashColor2",
      name = "Flash Color #2",
      description = "The second color to flash between.",
      position = 9,
      section = CHARGE_SECTION
  )
  default Color chargeFlashColor2() {
    return new Color(70, 61, 50, 150);
  }

  @ConfigItem(
      keyName = "chargeReminderRegex",
      name = "Remind on Regex",
      description = "Displays the reminder box upon a chat message matching the regex.",
      position = 10,
      section = CHARGE_SECTION
  )
  default String chargeReminderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "chargeHiderRegex",
      name = "Hide on Regex",
      description = "Hides the reminder (if active) upon a chat message matching the regex.",
      position = 11,
      section = CHARGE_SECTION
  )
  default String chargeHiderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "chargeMatchGameMessagesOnly",
      name = "Only Match Game Messages",
      description = "Only attempt to match game messages with the regex.",
      position = 12,
      section = CHARGE_SECTION
  )
  default boolean chargeMatchGameMessagesOnly() {
    return false;
  }

  // Vengeance

  @ConfigItem(
      keyName = "vengeanceEnabled",
      name = "Vengeance Enabled",
      description = "Enables reminders for Vengeance.",
      position = 0,
      section = VENGEANCE_SECTION
  )
  default boolean vengeanceEnabled() {
    return true;
  }

  @ConfigItem(
      keyName = "vengeanceShouldNotify",
      name = "Notification on Reminder",
      description = "Sends a notification once Vengeance can be recast.",
      position = 1,
      section = VENGEANCE_SECTION
  )
  default Notification vengeanceShouldNotify() {
    return Notification.ON;
  }

  @ConfigItem(
      keyName = "vengeanceTimeoutSeconds",
      name = "Reminder Box Timeout",
      description = "The duration in seconds before the reminder box disappears.",
      position = 2,
      section = VENGEANCE_SECTION
  )
  @Units(Units.SECONDS)
  default int vengeanceTimeoutSeconds() {
    return 120;
  }

  @ConfigItem(
      keyName = "vengeanceOnlyLunar",
      name = "Only on Lunar Spellbook",
      description = "Only display the reminder box when on the Lunar spellbook.",
      position = 3,
      section = VENGEANCE_SECTION
  )
  default boolean vengeanceOnlyLunar() {
    return false;
  }

  @ConfigItem(
      keyName = "vengeanceHideReminderHotkey",
      name = "Hide Reminder Hotkey",
      description = "Sets a hotkey to instantly hide the reminder box.",
      position = 4,
      section = VENGEANCE_SECTION
  )
  default Keybind vengeanceHideReminderHotkey() {
    return Keybind.NOT_SET;
  }

  @ConfigItem(
      keyName = "vengeanceReminderStyle",
      name = "Reminder style",
      description = "Changes the style of the reminder box",
      position = 5,
      section = VENGEANCE_SECTION
  )
  default SpellReminderStyle vengeanceReminderStyle() {
    return SpellReminderStyle.LONG_TEXT;
  }

  @ConfigItem(
      keyName = "vengeanceCustomText",
      name = "Custom Text",
      description = "Changes the text in the reminder box if the style is set to custom text",
      position = 6,
      section = VENGEANCE_SECTION
  )
  default String vengeanceCustomText() {
    return "You need to cast Vengeance!";
  }

  @ConfigItem(
      keyName = "vengeanceShouldFlash",
      name = "Flash the Reminder Box",
      description = "Makes the reminder box flash between the defined colors.",
      position = 7,
      section = VENGEANCE_SECTION
  )
  default boolean vengeanceShouldFlash() {
    return false;
  }

  @Alpha
  @ConfigItem(
      keyName = "vengeanceFlashColor1",
      name = "Flash Color #1",
      description = "The first color to flash between, also controls the non-flashing color.",
      position = 8,
      section = VENGEANCE_SECTION
  )
  default Color vengeanceFlashColor1() {
    return new Color(255, 0, 0, 150);
  }

  @Alpha
  @ConfigItem(
      keyName = "vengeanceFlashColor2",
      name = "Flash Color #2",
      description = "The second color to flash between.",
      position = 9,
      section = VENGEANCE_SECTION
  )
  default Color vengeanceFlashColor2() {
    return new Color(70, 61, 50, 150);
  }

  @ConfigItem(
      keyName = "vengeanceReminderRegex",
      name = "Remind on Regex",
      description = "Displays the reminder box upon a chat message matching the regex.",
      position = 10,
      section = VENGEANCE_SECTION
  )
  default String vengeanceReminderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "vengeanceHiderRegex",
      name = "Hide on Regex",
      description = "Hides the reminder (if active) upon a chat message matching the regex.",
      position = 11,
      section = VENGEANCE_SECTION
  )
  default String vengeanceHiderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "vengeanceMatchGameMessagesOnly",
      name = "Only Match Game Messages",
      description = "Only attempt to match game messages with the regex.",
      position = 12,
      section = VENGEANCE_SECTION
  )
  default boolean vengeanceMatchGameMessagesOnly() {
    return false;
  }

  // Vile Vigour

  @ConfigItem(
      keyName = "vileVigourEnabled",
      name = "Vile Vigour Enabled",
      description = "Enables reminders for Vile Vigour.",
      position = 0,
      section = VILE_VIGOUR_SECTION
  )
  default boolean vileVigourEnabled() {
    return true;
  }

  @ConfigItem(
      keyName = "vileVigourRunThreshold",
      name = "Run Energy Threshold",
      description = "The run energy threshold for the reminder box to appear.",
      position = 1,
      section = VILE_VIGOUR_SECTION
  )
  default int vileVigourRunThreshold() {
    return -1;
  }

  @ConfigItem(
      keyName = "vileVigourShouldNotify",
      name = "Notification on Reminder",
      description = "Sends a notification once the run energy threshold has been met.",
      position = 2,
      section = VILE_VIGOUR_SECTION
  )
  default Notification vileVigourShouldNotify() {
    return Notification.ON;
  }

  @ConfigItem(
      keyName = "vileVigourTimeoutSeconds",
      name = "Reminder Box Timeout",
      description = "The duration in seconds before the reminder box disappears.",
      position = 3,
      section = VILE_VIGOUR_SECTION
  )
  @Units(Units.SECONDS)
  default int vileVigourTimeoutSeconds() {
    return 120;
  }

  @ConfigItem(
      keyName = "vileVigourOnlyArceuus",
      name = "Only on Arceuus Spellbook",
      description = "Only display the reminder box when on the Arceuus spellbook.",
      position = 4,
      section = VILE_VIGOUR_SECTION
  )
  default boolean vileVigourOnlyArceuus() {
    return false;
  }

  @ConfigItem(
      keyName = "vileVigourHideReminderHotkey",
      name = "Hide Reminder Hotkey",
      description = "Sets a hotkey to instantly hide the reminder box.",
      position = 5,
      section = VILE_VIGOUR_SECTION
  )
  default Keybind vileVigourHideReminderHotkey() {
    return Keybind.NOT_SET;
  }

  @ConfigItem(
      keyName = "vileVigourReminderStyle",
      name = "Reminder style",
      description = "Changes the style of the reminder box",
      position = 6,
      section = VILE_VIGOUR_SECTION
  )
  default SpellReminderStyle vileVigourReminderStyle() {
    return SpellReminderStyle.LONG_TEXT;
  }

  @ConfigItem(
      keyName = "vileVigourCustomText",
      name = "Custom Text",
      description = "Changes the text in the reminder box if the style is set to custom text",
      position = 7,
      section = VILE_VIGOUR_SECTION
  )
  default String vileVigourCustomText() {
    return "You need to cast Vile Vigour!";
  }

  @ConfigItem(
      keyName = "vileVigourShouldFlash",
      name = "Flash the Reminder Box",
      description = "Makes the reminder box flash between the defined colors.",
      position = 8,
      section = VILE_VIGOUR_SECTION
  )
  default boolean vileVigourShouldFlash() {
    return false;
  }

  @Alpha
  @ConfigItem(
      keyName = "vileVigourFlashColor1",
      name = "Flash Color #1",
      description = "The first color to flash between, also controls the non-flashing color.",
      position = 9,
      section = VILE_VIGOUR_SECTION
  )
  default Color vileVigourFlashColor1() {
    return new Color(255, 0, 0, 150);
  }

  @Alpha
  @ConfigItem(
      keyName = "vileVigourFlashColor2",
      name = "Flash Color #2",
      description = "The second color to flash between.",
      position = 10,
      section = VILE_VIGOUR_SECTION
  )
  default Color vileVigourFlashColor2() {
    return new Color(70, 61, 50, 150);
  }

  @ConfigItem(
      keyName = "vileVigourReminderRegex",
      name = "Remind on Regex",
      description = "Displays the reminder box upon a chat message matching the regex.",
      position = 11,
      section = VILE_VIGOUR_SECTION
  )
  default String vileVigourReminderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "vileVigourHiderRegex",
      name = "Hide on Regex",
      description = "Hides the reminder (if active) upon a chat message matching the regex.",
      position = 12,
      section = VILE_VIGOUR_SECTION
  )
  default String vileVigourHiderRegex() {
    return "";
  }

  @ConfigItem(
      keyName = "vileVigourMatchGameMessagesOnly",
      name = "Only Match Game Messages",
      description = "Only attempt to match game messages with the regex.",
      position = 13,
      section = VILE_VIGOUR_SECTION
  )
  default boolean vileVigourMatchGameMessagesOnly() {
    return false;
  }

}
