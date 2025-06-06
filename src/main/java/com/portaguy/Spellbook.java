package com.portaguy;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;

/**
 * Represents the player's current spellbook.
 * <br/>
 * This enum maps the values of VarbitID.SPELLBOOK (4070).
 */
@Getter
@AllArgsConstructor
public enum Spellbook {
  STANDARD(0),
  ANCIENT(1),
  LUNAR(2),
  ARCEUUS(3);

  private final int varbit;

  @Nullable
  public static Spellbook fromVarbit(int varbitValue) {
    switch (varbitValue) {
      case 0: return STANDARD;
      case 1: return ANCIENT;
      case 2: return LUNAR;
      case 3: return ARCEUUS;
      default: return null;
    }
  }
}