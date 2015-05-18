package Calculator;

import Calculator.Calculator;

import javax.swing.*;
import java.awt.*;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since May 15 , 2015 10:04
 */
public class Demo {

  public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        Calculator calculator = new Calculator();
        Container contentPane = calculator.getContentPane();
        calculator.setTitle("Calculator");
        calculator.setSize(441, 417);
        calculator.pack();
        calculator.setLocation(600, 250);
        calculator.setVisible(true);
        calculator.setResizable(false);
      }
    });
  }
}
