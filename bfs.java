//bfs
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;

public class bfs {

    private final int N;
    private int solutionCount;

    public bfs(int N) {
        this.N = N;
        this.solutionCount = 0;
    }

    public void solve() {
        int[] board = new int[N]; // Array to represent the board, each index represents a column and its value represents the row where the queen is placed
        Arrays.fill(board, -1); // Initialize the board with -1, indicating no queen is placed initially
        Queue<int[]> queue = new ArrayDeque<>(); // Queue to store partial solutions

        queue.offer(board); // Start with an empty board

        while (!queue.isEmpty()) {
            int[] currentBoard = queue.poll(); // Get the next partial solution from the queue
            int col = findNextEmptyCol(currentBoard); // Find the next empty column on the board

            if (col == N) {
                // All queens are placed successfully
                printSolution(currentBoard); // Print the solution
                solutionCount++; // Increment the solution count
            } else {
                for (int row = 0; row < N; row++) {
                    if (isSafe(currentBoard, row, col)) { // Check if placing a queen at this row and column is safe
                        int[] newBoard = currentBoard.clone(); // Create a copy of the current board
                        newBoard[col] = row; // Place the queen at this row and column
                        queue.offer(newBoard); // Add the new board configuration to the queue
                    }
                }
            }
        }

        if (solutionCount == 0) {
            System.out.println("No solutions exist for N = " + N);
        }
    }

    private int findNextEmptyCol(int[] board) {
        for (int col = 0; col < N; col++) {
            if (board[col] == -1) {
                return col; // Return the index of the next empty column
            }
        }
        return N; // If no empty column found, return N
    }

    private boolean isSafe(int[] board, int row, int col) {
        for (int prevCol = 0; prevCol < col; prevCol++) {
            int prevRow = board[prevCol];
            // Check if queen can attack in the same row or diagonals
            if (prevRow == row || Math.abs(prevRow - row) == Math.abs(prevCol - col)) {
                return false; // If another queen can attack the current position, return false
            }
        }
        return true; // Otherwise, it's safe to place the queen
    }

    private void printSolution(int[] board) {
        System.out.println("Solution " + (solutionCount + 1) + ":");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[j] == i) {
                    System.out.print("Q "); // Print 'Q' if a queen is present in this cell
                } else {
                    System.out.print("* "); // Print '*' if the cell is empty
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the board size for N-Queens problem:");
        int N = scanner.nextInt(); // Get the board size from the user
        scanner.close();

        bfs c = new bfs(N);
        c.solve();
    }
}