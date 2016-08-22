package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Model.Card;
import Model.CharacterCard;
import Model.RoomCard;
import Model.WeaponCard;
import controller.Cluedo;
import controller.Game;
import controller.Player;
import util.CluedoError;

/**
 * Created by Adam on 14/08/16.
 * This is the menu panel that is hosted on the right hand side. It has buttons that the user can interact with
 * throughout the game
 */
public class MenuPanel extends JPanel implements ActionListener {

	private final int BUTTON_WIDTH = 120;
	private final int BUTTON_HEIGHT = 40;
	private Cluedo cluedo;
	private Game game;
	private JFrame jFrame;
	private JTextArea textAreaAnnounce;

	/**
	 * Create a new pannel
	 * @param jFrame
	 * @param cluedo
     */
	public MenuPanel(JFrame jFrame, Cluedo cluedo) {
		this.cluedo = cluedo;
		this.game = cluedo.getGame();
		this.jFrame = jFrame;
		initButtons();
		setBackground(new Color(255, 255, 255));
		setPreferredSize(new Dimension(BoardFrame.BOARD_WIDTH - 500, BoardFrame.BOARD_HEIGHT));
	}


	/**
	 * Set up the buttons
	 */
	public void initButtons() {
		// Move Button
		JButton b1 = new JButton("Move");
		b1.setActionCommand("Move");
		b1.setToolTipText("Move the current player, requires you to click on the desired square to move");
		b1.setVerticalTextPosition(AbstractButton.CENTER);
		b1.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		b1.addActionListener(this);
		add(b1, BorderLayout.LINE_END);

		// Show Hand
		b1 = new JButton("Show Hand");
		b1.setActionCommand("Show_Hand");
		b1.setToolTipText("Shows the your current cards in your hand");
		b1.setVerticalTextPosition(AbstractButton.CENTER);
		b1.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		b1.addActionListener(this);
		add(b1, BorderLayout.LINE_END);

		// Suggest
		b1 = new JButton("Suggest");
		b1.setActionCommand("Suggest");
		b1.setToolTipText("Make a suggestion. This uses your players current room");
		b1.setVerticalTextPosition(AbstractButton.CENTER);
		b1.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		b1.addActionListener(this);
		add(b1, BorderLayout.LINE_END);

		// Accuse
		b1 = new JButton("Accuse");
		b1.setActionCommand("Accuse");
		b1.setToolTipText("Accuse a player, weapon, and room. Correct Accusation makes you winner, wrong accusation "
				+ "eliminates you from the game");
		b1.setVerticalTextPosition(AbstractButton.CENTER);
		b1.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		b1.addActionListener(this);
		add(b1, BorderLayout.LINE_END);

		// End turn
		b1 = new JButton("End Turn");
		b1.setActionCommand("End_Turn");
		b1.setToolTipText("Ends the current players turn");
		b1.setVerticalTextPosition(AbstractButton.CENTER);
		b1.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		b1.addActionListener(this);
		add(b1, BorderLayout.LINE_END);

		textAreaAnnounce = new JTextArea("", 2, 2);

		JScrollPane scrollPane = new JScrollPane(textAreaAnnounce);
		textAreaAnnounce.setEditable(false);
		add(textAreaAnnounce, BorderLayout.LINE_END);

	}

	@Override
	/**
	 * A button was clicked
	 */
	public void actionPerformed(ActionEvent e) {
		// WeaponCards -- WC
		List<WeaponCard> wc = WeaponCard.generateObjects();
		final List<Card> weaponCards = new ArrayList<>();
		wc.forEach(c -> weaponCards.add(c));

		// CharactersCards -- CC
		List<CharacterCard> cc = CharacterCard.generateObjects();
		final List<Card> characterCards = new ArrayList<>();
		cc.forEach(c -> characterCards.add(c));

		// RoomCards -- RC
		List<RoomCard> rc = RoomCard.generateObjects();
		final List<Card> roomCards = new ArrayList<>();
		rc.forEach(c -> roomCards.add(c));

		switch (e.getActionCommand()) {

			case "Move":
                if(cluedo.hasPlayerRolledDice){
                    JOptionPane.showMessageDialog(this, "You can only roll the dice once per turn", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
				new DiceDialogue(jFrame, cluedo);
				break;

			case "Show_Hand":
				System.out.println(cluedo.getCurrentPlayer().printCards());
				List<Model.Card> cards = cluedo.getCurrentPlayer().getCards();
				new GenericDialogue(jFrame, cards, cluedo);
				break;

			case "Suggest":
				
				//Weapons
				new AccusationDialog(jFrame, weaponCards, cluedo, 2);
				//characters
				new AccusationDialog(jFrame, characterCards, cluedo, 2);
				break;

			case "Accuse":
				// Weapons
				new AccusationDialog(jFrame, weaponCards, cluedo, 3);
				// Characters
				new AccusationDialog(jFrame, characterCards, cluedo, 3);
                // Rooms
                new AccusationDialog(jFrame, roomCards, cluedo, 3);
                break;

			case "End_Turn":
				cluedo.nextPlayer();
				Player player  = cluedo.getCurrentPlayer();
				String t = "Its now: \n" + player.getCharacter().toString() + "'s turn. \n" + "You are at position: " +
						player.x() + ", " + player.y();
				textAreaAnnounce.setText(t);
				break;
			default:
				throw new CluedoError("Unrecognised button action command");
		}
	}

	/**
	 * Return the text pane
	 * @return
     */
	public JTextArea getTextArea() {
		return this.textAreaAnnounce;
	}
}
