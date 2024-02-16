/*
 * Author: Wes Golliher
 * ClueGame
 * Creates a custom dialog for the player selecting a suggestion
 */

package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PlayerSuggestionDialog extends JDialog {
	
	static JButton submit = new JButton("Submit");
	static Board board = Board.getInstance();
	static JTextField roomText;
	static JComboBox<String> personBox = new JComboBox<String>();
	static JComboBox<String> weaponBox = new JComboBox<String>();
	
	//Creates dialog for suggestions
	public PlayerSuggestionDialog(String roomName, ArrayList<String> weaponNames, ArrayList<String> personNames) {
		personBox = new JComboBox<String>();
		weaponBox = new JComboBox<String>();
		
		setTitle("Make a Suggestion");
		setSize(250,200);
		setLayout(new GridLayout(4,2));
		
		JLabel room = new JLabel("Current Room: ");
		add(room);
		
		roomText = new JTextField(roomName);
		roomText.setEditable(false);
		add(roomText);
		
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
	}
	
	//Return the selected suggestion from the combo box
	public static Solution getSolution() {
		Card room = board.getCard(roomText.getText());
		Card person = board.getCard(personBox.getSelectedItem().toString());
		Card weapon = board.getCard(weaponBox.getSelectedItem().toString());
		Solution sol = new Solution(room, person, weapon);
		return sol;
	}
}
