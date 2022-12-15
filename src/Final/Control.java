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

        JLabel actualMineLabel = new JLabel("Actual mine left: ");
        msg = new JLabel();
        msg_static = msg;

        restartBtn = new JButton("Restart");
        restartBtn.setBounds(20, 20, 50, 30);
        restartBtn.setBackground(Color.green);

        this.add(restartBtn);
        this.add(actualMineLabel);
        this.add(msg);

        restartBtn.addActionListener(this);

    } // end createContents


    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == restartBtn) {

           this.remove(mineSweap);
           mineSweap.setVisible(false);

           mineSweap = new MineSweapPart();

       }
    }


    public static void main(String[] args) {
       new Control();
    }
}
