package sudoku;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

class SudokuTester {

	@Test
	void testSolvableSudoku() {
		int[][] grid = new int[9][9];
		grid[0][2] = 8;
		grid[0][5] = 9;
		grid[0][7] = 6;
		grid[0][8] = 2;
		grid[1][8] = 5;
		grid[2][0] = 1;
		grid[2][2] = 2;
		grid[2][3] = 5;
		grid[3][3] = 2;
		grid[3][4] = 1;
		grid[3][7] = 9;
		grid[4][1] = 5;
		grid[4][6] = 6;
		grid[5][0] = 6;
		grid[5][7] = 2;
		grid[5][8] = 8;
		grid[6][0] = 4;
		grid[6][1] = 1;
		grid[6][3] = 6;
		grid[6][5] = 8;
		grid[7][0] = 8;
		grid[7][1] = 6;
		grid[7][4] = 3;
		grid[7][6] = 1;
		grid[8][6] = 4;
		SudokuMatrix matrix = new SudokuMatrix(grid, "solvable");
		SudokuGrid sudoku = new SudokuGrid(matrix);
		assertTrue(sudoku.solve(), "Solvable sudoku returns false");
	}
	
	@Test
	void testUnsolvableSudoku() {
		int[][] grid = new int[9][9];
		grid[0][0] = 2;
		grid[0][3] = 9;
		grid[1][7] = 6;
		grid[2][5] = 1;
		grid[3][0] = 5;
		grid[3][2] = 2;
		grid[3][3] = 6;
		grid[3][6] = 4;
		grid[3][8] = 7;
		grid[4][5] = 4;
		grid[4][6] = 1;
		grid[5][4] = 9;
		grid[5][5] = 8;
		grid[5][7] = 2;
		grid[5][8] = 3;
		grid[6][5] = 3;
		grid[6][7] = 8;
		grid[7][2] = 5;
		grid[7][4] = 1;
		grid[8][2] = 7;
		SudokuMatrix matrix = new SudokuMatrix(grid, "unsolvable");
		SudokuGrid sudoku = new SudokuGrid(matrix);
		assertFalse(sudoku.solve(), "Unsolvable sudoku returns true");
	}
	
	@Test
	void testBadInput() {
		int[][] grid1 = new int[9][9];
		grid1[0][1] = 5;
		grid1[0][5] = 5;
		SudokuMatrix matrix = new SudokuMatrix(grid1, "badinput");
		SudokuGrid sudoku = new SudokuGrid(matrix);
		assertFalse(sudoku.solve(), "Unsolvable sudoku because of bad input returns true");
		assertThrows(IllegalArgumentException.class, () -> sudoku.add(0, 0, 10), "does not throw exception when entering numbers too large");
		assertThrows(IllegalArgumentException.class, () -> sudoku.add(10, 10, 1), "does not throw exception when entering digit outside of sudoku");
	}
	
	@Test
	void testAddAndGet() {
		SudokuGrid grid = new SudokuGrid();
		grid.add(5, 5, 5);
		grid.add(4, 4, 4);
		grid.add(8, 8, 8);
		assertEquals(5, grid.get(5, 5), "Wrong digit at row col");
		assertEquals(4, grid.get(4, 4), "Wrong digit at row col");
		assertEquals(8, grid.get(8, 8), "Wrong digit at row col");
	}
	
	@Test
	void testRemove() {
		SudokuGrid grid = new SudokuGrid();
		grid.add(5, 5, 5);
		grid.add(4, 4, 4);
		grid.add(8, 8, 8);
		grid.remove(5, 5);
		grid.remove(4, 4);
		grid.remove(8, 8);
		assertEquals(0, grid.get(5, 5), "Digit not zero after remove");
		assertEquals(0, grid.get(4, 4), "Digit not zero after remove");
		assertEquals(0, grid.get(8, 8), "Digit not zero after remove");
	}
	
	@Test
	void testIsValid() {
		SudokuGrid grid = new SudokuGrid();
		grid.add(0, 0, 1);
		grid.add(0, 1, 1);
		assertFalse(grid.isValid(), "Returns true even though sudoku is not valid");
		grid.remove(0, 1);
		assertTrue(grid.isValid(), "Returns false even though sudoku is valid");
	}
	
	@Test
	void testClear() {
		SudokuGrid grid = new SudokuGrid();
		Random rand = new Random();
		for(int i = 0; i < 20; i++) {
			grid.add(rand.nextInt(8), rand.nextInt(8), rand.nextInt(8));
		}
		grid.clear();
		for(int r = 0; r < 9; r++) {
			for(int c = 0; c < 9; c++) {
				assertEquals(0, grid.get(r, c), "Sudoku not empty after clear");
			}
		}
	}
	
	@Test
	void testSetAndGetMatrix() {
		SudokuGrid sudoku = new SudokuGrid();
		int[][] grid = new int[10][10];
		grid[5][5] = 5;
		assertThrows(IllegalArgumentException.class, () -> sudoku.setMatrix(grid), "setMatrix does not throw error when matrix has wrong dimensions");
		
		int[][] m = new int[9][9];
		m[0][0] = 50;
		assertThrows(IllegalArgumentException.class, () -> sudoku.setMatrix(m), "setMatrix does not throw error when matrix has digit outside of range");
		
		int[][] matrix = new int[9][9];
		matrix[1][1] = 1;
		matrix[8][8] = 8;
		sudoku.setMatrix(matrix);
		int[][] matrixEquals = sudoku.getMatrix();
		assertTrue(() -> {
			for(int r = 0; r < 9; r++) {
				for(int c = 0; c < 9; c++) {
					if (matrix[r][c] != matrixEquals[r][c]) {
						return false;
					}
				}
			} return true;
		}, "setMatrix and then getmatrix does not give the same matrix back");
	}

}
