import java.util.Scanner;

public class TicTacToe {
    static char[] board = {'1', '2', '3', '4', '5', '6', '7', '8', '9'}; // Board representation
    static char ai = 'O';  // AI's symbol
    static char human = 'X';  // Human's symbol

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Scanner for human input
        int turn = 0;  // 0 means AI's turn, 1 means Human's turn

        while (true) {
            displayBoard();  // Display the current board

            // Check if there's a winner or a draw
            if (checkWinner() != 'Y') {
                if (checkWinner() == 'X') {
                    System.out.println("--------------------YOU WON------------------");
                } else if (checkWinner() == 'O') {
                    System.out.println("-------------------AI WON------------------");
                } else if (checkWinner() == 'D') {
                    System.out.println("-------------------MATCH  DRAW---------------------");
                }
                break;  // Exit the loop if game is over
            } else {
                if (turn == 0) {
                    aiMove();  // AI makes its move
                    turn = 1;  // Change turn to human
                } else {
                    humanMove(scanner);  // Human makes their move
                    turn = 0;  // Change turn to AI
                }
            }
        }
    }

    // Method to display the board
    public static void displayBoard() {
        System.out.println("\n---------------------TIC-TAC-TOE---------------------------");
        System.out.println("\n---------------------AI VS YOU--------------------------\n");

        System.out.println("     |     |     ");
        System.out.println("  " + board[0] + "  |  " + board[1] + "  |  " + board[2] + " ");
        System.out.println("||_");

        System.out.println("     |     |     ");
        System.out.println("  " + board[3] + "  |  " + board[4] + "  |  " + board[5] + " ");
        System.out.println("||_");

        System.out.println("     |     |     ");
        System.out.println("  " + board[6] + "  |  " + board[7] + "  |  " + board[8] + " ");
        System.out.println("     |     |     \n");
    }

    // Method to check the winner
    public static char checkWinner() {
        // Check rows for a win
        for (int i = 0; i < 9; i += 3) {
            if (board[i] == board[i + 1] && board[i + 1] == board[i + 2]) {
                return board[i];  // Return the winner
            }
        }

        // Check columns for a win
        for (int i = 0; i < 3; i++) {
            if (board[i] == board[i + 3] && board[i + 3] == board[i + 6]) {
                return board[i];  // Return the winner
            }
        }

        // Check diagonals for a win
        if (board[0] == board[4] && board[4] == board[8]) {
            return board[0];  // Return the winner
        }
        if (board[2] == board[4] && board[4] == board[6]) {
            return board[2];  // Return the winner
        }

        // Check for a draw
        int draw = 1;
        for (int i = 0; i < 9; i++) {
            if (board[i] != 'X' && board[i] != 'O') {
                draw = 0;  // If there's an empty spot, it's not a draw yet
                break;
            }
        }
        if (draw == 1) {
            return 'D';  // Return 'D' for a draw
        }

        return 'Y';  // Return 'Y' to indicate the game should continue
    }

    // Minimax algorithm to calculate the best move
    public static int minimax(char player) {
        char result = checkWinner();  // Check current game state

        // Base cases
        if (result == ai) {
            return 1;  // AI wins
        } else if (result == human) {
            return -1;  // Human wins
        } else if (result == 'D') {
            return 0;  // Draw
        }

        int bestScore;
        if (player == ai) {
            bestScore = Integer.MIN_VALUE;
            // Try all possible moves
            for (int i = 0; i < 9; i++) {
                if (board[i] != 'X' && board[i] != 'O') {
                    char ch = board[i];
                    board[i] = ai;
                    int score = minimax(human);  // Recur with human's turn
                    board[i] = ch;
                    bestScore = Math.max(score, bestScore);  // Maximize the score for AI
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            // Try all possible moves
            for (int i = 0; i < 9; i++) {
                if (board[i] != 'X' && board[i] != 'O') {
                    char ch = board[i];
                    board[i] = human;
                    int score = minimax(ai);  // Recur with AI's turn
                    board[i] = ch;
                    bestScore = Math.min(score, bestScore);  // Minimize the score for human
                }
            }
        }

        return bestScore;  // Return the best score
    }

    // Method for AI to make a move
    public static void aiMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        // Evaluate all possible moves
        for (int i = 0; i < 9; i++) {
            if (board[i] != 'X' && board[i] != 'O') {
                char ch = board[i];
                board[i] = ai;
                int score = minimax(human);  // Get the score for this move
                board[i] = ch;
                if (score > bestScore) {
                    bestScore = score;  // Update best score
                    bestMove = i;  // Update best move
                }
            }
        }

        board[bestMove] = ai;  // Make the best move
    }

    // Method for human to make a move
    public static void humanMove(Scanner scanner) {
        int move;
        do {
            System.out.print("Enter your move (1-9): ");
            move = scanner.nextInt();  // Read human's move
            move--;  // Adjust for 0-based index

            // Validate the move
            if (move < 0 || move >= 9 || board[move] == 'X' || board[move] == 'O') {
                System.out.println("Enter a valid number.");
            }
        } while (move < 0 || move >= 9 || board[move] == 'X' || board[move] == 'O');

        board[move] = human;  // Make the human's move
    }
}