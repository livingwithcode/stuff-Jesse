/*
 * User: Jesse Jiang
 * Created on: 22/05/2023
 */

package com.jesse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing String Calculator.
 * <p>
 *
 * @author $Author: Jesse Jiang
 */
class TestStringCalculator {

  private final StringCalculator stringCalculator = new StringCalculator();

  @Test
  void testEmptyParameter() {
    assertEquals(0, stringCalculator.add(""));
  }

  @Test
  void testCalculator() {
    assertEquals(0, stringCalculator.add(","));
    assertEquals(1, stringCalculator.add("1"));
    assertEquals(1, stringCalculator.add("1,"));
    assertEquals(1, stringCalculator.add("1,,"));
    assertEquals(1, stringCalculator.add(",1"));
    assertEquals(2, stringCalculator.add("0,2"));
    assertEquals(2, stringCalculator.add("0,2,,"));
    assertEquals(4, stringCalculator.add("0,2,2"));
    assertEquals(4, stringCalculator.add("0,2,2,a,b,c"));
  }

}
