/*
 * Author: Wes Golliher
 * ClueGame
 * Abstract player class for any player
 */

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public abstract class Player {
	private String name;
	private String color;
	protected int row, column;
	protected Boolean human;
	protected Set<Card> hand;
	Board board = Board.getInstance();
	protected Map<Card, Player> seen = new HashMap<Card, Player>();
	private Solution finalGuess;
	private boolean justMoved;

	//Constructor for player
	public Player(String name, String color) {
		super();
		this.name = name;
		this.color = color;
		if (name.equals("Miss Scarlett")) {
			row = 6;
			column = 0;
		} else if (name.equals("Mrs Peacock")) {
			row = 19;
			column = 1;
		} else if (name.equals("Professor Plum")) {
			row = 24;
			column = 7;
		}else if (name.equals("Mr Green")) {
			row = 25;
			column = 19;
		}else if (name.equals("Dr White")) {
			row = 0;
			column = 13;
		} else {
			row = 9;
			column = 24;
		}
		hand = new HashSet<Card>();
		finalGuess = null;
		justMoved = false;
	} 

	//Abstract suggestion method
	public abstract Solution createSuggestion();

	//Assemble a list of all cards that disprove the accusation and randomly pick one of those to return
	public Card disproveSugestion(Card room, Card person, Card weapon) {
		ArrayList<Card> validDisproves = new ArrayList<Card>();
		//Generate list of potential disproving cards
		for (Card card: hand) {
			if (card.equals(room)) {
				validDisproves.add(card);
			} else if (card.equals(person)) {
				validDisproves.add(card);
			} else if (card.equals(weapon)) {
				validDisproves.add(card);
			}
		}

		//Return card, select one randomly if more than 1
		if (validDisproves.size() == 0) {
			return null;
		} else if (validDisproves.size() == 1) {
			return validDisproves.get(0);
		} else {
			Random rand = new Random();
			int index = rand.nextInt(validDisproves.size());
			return validDisproves.get(index);
		}
	}

	//Setters and getters for people
	public boolean isJustMoved() {
		return justMoved;
	}

	public void setJustMoved(boolean justMoved) {
		this.justMoved = justMoved;
	}

	public Solution getFinalGuess() {
		return finalGuess;
	}

	public Map<Card, Player> getSeen() {
		return seen;
	}

	public void setLocation(int row, int col) {
		this.row = row;
		column = col;
	}
	
	public void setFinalGuess(Solution finalGuess) {
		this.finalGuess = finalGuess;
	}
	
	public String getName() {
		return name;
	}

	public String getColor() {
		return color;
	}

	public Boolean getHuman() {
		return human;
	}

	public Set<Card> getHand() {
		return hand;
	}
	
	public BoardCell getCurrentLocation() {
		BoardCell current = board.getCell(column,  row);
		return current;
	}

	//Add a card to the players hand
	public void addToHand(Card card) {
		hand.add(card);
	}

	//Add card to seen cards
	public void addToSeen(Card card, Player cardHolder) {
		seen.put(card, cardHolder);
	}

	//Abstract method to move to different board cell
	public abstract BoardCell move(int i);

	//Draw an oval for the player at their current location and in their color
	public void draw(Graphics g, int height, int width, int offset) {
		g.setColor(determineColor());
		g.fillOval(height*row + offset, column*width, height, width);
	}

	//Convert string color to color object
	private Color determineColor() {
		Color color;
		switch (this.color) {
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
