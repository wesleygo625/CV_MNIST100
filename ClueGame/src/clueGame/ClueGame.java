/*
 * Author: Wes Golliher
 * ClueGame
 * Clue Game class that adds together the drawn board, card panel, and control panel into one clear window
 */

package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClueGame extends JFrame {

	Board board = Board.getInstance();
	static GameControlPanel controlPanel = new GameControlPanel();
	static ClueGame clueGame = new ClueGame();
	CardPanel cardPanel = new CardPanel();
	PlayerSuggestionDialog dialog;
	AccusationDialog dialog2;

	public ClueGame() {
		//Set window title, size, and close conditions
		setTitle("Clue Game");
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Add the card panel
		cardPanel = new CardPanel();
		add(cardPanel, BorderLayout.EAST);

		//Ad the control panel
		add(controlPanel, BorderLayout.SOUTH);
		NextTurnListener next = new NextTurnListener();
		controlPanel.nextTurnButton.addActionListener(next);
		AccusationListener accuse = new AccusationListener();
		controlPanel.accusationButton.addActionListener(accuse);

		//Add the game board
		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");	
		board.initialize();
		Graphics g = board.getGraphics();

		addMouseListener(new NextClickListener());

		add(board, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		//Create panel and display

		clueGame.setVisible(true);
		JOptionPane.showMessageDialog(clueGame, "You are Miss Scarlett. \n Can you find the solution \n before the computer players?", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);

		Board board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		board.initialize();
		controlPanel.setTurn(board.getCurrentPlayer(), board.getDieRoll());
		clueGame.cardPanel.update(board.getPlayers().get(0));
		board.calcTargets(board.getCurrentPlayer().getCurrentLocation(), board.getDieRoll());
		clueGame.repaint();
	}

	//Listen for when the board is clicked
	private class NextClickListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			Boolean success = board.processClick(e.getPoint(), clueGame);
			if (!success) {
				JOptionPane.showMessageDialog(clueGame, "Not a valid target, please try again.", "Error! Invalid target!", JOptionPane.ERROR_MESSAGE);
				cardPanel.update(board.getPlayers().get(0));
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
	}

	//Listen for the next turn button being pressed
	private class NextTurnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			board.processNext(controlPanel, clueGame);
			controlPanel.setTurn(board.getCurrentPlayer(), board.getDieRoll());
			controlPanel.setGuess(board.getGuess());
			controlPanel.setGuessResult(board.getGuessResult());
			repaint();
		}
	}

	//Listen for the make accusation button being pressed
	private class AccusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			board.processAccusation(clueGame);
			repaint();
		}
	}

	//Listen for the submit accusation button being pressed
	private class SubmitAccusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Solution guess = AccusationDialog.getSolution();
			board.getCurrentPlayer().setFinalGuess(guess);
			board.processAccusation2(clueGame);
			dialog2.dispose();
			repaint();
		}
	}

	//Listen for the submit accusation button being pressed
	private class CancelAccusationListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dialog2.dispose();
			repaint();
		}
	}

	//Listen for the submit accusation button being pressed
	private class SubmitSuggestionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Solution guess = PlayerSuggestionDialog.getSolution();
			board.playerSuggestion2(guess, controlPanel);
			dialog.dispose();
			repaint();
		}
	}

	//Call this when suggestion dialog is needed
	public void suggestionDialog(String roomName, ArrayList<String> weaponNames, ArrayList<String> personNames) {
		dialog = new PlayerSuggestionDialog(roomName, weaponNames, personNames);
		dialog.setVisible(true);
		SubmitSuggestionListener submitSug = new SubmitSuggestionListener();
		PlayerSuggestionDialog.submit.addActionListener(submitSug);
	}

	//Call this when accusation dialog is needed
	public void accusationDialog(ArrayList<String> roomNames, ArrayList<String> personNames, ArrayList<String> weaponNames) {
		dialog2 = new AccusationDialog(roomNames, weaponNames, personNames);
		SubmitAccusationListener submitAcc = new SubmitAccusationListener();
		AccusationDialog.submit.addActionListener(submitAcc);
		CancelAccusationListener cancelAcc = new CancelAccusationListener();
		AccusationDialog.cancel.addActionListener(cancelAcc);
		dialog2.setVisible(true);
	}
}
