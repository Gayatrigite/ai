//DLS
import java.util.Arrays;
import java.util.Scanner; // Import Scanner class for user input

public class dls {

    private final int N; // Size of the chessboard
    private int solutionCount; // Counter to keep track of the number of solutions found

    public dls(int N) {
        this.N = N;
        this.solutionCount = 0;
    }

    // Method to solve the N-Queens problem with a depth limit
    public void solve(int maxDepth) {
        int[] board = new int[N]; // Array to represent the board, each element represents the queen's position in a column
        Arrays.fill(board, -1); // Initialize the board with -1 (no queen placed yet)
        depthLimitedSearch(board, 0, maxDepth); // Start the depth-limited search from the first column (0)

        if (solutionCount == 0) {
            System.out.println("No solutions for N = " + N);
        }
    }

    // Recursive method to perform depth-limited search
    private boolean depthLimitedSearch(int[] board, int depth, int maxDepth) {
        if (depth == N) { // If all queens are placed (base case)
            printSolution(board); // Print the solution
            solutionCount++; // Increment solution count
            return true;
        }

        if (depth == maxDepth) { // If reached the maximum depth limit
            return false; // Stop searching further from this branch
        }

        boolean foundSolution = false;
        for (int row = 0; row < N; row++) { // Try placing the queen in each row of the current column
            if (isSafe(board, row, depth)) { // If it's safe to place the queen in this row
                board[depth] = row; // Place the queen
                foundSolution = depthLimitedSearch(board, depth + 1, maxDepth); // Recur for the next column
                board[depth] = -1; // Backtrack: Remove the queen from this position
            }
        }

        return foundSolution;
    }

    // Method to check if it's safe to place a queen in a given row and column
    private boolean isSafe(int[] board, int row, int col) {
        for (int i = 0; i < col; i++) { // Check each previous column
            int prevRow = board[i]; // Previous row where a queen is placed
            if (prevRow == row || Math.abs(prevRow - row) == Math.abs(i - col)) {
                // If there is a queen in the same row or same diagonal
                return false; // It's not safe to place the queen here
            }
        }
        return true; // It's safe to place the queen in this row and column
    }

    // Method to print the solution
    private void printSolution(int[] board) {
        System.out.println("Solution " + (solutionCount + 1));
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[j] == i) {
                    System.out.print("Q "); // Print 'Q' if there is a queen in this position
                } else {
                    System.out.print("* "); // Print '*' if there is no queen in this position
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Create a Scanner object for user input
        System.out.println("Enter the size of the chessboard (N): ");
        int N = scanner.nextInt(); // Read the size of the chessboard from the user
        int maxDepth = N; // Set the maximum depth limit to N (default)
        System.out.println("Enter the depth limit: ");
        maxDepth = scanner.nextInt(); // Read the depth limit from the user
        dls dlsNQueens = new dls(N); // Create an instance of DLSNQueens class
        dlsNQueens.solve(maxDepth); // Solve the N-Queens problem with the given depth limit
    }
}