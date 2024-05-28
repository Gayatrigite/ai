//IDFS

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class idfs {
    private final int N; // Size of the chessboard
    private int[][] board; // The chessboard represented as a 2D array
    private int solutionCount; // Counter to keep track of the number of solutions found

    // Constructor to initialize the class with the size of the chessboard
    public idfs(int N) {
        this.N = N;
        this.board = new int[N][N];
        this.solutionCount = 0;
    }

    // Method to solve the N-Queens problem
    public void solve() {
        Stack<int[]> stack = new Stack<>(); // Stack to store partial solutions
        int[] initialBoard = new int[N]; // Initial state with no queens placed
        Arrays.fill(initialBoard, -1); // Fill the array with -1 indicating empty squares
        stack.push(initialBoard); // Push the initial state onto the stack

        // Loop until the stack is empty
        while (!stack.isEmpty()) {
            int[] currentBoard = stack.pop(); // Pop the current state from the stack
            int col = findNextEmptyCol(currentBoard); // Find the next empty column

            // If all queens are placed successfully (no empty columns remaining)
            if (col == N) {
                // Print the solution and increment the solution counter
                printSolution(currentBoard);
                solutionCount++;
            } else {
                // Iterate through each row in the current column
                for (int row = 0; row < N; row++) {
                    // If placing a queen in this row and column is safe
                    if (isSafe(currentBoard, row, col)) {
                        // Create a new board configuration by placing a queen in this position
                        int[] newBoard = currentBoard.clone();
                        newBoard[col] = row; // Place the queen in the current column
                        stack.push(newBoard); // Push the new configuration onto the stack
                    }
                }
            }
        }

        // If no solutions were found, print a message indicating so
        if (solutionCount == 0) {
            System.out.println("No solutions exist for N = " + N);
        }
    }

    // Method to find the next empty column in a board configuration
    private int findNextEmptyCol(int[] board) {
        for (int col = 0; col < N; col++) {
            if (board[col] == -1) {
                return col; // Return the index of the first empty column found
            }
        }
        return N; // If no empty columns are found, return N (board size)
    }

    // Method to check if placing a queen at a given row and column is safe
    private boolean isSafe(int[] board, int row, int col) {
        for (int prevCol = 0; prevCol < col; prevCol++) {
            int prevRow = board[prevCol];
            // Check if queens can attack each other horizontally or diagonally
            if (prevRow == row || Math.abs(prevRow - row) == Math.abs(prevCol - col)) {
                return false; // If there's a conflict, return false
            }
        }
        return true; // If no conflicts are found, return true
    }

    // Method to print a board configuration representing a solution
    private void printSolution(int[] board) {
        System.out.println("Solution " + (solutionCount + 1) + ":");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[j] == i) {
                    System.out.print("Q "); // Print 'Q' if a queen is placed in this square
                } else {
                    System.out.print("* "); // Print '*' for empty squares
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Main method to execute the program
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the board size for N-Queens problem: ");
        int N = scanner.nextInt(); // Board size for N-Queens problem
        scanner.close(); // Close the scanner to avoid resource leak
        idfs csp = new idfs(N); // Create an instance of the NQueensCSP class
        csp.solve(); // Solve the N-Queens problem
    }
}