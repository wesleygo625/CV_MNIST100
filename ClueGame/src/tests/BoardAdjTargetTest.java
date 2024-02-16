/*
 * Author: Wes Golliher
 * ClueGame
 * Tests to determine if adjacencies and targets are done correctly
 */

package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

class BoardAdjTargetTest {
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	//Test cells that are only adjacent to other walkways
	@Test
	public void testAdjacenciesWalkways() {
		Set<BoardCell> testList = board.getAdjList(19, 9);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(18, 9)));
		assertTrue(testList.contains(board.getCell(20, 9)));
		assertTrue(testList.contains(board.getCell(19, 8)));
		assertTrue(testList.contains(board.getCell(19, 10)));
		
		testList = board.getAdjList(5, 18);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(4, 18)));
		assertTrue(testList.contains(board.getCell(6, 18)));
		assertTrue(testList.contains(board.getCell(5, 17)));
		assertTrue(testList.contains(board.getCell(5, 19)));
	
		testList = board.getAdjList(19, 17);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(18, 17)));
		assertTrue(testList.contains(board.getCell(20, 17)));
		assertTrue(testList.contains(board.getCell(19, 16)));
		assertTrue(testList.contains(board.getCell(19, 18)));
	}
	
	//Test that position within a room, but not the center have no adjacencies
	@Test
	public void testAdjacenciesNotRoomCenter() {
		Set<BoardCell> testList = board.getAdjList(22, 0);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(4, 13);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(4, 21);
		assertEquals(0, testList.size());
	}
	
	//Test cells that are on each of the 4 edges of the board
	@Test
	public void testAdjacenciesEdge() {
		Set<BoardCell> testList = board.getAdjList(9, 1);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(9, 2)));
		assertTrue(testList.contains(board.getCell(10, 1)));
		assertTrue(testList.contains(board.getCell(8, 1)));
		
		testList = board.getAdjList(0, 11);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(1, 11)));
	
		testList = board.getAdjList(18, 24);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(19, 24)));
		assertTrue(testList.contains(board.getCell(18, 23)));
		
		testList = board.getAdjList(24, 9);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(23, 9)));
	}
	
	//Test cells that are next to rooms but not doors
	@Test
	public void testAdjacenciesBesideRoom() {
		Set<BoardCell> testList = board.getAdjList(13, 3);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(12, 3)));
		assertTrue(testList.contains(board.getCell(13, 2)));
		assertTrue(testList.contains(board.getCell(13, 4)));
		
		testList = board.getAdjList(22, 10);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(22, 9)));
		assertTrue(testList.contains(board.getCell(21, 10)));
		assertTrue(testList.contains(board.getCell(23, 10)));
	
		testList = board.getAdjList(3, 17);
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(3, 18)));
		assertTrue(testList.contains(board.getCell(4, 17)));
	}
	
	@Test
	public void testAdjacenciesDoorway() {
		Set<BoardCell> testList = board.getAdjList(17, 8);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(17, 9)));
		assertTrue(testList.contains(board.getCell(16, 8)));
		assertTrue(testList.contains(board.getCell(18, 8)));
		assertTrue(testList.contains(board.getCell(20, 3)));
		
		testList = board.getAdjList(6, 9);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(6, 8)));
		assertTrue(testList.contains(board.getCell(7, 9)));
		assertTrue(testList.contains(board.getCell(2, 8)));
	
		testList = board.getAdjList(3, 20);
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(3, 19)));
		assertTrue(testList.contains(board.getCell(4, 20)));
		assertTrue(testList.contains(board.getCell(2, 20)));
		assertTrue(testList.contains(board.getCell(3, 23)));
	}
	
	//Test that room centers are adjacent if there is a secret passage
	@Test
	public void testAdjacenciesSecretPassage() {
		Set<BoardCell> testList = board.getAdjList(3, 2);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(2, 5)));
		assertTrue(testList.contains(board.getCell(6, 3)));
		assertTrue(testList.contains(board.getCell(23, 22)));
		
		testList = board.getAdjList(20, 3);
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(13, 0)));
		assertTrue(testList.contains(board.getCell(17, 8)));
		assertTrue(testList.contains(board.getCell(3, 23)));
	}
	
	//Test targets for moving in walkways
	@Test
	public void testTargetWalkways() {
		board.calcTargets(board.getCell(4, 7), 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(2, 6)));
		assertTrue(targets.contains(board.getCell(3, 5)));
		assertTrue(targets.contains(board.getCell(5, 5)));
		assertTrue(targets.contains(board.getCell(6, 6)));
		assertTrue(targets.contains(board.getCell(5, 7)));
		assertTrue(targets.contains(board.getCell(4, 6)));
		assertTrue(targets.contains(board.getCell(6, 8)));
		
		board.calcTargets(board.getCell(23, 8), 2);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(21, 8)));
		assertTrue(targets.contains(board.getCell(22, 9)));
		assertTrue(targets.contains(board.getCell(23, 10)));
		assertTrue(targets.contains(board.getCell(24, 9)));
		
		board.calcTargets(board.getCell(18, 17), 2);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(19, 16)));
		assertTrue(targets.contains(board.getCell(17, 16)));
		assertTrue(targets.contains(board.getCell(16, 17)));
		assertTrue(targets.contains(board.getCell(17, 18)));
		assertTrue(targets.contains(board.getCell(18, 19)));
		assertTrue(targets.contains(board.getCell(19, 18)));
		assertTrue(targets.contains(board.getCell(20, 17)));
	}
	
	//Test targets for walking into rooms
	@Test
	public void testTargetIntoRoom() {
		board.calcTargets(board.getCell(1, 20), 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 23)));
		assertTrue(targets.contains(board.getCell(5, 20)));
		assertTrue(targets.contains(board.getCell(4, 19)));
		assertTrue(targets.contains(board.getCell(3, 18)));
		assertTrue(targets.contains(board.getCell(3, 20)));
		assertTrue(targets.contains(board.getCell(2, 19)));
		
		board.calcTargets(board.getCell(6, 2), 3);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCell(8, 1)));
		assertTrue(targets.contains(board.getCell(7, 2)));
		assertTrue(targets.contains(board.getCell(6, 1)));
		assertTrue(targets.contains(board.getCell(9, 2)));
		assertTrue(targets.contains(board.getCell(6, 3)));
		assertTrue(targets.contains(board.getCell(7, 4)));
		assertTrue(targets.contains(board.getCell(6, 5)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		
		board.calcTargets(board.getCell(12, 6), 2);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(12, 4)));
		assertTrue(targets.contains(board.getCell(13, 5)));
		assertTrue(targets.contains(board.getCell(10, 5)));
		assertTrue(targets.contains(board.getCell(13, 7)));
		assertTrue(targets.contains(board.getCell(12, 8)));
	}
	
	//Test targets for leaving rooms
	@Test
	public void testTargetLeavingRoom() {
		board.calcTargets(board.getCell(10, 5), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(10, 2)));
		assertTrue(targets.contains(board.getCell(7, 5)));
		assertTrue(targets.contains(board.getCell(10, 8)));
		assertTrue(targets.contains(board.getCell(12, 5)));
		
		board.calcTargets(board.getCell(20, 13), 3);
		targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(16, 12)));
		assertTrue(targets.contains(board.getCell(16, 14)));
		assertTrue(targets.contains(board.getCell(15, 11)));
		assertTrue(targets.contains(board.getCell(15, 13)));
		assertTrue(targets.contains(board.getCell(15, 15)));
		assertTrue(targets.contains(board.getCell(16, 10)));
		assertTrue(targets.contains(board.getCell(16, 16)));
	}
	
	//Test targets for leaving a room with a secret passage
	@Test
	public void testTargetLeavingRoomSP() {
		board.calcTargets(board.getCell(23, 22), 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(19, 24)));
		assertTrue(targets.contains(board.getCell(20, 20)));
		assertTrue(targets.contains(board.getCell(21, 19)));
		assertTrue(targets.contains(board.getCell(23, 16)));
		assertTrue(targets.contains(board.getCell(3, 2)));
	}

	//Test targets if spaces are blocked in walkways, doors and rooms
	@Test
	public void testTargetsPlayerBlock() {
		board.getCell(15, 9).setOccupied(true);
		board.calcTargets(board.getCell(14, 9), 2);
		board.getCell(15, 7).setOccupied(false);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(12, 9)));
		assertTrue(targets.contains(board.getCell(13, 8)));
		assertTrue(targets.contains(board.getCell(15, 8)));	
		
		board.getCell(2, 5).setOccupied(true);
		board.getCell(2, 8).setOccupied(true);
		board.calcTargets(board.getCell(1, 6), 3);
		board.getCell(2, 5).setOccupied(false);
		board.getCell(2, 8).setOccupied(false);
		targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(2, 8)));
		assertTrue(targets.contains(board.getCell(3, 5)));
		assertTrue(targets.contains(board.getCell(4, 6)));	
		
		board.getCell(3, 20).setOccupied(true);
		board.getCell(7, 23).setOccupied(true);
		board.calcTargets(board.getCell(3, 23), 1);
		board.getCell(3, 20).setOccupied(false);
		board.getCell(7, 23).setOccupied(false);
		targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(20, 3)));
	}
}
