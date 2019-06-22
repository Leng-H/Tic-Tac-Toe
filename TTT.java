import java.util.Random;
import java.util.Scanner;

/**
 * 5th attempt after accidentally deleted everything. Starting from scratch.
 * 
 * @author sivlenghul
 * @since June 21, 2019
 */
public class TTT {

	public static void main(String[] args) {
		System.out.println("Welcome to the game of Tic-Tac-Toe [Version 5.0]");
		
		startGame();
		
		//Check if the user wants to continue or stop the game
		while (true) {
			System.out.println("Want to do this all... over... again? =_=");
			System.out.println("Enter Y or N");

			input.nextLine();
			continueGame = input.nextLine();

			if (continueGame.equalsIgnoreCase("Y") || continueGame.equalsIgnoreCase("Yes")) {
				System.out.println("Since you ask for it. \nEnjoy!");
				startGame();
			}

			else if (continueGame.equalsIgnoreCase("N") || continueGame.equalsIgnoreCase("No")) {
				System.out.println("Good choice. See ya!");
				break;
			}

			else {
				System.out.println("Invalid input. Try again.");
			}
		}
	}

	//////////////////////////////////////////////////////////////////

	private static int i, j, row, col;
	private static char[][] board = new char[3][3];
	private static char currentPlayer = 'X';
	private static Scanner input = new Scanner(System.in);
	private static String continueGame;
	private static int numOfPlayers;
	private static Random random = new Random();

	//////////////////////////////////////////////////////////////////
	public static void startGame() {
		initializeBoard();
		printBoard();

		System.out.println("Please enter 1 or 2:");
		System.out.println("1. 1 Player Game; player vs. computer");
		System.out.println("2. 2 Players Game; player vs. player");
		numOfPlayers = input.nextInt();

		while (true) {
			// player versus computer
			if (numOfPlayers == 1) {
				System.out.println("NOTE: Player_O is a preset computer player");
				do {
					if(currentPlayer == 'O') {
						computerPlayer();
						System.out.println();
						System.out.println("Player_" + currentPlayer + ", (Computer's turn)");
						System.out.println("Row: " + row);
						System.out.println("Column: " + col);
						placeMark(currentPlayer);
						changePlayer();
					} else {
						System.out.println();
						System.out.println("Player_" + currentPlayer + ", (Human Player)");
						System.out.println("Enter a number from 1-3");
						System.out.print("Row: ");
						row = input.nextInt() - 1;
						System.out.print("Column: ");
						col = input.nextInt() - 1;
						
						evaluatePlayerMoves();
						placeMark(currentPlayer);
						changePlayer();
					}
					
					// Check for tie
					if (space() == true) {
						break;
					}
				// Keep playing while there's still no winner found
				} while (!checkForWin());
				break;
			}
			// player versus player
			else if (numOfPlayers == 2) {
				do {
					System.out.println("Player_" + currentPlayer + ", ");
					System.out.println("Enter a number from 1-3");
					System.out.print("Row: ");
					row = input.nextInt() - 1;
					System.out.print("Column: ");
					col = input.nextInt() - 1;

					evaluatePlayerMoves();
					placeMark(currentPlayer);
					changePlayer();

					if (space() == true) {
						break;
					}
				} while (!checkForWin());
				break;
			}

			else {
				System.out.println("Try again!");
				System.out.println("Enter 1 or 2");
			}
		}
	}

	public static void initializeBoard() {
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				board[i][j] = '-';
			}
		}
	}

	public static void printBoard() {
		System.out.println(" -----------------");
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				System.out.print(" | " + board[i][j] + " |");
			}
			System.out.println();
			System.out.println(" -----------------");
		}
	}

	public static void placeMark(char c) {
		if (currentPlayer == c) {
			board[row][col] = c;
		} else {
			board[row][col] = c;
		}
		printBoard();
		status();
	}

	public static void evaluatePlayerMoves() {
		while (true) {
			if (row < 0 || row > 2) {
				System.out.println("Out of range");
			} else if (col < 0 || col > 2) {
				System.out.println("Out of range");
			} else if (board[row][col] != '-') {
				System.out.println("Occupied spot");
			} else {
				// the move is valid
				break;
			}
			System.out.println("Try again!");

			System.out.print("Row: ");
			row = input.nextInt() - 1;
			System.out.print("Column: ");
			col = input.nextInt() - 1;
		}
	}

	/*
	 * Check for the status of "tie or win"
	 */
	public static void status() {
		if (space() == true) {
			System.out.println("It's a tie");
		} else if (checkForWin() == true) {
			System.out.println("Player_" + currentPlayer + " is the winner.");
		}
	}

	public static boolean space() {
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				if (board[i][j] == '-') {
					return false; // board is not full
				}
			}
		}
		return true; // board is full
	}

	/*
	 * Check the rows, columns, and diagonally for win
	 */
	public static boolean checkForWin() {
		// Check row for win
		for (i = 0; i < 3; i++) {
			if (checkForDupes(board[0][i], board[1][i], board[2][i]) == true) {
				return true;
			}
			// Check column for win
			if (checkForDupes(board[i][0], board[i][1], board[i][2]) == true) {
				return true;
			}

			// Check diagonally for win; 00, 11, 22 || 02, 11, 20
			if (checkForDupes(board[0][0], board[1][1], board[2][2]) == true
					|| checkForDupes(board[0][2], board[1][1], board[2][0]) == true) {
				return true;
			}
		}
		return false; // No winner
	}

	/*
	 * Return true if there are 3 same mark in a line, false otherwise.
	 */
	public static boolean checkForDupes(char c1, char c2, char c3) {
		if (c1 != '-' || c2 != '-' || c3 != '-') {
			if ((c1 == c2) && (c2 == c3)) {
				return true; // there are 3 duplicates
			}
		}
		return false; // no duplicates
	}

	/*
	 * Return the current player; Player_X or Player_O
	 */
	public static char currentPlayer() {
		return currentPlayer;
	}

	/*
	 * Alternating between the two players
	 */
	public static char changePlayer() {
		if (currentPlayer == 'X') {
			return currentPlayer = 'O';
		} else {
			return currentPlayer = 'X';
		}
	}

	/*
	 * Computer player
	 */
	public static void computerPlayer() {
		while (true) {
			row = (int) random.nextInt(3);
			col = (int) random.nextInt(3);

			// check for space availability
			if (board[row][col] == '-') {
				board[row][col] = 'O';
				break;
			}
		}
	}

}// END OF CLASS
