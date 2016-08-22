package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import controller.Cluedo;

public class CharacterDialouge extends JDialog implements ActionListener {

    BoardFrame owner;
    Cluedo cluedo;
    JButton play;
    JRadioButton three;
    JRadioButton four;
    JRadioButton five;
    JRadioButton six;

    ButtonGroup group;
    int numPlayer = 3;

    public CharacterDialouge(BoardFrame frame, Cluedo cluedo) {

        // In initialization code:
        // Create the radio buttons.

        this.setAlwaysOnTop(true);
        this.owner = frame;
        this.cluedo = cluedo;

        JDialog temp = new JDialog(this);
        temp.setLayout(new FlowLayout());

        three = new JRadioButton("3");
        three.setActionCommand("3");
        three.setSelected(true);


        four = new JRadioButton("4");
        four.setActionCommand("4");

        five = new JRadioButton("5");
        five.setActionCommand("5");

        six = new JRadioButton("6");
        six.setActionCommand("6");

        JLabel label = new JLabel("How many Players you want to play with?");
        temp.add(label);

        // Group the radio buttons.
        group = new ButtonGroup();
        group.add(three);
        group.add(four);
        group.add(five);
        group.add(six);

        // Register a listener for the radio buttons.
        three.addActionListener(this);
        four.addActionListener(this);
        five.addActionListener(this);
        six.addActionListener(this);

        temp.add(three);
        temp.add(four);
        temp.add(five);
        temp.add(six);

        temp.setVisible(true);

        play = new JButton("play");
        play.setActionCommand("play");
        play.addActionListener(this);
        temp.add(play, BorderLayout.NORTH);
        temp.setResizable(false);
        temp.setTitle("Select number of players");
        setSize(800, 800);
        temp.setSize(800, 800);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("3")) {
            this.numPlayer = 3;
            System.out.println("3");
        } else if (e.getActionCommand().equals("4")) {
            this.numPlayer = 4;
            System.out.println("4");
        } else if (e.getActionCommand().equals("5")) {
            this.numPlayer = 5;
            System.out.println("5");
        } else if (e.getActionCommand().equals("6")) {
            this.numPlayer = 6;
            System.out.println("6");
        } else if (e.getActionCommand().equals("play")) {
            dispose();
            SelectCharacters mansour = new SelectCharacters(this.owner, this.numPlayer, this.cluedo);
            mansour.setVisible(true);
        }
    }
}
