import java.util.*;

public class HillClimbingNQueen {
    private final int N;  // Size of the board (number of queens)
    private final Random random;  // Random number generator

    public HillClimbingNQueen(int N) {
        this.N = N;
        this.random = new Random();  // Initialize the random number generator
    }
//The solve method implements the hill climbing algorithm to solve the N-Queens problem.
//It starts with a random board configuration and iteratively improves it by moving queens to reduce conflicts.
//In each iteration, it evaluates all neighboring boards, selects the one with the lowest heuristic value, and moves to that board.
//The process continues until either a solution with no conflicts is found or the algorithm reaches a local optimum.
//The final board configuration and the number of conflicts are printed when the algorithm terminates.
    public void solve() {
        int[] board = generateRandomBoard();  // Generate a random initial board
        int heuristic = heuristic(board);  // Calculate the heuristic value of the initial board

        while (true) {
            List<int[]> neighbors = getNeighbors(board);  // Get all neighboring boards
            int[] nextBoard = null;
            int nextHeuristic = Integer.MAX_VALUE;

            // Iterate through all neighbors to find the one with the lowest heuristic value
            for (int[] neighbor : neighbors) {
                int currentHeuristic = heuristic(neighbor);
                if (currentHeuristic < nextHeuristic) {
                    nextHeuristic = currentHeuristic;
                    nextBoard = neighbor;
                }
            }

            // If no neighbor has a lower heuristic value, or the heuristic value is 0 (solved), exit the loop
            if (nextHeuristic >= heuristic) {
                printBoard(board);  // Print the final board configuration
                System.out.println("Conflicts: " + heuristic);  // Print the number of conflicts (heuristic value)
                break;
            }

            board = nextBoard;  // Move to the next best neighbor
            heuristic = nextHeuristic;  // Update the current heuristic value
        }
    }

    private int[] generateRandomBoard() {
        int[] board = new int[N];
        for (int i = 0; i < N; i++) {
            board[i] = random.nextInt(N);  // Randomly place queens on the board
        }
        return board;
    }
//The getNeighbors method generates all possible neighboring board configurations by moving one queen in each column to a different row.
//It creates a copy of the current board, modifies it by moving a queen, and adds the modified board to a list of neighbors.
    private List<int[]> getNeighbors(int[] board) {
        List<int[]> neighbors = new ArrayList<>();
        for (int col = 0; col < N; col++) {
            for (int row = 0; row < N; row++) {
                if (board[col] != row) {
                    int[] neighbor = board.clone();  // Create a copy of the current board
                    neighbor[col] = row;  // Move the queen in the current column to the new row
                    neighbors.add(neighbor);  // Add the new board configuration to the list of neighbors
                }
            }
        }
        return neighbors;  // Return the list of neighboring boards
    }

    private int heuristic(int[] board) {
        int conflicts = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                // Check for conflicts between queens (same row, same diagonal)
                if (board[i] == board[j] || Math.abs(board[i] - board[j]) == Math.abs(i - j)) {
                    conflicts++;
                }
            }
        }
        return conflicts;  // Return the number of conflicts (heuristic value)
    }

    public void printBoard(int[] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[j] == i) {
                    System.out.print("Q ");  // Print 'Q' for queens
                } else {
                    System.out.print("* ");  // Print '*' for empty cells
                }
            }
            System.out.println();  // Move to the next row
        }
        System.out.println();  // Add a blank line after printing the board
    }

    public static void main(String[] args) {
        int N = 8;  // Define the size of the board (number of queens)
        HillClimbingNQueen solver = new HillClimbingNQueen(N);  // Create a solver object
        solver.solve();  // Solve the N-Queens problem using hill climbing
    }
}
