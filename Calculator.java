package Tushar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Calculator extends JFrame {
    private JTextField displayField;
    private JButton[] numberButtons;
    private JButton[] operatorButtons;
    private JButton decimalButton;
    private JButton deleteButton;
    private JButton squareButton;
    private JButton sqrtButton;

    private double num1, num2, result;
    private String operator;

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setSize(400, 600);

        displayField = new JTextField();
        displayField.setForeground(Color.black);
        displayField.setBackground(Color.lightGray);
        displayField.setEditable(false);
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setFont(new Font("Arial", Font.PLAIN, 40));
        displayField.setPreferredSize(new Dimension(400, 80));
        add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 2, 2));
        buttonPanel.setPreferredSize(new Dimension(400, 400));
        buttonPanel.setBackground(Color.DARK_GRAY);

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 40));
        }

        operatorButtons = new JButton[4];
        String[] operators = {"+", "-", "*", "/"};
        for (int i = 0; i < 4; i++) {
            operatorButtons[i] = new JButton(operators[i]);
            operatorButtons[i].setFont(new Font("Arial", Font.BOLD, 40));
        }

        decimalButton = new JButton(".");
        decimalButton.setFont(new Font("Arial", Font.BOLD, 40));

        deleteButton = new JButton("Del");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 40));

        squareButton = new JButton("x²");
        squareButton.setFont(new Font("Arial", Font.BOLD, 40));

        sqrtButton = new JButton("√");
        sqrtButton.setFont(new Font("Arial", Font.BOLD, 40));

        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(operatorButtons[0]);
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(operatorButtons[1]);
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(operatorButtons[2]);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(decimalButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(operatorButtons[3]);
        buttonPanel.add(squareButton);
        buttonPanel.add(sqrtButton);

        JButton clearButton = new JButton("C");
        clearButton.setFont(new Font("Arial", Font.BOLD, 40));
        buttonPanel.add(clearButton);

        JButton equalsButton = new JButton("=");
        equalsButton.setFont(new Font("Arial", Font.BOLD, 40));
        buttonPanel.add(equalsButton);

        for (int i = 0; i < 10; i++) {
            int number = i;
            numberButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    displayField.setText(displayField.getText() + number);
                }
            });
        }

        decimalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!displayField.getText().contains(".")) {
                    displayField.setText(displayField.getText() + ".");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = displayField.getText();
                if (text.length() > 0) {
                    displayField.setText(text.substring(0, text.length() - 1));
                }
            }
        });

        squareButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!displayField.getText().isEmpty() && !displayField.getText().equals("-")) {
                    num1 = Double.parseDouble(displayField.getText());
                    result = num1 * num1;
                    displayField.setText(String.valueOf(result));
                }
            }
        });

        sqrtButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!displayField.getText().isEmpty() && !displayField.getText().equals("-")) {
                    num1 = Double.parseDouble(displayField.getText());
                    if (num1 >= 0) {
                        result = Math.sqrt(num1);
                        displayField.setText(String.valueOf(result));
                    } else {
                        JOptionPane.showMessageDialog(Calculator.this, "Square root of negative number is not defined", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayField.setText("");
            }
        });

        for (int i = 0; i < 4; i++) {
            String operator = operators[i];
            operatorButtons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!displayField.getText().isEmpty() && !displayField.getText().equals("-")) {
                        if (operator.equals("-") && (displayField.getText().isEmpty() || displayField.getText().endsWith(operator))) {
                            displayField.setText(displayField.getText() + operator);
                        } else {
                            num1 = Double.parseDouble(displayField.getText());
                            Calculator.this.operator = operator;
                            displayField.setText(displayField.getText() + operator);
                        }
                    }
                }
            });
        }

        equalsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!displayField.getText().isEmpty() && !displayField.getText().equals("-") && operator != null) {
                    String[] operands = displayField.getText().split("\\Q" + operator + "\\E");
                    if (operands.length == 2) {
                        num1 = Double.parseDouble(operands[0]);
                        num2 = Double.parseDouble(operands[1]);
                        switch (Calculator.this.operator) {
                            case "+":
                                result = num1 + num2;
                                break;
                            case "-":
                                result = num1 - num2;
                                break;
                            case "*":
                                result = num1 * num2;
                                break;
                            case "/":
                                if (num2 != 0) {
                                    result = num1 / num2;
                                } else {
                                    JOptionPane.showMessageDialog(Calculator.this, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                break;
                        }
                        displayField.setText(String.valueOf(result));
                    } else {
                        JOptionPane.showMessageDialog(Calculator.this, "Invalid expression", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        add(buttonPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
    }
                        }
                                     
