/*
 * Author: Wes Golliher
 * ClueGame
 * BoardCell that holds one individual cell in the board
 */

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {
	private int row, col;
	private char initial;
	private DoorDirection doorDirection;
	private Boolean roomLabel;
	private Boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList ;
	private Boolean occupied;
	
	//Initialize the board cell with its position, and as a regular space
	public BoardCell(int row, int col, char initial) {
		this.row = row;
		this.col = col;
		this.initial = initial;
		adjList = new HashSet<BoardCell>();
		doorDirection = DoorDirection.NONE;
		roomLabel = false;
		roomCenter = false;
		secretPassage = 'x';
		occupied = false;
	}
	
	//Add cell to this cells adjacency list
	public void addAdjacency( BoardCell cell ) {
		adjList.add(cell);
	}
	
	//All of the setters for BoardCell
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	public Set<BoardCell> getAdjList() {
		return adjList;
	}

	public void setRoomLabel(Boolean roomLabel) {
		this.roomLabel = roomLabel;
	}

	public void setRoomCenter(Boolean roomCenter) {
		this.roomCenter = roomCenter;
	}

	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}

	//All of the getters for board cell
	public boolean isRoomCenter() {
		return roomCenter;
	}
	
	public boolean isDoorway() {
		if (doorDirection == DoorDirection.NONE) {
			return false;
		}
		return true;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public char getSecretPassage() {
		return secretPassage;
	}

	public char getInitial() {
		return initial;
	}

	public void setOccupied(boolean b) {
		occupied = b;
	}

	public boolean isOccupied() {
		return occupied;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	//Check equality between two cells
	public Boolean equals(BoardCell cell) {
		if (this.row == cell.getRow() && this.col == cell.getCol()) {
			return true;
		}
		return false;
	}
	
	//Draws all of the board
	public void draw(Graphics g, int xpos, int ypos, int xsize, int ysize) {
		if (initial == 'X') { //If unused, color black
			g.setColor(Color.black);
			g.fillRect(xpos, ypos, xsize, ysize);
		} else if (initial == 'W') { //If a walkway, color yellow with black outline
			g.setColor(Color.yellow);
			g.fillRect(xpos, ypos, xsize, ysize);
			g.setColor(Color.black);
			g.drawRect(xpos, ypos, xsize, ysize);
			drawDoors(g, xpos, ypos, xsize, ysize);
		} else { //If a room, color grey
			g.setColor(Color.gray);
			g.fillRect(xpos, ypos, xsize, ysize);
		}
	}

	//If this cell is a room label, draw that rooms name in the cell
	public void drawRoomNames(Graphics g, int i, int j, int height, int width) {
		if (roomLabel) {
			String roomName = Board.getRoom(this).getName();
			g.setColor(Color.blue);
			g.drawString(roomName, i, j);
		}
	}

	//Color all targets light blue
	public void drawTargets(Graphics g, int i, int j, int height, int width) {
		Color lightBlue = new Color(173, 216, 230);
		g.setColor(lightBlue);
		g.fillRect(i,j,height,width);
		drawDoors(g, i, j, height, width);
	}

	//Draw the doors on 
	private void drawDoors(Graphics g, int i, int j, int height, int width) {
		if (doorDirection == DoorDirection.UP) { //If walkway with door, color with a blue door
			g.setColor(Color.blue);
			g.fillRect(i, j, height, width/4);
		} else if (doorDirection == DoorDirection.DOWN) {
			g.setColor(Color.blue);
			g.fillRect(i, j + width - width/4, height, width/4);
		} else if (doorDirection == DoorDirection.LEFT) {
			g.setColor(Color.blue);
			g.fillRect(i, j, height/4, width);
		} else if (doorDirection == DoorDirection.RIGHT) {
			g.setColor(Color.blue);
			g.fillRect(i + height - height/4, j, height/4, width);
		}
	}
}
