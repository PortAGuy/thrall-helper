package com.portaguy;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum Spellbook {
  STANDARD(0),
  ANCIENT(1),
  LUNAR(2),
  ARCEUUS(3);

  private final int varbit;
  private static final Map<Integer, Spellbook> spellbookMap = Map.of(
      0, STANDARD,
      1, ANCIENT,
      2, LUNAR,
      3, ARCEUUS
  );

  @Nullable
  public static Spellbook fromVarbit(int varbitValue) {
    return spellbookMap.get(varbitValue);
  }
}