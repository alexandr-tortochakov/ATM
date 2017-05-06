package ru.tortochakov.atm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.BoxLayout.X_AXIS;

public class GUI {
    JFrame frame = new JFrame("Cash Machine");
//    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel displayPanel = new JPanel();
    JPanel moneyPanel = new JPanel();
    JPanel sumPanel = new JPanel();
    ATM atm = new ATM();
    Map<Integer, JTextField> worthsFields = new HashMap<>();

    JButton displayButton = new JButton("Display State");
    JButton takeButton = new JButton("Take bills");
    JButton giveButton = new JButton("Give bills");
    JTextArea displayArea = new JTextArea();
    JTextField wantedSumField = new JTextField();
    JTextField w10Field = new JTextField();
    JTextField w50Field = new JTextField();
    JTextField w100Field = new JTextField();
    JTextField w500Field = new JTextField();
    JTextField w1000Field = new JTextField();
    JTextField w5000Field = new JTextField();
    JLabel sumLabel = new JLabel("Enter amount of money");
    JLabel w10FieldLabel = new JLabel("10");
    JLabel w50FieldLabel = new JLabel("50");
    JLabel w100FieldLabel = new JLabel("100");
    JLabel w500FieldLabel = new JLabel("500");
    JLabel w1000FieldLabel = new JLabel("1000");
    JLabel w5000FieldLabel = new JLabel("5000");


    public void go() {
        frame.setSize(450, 250);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        buttonPanel = mainPanel;
//        displayPanel = mainPanel;
//        moneyPanel = mainPanel;
//        sumPanel = mainPanel;

        //buttonPanel.setSize(600,10);
//        mainPanel.add(buttonPanel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(displayButton);
        buttonPanel.add(takeButton);
        buttonPanel.add(giveButton);
        frame.getContentPane().add(buttonPanel, BorderLayout.EAST);

        //displayPanel.setSize(600, 50);
//        mainPanel.add(displayPanel);
        displayArea.setRows(10);
        displayArea.setColumns(40);
        displayArea.setEditable(false);
        displayPanel.add(displayArea);
        frame.getContentPane().add(displayPanel, BorderLayout.NORTH);

        w10Field.setColumns(4);
        w50Field.setColumns(4);
        w100Field.setColumns(4);
        w500Field.setColumns(4);
        w1000Field.setColumns(4);
        w5000Field.setColumns(4);

        //moneyPanel.setSize(600, 10);
//        mainPanel.add(moneyPanel);
        moneyPanel.setLayout(new GridLayout(3, 2));
        w10FieldLabel.setLabelFor(w10Field);
        moneyPanel.add(w10FieldLabel);
        moneyPanel.add(w10Field);
        w50FieldLabel.setLabelFor(w50Field);
        moneyPanel.add(w50FieldLabel);
        moneyPanel.add(w50Field);
        w100FieldLabel.setLabelFor(w100Field);
        moneyPanel.add(w100FieldLabel);
        moneyPanel.add(w100Field);
        w500FieldLabel.setLabelFor(w500Field);
        moneyPanel.add(w500FieldLabel);
        moneyPanel.add(w500Field);
        w1000FieldLabel.setLabelFor(w1000Field);
        moneyPanel.add(w1000FieldLabel);
        moneyPanel.add(w1000Field);
        w5000FieldLabel.setLabelFor(w5000Field);
        moneyPanel.add(w5000FieldLabel);
        moneyPanel.add(w5000Field);
        frame.getContentPane().add(moneyPanel, BorderLayout.WEST);

        worthsFields.put(10, w10Field);
        worthsFields.put(50, w50Field);
        worthsFields.put(100, w100Field);
        worthsFields.put(500, w500Field);
        worthsFields.put(1000, w1000Field);
        worthsFields.put(5000, w5000Field);

//        mainPanel.add(sumPanel);
        wantedSumField.setColumns(10);
        sumLabel.setLabelFor(wantedSumField);
        sumPanel.add(sumLabel);
        sumPanel.add(wantedSumField);
        frame.getContentPane().add(sumPanel, BorderLayout.CENTER);

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.setText(atm.displayState());
            }
        });

        giveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sum = Integer.parseInt(wantedSumField.getText());
                Map<Integer, Integer> bills = atm.giveBills(sum, true);
                if (bills == null) {
                    displayArea.setText("Do not have enough money");
                    return;
                }
                resetWorthFields();
                for (Map.Entry<Integer, Integer> entry : bills.entrySet()
                        ) {
                    worthsFields.get(entry.getKey()).setText(Integer.toString(entry.getValue()));
                }
                displayArea.setText(atm.displayState());
            }
        });

        takeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Integer, Integer> bills = new HashMap<>();
                for (Map.Entry<Integer, JTextField> entry : worthsFields.entrySet()
                        ) {
                    int nBills = Integer.parseInt(entry.getValue().getText());
                    if (nBills > 0) {
                        bills.put(entry.getKey(), nBills);
                    }
                }
                if (atm.takeBills(bills)) {
                    displayArea.setText(atm.displayState());
                    resetWorthFields();
                } else {
                    displayArea.setText("Cannot take your money");
                }
            }
        });
    }

    private void  resetWorthFields() {
        for (Map.Entry<Integer, JTextField> entry : worthsFields.entrySet()
                ) {
            entry.getValue().setText("0");
        }
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
        }
        GUI gui = new GUI();
        gui.go();
    }
}
