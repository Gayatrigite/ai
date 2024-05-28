//best
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Scanner; // Importing Scanner for user input

public class bestfs {
    private final int N;
    private int sc;

    public bestfs(int N) {
        this.N = N;
        this.sc = 0;
    }

    public void solve() {
        int[] board = new int[N];
        Arrays.fill(board, -1); // Fill the board array with -1 (indicating no queen is placed initially)
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(this::heuristic)); // Priority queue to store boards based on heuristic value
        queue.offer(board); // Add initial empty board to the queue

        while (!queue.isEmpty()) { // Continue until queue is empty
            int[] cb = queue.poll(); // Get the board with the lowest heuristic value from the queue
            int col = findNextEmptyCol(cb); // Find the next empty column in the current board

            if (col == N) { // If all columns are filled, a solution is found
                printSolution(cb); // Print the solution
                sc++; // Increment solution count
            } else {
                for (int row = 0; row < N; row++) { // Try placing a queen in each row of the current column
                    if (isSafe(cb, row, col)) { // Check if placing a queen at this position is safe
                        int[] newBoard = cb.clone(); // Create a copy of the current board
                        newBoard[col] = row; // Place the queen at the current position
                        queue.offer(newBoard); // Add the new board to the queue
                    }
                }
            }
        }

        if (sc == 0) {
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
        for (int pc = 0; pc < col; pc++) { // Check each previous column
            int pr = board[pc]; // Row of the queen in the previous column
            if (pr == row || Math.abs(pr - row) == Math.abs(pc - col)) {
                return false; // If there is a conflict, return false
            }
        }
        return true; // If no conflict found, return true
    }

    private int heuristic(int[] board) {
        int conflicts = 0;
        for (int i = 0; i < N; i++) {
            if (board[i] == -1) continue; // Skip empty columns
            for (int j = i + 1; j < N; j++) {
                if (board[j] == -1) continue; // Skip empty columns
                if (board[i] == board[j] || Math.abs(board[i] - board[j]) == Math.abs(i - j)) {
                    conflicts++; // Increment conflicts if queens threaten each other
                }
            }
        }
        return conflicts; // Return total conflicts for the board
    }

    private void printSolution(int[] board) {
        System.out.println("Solution " + (sc + 1) + ":");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[j] == i) {
                    System.out.print("Q "); // Print 'Q' if queen is placed in the current position
                } else {
                    System.out.print("* "); // Print '*' if no queen is placed in the current position
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Creating a Scanner object for user input
        System.out.print("Enter the number of queens (N): ");
        int N = scanner.nextInt(); // Getting user input for the number of queens
        bestfs bfs = new bestfs(N);
        bfs.solve();
    }
}