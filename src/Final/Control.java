package Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Control  extends JFrame implements ActionListener  {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;

    private MineSweapPart mineSweap;
    private JButton restartBtn;

    private JLabel msg;

    public static JLabel msg_static;

    public Control() {
        setTitle("Control Panel");
        setSize(WIDTH, HEIGHT);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mineSweap = new MineSweapPart();

        createContents();
        setVisible(true);
    }

    private void createContents()
    {
        JTextField colBox;
        JTextField rowBox;

        colBox = new JTextField(2);
        rowBox = new JTextField(2);

        JLabel rowLabel = new JLabel("row: ");
        JLabel colLabel = new JLabel("col: ");
        JLabel actualMineLabel = new JLabel("Actual mine left: ");
        msg = new JLabel();
        msg_static = msg;

        JButton setBtn = new JButton("Set");
        restartBtn = new JButton("Restart");
        restartBtn.setBounds(20, 20, 50, 30);
        restartBtn.setBackground(Color.green);
        setBtn.setBackground(Color.orange);
        setBtn.setBounds(100, 100, 50, 30);

        this.add(rowLabel);
        this.add(rowBox);
        this.add(colLabel);
        this.add(colBox);
        this.add(setBtn);
        this.add(restartBtn);
        this.add(actualMineLabel);
        this.add(msg);

        restartBtn.addActionListener(this);

    } // end createContents


    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == restartBtn) {
           System.out.println("restartBtn clicked!");

           this.remove(mineSweap);
           mineSweap.setVisible(false);

           mineSweap = new MineSweapPart();

           //SwingUtilities.updateComponentTreeUI(this);
       }
    }


    public static void main(String[] args) {
       new Control();
    }
}
