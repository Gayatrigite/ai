import java.util.*;

public class AOStarNQueen {
    private final int N;
    private int solcnt; // Counter to keep track of the number of solutions found

    public AOStarNQueen(int N) {
        this.N = N; // Initialize the board size
        this.solcnt = 0; // Initialize the solution count
    }

    public void solve() {
        // Create the root node with an empty board, g = 0 (no queens placed), and heuristic value for the empty board
        Node root = new Node(new int[N], 0, heuristic(new int[N]));
        Arrays.fill(root.board, -1); // Fill the board with -1 (indicating no queen placed in that column)

        // Create a priority queue to store nodes based on their f value (f = g + h)
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
        open.add(root); // Add the root node to the priority queue

        while (!open.isEmpty()) { // Continue until the priority queue is empty
            Node currentNode = open.poll(); // Get the node with the lowest f value from the priority queue
            int[] currentBoard = currentNode.board; // Get the board configuration from the current node

            if (currentNode.g == N) { // If g equals N (all queens placed), print the solution
                printsol(currentBoard);
                solcnt++;
                continue; // Continue to find other solutions
            }

            int col = findNextEmptyCol(currentBoard); // Find the next empty column to place a queen

            for (int row = 0; row < N; row++) {
                if (isSafe(currentBoard, row, col)) { // Check if placing a queen at (row, col) is safe
                    int[] newBoard = currentBoard.clone(); // Create a copy of the current board
                    newBoard[col] = row; // Place a queen at (row, col) in the new board
                    int g = currentNode.g + 1; // Increment g (number of queens placed)
                    int h = heuristic(newBoard); // Calculate the heuristic value for the new board
                    Node childNode = new Node(newBoard, g, g + h); // Create a child node with the new board
                    open.add(childNode); // Add the child node to the priority queue
                    currentNode.children.add(childNode); // Add the child node to the current node's children
                }
            }
        }

        if (solcnt == 0) { // If no solutions were found, print a message
            System.out.println("No solution exists");
        }
    }

    public void printsol(int[] board) {
        // Print the board configuration with queens represented as 'Q' and empty squares as '*'
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[j] == i) {
                    System.out.print("Q ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isSafe(int[] board, int row, int col) {
        // Check if placing a queen at (row, col) is safe in the current board configuration
        for (int prevCol = 0; prevCol < col; prevCol++) {
            int prevRow = board[prevCol];
            if (prevRow == row || Math.abs(prevRow - row) == Math.abs(prevCol - col)) {
                return false;
            }
        }
        return true;
    }

    public int findNextEmptyCol(int[] board) {
        // Find the next empty column in the current board configuration
        for (int col = 0; col < N; col++) {
            if (board[col] == -1) {
                return col;
            }
        }
        return N; // Return N if no empty column is found (all columns filled)
    }

    public int heuristic(int[] board) {
        // Calculate the heuristic value (number of conflicts) for the given board configuration
        int conflicts = 0;
        for (int i = 0; i < N; i++) {
            if (board[i] == -1) continue; // Skip if no queen in this column
            for (int j = i + 1; j < N; j++) {
                if (board[j] == -1) continue; // Skip if no queen in this column
                if (board[i] == board[j] || Math.abs(board[i] - board[j]) == Math.abs(i - j)) {
                    conflicts++; // Increment conflicts if queens threaten each other
                }
            }
        }
        return conflicts / 2; // Divide by 2 to avoid double counting conflicts
    }

    public static void main(String[] args) {
        int N = 4; // Change N to the desired size of the board
        AOStarNQueen csp = new AOStarNQueen(N);
        csp.solve(); // Solve the N-Queens problem
    }

    static class Node {
        int[] board; // Board configuration
        int g; // Number of queens placed (depth)
        int f; // Evaluation function f = g + h
        List<Node> children; // Child nodes

        Node(int[] board, int g, int f) {
            this.board = board.clone();
            this.g = g;
            this.f = f;
            this.children = new ArrayList<>();
        }
    }
}
