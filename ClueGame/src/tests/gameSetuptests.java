/*
 * Author: Wes Golliher
 * ClueGame
 * Tests that set up the players and cards
 */

package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;

class gameSetuptests {

	private static Board board;
	private static Map<String, Card> deck;
	private static ArrayList<Player> players;
	private static Solution solution;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		deck = board.getDeck();
		
		players = board.getPlayers();
		
		solution = board.getSolution();
	}
	
	//Make sure the right number of cards are loaded in, and their names are correct
	@Test
	void testWeapons() {
		Set<Card> weapons = new HashSet<Card>();
		for (String name: deck.keySet()) {
			if (board.getCard(name).getCardType() == CardType.WEAPON) {
				weapons.add(board.getCard(name));
			}
		}
		
		//Make sure there are 6 weapons
		assertEquals(weapons.size(), 6);
		
		//Check all of their names are correct
		assertTrue(weapons.contains(board.getCard("Knife")));
		assertTrue(weapons.contains(board.getCard("Gun")));
		assertTrue(weapons.contains(board.getCard("Candlestick")));
		assertTrue(weapons.contains(board.getCard("Pipe")));
		assertTrue(weapons.contains(board.getCard("Rope")));
		assertTrue(weapons.contains(board.getCard("Wrench")));
	}
	
	//Test that all the people cards are created
	@Test
	void testPeopleCards() {
		Set<Card> people = new HashSet<Card>();
		for (String name: deck.keySet()) {
			if (board.getCard(name).getCardType() == CardType.PERSON) {
				people.add(board.getCard(name));
			}
		}
		
		//Check there are 6 people
		assertEquals(people.size(), 6);
		
		//Check all player names
		assertTrue(people.contains(board.getCard("Miss Scarlett")));
		assertTrue(people.contains(board.getCard("Professor Plum")));
		assertTrue(people.contains(board.getCard("Mrs Peacock")));
		assertTrue(people.contains(board.getCard("Mr Green")));
		assertTrue(people.contains(board.getCard("Colonel Mustard")));
		assertTrue(people.contains(board.getCard("Dr White")));
	}

	//Test that all the person objects are created correctly
	@Test
	void testPeople() {
		//Make sure 6 people are loaded in
		assertEquals(players.size(), 6);
		
		//Check that all colors are correct
		assertEquals(players.get(0).getColor(), "Red");
		assertEquals(players.get(1).getColor(), "Purple");
		assertEquals(players.get(2).getColor(), "Blue");
		assertEquals(players.get(3).getColor(), "Green");
		assertEquals(players.get(4).getColor(), "Orange");
		assertEquals(players.get(5).getColor(), "White");
		
		//Check that human vs computer is correct
		assertTrue(players.get(0).getHuman());
		assertFalse(players.get(1).getHuman());
		assertFalse(players.get(2).getHuman());
		assertFalse(players.get(3).getHuman());
		assertFalse(players.get(4).getHuman());
		assertFalse(players.get(5).getHuman());
	}
	
	//Test that the deck has 21 cards and contains the correct 21 cards
	@Test
	void testDeck() {
		assertEquals(deck.size(), 21);
		
		assertTrue(deck.containsValue(board.getCard("Knife")));
		assertTrue(deck.containsValue(board.getCard("Gun")));
		assertTrue(deck.containsValue(board.getCard("Candlestick")));
		assertTrue(deck.containsValue(board.getCard("Pipe")));
		assertTrue(deck.containsValue(board.getCard("Rope")));
		assertTrue(deck.containsValue(board.getCard("Wrench")));
		
		assertTrue(deck.containsValue(board.getCard("Miss Scarlett")));
		assertTrue(deck.containsValue(board.getCard("Professor Plum")));
		assertTrue(deck.containsValue(board.getCard("Mrs Peacock")));
		assertTrue(deck.containsValue(board.getCard("Mr Green")));
		assertTrue(deck.containsValue(board.getCard("Colonel Mustard")));
		assertTrue(deck.containsValue(board.getCard("Dr White")));
		
		assertTrue(deck.containsValue(board.getCard("Kitchen")));
		assertTrue(deck.containsValue(board.getCard("Dining Room")));
		assertTrue(deck.containsValue(board.getCard("Office")));
		assertTrue(deck.containsValue(board.getCard("Game Room")));
		assertTrue(deck.containsValue(board.getCard("Living Room")));
		assertTrue(deck.containsValue(board.getCard("Theater")));
		assertTrue(deck.containsValue(board.getCard("Bedroom")));
		assertTrue(deck.containsValue(board.getCard("Foyer")));
		assertTrue(deck.containsValue(board.getCard("Garage")));
	}
	
	//Test that cards are dealt out correctly
	@Test
	void testDealing() {
		//Check that solution has 3 cards of correct type
		assertEquals(solution.getPerson().getCardType(), CardType.PERSON);
		assertEquals(solution.getRoom().getCardType(), CardType.ROOM);
		assertEquals(solution.getWeapon().getCardType(), CardType.WEAPON);
		
		//Check that each player has 3 cards
		for (Player player: players) {
			assertEquals(player.getHand().size(), 3);
		}
	}

}
