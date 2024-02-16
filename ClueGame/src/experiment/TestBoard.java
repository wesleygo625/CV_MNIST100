package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;
	
	public TestBoard() {
		grid = new TestBoardCell[ROWS][COLS];
		
		//Create the board full of cells
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				grid[i][j] = new TestBoardCell(i,j);
			}
		}
		
		//Creates adjacency list for the board
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++) {
				if (i > 0) {
					grid[i][j].addAdjacency(this.getCell(i - 1,j));
				}
				if (i < ROWS - 1) {
					grid[i][j].addAdjacency(this.getCell(i + 1,j));
				}
				if (j > 0) {
					grid[i][j].addAdjacency(this.getCell(i,j - 1));
				}
				if (j < COLS - 1) {
					grid[i][j].addAdjacency(this.getCell(i,j + 1));
				}
			}
		}
	}
	
	//Initialized and calls the recursive function to find targets
	public void calcTargets( TestBoardCell startCell, int pathLength) {
		visited = new HashSet<TestBoardCell>();
		targets = new HashSet<TestBoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}
	
	public Set<TestBoardCell> getTargets() {
		return targets;
	}
	
	public TestBoardCell getCell( int row, int col ) {
		return grid[row][col];
	}
	
	//Recursively find and add targets to the target list
	private void findAllTargets(TestBoardCell startCell, int pathLength) {
		for (TestBoardCell adjCell: startCell.getAdjList()) {
			if (!visited.contains(adjCell)) {
				visited.add(adjCell);
				if (pathLength == 1) {
					targets.add(adjCell);
				}
				else if (adjCell.isRoom()) {
					targets.add(adjCell);
				}
				else if (!adjCell.isOccupied()) {
					findAllTargets(adjCell, pathLength - 1);
				}
				visited.remove(adjCell);
			}
		}
	}
}
