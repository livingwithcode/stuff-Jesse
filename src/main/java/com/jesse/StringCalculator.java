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

  private final Logger logger = LogManager.getLogger(StringCalculator.class);

  public int add(String numbers) {
    if (null == numbers || numbers.replace(DEFAULT_DELIMITER, "").strip().isBlank()) {
      return 0;
    }
    String sanitizedNumberStr = numbers.strip();

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
