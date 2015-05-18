import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since May 14 , 2015 17:00
 */
public class Calculator extends JFrame implements ActionListener {

  private int INPUT_MODE = 0;
  private int RESULT_MODE = 1;
  private int ERROR_MODE = 2;
  private int displayMode;

  boolean clearOnNextDigit;
  double lastNumber;
  String lastOperator;

  private JLabel jlbOutput;
  private JButton jbnButtons[];


  Font f12 = new Font("Times New Roman", 0, 12);

  /**
   * Add component to the frame in constructor and create the operations.
   */
  public Calculator() {
    setBackground(Color.gray);

    JPanel jplMaster = new JPanel();

    jlbOutput = new JLabel("0");
    jlbOutput.setHorizontalTextPosition(JLabel.RIGHT);
    jlbOutput.setBackground(Color.WHITE);
    jlbOutput.setOpaque(true);

    getContentPane().add(jlbOutput, BorderLayout.NORTH);

    jbnButtons = new JButton[23];

    JPanel jplButtons = new JPanel();

    for (int i = 0; i <= 9; i++) {
      jbnButtons[i] = new JButton(String.valueOf(i));
    }

    jbnButtons[10] = new JButton("+/-");
    jbnButtons[11] = new JButton(".");
    jbnButtons[12] = new JButton("=");
    jbnButtons[13] = new JButton("/");
    jbnButtons[14] = new JButton("*");
    jbnButtons[15] = new JButton("-");
    jbnButtons[16] = new JButton("+");
    jbnButtons[17] = new JButton("sqrt");
    jbnButtons[18] = new JButton("1/x");
    jbnButtons[19] = new JButton("%");

    JPanel jplBackSpace = new JPanel();
    jplBackSpace.setLayout(new GridLayout(1, 1, 2, 2));

    jbnButtons[20] = new JButton(" Backspace");
    jplBackSpace.add(jbnButtons[20]);

    JPanel jplControl = new JPanel();
    jplControl.setLayout(new GridLayout(1, 2, 2, 2));

    jbnButtons[21] = new JButton(" CE ");
    jbnButtons[22] = new JButton("C");

    jplControl.add(jbnButtons[21]);
    jplControl.add(jbnButtons[22]);

    for (int i = 0; i < jbnButtons.length; i++) {
      jbnButtons[i].setFont(f12);

      if (i < 10)
        jbnButtons[i].setForeground(Color.blue);

      else
        jbnButtons[i].setForeground(Color.red);
    }

    jplButtons.setLayout(new GridLayout(4, 5, 2, 2));

    for (int i = 7; i <= 9; i++) {
      jplButtons.add(jbnButtons[i]);
    }

    jplButtons.add(jbnButtons[13]);
    jplButtons.add(jbnButtons[17]);

    for (int i = 4; i <= 6; i++) {
      jplButtons.add(jbnButtons[i]);
    }

    jplButtons.add(jbnButtons[14]);
    jplButtons.add(jbnButtons[18]);

    for (int i = 1; i <= 3; i++) {
      jplButtons.add(jbnButtons[i]);
    }

    jplButtons.add(jbnButtons[15]);
    jplButtons.add(jbnButtons[19]);

    jplButtons.add(jbnButtons[0]);
    jplButtons.add(jbnButtons[10]);
    jplButtons.add(jbnButtons[11]);
    jplButtons.add(jbnButtons[16]);
    jplButtons.add(jbnButtons[12]);

    jplMaster.setLayout(new BorderLayout());
    jplMaster.add(jplBackSpace, BorderLayout.WEST);
    jplMaster.add(jplControl, BorderLayout.EAST);
    jplMaster.add(jplButtons, BorderLayout.SOUTH);

    getContentPane().add(jplMaster, BorderLayout.SOUTH);
    requestFocus();

    for (JButton jbnButton : jbnButtons) {
      jbnButton.addActionListener(this);
    }

    clearAll();

    addWindowListener(new WindowAdapter() {

                        public void windowClosed(WindowEvent e) {
                          System.exit(0);
                        }
                      }
    );
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  public void actionPerformed(ActionEvent e) {
    double result;

    for (int i = 0; i < jbnButtons.length; i++) {
      if (e.getSource() == jbnButtons[i]) {
        switch (i) {
          case 0:
            addDigitToDisplay(i);
            break;

          case 1:
            addDigitToDisplay(i);
            break;

          case 2:
            addDigitToDisplay(i);
            break;

          case 3:
            addDigitToDisplay(i);
            break;

          case 4:
            addDigitToDisplay(i);
            break;

          case 5:
            addDigitToDisplay(i);
            break;

          case 6:
            addDigitToDisplay(i);
            break;

          case 7:
            addDigitToDisplay(i);
            break;

          case 8:
            addDigitToDisplay(i);
            break;

          case 9:
            addDigitToDisplay(i);
            break;

          case 10:
            processSignChange();
            break;

          case 11:
            addDecimalPoint();
            break;

          case 12:
            processEquals();
            break;

          case 13:
            processOperator("/");
            break;

          case 14:
            processOperator("*");
            break;

          case 15:
            processOperator("-");
            break;

          case 16:
            processOperator("+");
            break;

          case 17:
            if (displayMode != ERROR_MODE) {
              try {
                if (getDisplayString().indexOf("-") == 0)
                  displayError("Invalid input for function!");

                result = Math.sqrt(getNumberInDisplay());
                displayResult(result);
              } catch (Exception ex) {
                displayError("Invalid input for function!");
                displayMode = ERROR_MODE;
              }
            }
            break;

          case 18:
            if (displayMode != ERROR_MODE) {
              try {
                if (getNumberInDisplay() == 0)
                  displayError("Cannot divide by zero!");

                result = 1 / getNumberInDisplay();
                displayResult(result);
              } catch (Exception ex) {
                displayError("Cannot divide by zero!");
                displayMode = ERROR_MODE;
              }
            }
            break;

          case 19:
            if (displayMode != ERROR_MODE) {
              try {
                result = getNumberInDisplay() / 100;
                displayResult(result);
              } catch (Exception ex) {
                displayError("Invalid input for function!");
                displayMode = ERROR_MODE;
              }
            }
            break;

          case 20:
            if (displayMode != ERROR_MODE) {
              setDisplayString(getDisplayString().substring(0,
                      getDisplayString().length() - 1));

              if (getDisplayString().length() < 1)
                setDisplayString("0");
            }
            break;

          case 21:
            clearExisting();
            break;

          case 22:
            clearAll();
            break;
        }
      }
    }
  }

  private void setDisplayString(String s) {

    jlbOutput.setText(s);
  }

  private String getDisplayString() {
    return jlbOutput.getText();
  }

  private void addDigitToDisplay(int digit) {
    if (clearOnNextDigit)
      setDisplayString("");

    String inputString = getDisplayString();

    if (inputString.indexOf("0") == 0) {
      inputString = inputString.substring(1);
    }

    int MAX_INPUT_LENGTH = 20;
    if ((!inputString.equals("0") || digit > 0) && inputString.length() < MAX_INPUT_LENGTH) {
      setDisplayString(inputString + digit);
    }


    displayMode = INPUT_MODE;
    clearOnNextDigit = false;
  }

  private void addDecimalPoint() {
    displayMode = INPUT_MODE;

    if (clearOnNextDigit)
      setDisplayString("");

    String inputString = getDisplayString();

    if (!inputString.contains("."))
      setDisplayString(inputString + ".");
  }

  private void processSignChange() {
    if (displayMode == INPUT_MODE) {
      String input = getDisplayString();

      if (input.length() > 0 && !input.equals("0")) {
        if (input.indexOf("-") == 0) {
          setDisplayString(input.substring(1));
        } else {
          setDisplayString("-" + input);
        }
      }

    } else if (displayMode == RESULT_MODE) {
      double numberInDisplay = getNumberInDisplay();

      if (numberInDisplay != 0)
        displayResult(-numberInDisplay);
    }
  }

  private void clearAll() {
    setDisplayString("0");
    lastOperator = "0";
    lastNumber = 0;
    displayMode = INPUT_MODE;
    clearOnNextDigit = true;
  }

  private void clearExisting() {
    setDisplayString("0");
    clearOnNextDigit = true;
    displayMode = INPUT_MODE;
  }

  private double getNumberInDisplay() {
    String input = jlbOutput.getText();
    return Double.parseDouble(input);
  }

  private void processOperator(String op) {
    if (displayMode != ERROR_MODE) {
      double numberInDisplay = getNumberInDisplay();

      if (!lastOperator.equals("0")) {
        try {
          double result = processLastOperator();
          displayResult(result);
          lastNumber = result;
        } catch (DivideByZeroException ignored) {
        }
      } else {
        lastNumber = numberInDisplay;
      }

      clearOnNextDigit = true;
      lastOperator = op;
    }
  }

  private void processEquals() {
    double result;

    if (displayMode != ERROR_MODE) {
      try {
        result = processLastOperator();
        displayResult(result);
      } catch (DivideByZeroException e) {
        displayError("Cannot divide by zero!");
      }

      lastOperator = "0";
    }
  }

  private double processLastOperator() throws DivideByZeroException {
    double result = 0;
    double numberInDisplay = getNumberInDisplay();

    if (lastOperator.equals("/")) {
      if (numberInDisplay == 0)
        throw (new DivideByZeroException());

      result = lastNumber / numberInDisplay;
    }

    if (lastOperator.equals("*"))
      result = lastNumber * numberInDisplay;

    if (lastOperator.equals("-"))
      result = lastNumber - numberInDisplay;

    if (lastOperator.equals("+"))
      result = lastNumber + numberInDisplay;

    return result;
  }

  private void displayResult(double result) {
    setDisplayString(Double.toString(result));
    lastNumber = result;
    displayMode = RESULT_MODE;
    clearOnNextDigit = true;
  }

  private void displayError(String errorMessage) {
    setDisplayString(errorMessage);
    lastNumber = 0;
    displayMode = ERROR_MODE;
    clearOnNextDigit = true;
  }
}