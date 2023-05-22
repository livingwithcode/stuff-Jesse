/*
 * User: Jesse Jiang
 * Created on: 22/05/2023
 */


package com.jesse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

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

  /**
   * max number
   */
  private static final int MAX_NUMBER = 1000;

  private final Logger logger = LogManager.getLogger(StringCalculator.class);

  public int add(String numbers) {
    if (null == numbers || numbers.replace(DEFAULT_DELIMITER, "").isBlank()) {
      return 0;
    }
    if (numbers.matches(CHECK_INPUT)) {
      throw new IllegalArgumentException("one or the other delimiter only, but not two delimiter come together.");
    }
    Set<String> delimiters = new HashSet<>();
    String sanitizedNumbers = numbers;
    if (numbers.startsWith("//") && numbers.contains(NEWLINE)) {
      int startIndex = numbers.indexOf(NEWLINE);
      delimiters = getDelimiter(numbers.substring(0, startIndex + 1));
      if (numbers.length() > startIndex + 2) {
        sanitizedNumbers = numbers.substring(startIndex + 1);
      }
      else {
        return 0;
      }
    }
    delimiters.add(NEWLINE);
    delimiters.add(DEFAULT_DELIMITER);
    List<Integer> numberList = Arrays.stream(sanitizedNumbers.split("[" + String.join("", delimiters) + "]"))
                                     .map(num -> {
                                       int number = 0;
                                       try {
                                         number = Integer.parseInt(num);
                                       }
                                       catch (NumberFormatException e) {
                                         logger.error("{} is not an valid number, so ignore it.", num);
                                       }
                                       return number;
                                     }).collect(toList());
    String negativeNumbers = numberList.stream().filter(num -> num < 0)
                                       .map(String::valueOf).collect(Collectors.joining(DEFAULT_DELIMITER));
    if (negativeNumbers.length() > 0) {
      throw new IllegalArgumentException(String.format("Negative numbers are not allowed [%s]", negativeNumbers));
    }

    return numberList.stream().filter(num -> num < MAX_NUMBER).reduce(0, Integer::sum);
  }

  private Set<String> getDelimiter(String numbers) {
    if (null == numbers) {
      return new TreeSet<>();
    }
    if (numbers.startsWith("//") && numbers.endsWith("\n")) {
      String delemiters = numbers.substring(2, numbers.indexOf(NEWLINE));
      if (delemiters.startsWith("[") && delemiters.endsWith("]")) {
        delemiters = delemiters.substring(1, delemiters.length() - 1);
        return Arrays.stream(delemiters.split("\\]\\[")).collect(toSet());
      }
    }
    return new TreeSet<>();
  }
}
