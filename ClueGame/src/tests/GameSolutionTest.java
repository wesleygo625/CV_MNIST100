/*
 * Author: Wes Golliher
 * ClueGame
 * Test class for testing accusations and suggestions 
 */

package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSolutionTest {
	private static Board board;
	private static Map<String, Card> deck;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		deck = board.getDeck();
	}
	
	//Test for someone accusing, and comparing that to the solution
	@Test
	void testAccusation() {
		Solution solution = new Solution(deck.get("Game Room"), deck.get("Dr White"), deck.get("Pipe"));
		
		//Solution with all correct
		assertTrue(board.checkAccusation(solution, deck.get("Game Room"), deck.get("Dr White"), deck.get("Pipe")));
		
		//Solution with wrong room
		assertFalse(board.checkAccusation(solution, deck.get("Garage"), deck.get("Dr White"), deck.get("Pipe")));
		
		//Solution with wrong person
		assertFalse(board.checkAccusation(solution, deck.get("Game Room"), deck.get("Colonel Mustard"), deck.get("Pipe")));
		
		//Solution with wrong weapon
		assertFalse(board.checkAccusation(solution, deck.get("Game Room"), deck.get("Dr White"), deck.get("Gun")));
	}
	
	//Test for a player disproving anothers suggestion (null if no cards or randomly pick between options if they have cards)
	@Test 
	void testDisprove() {
		Player examplePlayer = new ComputerPlayer("name", "color");
		examplePlayer.addToHand(deck.get("Game Room"));
		examplePlayer.addToHand(deck.get("Dr White"));
		examplePlayer.addToHand(deck.get("Pipe"));
		
		//Trying to disprove with no cards returns null
		assertEquals(examplePlayer.disproveSugestion(deck.get("Garage"), deck.get("Colonel Mustard"), deck.get("Gun")), null);
		
		//Return the 1 matching card if player has only 1 matching cards
		assertEquals(examplePlayer.disproveSugestion(deck.get("Game Room"), deck.get("Colonel Mustard"), deck.get("Gun")), deck.get("Game Room"));
		assertEquals(examplePlayer.disproveSugestion(deck.get("Garage"), deck.get("Dr White"), deck.get("Gun")), deck.get("Dr White"));
		assertEquals(examplePlayer.disproveSugestion(deck.get("Garage"), deck.get("Colonel Mustard"), deck.get("Pipe")), deck.get("Pipe"));
		
		
		//Randomly pick a card if more than 1 match
		int card1 = 0, card2 = 0, card3 = 0;
		for (int i = 0; i < 50; i++) {
			Card disprove = examplePlayer.disproveSugestion(deck.get("Game Room"), deck.get("Dr White"), deck.get("Pipe"));
			if (disprove.equals(deck.get("Game Room"))) {
				card1 += 1;
			} else if (disprove.equals(deck.get("Dr White"))) {
				card2 += 1;
			} else if (disprove.equals(deck.get("Pipe"))) {
				card3 += 1;
			} else {
				assertTrue(false);
			}
		}
		assertTrue(card1 > 0);
		assertTrue(card2 > 0);
		assertTrue(card3 > 0);
	}
	
	//Test for handling a players suggestion by going through each player and seeing if they can disprove
	@Test
	void testSuggestionHandling() {
		Player human = new HumanPlayer("human", "color");
		human.addToHand(deck.get("Foyer"));
		human.addToHand(deck.get("Miss Scarlett"));
		human.addToHand(deck.get("Rope"));
		
		Player comp1 = new HumanPlayer("comp1", "color");
		comp1.addToHand(deck.get("Knife"));
		comp1.addToHand(deck.get("Dr White"));
		comp1.addToHand(deck.get("Knife"));
		
		Player comp2 = new HumanPlayer("comp2", "color");
		comp2.addToHand(deck.get("Living Room"));
		comp2.addToHand(deck.get("Theater"));
		comp2.addToHand(deck.get("Bedroom"));
		
		Player comp3 = new HumanPlayer("comp3", "color");
		comp3.addToHand(deck.get("Garage"));
		comp3.addToHand(deck.get("Professor Plum"));
		comp3.addToHand(deck.get("Gun"));
		
		ArrayList<Player> players2 = new ArrayList<Player>();
		players2.add(human);
		players2.add(comp1);
		players2.add(comp2);
		players2.add(comp3);
		
		//Suggestions with no disputable cards returns null
		assertEquals(board.handleSuggestion(players2, human, deck.get("Kitchen"), deck.get("Mrs Peacock"), deck.get("Candlestick")), null);
		
		//Suggestion where disputable cards are in suggesting players hands
		assertEquals(board.handleSuggestion(players2, human, deck.get("Foyer"), deck.get("Miss Scarlett"), deck.get("Rope")), null);
		
		//Suggestions where one player can disprove are returned with the disproving card
		assertEquals(board.handleSuggestion(players2, human, deck.get("Dining Room"), deck.get("Mr Green"), deck.get("Gun")), deck.get("Gun"));
		assertEquals(board.handleSuggestion(players2, human, deck.get("Theater"), deck.get("Mr Green"), deck.get("Wrench")), deck.get("Theater"));
		assertEquals(board.handleSuggestion(players2, human, deck.get("Dining Room"), deck.get("Professor Plum"), deck.get("Wrench")), deck.get("Professor Plum"));
		
		//Suggestions with more than one player having disproving card returns the card of the player first in the list
		assertEquals(board.handleSuggestion(players2, comp1, deck.get("Dining Room"), deck.get("Mr Green"), deck.get("Gun")), deck.get("Gun"));
		assertEquals(board.handleSuggestion(players2, comp2, deck.get("Theater"), deck.get("Mr Green"), deck.get("Wrench")), null);
		assertEquals(board.handleSuggestion(players2, comp3, deck.get("Dining Room"), deck.get("Professor Plum"), deck.get("Rope")), deck.get("Rope"));
		
		//If someone has multiple disproving cards, check that they pick one randomly
		int card1 = 0, card2 = 0, card3 = 0;
		for (int i = 0; i < 50; i++) {
			Card disprove = board.handleSuggestion(players2, human, deck.get("Garage"), deck.get("Gun"), deck.get("Professor Plum"));
			if (disprove.equals(deck.get("Garage"))) {
				card1 += 1;
			} else if (disprove.equals(deck.get("Gun"))) {
				card2 += 1;
			} else if (disprove.equals(deck.get("Professor Plum"))) {
				card3 += 1;
			} else {
				assertTrue(false);
			}
		}
		
		assertTrue(card1 > 0);
		assertTrue(card2 > 0);
		assertTrue(card3 > 0);
	}

}
