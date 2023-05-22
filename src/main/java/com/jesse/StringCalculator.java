/*
 * User: Jesse Jiang
 * Created on: 22/05/2023
 */


package com.jesse;

import java.util.Arrays;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A Calculator utility class
 */
public class StringCalculator {
  private static final String DEFAULT_DELIMITER = ",";

  private static final String NEWLINE = "\n";

  private static final String CHECK_INPUT = ".*(\n,|,\n).*";

  private final Logger logger = LogManager.getLogger(StringCalculator.class);

  public int add(String numbers) {
    if (null == numbers || numbers.replace(DEFAULT_DELIMITER, "").strip().isBlank()) {
      return 0;
    }
    if (numbers.matches(CHECK_INPUT)) {
      throw new IllegalArgumentException("one or the other delimiter only, but not two delimiter come together.");
    }
    String sanitizedNumberStr = numbers.replace(NEWLINE, ",").strip();

    return Arrays.stream(sanitizedNumberStr.split(DEFAULT_DELIMITER))
                 .filter(Objects::nonNull)
                 .mapToInt(num -> {
                   int number = 0;
                   try {
                     number = Integer.parseInt(num);
                   }
                   catch (NumberFormatException e) {
                     logger.error("{} is not an valid number, so ignore it.", num);
                   }
                   return number;
                 })
                 .reduce(0, Integer::sum);
  }
}
