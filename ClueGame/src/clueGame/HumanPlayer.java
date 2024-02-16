/*
 * Author: Wes Golliher
 * ClueGame
 * Child of player that is the one human player
 */

package clueGame;

public class HumanPlayer extends Player{

	//Default constructor
	public HumanPlayer(String name, String color) {
		super(name, color);
		human = true;
	}

	//Null move method
	@Override
	public BoardCell move(int i) {
		return null;
	}

	//Null solution method
	@Override
	public Solution createSuggestion() {
		return null;
	}

}
