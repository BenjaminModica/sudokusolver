package sudoku;

public interface SudokuSolver {
	/**
	 * Checks if sudoku is solvable. Will update grid with solution if solvable
	 * Calls method solve which tries to solve sudoku recursively
	 * @return true if sudoku is solvable otherwise false
	 */
	boolean solve();

	/**
	 * Puts digit in the box row, col.
	 * 
	 * @param row   The row
	 * @param col   The column
	 * @param digit The digit to insert in box row, col
	 * @throws IllegalArgumentException if row, col or digit is outside the range
	 *                                  [0..9]
	 */
	void add(int row, int col, int digit);

	/**
	 * Removes number in sudoku
	 * @param row the row
	 * @param col the column
	 */
	void remove(int row, int col);

	/**
	 * Return number in grid
	 * @param row the row
	 * @param col the col
	 * @return the number in that row and col
	 */
	int get(int row, int col);

	/**
	 * Checks that all filled in digits follows the the sudoku rules.
	 * @return true if all digits follow sudoku rules otherwise false
	 */
	boolean isValid();

	/**
	 * Clears the sudoku grid
	 */
	void clear();

	/**
	 * Fills the grid with the digits in m. The digit 0 represents an empty box.
	 * 
	 * @param m the matrix with the digits to insert
	 * @throws IllegalArgumentException if m has the wrong dimension or contains
	 *                                  values outside the range [0..9]
	 */
	void setMatrix(int[][] m);

	/**
	 * Returns the current sudoku matrix
	 * @return grid matrix
	 */
	int[][] getMatrix();
}