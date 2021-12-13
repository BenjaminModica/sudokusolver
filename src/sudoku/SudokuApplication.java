package sudoku;

import java.util.ArrayList;

public class SudokuApplication {

	public static void main(String[] args) {
		ArrayList<SudokuMatrix> sudokuList = new ArrayList<SudokuMatrix>();
		
		addPremadeSudokus(sudokuList);

		SudokuGrid sudoku = new SudokuGrid(sudokuList.get(0));
		SudokuView view = new SudokuView(sudoku, sudokuList);
	}
	
	private static void addPremadeSudokus(ArrayList<SudokuMatrix> sudokuList) {
		// Solvable Sudoku
		int[][] grid1 = new int[9][9];
		grid1[0][2] = 8;
		grid1[0][5] = 9;
		grid1[0][7] = 6;
		grid1[0][8] = 2;
		grid1[1][8] = 5;
		grid1[2][0] = 1;
		grid1[2][2] = 2;
		grid1[2][3] = 5;
		grid1[3][3] = 2;
		grid1[3][4] = 1;
		grid1[3][7] = 9;
		grid1[4][1] = 5;
		grid1[4][6] = 6;
		grid1[5][0] = 6;
		grid1[5][7] = 2;
		grid1[5][8] = 8;
		grid1[6][0] = 4;
		grid1[6][1] = 1;
		grid1[6][3] = 6;
		grid1[6][5] = 8;
		grid1[7][0] = 8;
		grid1[7][1] = 6;
		grid1[7][4] = 3;
		grid1[7][6] = 1;
		grid1[8][6] = 4;
		sudokuList.add(new SudokuMatrix(grid1, "Solvable"));
		
		// Unsolvable Sudoku
		int[][] grid2 = new int[9][9];
		grid2[0][0] = 2;
		grid2[0][3] = 9;
		grid2[1][7] = 6;
		grid2[2][5] = 1;
		grid2[3][0] = 5;
		grid2[3][2] = 2;
		grid2[3][3] = 6;
		grid2[3][6] = 4;
		grid2[3][8] = 7;
		grid2[4][5] = 4;
		grid2[4][6] = 1;
		grid2[5][4] = 9;
		grid2[5][5] = 8;
		grid2[5][7] = 2;
		grid2[5][8] = 3;
		grid2[6][5] = 3;
		grid2[6][7] = 8;
		grid2[7][2] = 5;
		grid2[7][4] = 1;
		grid2[8][2] = 7;
		sudokuList.add(new SudokuMatrix(grid2, "Unsolvable"));
		
		int[][] grid3 = new int[9][9];
		grid3[0][0] = 9;
		grid3[0][1] = 9;
		sudokuList.add(new SudokuMatrix(grid3, "Not Valid"));
	}

}
