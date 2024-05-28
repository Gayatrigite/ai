import java.util.Arrays;
import java.util.Scanner;

public class csp {
    private final int N; // Size of the chessboard
    private int[] board; // Representation of the board
    private int solutionCount; // Count of solutions found

    // Constructor to initialize the board size and solution count
    public csp(int N) {
        this.N = N;
        this.board = new int[N]; // Initialize the board with size N
        Arrays.fill(board, -1); // Fill the board with -1 to denote no queens placed
        this.solutionCount = 0; // Initially, no solutions found
    }

    // Method to solve the N-Queens problem
    public void solve() {
        solveNQueens(0); // Start solving from column 0
        if (solutionCount == 0) {
            System.out.println("No solutions exist for N = " + N);
        }
    }

    // Recursive method to place queens on the board
    private void solveNQueens(int col) {
        if (col == N) { // If all queens are placed successfully
            printSolution(); // Print the solution
            solutionCount++; // Increment solution count
            return;
        }

        for (int row = 0; row < N; row++) {
            if (isSafe(row, col)) { // Check if it's safe to place a queen at this position
                board[col] = row; // Place the queen
                solveNQueens(col + 1); // Recur for the next column
                board[col] = -1; // Backtrack (remove the queen from this position)
            }
        }
    }

    // Method to check if it's safe to place a queen at a particular position
    private boolean isSafe(int row, int col) {
        for (int prevCol = 0; prevCol < col; prevCol++) {
            int prevRow = board[prevCol];

            // Check if queen can attack in the same row or diagonals
            if (prevRow == row || Math.abs(prevRow - row) == Math.abs(prevCol - col)) {
                return false; // Unsafe to place queen here
            }
        }
        return true; // Safe to place queen here
    }

    // Method to print the board configuration for a solution
    private void printSolution() {
        System.out.println("Solution " + (solutionCount + 1) + ":");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[j] == i) {
                    System.out.print("Q "); // Print 'Q' for queen
                } else {
                    System.out.print("* "); // Print '*' for empty cell
                }
            }
            System.out.println(); // Move to the next row
        }
        System.out.println(); // Empty line between solutions
    }

    // Main method to take input and initiate solving
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the board size for N-Queens problem:");
        int N = scanner.nextInt(); // Board size for N-Queens problem
        csp c = new csp(N);
        c.solve();
        scanner.close();
    }
}