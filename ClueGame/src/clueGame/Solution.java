/*
 * Author: Wes Golliher
 * ClueGame
 * Solution class that holds three cards that represent the game solution
 */

package clueGame;

public class Solution {
	public Card person;
	public Card room;
	public Card weapon;
	
	//Default constructor
	public Solution(Card room, Card person, Card weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}

	//Getters for each card
	public Card getPerson() {
		return person;
	}

	public Card getRoom() {
		return room;
	}

	public Card getWeapon() {
		return weapon;
	}
	
	//Sets a new value for each card
	public void setSolution(Card room, Card person, Card weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	
	
	
}
