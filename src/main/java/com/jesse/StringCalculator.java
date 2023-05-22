/*
 * User: Jesse Jiang
 * Created on: 22/05/2023
 */


package com.jesse;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A Calculator utility class
 */
public class StringCalculator {
  /**
   * Default delimiter
   */
  private static final String DEFAULT_DELIMITER = ",";

  /**
   * New Line
   */
  private static final String NEWLINE = "\n";

  /**
   * Regex to check if we have , followed by \n or \n followed by ,
   */
  private static final String CHECK_INPUT = ".*(\n,|,\n).*";

  private final Logger logger = LogManager.getLogger(StringCalculator.class);

  public int add(String numbers) {
    if (null == numbers || numbers.replace(DEFAULT_DELIMITER, "").strip().isBlank()) {
      return 0;
    }
    if (numbers.matches(CHECK_INPUT)) {
      throw new IllegalArgumentException("one or the other delimiter only, but not two delimiter come together.");
    }
    Optional<String> delimiter = getDelimiter(numbers);
    String sanitizedNumberStr;
    if (delimiter.isPresent()) {
      sanitizedNumberStr = numbers.substring(numbers.indexOf(NEWLINE)+1).replace(NEWLINE, ",").replace(delimiter.get(), ",").strip();
    }
    else {
      sanitizedNumberStr = numbers.replace(NEWLINE, ",").strip();
    }

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

  private Optional<String> getDelimiter(String numbers) {
    if (null == numbers || !numbers.contains(NEWLINE)) {
      return Optional.empty();
    }
    String[] split = numbers.strip().split(NEWLINE);
    String delimiterLine = split[0];
    if (delimiterLine.startsWith("//") && delimiterLine.length() > 2) {
      return Optional.of(delimiterLine.substring(2));
    }
    return Optional.empty();
  }
}
