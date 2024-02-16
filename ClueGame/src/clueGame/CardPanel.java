/*
 * Author: Wes Golliher
 * ClueGame
 * Creates GUI for displaying the cards
 */

package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardPanel extends JPanel {

	private JPanel panel = new JPanel();

	private JPanel peopleHandPanel = new JPanel();
	private JPanel peopleSeenPanel = new JPanel();
	private JPanel roomHandPanel = new JPanel();
	private JPanel roomSeenPanel = new JPanel();
	private JPanel weaponHandPanel = new JPanel();
	private JPanel weaponSeenPanel = new JPanel();

	private ArrayList<String> handPeople = new ArrayList<>();
	private ArrayList<String> seenPeople = new ArrayList<>();
	private ArrayList<String> handRooms = new ArrayList<>();
	private ArrayList<String> seenRooms = new ArrayList<>();
	private ArrayList<String> handWeapons = new ArrayList<>();
	private ArrayList<String> seenWeapons = new ArrayList<>();

	private ArrayList<Color> seenPeopleColors = new ArrayList<Color>();
	private ArrayList<Color> seenRoomColors = new ArrayList<Color>();
	private ArrayList<Color> seenWeaponColors = new ArrayList<Color>();


	public CardPanel() {
		//Create the large panel
		panel.setLayout(new GridLayout(3,1));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));

		//Add everything to the frame
		add(panel, BorderLayout.CENTER);

		//Create the panel for people cards
		JPanel personPanel = new JPanel();
		personPanel.setLayout(new GridLayout(0,1));
		panel.add(personPanel);
		personPanel.setBorder(new TitledBorder(new EtchedBorder(), "People:"));
		personPanel.setPreferredSize(new Dimension(150,200));

		//Create panel for the cards in the hand for people
		peopleHandPanel.setLayout(new GridLayout(0,1));
		personPanel.add(peopleHandPanel);

		//Create panel for people that have been seen
		peopleSeenPanel.setLayout(new GridLayout(0,1));
		personPanel.add(peopleSeenPanel);

		//Create panel for room cards
		JPanel roomPanel = new JPanel();
		roomPanel.setLayout(new GridLayout(2,1));
		roomPanel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms:"));
		panel.add(roomPanel);
		roomPanel.setPreferredSize(new Dimension(150,200));

		//Create panel for the cards in the hand for rooms
		roomHandPanel.setLayout(new GridLayout(0,1));
		roomPanel.add(roomHandPanel);

		//Create panel for rooms that have been seen
		roomSeenPanel.setLayout(new GridLayout(0,1));
		roomPanel.add(roomSeenPanel);

		//Create panel for all weapon cards
		JPanel weaponPanel = new JPanel();
		weaponPanel.setLayout(new GridLayout(2,1));
		weaponPanel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons:"));
		panel.add(weaponPanel);
		weaponPanel.setPreferredSize(new Dimension(150,200));

		//Create panel for the cards in the hand for weapons
		weaponHandPanel.setLayout(new GridLayout(0,1));
		weaponPanel.add(weaponHandPanel);

		//Create panel for weapons that have been seen
		weaponSeenPanel.setLayout(new GridLayout(0,1));
		weaponPanel.add(weaponSeenPanel);

		//Create the card elements
		createSeen();
		createHand();
	}

	//Create cards that have been seen
	private void createSeen() {
		//Remove past people cards to prevent overlap
		peopleSeenPanel.removeAll();

		//Create label for seen people cards
		JLabel peopleSeenLabel = new JLabel("Seen: ");
		peopleSeenPanel.add(peopleSeenLabel);

		//Create cards for seen people, or create a none card if none have been seen
		if (seenPeople.size() == 0) {
			JTextField peopleSeenText = new JTextField("None");
			peopleSeenText.setEditable(false);
			peopleSeenPanel.add(peopleSeenText);
		} else {
			for (int i = 0; i < seenPeople.size(); i++) {
				JTextField peopleSeenText = new JTextField(seenPeople.get(i));
				peopleSeenText.setBackground(seenPeopleColors.get(i));
				peopleSeenText.setEditable(false);
				peopleSeenPanel.add(peopleSeenText);
			}
		}

		//Remove past room cards
		roomSeenPanel.removeAll();

		//Create cards for seen rooms, or create a none card if none have been seen
		JLabel roomSeenLabel = new JLabel("Seen: ");
		roomSeenPanel.add(roomSeenLabel);

		if (seenRooms.size() == 0) {
			JTextField roomSeenText = new JTextField("None");
			roomSeenText.setEditable(false);
			roomSeenPanel.add(roomSeenText);
		} else {
			for (int i = 0; i < seenRooms.size(); i++) {
				JTextField roomSeenText = new JTextField(seenRooms.get(i));
				roomSeenText.setBackground(seenRoomColors.get(i));
				roomSeenText.setEditable(false);
				roomSeenPanel.add(roomSeenText);
			}
		}

		//Remove past weapon cards
		weaponSeenPanel.removeAll();

		//Create label for seen weapon cards
		JLabel weaponSeenLabel = new JLabel("Seen: ");
		weaponSeenPanel.add(weaponSeenLabel);

		//Create cards for seen weapons, or create a none card if none have been seen
		if (seenWeapons.size() == 0) {
			JTextField weaponSeenText = new JTextField("None");
			weaponSeenText.setEditable(false);
			weaponSeenPanel.add(weaponSeenText);
		} else {
			for (int i = 0; i < seenWeapons.size(); i++) {
				JTextField weaponSeenText = new JTextField(seenWeapons.get(i));
				weaponSeenText.setBackground(seenWeaponColors.get(i));
				weaponSeenText.setEditable(false);
				weaponSeenPanel.add(weaponSeenText);
			}
		}
	}

	private void createHand() {
		//Remove past people cards
		peopleHandPanel.removeAll();

		//Create the label for people in hand
		JLabel peopleHandLabel = new JLabel("In hand: ");
		peopleHandPanel.add(peopleHandLabel);

		//Create cards for hand people, or create a none card if none have been seen
		if (handPeople.size() == 0) {
			JTextField peopleHandText = new JTextField("None");
			peopleHandText.setEditable(false);
			peopleHandPanel.add(peopleHandText);
		} else {
			for (int i = 0; i < handPeople.size(); i++) {
				JTextField peopleHandText = new JTextField(handPeople.get(i));
				peopleHandText.setEditable(false);
				peopleHandPanel.add(peopleHandText);
			}
		}

		//Remove past hand rooms
		roomHandPanel.removeAll();

		//Create the label for rooms in hand
		JLabel roomHandLabel = new JLabel("In hand: ");
		roomHandPanel.add(roomHandLabel);

		//Create cards for hand rooms, or create a none card if none have been seen
		if (handRooms.size() == 0) {
			JTextField roomHandText = new JTextField("None");
			roomHandText.setEditable(false);
			roomHandPanel.add(roomHandText);
		} else {
			for (int i = 0; i < handRooms.size(); i++) {
				JTextField roomHandText = new JTextField(handRooms.get(i));
				roomHandText.setEditable(false);
				roomHandPanel.add(roomHandText);
			}
		}

		//Remove past hand weapons
		weaponHandPanel.removeAll();

		//Create the label for weapons in hand
		JLabel weaponHandLabel = new JLabel("In hand: ");
		weaponHandPanel.add(weaponHandLabel);

		//Create cards for hand weapons, or create a none card if none have been seen
		if (handWeapons.size() == 0) {
			JTextField weaponHandText = new JTextField("None");
			weaponHandText.setEditable(false);
			weaponHandPanel.add(weaponHandText);
		} else {
			for (int i = 0; i < handWeapons.size(); i++) {
				JTextField weaponHandText = new JTextField(handWeapons.get(i));
				weaponHandText.setEditable(false);
				weaponHandPanel.add(weaponHandText);
			}
		}
	}

	public static void main(String[] args) {
		//Create panel and display
		CardPanel panel = new CardPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(300, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// test filling in the data
		ComputerPlayer comp = new ComputerPlayer("Mrs Peacock", "Blue");
		ComputerPlayer holder1 = new ComputerPlayer("Miss Scarlett", "Red");
		ComputerPlayer holder2 = new ComputerPlayer("Professor Plum", "Purple");
		ComputerPlayer holder3 = new ComputerPlayer("Mr Green", "Green");
		ComputerPlayer holder4 = new ComputerPlayer("Colonel Mustard", "Orange");
		ComputerPlayer holder5 = new ComputerPlayer("Dr White", "White");

		comp.addToHand(new Card("Pipe", CardType.WEAPON));
		comp.addToHand(new Card("Gun", CardType.WEAPON));
		comp.addToHand(new Card("Living Room", CardType.ROOM));
		comp.addToSeen(new Card("Wrench", CardType.WEAPON), holder1);
		comp.addToSeen(new Card("Rope", CardType.WEAPON), holder2);
		comp.addToSeen(new Card("Garage", CardType.ROOM), holder3);
		comp.addToSeen(new Card("Dining Room", CardType.ROOM), holder3);
		comp.addToSeen(new Card("Foyer", CardType.ROOM), holder3);
		comp.addToSeen(new Card("Bedroom", CardType.ROOM), holder4);
		comp.addToSeen(new Card("Game Room", CardType.ROOM), holder5);
		comp.addToSeen(new Card("Colonel Mustard", CardType.PERSON), holder5);
		//panel.update(comp);
		
		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		board.initialize();
		panel.update(board.getPlayers().get(0));
	}

	//Update the card GUI for a player
	public void update(Player player) {
		//Empty all arrays prior to adding new things
		handWeapons.clear();
		handRooms.clear();
		handPeople.clear();
		seenWeapons.clear();
		seenWeaponColors.clear();
		seenRooms.clear();
		seenRoomColors.clear();
		seenPeople.clear();
		seenPeopleColors.clear();
		
		//Separate all hand cards into types
		for (Card card: player.getHand()) {
			if (card.getCardType() == CardType.WEAPON) {
				handWeapons.add(card.getCardName());
			} else if (card.getCardType() == CardType.ROOM) {
				handRooms.add(card.getCardName());
			} else {
				handPeople.add(card.getCardName());
			}
		}

		//Separate all seen cards into card types
		if (player.getSeen() != null) {
			for (Card card: player.getSeen().keySet()) {
				if (card.getCardType() == CardType.WEAPON) {
					seenWeapons.add(card.getCardName());
					Color color = determineColor(player, card);
					seenWeaponColors.add(color);
				} else if (card.getCardType() == CardType.ROOM) {
					seenRooms.add(card.getCardName());
					Color color = determineColor(player, card);
					seenRoomColors.add(color);
				} else {
					seenPeople.add(card.getCardName());
					Color color = determineColor(player, card);
					seenPeopleColors.add(color);
				}
			}
		}

		//Update the cards
		createSeen();
		createHand();
		panel.repaint();
		validate();
	}

	//Use a switch to determine the color based off the string
	private Color determineColor(Player player, Card card) {
		Color color;
		switch (player.getSeen().get(card).getColor()) {
		case "Red":
			color = Color.red;
			break;
		case "Purple":
			color = Color.magenta;
			break;
		case "Blue":
			color = Color.blue;
			break;
		case "Green":
			color = Color.green;
			break;
		case "Orange":
			color = Color.orange;
			break;
		case "White":
			color = Color.white;
			break;
		default:
			color = Color.black;
			break;
		}
		return color;
	}
}
