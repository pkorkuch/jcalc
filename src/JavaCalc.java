import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * Mobile Apps and Open Source Final Project: 
 * JavaCalc - a GUI-based Java calculator
 * 
 * @author Patrick Korkuch
 * @version 1.0
 */
public class JavaCalc extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JTextField display;
    private JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, 
                    bAdd, bSubtract, bMult, bDivide, bMod, bExp, 
                    bLeftParen, bRightParen, bEquals, bClear, 
                    bBackspace, bDecimalPoint, bANS;
    private boolean displayIsShowingAnswer = false;
    
    /**
     * Constructor: constructs a JavaCalc with default name
     * of "JavaCalc"
     */
    public JavaCalc() {
        this("JavaCalc");
    }
    
    /**
     * Constructor: constructs a JavaCalc with the given name
     * @param name the name of the calculator frame
     */
    public JavaCalc(String name) {
        super(name);
        initFrame();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    /**
     * Initalizes the components of the frame
     */
    private void initFrame() {
        display = new JTextField();
        b1 = new JButton("1");
        b2 = new JButton("2");
        b3 = new JButton("3");
        b4 = new JButton("4");
        b5 = new JButton("5");
        b6 = new JButton("6");
        b7 = new JButton("7");
        b8 = new JButton("8");
        b9 = new JButton("9");
        b0 = new JButton("0");
        bAdd = new JButton("+");
        bSubtract = new JButton("-");
        bMult = new JButton("*");
        bDivide = new JButton("/");
        bMod = new JButton("%");
        bExp = new JButton("^"); 
        bLeftParen = new JButton("(");
        bRightParen = new JButton(")");
        bEquals = new JButton("=");
        bClear = new JButton("C");
        bBackspace = new JButton("\u2190");
        bDecimalPoint = new JButton(".");
        bANS = new JButton("ANS");
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
        b0.addActionListener(this);
        bAdd.addActionListener(this);
        bSubtract.addActionListener(this);
        bMult.addActionListener(this);
        bDivide.addActionListener(this);
        bMod.addActionListener(this);
        bExp.addActionListener(this);
        bLeftParen.addActionListener(this);
        bRightParen.addActionListener(this);
        bEquals.addActionListener(this);
        bClear.addActionListener(this);
        bBackspace.addActionListener(this);
        bDecimalPoint.addActionListener(this);
        bANS.addActionListener(this);
        
        //Exponents are unsupported as of now
        bExp.setEnabled(false);
        
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        
        Container contentPane = this.getContentPane();
        
        contentPane.setLayout(new GridBagLayout());
        ((JComponent)contentPane).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        GridBagConstraints gb = new GridBagConstraints();
        gb.ipadx = 10;
        gb.ipady = 10;
        
        gb.gridx = 0;
        gb.gridy = 0;
        gb.fill = GridBagConstraints.BOTH;
        gb.gridwidth = 3;
        contentPane.add(display, gb);
        //gb.fill = GridBagConstraints.NONE;
        gb.gridwidth = 1;
        gb.gridx = 3;
        contentPane.add(bBackspace, gb);
        
        gb.gridy = 1;
        gb.gridx = 0;
        contentPane.add(bMod, gb);
        gb.gridx = 1;
        contentPane.add(bLeftParen, gb);
        gb.gridx = 2;
        contentPane.add(bRightParen, gb);
        gb.gridx = 3;
        contentPane.add(bANS, gb);
        
        gb.gridy = 2;
        gb.gridx = 0;
        contentPane.add(bAdd, gb);
        gb.gridx = 1;
        contentPane.add(bSubtract, gb);
        gb.gridx = 2;
        contentPane.add(bMult, gb);
        gb.gridx = 3;
        contentPane.add(bDivide, gb);
        
        gb.gridy = 3;
        gb.gridx = 0;
        contentPane.add(b7, gb);
        gb.gridx = 1;
        contentPane.add(b8, gb);
        gb.gridx = 2;
        contentPane.add(b9, gb);
        gb.gridx = 3;
        contentPane.add(bAdd, gb);
        
        gb.gridy = 4;
        gb.gridx = 0;
        contentPane.add(b4, gb);
        gb.gridx = 1;
        contentPane.add(b5, gb);
        gb.gridx = 2;
        contentPane.add(b6, gb);
        gb.gridx = 3;
        contentPane.add(bSubtract, gb);
        
        gb.gridy = 5;
        gb.gridx = 0;
        contentPane.add(b1, gb);
        gb.gridx = 1;
        contentPane.add(b2, gb);
        gb.gridx = 2;
        contentPane.add(b3, gb);
        gb.gridx = 3;
        contentPane.add(bMult, gb);
        
        gb.gridy = 6;
        gb.gridx = 0;
        contentPane.add(b0, gb);
        gb.gridx = 1;
        contentPane.add(bClear, gb);
        gb.gridx = 2;
        contentPane.add(bDecimalPoint, gb);
        gb.gridx = 3;
        contentPane.add(bDivide, gb);
        
        gb.gridy = 7;
        gb.gridx = 0;
        gb.gridwidth = 4;
        contentPane.add(bEquals, gb);
        
        this.pack();
        this.setResizable(false);
    }
    
    /**
     * Action listener: implements ActionListener.actionPerformed
     */
    @Override
    public void actionPerformed(ActionEvent arg0) {
        Object obj = arg0.getSource();
        if (obj == bClear) {
            display.setText("");
            displayIsShowingAnswer = false;
        } else if (obj == bEquals) {
            String expr = display.getText();
            if (expr.length() > 0) {
                try {
                    BigDecimal result = Util.evaluatePostfix(Util.shuntingYard(expr));
                    display.setText(result.toPlainString());
                    displayIsShowingAnswer = true;
                } catch (IllegalArgumentException iae) {
                    JOptionPane.showMessageDialog(this, "Error: syntax", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (obj == bBackspace) {
            if (displayIsShowingAnswer) {
                display.setText("");
                displayIsShowingAnswer = false;
            } else {
                String text = display.getText();
                if (text.length() > 0) {
                    if (text.charAt(text.length() - 1) == 'S') {
                        text = text.substring(0, text.length() - 3);
                    } else {
                        text = text.substring(0, text.length() - 1); 
                    }
                }
                display.setText(text);
            }
        } else {
            String text = ((JButton) obj).getText();
            if (displayIsShowingAnswer) {
                display.setText("");
                displayIsShowingAnswer = false;
                if (Util.isOperator(text.toCharArray()[0])) {
                    display.setText("ANS" + text);
                    return;
                }
            }
            display.setText(display.getText() + text);
        }
    }
    
    public static void main(String[] args) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
        new JavaCalc();
    }
}