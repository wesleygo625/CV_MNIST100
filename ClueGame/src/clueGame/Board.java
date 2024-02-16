/*
 * Author: Wes Golliher
 * ClueGame
 * Board class with simpleton pattern
 */


package clueGame;

import java.awt.Graphics;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import experiment.TestBoardCell;

public class Board extends JPanel {
	private static BoardCell[][] grid;
	int numRows, numColumns;
	private static Set<BoardCell> targets = new HashSet<BoardCell>();;
	private static Set<BoardCell> visited;
	private String layoutConfigFile;
	private String setupConfigFile;
	private static Map<Character, Room> roomMap;
	private static Board theInstance = new Board();
	private static Map<String, Card> deck;
	private ArrayList<Player> players;
	private static Solution Solution;
	private Player currentPlayer;
	private int dieRoll;
	private int width, height;
	private Boolean needToMove;
	private String guess, guessResult;

	private Board() {
		super();
	}

	//Initialize the board
	public void initialize() {
		deck = new HashMap<String, Card>();
		players = new ArrayList<Player>();

		try {
			loadSetupConfig();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			loadLayoutConfig();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		initializeAdjacencies();

		if (deck.size() == Card.DECK_SIZE) {
			dealCards();
		}
		if (players.size() > 0) {
			currentPlayer = players.get(0);
		}
		dieRoll = rollDie();
		BoardCell currentCell = currentPlayer.getCurrentLocation();
		calcTargets(currentCell, dieRoll);
		needToMove = true;
	}

	//Deal 1 card of each type to the solution, and then evenly deal the rest of the cards to the players
	private void dealCards() {
		ArrayList<Card> deckList = new ArrayList<Card>(deck.values());

		//Generate random room card for the solution
		int index = new Random().nextInt(deckList.size());
		Card solutionRoom = deckList.get(index);
		while (solutionRoom.getCardType() != CardType.ROOM) {
			index = new Random().nextInt(deckList.size());
			solutionRoom = deckList.get(index);
			if (solutionRoom.getCardType() == CardType.ROOM) {
				deckList.remove(index);
			}
		}

		//Generate random person card for the solution
		index = new Random().nextInt(deckList.size());
		Card solutionPerson = deckList.get(index);
		while (solutionPerson.getCardType() != CardType.PERSON) {
			index = new Random().nextInt(deckList.size());
			solutionPerson = deckList.get(index);
			if (solutionRoom.getCardType() == CardType.PERSON) {
				deckList.remove(index);
			}
		}

		//Generate random weapon card for the solution
		index = new Random().nextInt(deckList.size());
		Card solutionWeapon = deckList.get(index);
		while (solutionWeapon.getCardType() != CardType.WEAPON) {
			index = new Random().nextInt(deckList.size());
			solutionWeapon = deckList.get(index);
			if (solutionWeapon.getCardType() == CardType.WEAPON) {
				deckList.remove(index);
			}
		}

		Solution = new Solution(solutionRoom, solutionPerson, solutionWeapon);

		//Deal remaining cards to the players evenly
		for (Player player: players) {
			for (int i = 0; i < (deck.size()/players.size()); i++) {
				if (deckList.size() == 1) {
					Card card = deckList.get(0);
					player.addToHand(card);
					deckList.remove(index);
				} else {
					index = new Random().nextInt(deckList.size());
					Card card = deckList.get(index);
					player.addToHand(card);
					deckList.remove(index);
				}
			}
		}
	}

	//Create each cell's adjacency list
	private void initializeAdjacencies() {
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				BoardCell cell = grid[i][j];
				if (cell.getInitial() == 'W' && cell.getDoorDirection() == DoorDirection.NONE) { //Case where we have a walkway that isn't a door
					calculateDirectAdjacencies(i, j);
				} else if (cell.getInitial() == 'W') { //Case with walkway that is a door
					calculateDirectAdjacencies(i, j);
					calculateDoorAdjacencies(i, j);
				} else if (cell.getSecretPassage() != 'x') { //Case for secret passage adjacencies
					Room room = roomMap.get(cell.getSecretPassage());
					BoardCell roomCenter = room.getCenterCell();
					BoardCell targetRoomCenter = roomMap.get(cell.getInitial()).getCenterCell();
					roomCenter.addAdjacency(targetRoomCenter);
				}
			}
		}
	}

	//Add room centers to the adjacency lists of doors, and add doors to the adjacency lists of room centers
	private void calculateDoorAdjacencies(int i, int j) {
		BoardCell cell = grid[i][j];
		DoorDirection doorDirection = cell.getDoorDirection();
		if (doorDirection == DoorDirection.LEFT) {
			Room room = roomMap.get(this.getCell(i, j-1).getInitial());
			cell.addAdjacency(room.getCenterCell());
			room.getCenterCell().addAdjacency(cell);
		}
		if (doorDirection == DoorDirection.RIGHT) {
			Room room = roomMap.get(this.getCell(i, j+1).getInitial());
			cell.addAdjacency(room.getCenterCell());
			room.getCenterCell().addAdjacency(cell);
		}
		if (doorDirection == DoorDirection.UP) {
			Room room = roomMap.get(this.getCell(i-1, j).getInitial());
			cell.addAdjacency(room.getCenterCell());
			room.getCenterCell().addAdjacency(cell);
		}
		if (doorDirection == DoorDirection.DOWN) {
			Room room = roomMap.get(this.getCell(i+1, j).getInitial());
			cell.addAdjacency(room.getCenterCell());
			room.getCenterCell().addAdjacency(cell);
		}
	}

	//Look in all 4 directions to see if they are valid move directions, and then add to adjacency list
	private void calculateDirectAdjacencies(int i, int j) {
		BoardCell cell = grid[i][j];
		if (i > 0 && this.getCell(i - 1, j).getInitial() == 'W') {
			cell.addAdjacency(this.getCell(i - 1,j));
		}
		if (i < numRows - 1 && this.getCell(i + 1, j).getInitial() == 'W') {
			cell.addAdjacency(this.getCell(i + 1,j));
		}
		if (j > 0 && this.getCell(i, j - 1).getInitial() == 'W') {
			cell.addAdjacency(this.getCell(i,j - 1));
		}
		if (j < numColumns - 1 && this.getCell(i, j + 1).getInitial() == 'W') {
			cell.addAdjacency(this.getCell(i,j + 1));
		}
	}

	//Set file name variables
	public void setConfigFiles(String layout, String setup) {
		layoutConfigFile = layout;
		setupConfigFile = setup;
	}

	//Load in the game setup
	public void loadSetupConfig() throws FileNotFoundException {
		roomMap = new HashMap<Character, Room>();
		FileReader reader = new FileReader(setupConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			setupRoom(line);
		}
	}

	//Parse lines in setup and create new room
	private void setupRoom(String line) {
		if (line.charAt(0) == '/' && line.charAt(1) == '/') { //Don't do anything if the first chars are a comment

		} else {
			String[] words = line.split(", ");
			if (words[0].equals("Room") || words[0].equals("Space")) {
				roomSetup(words);
			} else if (words[0].equals("Weapon")) {
				Card tempCard = new Card(words[1], CardType.WEAPON);
				deck.put(words[1], tempCard);
			} else if (words[0].equals("Person") || words[0].equals("Human Person")) {
				playerSetup(words);
			} else { //If we have a letter that isn't a room initial, throw a bad format exception
				throw new BadConfigFormatException(setupConfigFile);
			}
		}
	}

	//Create room and card objects for the room
	private void roomSetup(String[] words) {
		Room room = new Room(words[1]);
		char roomInitial = words[2].charAt(0);
		roomMap.put(roomInitial, room);
		if (words[0].equals("Room")) {
			Card tempCard = new Card(words[1], CardType.ROOM);
			if (!deck.containsKey(words[1])) {
				deck.put(words[1], tempCard);
			}
		}
	}

	//Create cards and objects for the players
	private void playerSetup(String[] words) {
		Card tempCard = new Card(words[1], CardType.PERSON);
		deck.put(words[1], tempCard);
		Player person;
		if (words[0].equals("Person")) {
			person = new ComputerPlayer(words[1], words[2]);
		} else {
			person = new HumanPlayer(words[1], words[2]);
		}
		players.add(person);
	}


	//Load in the board layout
	public void loadLayoutConfig() throws FileNotFoundException {
		ArrayList<ArrayList<String>> cellChars = readLayout();
		numRows = cellChars.size();
		numColumns = cellChars.get(0).size();
		for (ArrayList<String> row: cellChars) {
			if (row.size() != numColumns) { //If the rows are different sizes throw a bad format exception
				throw new BadConfigFormatException(layoutConfigFile);
			}
		}
		createBoardCells(cellChars);
	}

	//Create each board cell in the layout
	private void createBoardCells(ArrayList<ArrayList<String>> cellChars) {
		grid = new BoardCell[numRows][numColumns];
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				String cellChar = cellChars.get(i).get(j);
				if (!roomMap.containsKey(cellChar.charAt(0))) { //If a cell has a char that isnt a room, throw a bad format exception
					throw new BadConfigFormatException(layoutConfigFile); 
				}
				grid[i][j] = new BoardCell(i,j,cellChar.charAt(0));
				if (cellChar.length() != 1) {
					checkSpecialChars(cellChars, i, j);
				} 
			}
		}
	}

	//Find if the cell in layout had any special characters
	private void checkSpecialChars(ArrayList<ArrayList<String>> cellChars, int i, int j) {
		BoardCell cell = grid[i][j];
		char specialChar = cellChars.get(i).get(j).charAt(1);
		switch (specialChar) { //Switch for the second char if it has 2 chars
		case '^':
			cell.setDoorDirection(DoorDirection.UP);
			break;
		case '>':
			cell.setDoorDirection(DoorDirection.RIGHT);
			break;
		case '<':
			cell.setDoorDirection(DoorDirection.LEFT);
			break;
		case 'v':
			cell.setDoorDirection(DoorDirection.DOWN);
			break;
		case '#':
			roomMap.get(cell.getInitial()).setLabelCell(cell);
			cell.setRoomLabel(true);
			break;
		case '*':
			roomMap.get(cell.getInitial()).setCenterCell(cell);
			cell.setRoomCenter(true);
			break;
		default:
			cell.setSecretPassage(specialChar);
			break;
		}
	}

	//Read in layout and return it as a 2d array list
	private ArrayList<ArrayList<String>> readLayout() throws FileNotFoundException {
		FileReader reader = new FileReader(layoutConfigFile);
		Scanner in = new Scanner(reader);
		ArrayList<ArrayList<String>> cellChars = new ArrayList<ArrayList<String>>();
		while (in.hasNextLine()) { //Read in each line
			String line = in.nextLine();
			String[] words = line.split(",");
			ArrayList<String> row = new ArrayList<String>();
			for (String i: words) {
				row.add(i);
			}
			cellChars.add(row);
		}
		return cellChars;
	}

	//Starts the target calculation process
	public void calcTargets(BoardCell cell, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets.clear();
		visited.add(cell);
		findAllTargets(cell, pathLength);
		if (currentPlayer.isJustMoved()) {
			targets.add(currentPlayer.getCurrentLocation());
			currentPlayer.setJustMoved(false);
		}
	}

	//Recursive call to search through targets
	private static void findAllTargets(BoardCell cell, int pathLength) {
		for (BoardCell adjCell: cell.getAdjList()) {
			//If the target is not visited and either not occupied or a room center, then continue
			if (!visited.contains(adjCell) && (!adjCell.isOccupied() || adjCell.isRoomCenter())) {
				visited.add(adjCell);
				if (pathLength == 1) { //If path is 1, add to targets
					targets.add(adjCell);
				}
				else if (adjCell.isRoomCenter()) { //If we have a room center, add it to targets no matter the path
					targets.add(adjCell);
				}
				else { //Otherwise, find all targets from here with shorter path length
					findAllTargets(adjCell, pathLength - 1);
				}
				visited.remove(adjCell); //Remove this cell from visited after calculating all its targets
			}
		}
	}

	//Check to see if an accusation is the correct solution
	public Boolean checkAccusation(Solution realSolution, Card room, Card person, Card weapon) {
		if (realSolution.getPerson().equals(person) && realSolution.getRoom().equals(room) && realSolution.getWeapon().equals(weapon)) {
			return true;
		} else {
			return false;
		}
	}

	//Call every player except the suggesting player to try and disprove the suggestion
	public Card handleSuggestion(ArrayList<Player> players, Player suggester, Card room, Card person, Card weapon) {
		Card disprove = null;
		int starting = players.indexOf(suggester);
		for (int i = 0; i < players.size(); i++) {
			int index;
			if (i + starting < 6) {
				index = i + starting;
			} else {
				index = i + starting - 6;
			}
			if (index != starting) {
				disprove = players.get(index).disproveSugestion(room, person, weapon);
			}
			if (disprove != null) {
				if (players.get(index).getHuman() && !suggester.getHuman()) {
					guessResult = "You disproved with the " + disprove.getCardName() + " card.";
				} else if (!players.get(index).getHuman() && !suggester.getHuman()){
					guessResult = "This was disproved by " + players.get(index).getName();
				} else {
					guessResult = players.get(index).getName() + " showed you the " + disprove.getCardName() + " card.";
				}
				suggester.addToSeen(disprove, players.get(index));
				break;
			}
		}
		if (disprove == null) {
			guessResult = "No one can disprove";
			if (!suggester.getHuman()) {
				Solution nextAccusation = new Solution(room, person, weapon);
				suggester.setFinalGuess(nextAccusation);
			}
		}
		return disprove;
	}

	//Method that paint the square, room labels, and players in order
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		width = getWidth() / numColumns;
		height = getHeight() / numRows;
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				grid[j][i].draw(g, width*i, height*j, width, height);
			}
		}
		if (currentPlayer.getHuman() && needToMove) {
			for (BoardCell target: targets) {
				target.drawTargets(g, width*target.getCol(), height*target.getRow(), width, height);
			}
		}
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				grid[j][i].drawRoomNames(g, width*i, height*j, width, height);
			}
		}
		for (Player player: players) {
			ArrayList<Player> sameLocPlayers = new ArrayList<Player>();
			for (Player player2: players) {
				if (player.getCurrentLocation().equals(player2.getCurrentLocation())) {
					sameLocPlayers.add(player2);
				}
			}
			for (int i = 0; i < sameLocPlayers.size(); i++) {
				int offset = 10*i;
				sameLocPlayers.get(i).draw(g, width, height, offset);
			}
		}
	}

	//Process everything when the next turn button is hit
	public void processNext(GameControlPanel panel, ClueGame clueGame) {
		if (!finished()) {
			JOptionPane.showMessageDialog(clueGame, "You must move before you can end your turn.", "Cannot Finish Turn", JOptionPane.ERROR_MESSAGE);
			return;
		} 
		int currentIndex = players.indexOf(currentPlayer);
		currentIndex += 1;
		if (currentIndex == players.size()) {
			currentIndex = 0;
		}
		currentPlayer = players.get(currentIndex);
		dieRoll = rollDie();
		if (!currentPlayer.getHuman() && currentPlayer.getFinalGuess() != null) {
			computerWins(clueGame);
		}
		if (currentPlayer.getHuman()) {
			BoardCell currentCell = currentPlayer.getCurrentLocation();
			calcTargets(currentCell, dieRoll);
			Set<BoardCell> targetSet = this.getTargets();
			needToMove = true;
		} else {
			BoardCell moveTo = currentPlayer.move(dieRoll);
			currentPlayer.setLocation(moveTo.getCol(), moveTo.getRow());
			currentPlayer.getCurrentLocation().setOccupied(true);
			if (currentPlayer.getCurrentLocation().isRoomCenter()) {
				computerSuggestion(panel);
			} else {
				guess = "No guess";
				guessResult = "No result.";
			}
		}
		clueGame.cardPanel.update(players.get(0));
		repaint();
	}

	//Call this when the computer wins the game
	private void computerWins(ClueGame clueGame) {
		JOptionPane.showMessageDialog(clueGame, currentPlayer.getName() + " has found the solution before you, you have lost.", "You have lost :(", JOptionPane.INFORMATION_MESSAGE);
		String realPerson = getSolution().getPerson().getCardName();
		String realRoom = getSolution().getRoom().getCardName();
		String realWeapon = getSolution().getWeapon().getCardName();
		JOptionPane.showMessageDialog(clueGame, "The true accusation was " + realPerson + " in the " + realRoom + " with the " + realWeapon , "True accusation", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(clueGame, "You have finished the game, press OK to close.", "Game has Ended", JOptionPane.INFORMATION_MESSAGE);
		clueGame.dispose();
	}

	//Have the computer generate a suggestion
	private void computerSuggestion(GameControlPanel panel) {
		Solution suggestion = currentPlayer.createSuggestion();
		if (suggestion != null) {
			String personName = suggestion.getPerson().getCardName();
			String roomName = suggestion.getRoom().getCardName();
			String weaponName = suggestion.getWeapon().getCardName();
			guess = "It was " + personName + " in the " + roomName + " with the " + weaponName + ".";
			movePlayertoSuggested(personName);
			handleSuggestion(players, currentPlayer, suggestion.getRoom(), suggestion.getPerson(), suggestion.getWeapon());
		}
	}

	//Move a player to the place a suggestion brings them
	private void movePlayertoSuggested(String personName) {
		for (Player player: players) {
			if (player.getName() == personName) {
				BoardCell roomToMove = currentPlayer.getCurrentLocation();
				player.setLocation(roomToMove.getCol(), roomToMove.getRow());
				player.setJustMoved(true);
			}
		}
	}

	//Returns true if player turn is finished
	private Boolean finished() {
		if (!currentPlayer.getHuman()) {
			return true;
		} else {
			return !needToMove;
		}
	}

	//Process everything when the board is clicked
	public Boolean processClick(Point clickedPoint, ClueGame clueGame) {
		if (currentPlayer.getHuman() && needToMove) {
			int col = (int) (clickedPoint.getX() / width);
			int row =  (int) (clickedPoint.getY() / height) - 1;
			BoardCell clicked = getCell(row, col);
			if (targets.contains(clicked)) {
				currentPlayer.getCurrentLocation().setOccupied(false);
				currentPlayer.setLocation(col, row);
				currentPlayer.getCurrentLocation().setOccupied(true);
				needToMove = false;
				repaint();
				if (currentPlayer.getCurrentLocation().isRoomCenter()) {
					playerSuggestion(clueGame);
				} else {
					clueGame.controlPanel.setGuess("No guess");
					clueGame.controlPanel.setGuessResult("No result");
				}
			} else {
				return false;
			}
		}
		return true;
	}

	//Set up the suggestion and call the dialog
	private void playerSuggestion(ClueGame clueGame) {
		String roomName = roomMap.get(currentPlayer.getCurrentLocation().getInitial()).getName();
		ArrayList<String> weaponNames = new ArrayList<String>();
		ArrayList<String> personNames = new ArrayList<String>();
		for (Card card: deck.values()) {
			if (card.getCardType() == CardType.WEAPON) {
				weaponNames.add(card.getCardName());
			} else if (card.getCardType() == CardType.PERSON) {
				personNames.add(card.getCardName());
			}
		}
		clueGame.suggestionDialog(roomName, weaponNames, personNames);
	}
	
	//Handle the suggestion after the dialog
	public void playerSuggestion2(Solution guess, GameControlPanel control) {
		handleSuggestion(players, currentPlayer, guess.getRoom(), guess.getPerson(), guess.getWeapon());
		this.guess = "It was " + guess.getPerson().getCardName() + " in the " + guess.getRoom().getCardName() + " with the " + guess.getWeapon().getCardName() + ".";
		control.setGuess(this.guess);
		control.setGuessResult(guessResult);
		movePlayertoSuggested(guess.getPerson().getCardName());
		repaint();
	}

	//Do this when someone hits the accusation button, calls up the dialog
	public void processAccusation(ClueGame clueGame) {
		ArrayList<String> weaponNames = new ArrayList<String>();
		ArrayList<String> personNames = new ArrayList<String>();
		ArrayList<String> roomNames = new ArrayList<String>();
		for (Card card: deck.values()) {
			if (card.getCardType() == CardType.WEAPON) {
				weaponNames.add(card.getCardName());
			} else if (card.getCardType() == CardType.PERSON) {
				personNames.add(card.getCardName());
			} else {
				roomNames.add(card.getCardName());
			}
		}
		clueGame.accusationDialog(roomNames, personNames, weaponNames);
	}

	//Handle when the accusation dialog is done
	public void processAccusation2(ClueGame clueGame) {
		if (currentPlayer.getHuman() && needToMove) {
			Solution guess = currentPlayer.getFinalGuess();
			Boolean win = checkAccusation(getSolution(), guess.getRoom(), guess.getPerson(), guess.getWeapon());
			JOptionPane.showMessageDialog(clueGame, "You accusation was " + guess.getPerson().getCardName() + " in the " + guess.getRoom().getCardName() + " with the " + guess.getWeapon().getCardName(), "Accusation", JOptionPane.INFORMATION_MESSAGE);
			if (win) {
				JOptionPane.showMessageDialog(clueGame, "You found the solution before any of the other players! You win!", "You have won :)", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(clueGame, "Your accusation was incorrect, you have lost", "You have lost :(", JOptionPane.INFORMATION_MESSAGE);
				String realPerson = getSolution().getPerson().getCardName();
				String realRoom = getSolution().getRoom().getCardName();
				String realWeapon = getSolution().getWeapon().getCardName();
				JOptionPane.showMessageDialog(clueGame, "The true accusation was " + realPerson + " in the " + realRoom + " with the " + realWeapon , "True accusation", JOptionPane.INFORMATION_MESSAGE);
			}
			JOptionPane.showMessageDialog(clueGame, "You have finished the game, press OK to close.", "Game has Ended", JOptionPane.INFORMATION_MESSAGE);
			clueGame.dispose();
		} else {
			JOptionPane.showMessageDialog(clueGame, "You must make an accusation at the beginning of your turn.", "Not your turn", JOptionPane.ERROR_MESSAGE);
		}
	}

	//Get a random number from 1-6
	private int rollDie() {
		Random rand = new Random();
		return rand.nextInt(6) + 1;
	}

	//Set the solution for the game
	public void setSolution(Card room, Card person, Card weapon) {
		Solution.setSolution(room, person, weapon);
	}

	//Board getters
	public static Board getInstance() {
		return theInstance;
	}

	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Map<Character, Room> getRoomMap() {
		return roomMap;
	}

	public static BoardCell getCell(int i, int j) {
		return grid[i][j];
	}

	public static Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return grid[i][j].getAdjList();
	}

	public static Set<BoardCell> getTargets() {
		return targets;
	}

	public Card getCard(String name) {
		return deck.get(name);
	}

	public static Map<String, Card> getDeck() {
		return deck;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Solution getSolution() {
		return Solution;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public int getDieRoll() {
		return dieRoll;
	}

	public String getGuess() {
		return guess;
	}

	public String getGuessResult() {
		return guessResult;
	}
}
