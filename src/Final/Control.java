package Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Control  extends JFrame implements ActionListener  {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;

    MineSweapPart mineSweap;
    JButton restartBtn;

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
