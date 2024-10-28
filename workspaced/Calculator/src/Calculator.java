import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Calculator extends JFrame {

    private JTextField display;
    private operatii calculator;
    private double currentResult;
    private String operator;

    public Calculator() {
        calculator = new operatii();
        currentResult = 0;
        operator = "";
        createUI();
    }

    private void createUI() {
        setTitle("Calculator de Buzunar");
        setSize(400, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 32));
        display.setPreferredSize(new Dimension(400,100));
        getContentPane().add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        addButtons(buttonPanel);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);

        
        display.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char key = e.getKeyChar();
                if (Character.isDigit(key) || "+-*/".indexOf(key) >= 0) {
                    handleInput(String.valueOf(key));
                } else if (key == KeyEvent.VK_ENTER) {
                    calculateResult();
                } else if (key == KeyEvent.VK_BACK_SPACE) {
                    clear();
                }
            }
        });
    }

    private void addButtons(JPanel panel) {
        String[] buttonLabels = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };
    

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Transforma", Font.BOLD, 24));
            button.addActionListener(new ButtonClickListener());
            if (label.matches("[0-9]")) { // 
                button.setBackground(new Color(173,216,230)); 
                button.setForeground(Color.white); 
            } else {
                button.setBackground(new Color(255, 213, 128)); 
                button.setForeground(Color.white); 
            }
            panel.add(button);
        }
      
     
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "C":
                    clear();
                    break;
                case "=":
                    calculateResult();
                    break;
                default:
                    handleInput(command);
                    break;
            }
        }
    }

    private void clear() {
        display.setText("");
        currentResult = 0;
        operator = "";
    }

    private void handleInput(String command) {
        if ("+-*/".contains(command)) {
            if (!display.getText().isEmpty()) {
            	
                double currentOperand = Double.parseDouble(display.getText());
                if (operator.isEmpty()) {
                    currentResult = currentOperand;        
                } else {
                    currentResult = performCalculation(currentResult, currentOperand, operator);
                }
                operator = command; 
                display.setText(""); 
            }
        } else {
            display.setText(display.getText() + command);
        }
    }

    private double performCalculation(double a, double b, String operator) {
        switch (operator) {
            case "+":
                return calculator.add(a, b);
            case "-":
                return calculator.subtract(a, b);
            case "*":
                return calculator.multiply(a, b);
            case "/":
                return calculator.divide(a, b);
            default:
                return 0;
        }
    }

    private void calculateResult() {
        if (display.getText().isEmpty() || operator == null || operator.isEmpty()) {
            display.setText("Input invalid");
            return;
        }

        double secondOperand;
        try {
            secondOperand = Double.parseDouble(display.getText());
            currentResult = performCalculation(currentResult, secondOperand, operator);
            display.setText(String.valueOf(currentResult)); 
            operator = ""; 
        } catch (NumberFormatException e) {
            display.setText("Input invalid");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator operatii = new Calculator();
            operatii.setVisible(true);
        });
    }
}



