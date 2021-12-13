package sudoku;

public class SudokuMatrix {
	int[][] matrix;
	String name;
	
	public SudokuMatrix(int[][] matrix, String name) {
		this.matrix = matrix;
		this.name = name;
	}
	
	public int[][] get() {
		return matrix;
	}
	
	public String toString() {
		return name;
	}
		
		
}
