import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since May 14 , 2015 17:00
 */
public class Calculator extends JFrame {

  private static final String NUMBER_PROPERTY = "NUMBER_PROPERTY";
  private static final String OPERATOR_PROPERTY = "OPERATOR_PROPERTY";
  private static final String FIRST = "FIRST";
  private static final String VALID = "VALID";

  private String status;
  private int previousOperation;
  private double lastValue;
  private JTextArea lcdDisplay;
  private JLabel errorDisplay;

  /**
   * Create constructor for the Calculator class and define the JPanel Properties.
   */
  public Calculator() {
    super("Calculator");

    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel numberPanel = buildNumberPanel();
    JPanel operatorPanel = buildOperatorPanel();
    JPanel clearPanel = buildClearPanel();
    lcdDisplay = new JTextArea();
    lcdDisplay.setFont(new Font("Dialog", Font.ITALIC, 16));
    mainPanel.add(clearPanel, BorderLayout.SOUTH);
    mainPanel.add(numberPanel, BorderLayout.CENTER);
    mainPanel.add(operatorPanel, BorderLayout.EAST);
    mainPanel.add(lcdDisplay, BorderLayout.NORTH);

    errorDisplay = new JLabel(" ");
    errorDisplay.setFont(new Font("Dialog", Font.ITALIC, 12));

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(mainPanel, BorderLayout.CENTER);
    getContentPane().add(errorDisplay, BorderLayout.SOUTH);

    pack();
    resetState();
  }

  /**
   * When press number , if the number is equals to null throw exception.
   */
  private final ActionListener numberListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      JComponent source = (JComponent) e.getSource();
      Integer number = (Integer) source.getClientProperty(NUMBER_PROPERTY);
      if (number == null) {
        throw new IllegalStateException("No NUMBER_PROPERTY on component");
      }

