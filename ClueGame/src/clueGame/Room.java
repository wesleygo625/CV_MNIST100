/*
 * Author: Wes Golliher
 * ClueGame
 * Room class that holds information on the rooms
 */

package clueGame;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	
	//Constructor with name
	public Room(String name) {
		super();
		this.name = name;
	}
	
	//Getters for Room
	public String getName() {
		return name;
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}
	
	public BoardCell getCenterCell() {
		return centerCell;
	}

	//Setters for Room
	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}
}
