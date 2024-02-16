/*
 * Author: Wes Golliher
 * ClueGame
 * Child of player that is a computer player
 */

package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	

	//Default constructor
	public ComputerPlayer(String name, String color) {
		super(name, color);
		human = false;
		seen = new HashMap<Card, Player>();
	}

	//AI method to generate a suggestion
	public Solution createSuggestion() {
		BoardCell location = getCurrentLocation();
		Room roomObject = Board.getRoom(location);
		String roomName = roomObject.getName();
		Card room = Board.getDeck().get(roomName);
		if (roomName.equals("Walkway") || roomName == "Unused") { //Only set a room card if it is an actual room
			return null;
		}
		Card weapon;
		Card person;
		ArrayList<Card> validWeapons = new ArrayList<Card>();
		ArrayList<Card> validPeople = new ArrayList<Card>();
		
		//Separate potential suggestions into people and weapons
		for (Card card: Board.getDeck().values()) {
			if (!seen.containsKey(card) && !hand.contains(card)) {
				if (card.getCardType() == CardType.WEAPON) {
					validWeapons.add(card);
				} else if (card.getCardType() == CardType.PERSON) {
					validPeople.add(card);
				}
			}
		}

		//Pick a random weapon from the potentials
		if (validWeapons.size() == 1) {
			weapon = validWeapons.get(0);
		} else {
			Random rand = new Random();
			int index = rand.nextInt(validWeapons.size());
			weapon = validWeapons.get(index);
		}

		//Pick a random person rom potentials
		if (validPeople.size() == 1) {
			person = validPeople.get(0);
		} else {
			Random rand = new Random();
			int index = rand.nextInt(validPeople.size());
			person = validPeople.get(index);
		}

		//Create solution object with the three cards and return it
		Solution solution = new Solution(room, person, weapon);
		return solution;
	}

	//AI method to move the computer player
	@Override
	public BoardCell move(int roll) {
		BoardCell cell = Board.getCell(column,  row);
		cell.setOccupied(false);
		board.calcTargets(cell, roll);
		Set<BoardCell> targets = Board.getTargets();
		ArrayList<BoardCell> walkwayTargets = new ArrayList<BoardCell>();
		ArrayList<BoardCell> roomTargets = new ArrayList<BoardCell>();
		
		//Split the targets into unseen rooms and walkways
		for (BoardCell target: targets) {
			char init = target.getInitial();
			if (init != 'W') {
				Room room = Board.getRoom(target);
				Card roomCard = Board.getDeck().get(room.getName());
				if (!seen.containsKey(roomCard)) {
					roomTargets.add(target);
				}
			} else {
				walkwayTargets.add(target);
			}
		}
		
		//Randomly pick a room first (if they are in target list)
		if (roomTargets.size() > 0) {
			Random rand = new Random();
			int index = rand.nextInt(roomTargets.size());
			return roomTargets.get(index);
		} else { //Otherwise pick a random walkway target
			Random rand = new Random();
			int index = rand.nextInt(walkwayTargets.size());
			return walkwayTargets.get(index);
		}
	}
}
