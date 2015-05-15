import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Ivan Kulekov (ivankulekov10@gmail.com)
 * @since May 15 , 2015 14:53
 */
public class CustomABOUTDialog extends JDialog implements ActionListener {
  JButton jbnOk;

  CustomABOUTDialog(JFrame parent, String title, boolean modal) {
    super(parent, title, modal);
    setBackground(Color.black);

    JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));

    StringBuffer text = new StringBuffer();

    JTextArea jtAreaAbout = new JTextArea(5, 21);
    jtAreaAbout.setText(text.toString());
    jtAreaAbout.setFont(new Font("Times New Roman", 1, 13));
    jtAreaAbout.setEditable(false);

    p1.add(jtAreaAbout);
    p1.setBackground(Color.red);
    getContentPane().add(p1, BorderLayout.CENTER);

    JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jbnOk = new JButton(" OK ");
    jbnOk.addActionListener(this);

    p2.add(jbnOk);
    getContentPane().add(p2, BorderLayout.SOUTH);

    setLocation(408, 270);
    setResizable(false);

    addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                          Window aboutDialog = e.getWindow();
                          aboutDialog.dispose();
                        }
                      }
    );

    pack();
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == jbnOk) {
      this.dispose();
    }
  }
}