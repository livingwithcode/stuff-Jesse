/*
 * User: Jesse Jiang
 * Created on: 22/05/2023
 */

package com.jesse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  @Test
  void testNewLine() {
    assertEquals(6, stringCalculator.add("1\n2,3"));
    assertEquals(6, stringCalculator.add("1\n\n2,3"));
    assertEquals(6, stringCalculator.add("1\n\n2,,3"));
    assertThrows(IllegalArgumentException.class, () -> stringCalculator.add("1,\n2"));
    assertThrows(IllegalArgumentException.class, () -> stringCalculator.add("1\n,3"));
  }
  @Test
  void testCustomDelimiter(){
    // we suppose the delimiter line is start with // and end with new line \n
    assertEquals(3,stringCalculator.add("//;\n1;2"));
    assertEquals(3,stringCalculator.add("//;\n1,2"));
    assertEquals(3,stringCalculator.add("//;\n1\n2"));
    assertEquals(0,stringCalculator.add(";\n1;2"));
    assertEquals(3,stringCalculator.add(";\n1\n2"));
    assertEquals(3,stringCalculator.add(";\n1,2"));
  }

  @Test
  void testNegative() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> stringCalculator.add("1,-1"));
    assertTrue(exception.getMessage().contains("Negative numbers are not allowed [-1]"));
    exception = assertThrows(IllegalArgumentException.class, () -> stringCalculator.add("1,-1,-2"));
    assertTrue(exception.getMessage().contains("Negative numbers are not allowed [-1,-2]"));
  }
}