      numberButtonPressed(number.intValue());
    }
  };

  /**
   * When press a decimal format , call decimalButtonPressed().
   */
  private final ActionListener decimalListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      decimalButtonPressed();
    }
  };

  /**
   * When press an operator button , if it's null throw new exception.
   */
  private final ActionListener operatorListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      JComponent source = (JComponent) e.getSource();
      Integer opCode = (Integer) source.getClientProperty(OPERATOR_PROPERTY);
      if (opCode == null) {
        throw new IllegalStateException("No OPERATOR_PROPERTY on component");
      }

      operatorButtonPressed(opCode);
    }
  };

  /**
   * when press the clear button , just reset state.
   */
  private final ActionListener clearListener = new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      resetState();
    }
  };

  /**
   * when press the button , add listener.
   *
   * @param number the button pressed.
   * @return the button which is active.
   */
  private JButton buildNumberButton(int number) {
    JButton button = new JButton(Integer.toString(number));
    button.putClientProperty(NUMBER_PROPERTY, Integer.valueOf(number));
    button.addActionListener(numberListener);
    return button;
  }

  /**
   * when press operator button.
   *
   * @param symbol is the operator.
   * @param opType is the number.
   * @return the action.
   */
  private JButton buildOperatorButton(String symbol, int opType) {
    JButton plus = new JButton(symbol);
    plus.putClientProperty(OPERATOR_PROPERTY, Integer.valueOf(opType));
    plus.addActionListener(operatorListener);
    return plus;
  }

  /**
   * Create a panel where we define the calculator style.
   *
   * @return the build panel.
   */
  public JPanel buildNumberPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(4, 3));

    panel.add(buildNumberButton(7));
    panel.add(buildNumberButton(8));
    panel.add(buildNumberButton(9));
    panel.add(buildNumberButton(4));
    panel.add(buildNumberButton(5));
    panel.add(buildNumberButton(6));
    panel.add(buildNumberButton(1));
    panel.add(buildNumberButton(2));
    panel.add(buildNumberButton(3));

    JButton buttonDec = new JButton(".");
    buttonDec.addActionListener(decimalListener);
    panel.add(buttonDec);

    panel.add(buildNumberButton(0));

    JButton buttonExit = new JButton("EXIT");
    buttonExit.setMnemonic(KeyEvent.VK_C);
    buttonExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }
    });
    panel.add(buttonExit);
    return panel;

  }

  /**
   * build the operator buttons in the Calculator panel.
   *
   * @return the build panel.
   */
  public JPanel buildOperatorPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(4, 1));

    panel.add(buildOperatorButton("+", Operator.PLUS));
    panel.add(buildOperatorButton("-", Operator.MINUS));
    panel.add(buildOperatorButton("*", Operator.MULTIPLY));
    panel.add(buildOperatorButton("/", Operator.DIVIDE));
    return panel;
  }

  /**
   * Build the clear and equals panel buttons.
   *
   * @return the build panel.
   */
  public JPanel buildClearPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(1, 3));

    JButton clear = new JButton("C");
    clear.addActionListener(clearListener);
    panel.add(clear);

    JButton CEntry = new JButton("CE");
    CEntry.addActionListener(clearListener);
    panel.add(CEntry);

    panel.add(buildOperatorButton("=", Operator.EQUALS));

    return panel;
  }

  /**
   * Define when press the button and max digits allowed.
   *
   * @param i is the pressed button.
   */
  public void numberButtonPressed(int i) {
    String displayText = lcdDisplay.getText();
    String valueString = Integer.toString(i);

    if (("0".equals(displayText)) || (FIRST.equals(status))) {
      displayText = "";
    }

    int maxLength = (displayText.indexOf(".") >= 0) ? 21 : 20;
    if (displayText.length() + valueString.length() <= maxLength) {
      displayText += valueString;
      clearError();
    } else {
      setError("Reached the 20 digit max");
    }

    lcdDisplay.setText(displayText);
    status = VALID;
  }

  /**
   * When press the operator button.
   *
   * @param newOperation is the concrete operator.
   */
  public void operatorButtonPressed(int newOperation) {
    Double displayValue = Double.valueOf(lcdDisplay.getText());

    switch (previousOperation) {
      case Operator.PLUS:
        displayValue = lastValue + displayValue;
        commitOperation(newOperation, displayValue);
        break;
      case Operator.MINUS:
        displayValue = lastValue - displayValue;
        commitOperation(newOperation, displayValue);
        break;
      case Operator.MULTIPLY:
        displayValue = lastValue * displayValue;
        commitOperation(newOperation, displayValue);
        break;
      case Operator.DIVIDE:
        if (displayValue == 0) {
          setError("ERROR: Division by Zero");
        } else {
          displayValue = lastValue / displayValue;
          commitOperation(newOperation, displayValue);
        }
        break;
      case Operator.EQUALS:
        commitOperation(newOperation, displayValue);
    }
  }

  /**
   * When press decimal button.
   */
  public void decimalButtonPressed() {
    String displayText = lcdDisplay.getText();
    if (FIRST.equals(status)) {
      displayText = "0";
    }

    if (!displayText.contains(".")) {
      displayText = displayText + ".";
    }
    lcdDisplay.setText(displayText);
    status = VALID;
  }

  /**
   * If message is empty.
   *
   * @param errorMessage is the message.
   */
  private void setError(String errorMessage) {
    if (errorMessage.trim().equals("")) {
      errorMessage = " ";
    }
    errorDisplay.setText(errorMessage);
  }

  /**
   * To reset the error messages.
   */
  private void clearError() {
    status = FIRST;
    errorDisplay.setText(" ");
  }

  /**
   * Define the commit operation.
   *
   * @param operation define the operation type.
   * @param result    is the result.
   */
  private void commitOperation(int operation, double result) {
    status = FIRST;
    lastValue = result;
    previousOperation = operation;
    lcdDisplay.setText(String.valueOf(result));
  }

  /**
   * Resets the program state.
   */
  void resetState() {
    clearError();
    lastValue = 0;
    previousOperation = Operator.EQUALS;

    lcdDisplay.setText("0");
  }


}
