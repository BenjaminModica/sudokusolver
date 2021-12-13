package sudoku;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SudokuView {
	private JTextField[][] reference;

	public SudokuView(SudokuGrid grid, ArrayList<SudokuMatrix> sudokuList) {
		reference = new JTextField[9][9];
		SwingUtilities.invokeLater(() -> createWindow(grid, "Sudoku", 500, 550, sudokuList));
	}

	private void createWindow(SudokuGrid grid, String title, int width, int height, ArrayList<SudokuMatrix> sudokuList) {
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			System.out.println("Could not update UI");
			e1.printStackTrace();
		}

		// Create a grid for the SudokuGrid
		JPanel panel = new JPanel(new GridLayout(9, 9, 3, 3));
		paintGrid(grid, panel);

		// Create buttons
		JButton solveButton = new JButton("Solve");
		JButton clearButton = new JButton("Clear");
		solveButton.setFont(new java.awt.Font("Helvetica", 1, 15));
		clearButton.setFont(new java.awt.Font("Helvetica", 1, 15));
		clearButton.setBorderPainted(false);
		clearButton.setBackground(Color.pink);
		JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 0));
		buttonPanel.add(solveButton);
		buttonPanel.add(clearButton);
		buttonPanel.setPreferredSize(new Dimension(500, 50));

		//ActionListener for Solve button
		solveButton.addActionListener(e -> {
			try {
				updateGridBeforeSolve(grid);

				if (grid.solve()) {
					updateGrid(grid);
					JOptionPane.showMessageDialog(null, "The sudoku is solvable", "SOLVABLE", JOptionPane.PLAIN_MESSAGE);
				} else {
					updateGrid(grid);
					JOptionPane.showMessageDialog(null, "The sudoku is not solvable", "NOT SOLVABLE", JOptionPane.PLAIN_MESSAGE);
				}
			
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Added digit is not allowed, try again", " ", JOptionPane.ERROR_MESSAGE);
				//e1.printStackTrace();
			}});

		//ActionListener for Clear Button
		clearButton.addActionListener(e -> {
			grid.clear();
			updateGrid(grid);
		});
		
		//Dropdown-list for premade sudokus
		JComboBox<SudokuMatrix> comboBox = new JComboBox<SudokuMatrix>();
		
		for(int i = 0; i < sudokuList.size(); i++) {
			comboBox.addItem(sudokuList.get(i));
			
		}
		
		comboBox.setFont(new java.awt.Font("Helvetica", 1, 15));
		((JLabel) comboBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
        comboBox.addActionListener(e -> {
            //grid.setMatrix( (int[][]) comboBox.getSelectedItem());
        	grid.setMatrix(((SudokuMatrix) comboBox.getSelectedItem()).get());
            updateGrid(grid);
        });
        
        buttonPanel.add(comboBox);

		// Add panels to window
		pane.add(panel);
		pane.add(buttonPanel, BorderLayout.SOUTH);

		frame.setSize(new Dimension(width, height));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Update view from model
	 */
	private void updateGrid(SudokuGrid grid) {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (grid.get(r, c) != 0) {
					reference[r][c].setText(String.valueOf(grid.get(r, c)));
				} else {
					reference[r][c].setText(null);
				}
			}
		}
//		// For debugging purposes
//		for (int r = 0; r < 9; r++) {
//			System.out.println(" ");
//			for (int c = 0; c < 9; c++) {
//				System.out.print(grid.get(r, c));
//			}
//		}
//		// For debugging purposes
//		System.out.println(" ");
//		System.out.println("-----------");
	}

	/**
	 * Update model from view before trying to solve
	 */
	private void updateGridBeforeSolve(SudokuGrid grid) throws IllegalArgumentException{
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				String newValueString = reference[r][c].getText();
				
				if(newValueString.equals("0")) {
					throw new IllegalArgumentException("Digit is outside of range[1..9]");
				}
				
				if (newValueString.length() != 0) {
					int newValue = Integer.valueOf(newValueString);
					grid.add(r, c, newValue);
				} else {
					grid.add(r, c, 0);
				}
			}
		}
//		// For debugging purposes
//		for (int r = 0; r < 9; r++) {
//			System.out.println(" ");
//			for (int c = 0; c < 9; c++) {
//				System.out.print(grid.get(r, c));
//			}
//		}
//		System.out.println("-----------");
	}

	/**
	 * Updates view from start.  
	 */
	private void paintGrid(SudokuGrid grid, JPanel panel) {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				JTextField textField = new JTextField();
				if (grid.get(r, c) != 0) {
					textField = new JTextField(String.valueOf(grid.get(r, c)));
				}
				textField.setFont(new java.awt.Font("Helvetica", 2, 25));
				textField.setHorizontalAlignment(JTextField.CENTER);
				if (r <= 2 && c <= 2 || r <= 2 && c >= 6 || r >= 3 && r <= 5 && c >= 3 && c <= 5 || r >= 6 && c <= 2
						|| r >= 6 && c >= 6) {
					textField.setBackground(Color.pink);
				}
				reference[r][c] = textField; // Save a reference to these textfields
				panel.add(textField);
			}
		}
	}

}
