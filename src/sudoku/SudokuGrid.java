package sudoku;

public class SudokuGrid implements SudokuSolver {
	private int[][] grid;

	/**
	 *Constructor for empty starting sudoku 
	 */
	public SudokuGrid() {
		grid = new int[9][9];
	}
	/**
	 *Constructor with a premade sudoku added
	 * @param SudokuMatrix object
	 */
	public SudokuGrid(SudokuMatrix grid) {
		this.grid = new int[9][9];
		setMatrix(grid.get());
	}

	/**
	 * Checks if sudoku is solvable. Will update grid with solution if solvable
	 * Calls method solve which tries to solve sudoku recursively
	 * @return true if sudoku is solvable otherwise false
	 */
	@Override
	public boolean solve() {
		if (isValid()) { //Check if user input is correct before trying to solve
			return solve(0, 0);
		} else {
			return false;
		}
		
	}

	/**
	 * Recursive method which tries to solve the sudoku.
	 * @param r The current row
	 * @param c The current column
	 * @return true If it succesfully traversed the whole board
	 * @return false If all numbers 0-9 are tried in first box 
	 */
	private boolean solve(int r, int c) {
		if (r == 9) { // If the whole sudoku is traversed succesfully, then it's solveable --> return
						// true
			return true;
		} else if (grid[r][c] != 0) { // Checks if a number is already added by user --> skip that number
			if (c == 8) {
				return solve(r + 1, 0);
			} else {
				return solve(r, c + 1);
			}
		} else {
			for (int i = 1; i <= 9; i++) { // Tries to add numbers 1-9 in field, if possible --> add number and move on to next field,
											// if not --> remove number and backtrack to previous for loop
				grid[r][c] = 0;
				if (checkIfPossible(r, c, i)) {
					grid[r][c] = i;
					if (c == 8) {
						if (solve(r + 1, 0)) {
							return true;
						}
					} else {
						if (solve(r, c + 1)) {
							return true;
						}
					}
				} else {
					grid[r][c] = 0; //If it was not possible to place number, remove it from grid matrix
				}
			}
		}
		grid[r][c] = 0; //Removes remaining 9's from grid matrix when backtracking
		return false;
	}

	/**
	 * Checks if it is possible to add a number according to sudoku rules
	 * @param r The row of current number
	 * @param c The column of curent number
	 * @param i The number to check
	 * @return True if it works otherwise false
	 */
	private boolean checkIfPossible(int r, int c, int i) {
		// check row
		for (int cc = 0; cc < 9; cc++) {
			if (grid[r][cc] == i && c != cc) {
				return false;
			}
		}

		// check column
		for (int rr = 0; rr < 9; rr++) {
			if (grid[rr][c] == i && r != rr) {
				return false;
			}
		}

		// check subsquare
		if (!possibleInSquare(r, c, i)) {
			return false;
		}
		// If placeable in row, column and square return true
		return true;
	}

	/**
	 * Check if it is possible to place in a subsquare. 
	 * else - if cases that decides which subsquare to check
	 * @param r The row of current number
	 * @param c The column of current number
	 * @param i The number to check
	 * @return True if it works in subsquare otherwise false
	 */
	private boolean possibleInSquare(int r, int c, int i) {
		// One case for each subsquare. Calls private checkSquare method that checks if
		// possible to place in that square
		if (r <= 2 && c <= 2) {
			return checkSubSquare(r, c, 0, 2, 0, 2, i);
		} else if (c >= 3 && c <= 5 && r <= 2) {
			return checkSubSquare(r, c, 0, 2, 3, 5, i);
		} else if (r <= 2 && c >= 6) {
			return checkSubSquare(r, c, 0, 2, 6, 8, i);
		} else if (r >= 3 && r <= 5 && c <= 2) {
			return checkSubSquare(r, c, 3, 5, 0, 2, i);
		} else if (r >= 3 && r <= 5 && c >= 3 && c <= 5) {
			return checkSubSquare(r, c, 3, 5, 3, 5, i);
		} else if (r >= 3 && r <= 5 && c >= 6) {
			return checkSubSquare(r, c, 3, 5, 6, 8, i);
		} else if (r >= 6 && c <= 2) {
			return checkSubSquare(r, c, 6, 8, 0, 2, i);
		} else if (r >= 6 && c >= 3 && c <= 5) {
			return checkSubSquare(r, c, 6, 8, 3, 5, i);
		} else if (r >= 6 && c >= 6) {
			return checkSubSquare(r, c, 6, 8, 6, 8, i);
		} else {
			return false;
		}
	}

	/**
	 * Checks if it is possible to add number in subsquare
	 * @param r The current row
	 * @param c The current column
	 * @param ri Initial row of subsquare
	 * @param rf final row of subsquare
	 * @param ci Initial column of subsquare
	 * @param cf Final column of subsquare
	 * @param i The current number
	 * @return true if possible, otherwise false
	 */
	private boolean checkSubSquare(int r, int c, int ri, int rf, int ci, int cf, int i) {
		for (int rr = ri; rr <= rf; rr++) {
			for (int cc = ci; cc <= cf; cc++) {
				if (grid[rr][cc] == i && r != rr && c != cc) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Puts digit in the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range
	 *                                  [0..9]
	 */
	@Override
	public void add(int row, int col, int digit) throws IllegalArgumentException {
		if (row >= 0 && col >= 0 && digit >= 0 && row <= 9 && col <= 9 && digit <= 9) {
			grid[row][col] = digit;
		} else {
			throw new IllegalArgumentException("row, column or digit is outside range [0..9]");
		}
	}

	/**
	 * Removes number in sudoku
	 * @param row the row
	 * @param col the column
	 */
	@Override
	public void remove(int row, int col) {
		grid[row][col] = 0;
	}

	/**
	 * Return number in grid
	 * @param row the row
	 * @param col the col
	 * @return the number in that row and col
	 */
	@Override
	public int get(int row, int col) {
		return grid[row][col];
	}

	/**
	 * Checks that all filled in digits follows the the sudoku rules.
	 * @return true if all digits follow sudoku rules
	 * @return false if digits break the sudoku rules
	 */
	@Override
	public boolean isValid() {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (grid[r][c] != 0) {
					if (!checkIfPossible(r, c, grid[r][c]) || grid[r][c] > 9 || grid[r][c] < 0) { 
						return false;
					}
				}
			}
		} return true;
	}

	/**
	 * Clears the sudoku grid
	 */
	@Override
	public void clear() {
		int[][] emptyGrid = new int[9][9];
		grid = emptyGrid;
	}

	/**
	 * Fills the grid with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains
	 *                                  values outside the range [0..9]
	 */
	@Override
	public void setMatrix(int[][] m) throws IllegalArgumentException {
		
		if (m.length != 9 && m[0].length != 9) {
			throw new IllegalArgumentException("Matrix has wrong dimensions");
		}
		
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (m[r][c] >= 0 && m[r][c] <= 9) {
					grid[r][c] = m[r][c];
				} else {
					throw new IllegalArgumentException("Digit outside of range [0 .. 9]");
				}
			}
		}
	}

	/**
	 * Returns the current sudoku matrix
	 * @return grid matrix
	 */
	@Override
	public int[][] getMatrix() {
		return grid;
	}

}
