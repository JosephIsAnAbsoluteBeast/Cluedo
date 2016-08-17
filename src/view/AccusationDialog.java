package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import cards.Card;
import cards.*;
import cards.CharacterCard;
import cluedo.Cluedo;
import util.Accusation;
import util.CluedoError;

/**
 * Created by javahemans on 14/08/16.
 */
public class AccusationDialog extends JDialog implements ActionListener {

    Cluedo cluedo;
    List<Card> cards;

    int index = -1;

    public AccusationDialog(JFrame parent, List<Card> cards, Cluedo cluedo) {
		super((Window)null);
        //super(parent, "Selecting Characters ...", false);
        this.cluedo = cluedo;
        this.cards = cards;
        setSize(800, 800);
        setVisible(true);

        for (Card c : cards) {
            setLayout(new GridLayout(0, 3));
			if (c instanceof CharacterCard) {
				JButton tmp = new JButton(new ImageIcon("images/characters/" + c.getValue() + ".png"));
				tmp.addActionListener(this);
				tmp.setActionCommand(String.valueOf(++index));
				this.add(tmp);

			} else if (c instanceof RoomCard) {
				JButton tmp = new JButton(new ImageIcon("images/rooms/" + c.getValue() + ".png"));
				tmp.addActionListener(this);
				tmp.setActionCommand(String.valueOf(++index));
				this.add(tmp);

			} else if (c instanceof WeaponCard) {
				JButton tmp = new JButton(new ImageIcon("images/weapons/" + c.getValue() + ".png"));
				tmp.addActionListener(this);
				int z = ++index;
				tmp.setActionCommand(String.valueOf(z));
				this.add(tmp);
			}
		}
		setModal(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Accusation.selectedCards.add(this.cards.get(Integer.valueOf(e.getActionCommand())));
		System.err.println(this.cards.get(Integer.valueOf(e.getActionCommand())));
		dispose();
	}


}
