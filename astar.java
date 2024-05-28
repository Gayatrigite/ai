//A*

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Scanner; // Import Scanner class for user input

public class astar {
    private final int N;
    private int sc;

    // Constructor to initialize the size of the board
    public astar(int N) {
        this.N = N;
        this.sc = 0;
    }

    // Method to solve the N-Queens problem
    public void solve() {
        int[] board = new int[N];
        Arrays.fill(board, -1);
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(state -> state.f));
        queue.offer(new State(board, 0, heuristic(board)));

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            int[] cb = currentState.board;
            int col = findNextEmptyCol(cb);

            if (col == N) {
                printSolution(cb);
                sc++;
            } else {
                for (int row = 0; row < N; row++) {
                    if (isSafe(cb, row, col)) {
                        int[] newBoard = cb.clone();
                        newBoard[col] = row;
                        int g = currentState.g + 1;
                        int h = heuristic(newBoard);
                        queue.offer(new State(newBoard, g, g + h));
                    }
                }
            }
        }

        if (sc == 0) {
            System.out.println("No solutions exist for N = " + N);
        }
    }

    // Method to find the next empty column on the board
    private int findNextEmptyCol(int[] board) {
        for (int col = 0; col < N; col++) {
            if (board[col] == -1) {
                return col;
            }
        }
        return N;
    }

    // Method to check if placing a queen at a given row and column is safe
    private boolean isSafe(int[] board, int row, int col) {
        for (int pc = 0; pc < col; pc++) {
            int pr = board[pc];
            if (pr == row || Math.abs(pr - row) == Math.abs(pc - col)) {
                return false;
            }
        }
        return true;
    }

    // Method to calculate the number of conflicts on the board (heuristic function)
    private int heuristic(int[] board) {
        int conflicts = 0;
        for (int i = 0; i < N; i++) {
            if (board[i] == -1) continue;
            for (int j = i + 1; j < N; j++) {
                if (board[j] == -1) continue;
                if (board[i] == board[j] || Math.abs(board[i] - board[j]) == Math.abs(i - j)) {
                    conflicts++;
                }
            }
        }
        return conflicts;
    }

    // Method to print the solution
    private void printSolution(int[] board) {
        System.out.println("Solution " + (sc + 1) + ":");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[j] == i) {
                    System.out.print("Q "); // Q represents a queen
                } else {
                    System.out.print("* "); // * represents an empty cell
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Main method to get user input and solve the N-Queens problem
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the size of the board (N): ");
        int N = scanner.nextInt(); // User inputs the size of the board
        astar aStarNQueens = new astar(N);
        aStarNQueens.solve();
        scanner.close(); // Close the scanner to prevent resource leak
    }

    // Inner class representing a state in the search space
    private static class State {
        int[] board; // Configuration of the board
        int g; // Cost to reach the current state
        int f; // Estimated cost to reach the goal (g + h)

        // Constructor to initialize the state
        State(int[] board, int g, int f) {
            this.board = board;
            this.g = g;
            this.f = f;
        }
    }
}