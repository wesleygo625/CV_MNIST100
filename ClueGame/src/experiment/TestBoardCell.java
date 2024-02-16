package experiment;

import java.util.*;

public class TestBoardCell {
	private int row, col;
	private Boolean isRoom, isOccupied;
	private Set<TestBoardCell> adjList ;
	
	//Initialize the board cell with its position, and as a regular space
	public TestBoardCell(int row, int col) {
		this.row = row;
		this.col = col;
		isRoom = false;
		isOccupied = false;
		adjList = new HashSet<TestBoardCell>();
	}
	
	public void addAdjacency( TestBoardCell cell ) {
		adjList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjList;
	}
	
	public void setRoom(boolean isRoom) {
		this.isRoom = isRoom;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}
}
