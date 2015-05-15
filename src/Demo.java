import javax.swing.*;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since May 15 , 2015 10:04
 */
public class Demo {

  public static void main(String[] args) {

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        Calculator calc = new Calculator();
        calc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calc.setVisible(true);
      }
    });
  }
}
