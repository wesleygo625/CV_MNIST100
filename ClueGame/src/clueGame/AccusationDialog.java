/*
 * Author: Wes Golliher
 * ClueGame
 * Creates a custom dialog for the player selecting an accusation
 */

package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class AccusationDialog extends JDialog {
	
	static JButton submit = new JButton("Submit");
	static JButton cancel = new JButton("Cancel");
	static JComboBox<String> roomBox = new JComboBox<String>();
	static JComboBox<String> personBox = new JComboBox<String>();
	static JComboBox<String> weaponBox = new JComboBox<String>();
	static Board board = Board.getInstance();
	
	//Creates the accusation dialog
	public AccusationDialog(ArrayList<String> roomNames, ArrayList<String> weaponNames, ArrayList<String> personNames) {
		setTitle("Make an Accusation");
		setSize(250,200);
		setLayout(new GridLayout(4,2));
		
		JLabel room = new JLabel("Current Room: ");
		add(room);
		
		for (String p: roomNames) {
			roomBox.addItem(p);
		}
		add(roomBox);
		
		JLabel person = new JLabel("Person: ");
		add(person);
		
		for (String p: personNames) {
			personBox.addItem(p);
		}
		add(personBox);
		
		JLabel weapon = new JLabel("Weapon: ");
		add(weapon);
		
		for (String w: weaponNames) {
			weaponBox.addItem(w);
		}
		add(weaponBox);
		
		add(submit);
		
		add(cancel);
	}
	
	//Returns the accusation in the combo boxes
	public static Solution getSolution() {
		Card room = board.getCard(roomBox.getSelectedItem().toString());
		Card person = board.getCard(personBox.getSelectedItem().toString());
		Card weapon = board.getCard(weaponBox.getSelectedItem().toString());
		Solution sol = new Solution(room, person, weapon);
		return sol;
	}
}
