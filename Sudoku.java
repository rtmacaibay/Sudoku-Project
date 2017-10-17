import java.util.Random;

public class Sudoku {
	//global variables used throughout program
	private int[][] board; //sudoku board
	private int size; //size of the board ~ preset
	private Random r; //random variable to randomize things (duh)
	
	/**
	 * main method to run program
	 * @param args
	 */
	public static void main(String[] args) {
		//create sudoku object
		Sudoku s = new Sudoku();
		
		//generate board and print it
		s.generateBoard();
		s.printBoard();
		
		System.out.println();
		
		//fill the board then print it if its good
		if(s.fillBoard())
			s.printBoard();
	}
	
	/**
	 * constructor to initialize global variables
	 */
	public Sudoku() {
		size = 9;
		board = new int[size][size];
		r =  new Random();
	}
	
	/**
	 * generate board by randomly placing one value in each cell
	 */
	public void generateBoard() {
		//iterate three times for each cell vertically ~ row offset
		for (int row = 0; row < 3; row++) {
			//iterate three times for each cell horizontally ~ column offset
			for (int col = 0; col < 3; col++) {
				//randomize an a spot for a random value 
				int randRow = r.nextInt(3) + (row * 3);
				int randCol = r.nextInt(3) + (col * 3);
				int randVal = r.nextInt(9) + 1;
				//check if this value is good, if not, redo it
				if (check(randVal, randRow, randCol)) {
					board[randRow][randCol] = randVal;
				} else {
					col--;
				}
			}
		}
	}
	
	/**
	 * helper method to fill the Sudoku board recursively
	 * @return whether or not filling the board was successful
	 */
	public boolean fillBoard() {
		return fillBoard(0, 0);
	}
	
	/**
	 * recursive method to fill the Sudoku board completely
	 * @param row - coordinate for row
	 * @param col - coordinate for col
	 * @return whether or not filling the board was successful
	 */
	private boolean fillBoard(int row, int col) {
		//check if we've reached the end (col + 1 bc you dont want to accidentally go out of bounds)
		if (row >= size && col + 1 >= size) {
			return true;
		} else if (row >= size) {
			//reset row
			row = 0;
			//go to next column if we finished the row
			col++;
		}
		
		//check if there is a value occupying the spot just in case
		if (board[row][col] != 0) {
			//continue
			return fillBoard(row + 1, col);
		}
		
		//iterate through possible values to find correct value
		for (int newVal = 1; newVal <= 9; newVal++) {
			//checks if the value can be placed
			if (check(newVal, row, col)) {
				//places value into spot
				board[row][col] = newVal;
				//goes into next spot and check if its good
				if(fillBoard(row + 1, col))
					//if all good, return true
					return true;
				//backtracking
				board[row][col] = 0;
			}
		}
		//return false to indicate filling board is unsuccessful OR value isnt working for the current spot
		return false;
	}
	
	/**
	 * checks if a value can be in that spot
	 * @param newVal - target value to go into the spot
	 * @param row - target row
	 * @param col - target column
	 * @return whether or not the value can go into that spot
	 */
	private boolean check(int newVal, int row, int col) {
		//check if the value can go into that row
		for (int r = 0; r < size; r++) {
			if (newVal == board[r][col])
				return false;
		}
		//check if the value can go into that column
		for (int c = 0; c < size; c++) {
			if (newVal == board[row][c])
				return false;
		}
		
		//create the offsets to indicate which cell we are in
		int cellRestraintRow = (row / 3) * 3;
		int cellRestraintCol = (col / 3) * 3;
		
		//check if the value can go into that cell
		for (int r = cellRestraintRow; r < cellRestraintRow + 3; r++) {
			for (int c = cellRestraintCol; c < cellRestraintCol + 3; c++) {
				if (newVal == board[r][c])
					return false;
			}
		}
		
		//if all is well
		return true;
	}
	
	/**
	 * prints the board
	 */
	public void printBoard() {
		//iterates through the rows
		for (int row = 0; row < size; row++) {
			//separates board from outside things
			if (row == 0)
				System.out.println(" -----------------------");
			//iterates through the columns
			for (int col = 0; col < size; col++) {
				//separates the board from outside things
				if (col == 0)
					System.out.print("| ");
				//display value
				System.out.print(board[row][col] + " ");
				//separates cells
				if ((col + 1) % 3 == 0)
					System.out.print("| ");
			}
			System.out.println();
			//separates cells
			if ((row + 1) % 3 == 0)
				System.out.println(" -----------------------");
		}
	}
}