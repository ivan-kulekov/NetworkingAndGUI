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
        Calculator calci = new Calculator();
        Container contentPane = calci.getContentPane();
        calci.setTitle("Java Calculator");
        calci.setSize(441, 417);
        calci.pack();
        calci.setLocation(600, 250);
        calci.setVisible(true);
        calci.setResizable(false);
      }
    });
  }
}
