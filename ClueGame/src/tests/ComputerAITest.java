/*
 * Author: Wes Golliher
 * ClueGame
 * Test class thattests if the computers AI is behaving as expected
 */

package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

class ComputerAITest {
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
	
	//Test the computer making a suggestion when there is only one option
	@Test
	void testComputerSuggestionOneOption() {
		ComputerPlayer comp1 = new ComputerPlayer("name", "color");
		ComputerPlayer cardHolder = new ComputerPlayer("Name", "color");
		comp1.setLocation(3, 2);
		comp1.addToHand(deck.get("Professor Plum"));
		comp1.addToHand(deck.get("Dr White"));
		comp1.addToHand(deck.get("Mr Green"));
		comp1.addToSeen(deck.get("Miss Scarlett"), cardHolder);
		comp1.addToSeen(deck.get("Colonel Mustard"), cardHolder);
		comp1.addToSeen(deck.get("Knife"), cardHolder);
		comp1.addToSeen(deck.get("Gun"), cardHolder);
		comp1.addToSeen(deck.get("Pipe"), cardHolder);
		comp1.addToSeen(deck.get("Candlestick"), cardHolder);
		comp1.addToSeen(deck.get("Rope"), cardHolder);
		Solution sol1 = comp1.createSuggestion();
		assertEquals(sol1.person, deck.get("Mrs Peacock")); //Room matches location
		assertEquals(sol1.weapon, deck.get("Wrench"));
		assertEquals(sol1.room, deck.get("Office"));
				
	}
	
	//Test the computer making a suggestion when it has to randomly pick between people and weapons
	@Test 
	void testComputerSuggestionAnyOption() {
		ComputerPlayer comp2 = new ComputerPlayer("name", "color");
		comp2.setLocation(13, 20);
		comp2.addToHand(deck.get("Dining Room"));
		comp2.addToHand(deck.get("Living Room"));
		comp2.addToHand(deck.get("Kitchen"));
		int c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, c6 = 0, c7 = 0, c8 = 0, c9 = 0, c10 = 0, c11 = 0, c12 = 0;
		for (int i = 0; i < 500; i++) {
			Solution sol2 = comp2.createSuggestion();
			assertEquals(sol2.room, deck.get("Foyer")); //Room matches location
			if (sol2.person == deck.get("Dr White")) {
				c1 += 1;
			}
			if (sol2.person == deck.get("Mr Green")) {
				c2 += 1;
			}
			if (sol2.person == deck.get("Mrs Peacock")) {
				c3 += 1;
			}
			if (sol2.person == deck.get("Professor Plum")) {
				c4 += 1;
			}
			if (sol2.person == deck.get("Miss Scarlett")) {
				c5 += 1;
			}
			if (sol2.person == deck.get("Colonel Mustard")) {
				c6 += 1;
			}
			if (sol2.weapon == deck.get("Knife")) {
				c7 += 1;
			}
			if (sol2.weapon == deck.get("Pipe")) {
				c8 += 1;
			}
			if (sol2.weapon == deck.get("Gun")) {
				c9 += 1;
			}
			if (sol2.weapon == deck.get("Candlestick")) {
				c10 += 1;
			}
			if (sol2.weapon == deck.get("Rope")) {
				c11 += 1;
			}
			if (sol2.weapon == deck.get("Wrench")) {
				c12 += 1;
			}
		}
		assertTrue(c1 > 0);
		assertTrue(c2 > 0);
		assertTrue(c3 > 0);
		assertTrue(c4 > 0);
		assertTrue(c5 > 0);
		assertTrue(c6 > 0);
		assertTrue(c7 > 0);
		assertTrue(c8 > 0);
		assertTrue(c9 > 0);
		assertTrue(c10 > 0);
		assertTrue(c11 > 0);
		assertTrue(c12 > 0);
	}
	
	//Find computer targets: Case with 1 room unvisited in targets
	@Test
	void testComputerTargets1() {
		Player comp = new ComputerPlayer("name", "color");
		comp.setLocation(16, 5);
		BoardCell destination = comp.move(2);
		assertEquals(destination, Board.getCell(2, 14));
	}

	//Find computer targets: Case with multiple unvisited rooms
	@Test
	void testComputerTargets2() {
		Player comp = new ComputerPlayer("name", "color");
		comp.setLocation(5, 6);
		int room1 = 0, room2 = 0, room3 = 0;
		for (int i = 0; i < 100; i++) {
			BoardCell destination = comp.move(5);
			if (destination.equals(Board.getCell(3, 2))) {
				room1 += 1;
			} else if (destination.equals(Board.getCell(2, 8))) {
				room2 += 1;
			} else if (destination.equals(Board.getCell(10, 5))) {
				room3 += 1;
			} else {
				assertTrue(false);
			}
		}
		assertTrue(room1 > 0);
		assertTrue(room2 > 0);
		assertTrue(room3 > 0);
	}
	
	//Find computer targets: Case with no rooms in targets
	@Test
	void testComputerTargets3() {
		Player comp = new ComputerPlayer("name", "color");
		comp.setLocation(8, 23);
		int space1 = 0, space2 = 0, space3 = 0, space4 = 0;
		for (int i = 0; i < 100; i++) {
			BoardCell destination = comp.move(2);
			if (destination.equals(Board.getCell(21, 8))) {
				space1 += 1;
			} else if (destination.equals(Board.getCell(22, 9))) {
				space2 += 1;
			} else if (destination.equals(Board.getCell(23, 10))) {
				space3 += 1;
			} else if (destination.equals(Board.getCell(24, 9))) {
				space4 += 1;
			} else {
				assertTrue(false);
			}
		}
		assertTrue(space1 > 0);
		assertTrue(space2 > 0);
		assertTrue(space3 > 0);
		assertTrue(space4 > 0);
	}
	
	//Find computer targets: Case with only seen rooms in targets
		@Test
		void testComputerTargets4() {
			ComputerPlayer comp = new ComputerPlayer("name", "color");
			ComputerPlayer cardHolder = new ComputerPlayer("name", "color");
			comp.setLocation(11, 3);
			comp.addToSeen(deck.get("Living Room"), cardHolder);
			comp.addToSeen(deck.get("Game Room"), cardHolder);
			int space1 = 0, space2 = 0;
			for (int i = 0; i < 100; i++) {
				BoardCell destination = comp.move(3);
				System.out.println(destination);
				if (destination.equals(Board.getCell(0, 11))) {
					space1 += 1;
				} else if (destination.equals(Board.getCell(6, 11))) {
					space2 += 1;
				} else {
					assertTrue(false);
				}
			}
			assertTrue(space1 > 0);
			assertTrue(space2 > 0);
		}
}
