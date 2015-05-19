package Calculator;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;


/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since May 18 , 2015 17:07
 */
public class CalculatorTest {

  public Calculator calculator = new Calculator();
  ;
  public Container contentPane = calculator.getContentPane();


  @Test
  public void happyPath() {

    calculator.addDigitToDisplay(4);
    calculator.addDecimalPoint();
    calculator.addDigitToDisplay(4);

    assertEquals("4.4", calculator.getDisplayString());
  }

  @Test
  public void clearAllDigitsOnDisplay() {
    calculator.addDigitToDisplay(5);
    calculator.processOperator("+");
    calculator.addDigitToDisplay(5);
    calculator.clearAll();
    assertEquals("0", calculator.getDisplayString());
  }


  @Test
  public void subtractionOnDigits() {
    calculator.addDigitToDisplay(4);
    calculator.processOperator("-");
    calculator.addDigitToDisplay(2);
    calculator.processEquals();

    assertEquals("2.0", calculator.getDisplayString());
  }

  @Test
  public void multiplicationOnDigits() {

    calculator.addDigitToDisplay(5);
    calculator.processOperator("*");
    calculator.addDigitToDisplay(5);
    calculator.processEquals();

    assertEquals("25.0", calculator.getDisplayString());
  }

  @Test
  public void divideByZero() {
    calculator.addDigitToDisplay(6);
    calculator.processOperator("/");
    calculator.addDigitToDisplay(0);
    calculator.processEquals();

    assertEquals("Cannot divide by zero!", calculator.getDisplayString());
  }

  @Test
  public void divisionOfNumbers() {
    calculator.addDigitToDisplay(9);
    calculator.processOperator("/");
    calculator.addDigitToDisplay(3);
    calculator.processEquals();

    assertEquals("3.0", calculator.getDisplayString());
  }

  @Test
  public void signChange() {

    calculator.addDigitToDisplay(49);
    calculator.processSignChange();
    assertEquals("-49", calculator.getDisplayString());
  }

  @Test
  public void lastOperatorPressed() {
    calculator.addDigitToDisplay(9);
    calculator.processOperator("+");

    assertEquals("+", calculator.lastOperator);
  }


  @Test
  public void clearExistingPressed() {
    calculator.addDigitToDisplay(6);
    calculator.processOperator("+");
    calculator.addDigitToDisplay(4);
    calculator.clearExisting();
    calculator.addDigitToDisplay(5);
    calculator.processEquals();

    assertEquals("11.0", calculator.getDisplayString());
  }

  @Test
  public void getLastInputNumber() {
    calculator.addDigitToDisplay(9);
    calculator.processOperator("+");
    calculator.addDigitToDisplay(8);

    assertEquals("8", calculator.getDisplayString());
  }

}
