import javax.swing.*;
import java.awt.*;

/**
 * @author Jiping Lyu
 * Reference: https://bbs.csdn.net/topics/360044694
 * Using the general code structure from this blog
 */
public class InputPop extends JDialog {
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JLabel jLabelMerberIDTitle = null;
    private JTextField jTextFieldFactor = null;
    private JButton jButtonSure = null;
    private JButton jButtonExit = null;
    private int anInt;
    private final String text;

    public InputPop(JFrame owner, String s,int option) {
        super(owner,s,true);
        this.anInt = option;
        this.text = s;
        initialize();
    }

    private void initialize() {
        this.setSize(332, 198);
        this.setContentPane(getJContentPane());
        this.setLocationRelativeTo(this);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jLabelMerberIDTitle = new JLabel();
            jLabelMerberIDTitle.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12));
            jLabelMerberIDTitle.setText(text);
            jLabelMerberIDTitle.setBounds(new Rectangle(49, 37, 114, 33));
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(jLabelMerberIDTitle, null);
            jContentPane.add(getJTextFieldMerberID(), null);
            jContentPane.add(getJButtonSure(), null);
            jContentPane.add(getJButtonExit(), null);
        }
        return jContentPane;
    }

    private JTextField getJTextFieldMerberID() {
        if (jTextFieldFactor == null) {
            jTextFieldFactor = new JTextField();
            jTextFieldFactor.setBounds(new Rectangle(162, 37, 114, 33));
        }
        return jTextFieldFactor;
    }

    private JButton getJButtonSure() {
        if (jButtonSure == null) {
            jButtonSure = new JButton();
            jButtonSure.setBounds(new Rectangle(61, 98, 70, 33));
            jButtonSure.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12));
            jButtonSure.setText("Sure");
            jButtonSure.addActionListener(e -> {
                String factorText = jTextFieldFactor.getText();

                switch (anInt){
                    /* Float */
                    case 0: {
                        App.tempF = Float.parseFloat(factorText);
                        setVisible(false);
                        return;
                    }
                    /* Int */
                    case 1: {
                        App.tempI = Integer.parseInt(factorText);
                        setVisible(false);
                        return;
                    }
                    default:
                        setVisible(false);
                }
            });
        }
        return jButtonSure;
    }

    private JButton getJButtonExit() {
        if (jButtonExit == null) {
            jButtonExit = new JButton();
            jButtonExit.setBounds(new Rectangle(192, 98, 70, 33));
            jButtonExit.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 12));
            jButtonExit.setText("Cancel");
            jButtonExit.addActionListener(e -> setVisible(false));
        }
        return jButtonExit;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }
}