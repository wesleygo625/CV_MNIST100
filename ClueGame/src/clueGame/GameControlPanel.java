/*
 * Author: Wes Golliher
 * ClueGame
 * Creates GUI for the control panel of the game
 */

package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	
	JTextField playerName = new JTextField();
	JTextField rollValue = new JTextField();
	JTextField guess = new JTextField();
	JTextField guessResult = new JTextField();
	JButton nextTurnButton = new JButton("Next Turn");
	Board board = Board.getInstance();
	static GameControlPanel panel = new GameControlPanel();
	JButton accusationButton = new JButton("Make Accusation");

	public GameControlPanel()  {
		//Create largest panel
		JPanel basePanel = new JPanel();
		basePanel.setLayout(new GridLayout(2,0));
		
		//Create top panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,4));
		basePanel.add(topPanel);
		
		//Create panel for current player
		JPanel currentPlayerPanel = new JPanel();
		currentPlayerPanel.setLayout(new GridLayout(2,1));
		topPanel.add(currentPlayerPanel);
		
		//Create label for current player
		JLabel nameLabel = new JLabel("Whose Turn?");
		currentPlayerPanel.add(nameLabel);
		
		//Add the panel with current players name
		playerName.setEditable(false);
		currentPlayerPanel.add(playerName);
		
		//Create panel for current roll
		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new GridLayout(1,2));
		topPanel.add(rollPanel);
		
		//Create label for current roll
		JLabel rollLabel = new JLabel("Roll:");
		rollPanel.add(rollLabel);
		
		//Display value of the roll
		rollValue.setEditable(false);
		rollPanel.add(rollValue);
		
		//Create button for creating accusation
		topPanel.add(accusationButton);
		
		//Create button for next players turn
		topPanel.add(nextTurnButton);
		
		//Create the panel for the stuff on the bottom
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(0,2));
		basePanel.add(bottomPanel);
		
		//Create bordered panel for the guess
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1,0));
		bottomPanel.add(guessPanel);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		
		//Display the text for guess
		guess.setEditable(false);
		guessPanel.add(guess);
		
		//Create bordered panel for guess result
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setLayout(new GridLayout(1,0));
		bottomPanel.add(guessResultPanel);
		guessResultPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		
		//Display text for guess result
		guessResult.setEditable(false);
		guessResultPanel.add(guessResult);
		
		//Add everything to the frame
		add(basePanel, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		//Create panel and display
		panel = new GameControlPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(panel);
		frame.setSize(750, 180);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		// test filling in the data
		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		board.initialize();
		panel.setTurn(board.getCurrentPlayer(), board.getDieRoll());
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}

	//Set the result of the guess
	public void setGuessResult(String guessResult) {
		this.guessResult.setText(guessResult);
	}

	//Set the guess
	public void setGuess(String guess) {
		this.guess.setText(guess);
	}

	//Set color, name and roll on each turn
	protected void setTurn(Player player, int roll) {
		//Switch to pick the color
		Color color;
		switch (player.getColor()) {
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
			
		playerName.setText(player.getName());
		playerName.setBackground(color);
		rollValue.setText(Integer.toString(roll));
	}
}
