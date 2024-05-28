import java.util.Scanner;

public class NonAiTicTacToe {

    // The board is represented by an array, where 2 represents empty, 3 represents X (player), and 5 represents O (computer).
    static int[] board = {0, 2, 2, 2, 2, 2, 2, 2, 2, 2};

    // This function checks if the specified player has won the game.
    static boolean checkWin(int player) {
        // Check for win in rows
        for (int i = 1; i <= 9; i += 3) {
            if (board[i] == player && board[i + 1] == player && board[i + 2] == player) {
                return true;
            }
        }

        // Check for win in columns
        for (int i = 1; i <= 3; i++) {
            if (board[i] == player && board[i + 3] == player && board[i + 6] == player) {
                return true;
            }
        }

        // Check for win in diagonals
        if (board[1] == player && board[5] == player && board[9] == player) {
            return true;
        }

        if (board[3] == player && board[5] == player && board[7] == player) {
            return true;
        }

        // No win detected
        return false;
    }

    // This function checks if the player or computer can win in the next move.
    static int posswin(int p) {
        int pos;

        // Check rows for possible win
        for (int i = 1; i <= 9; i += 3) {
            pos = i;
            if (board[pos] * board[pos + 1] * board[pos + 2] == p * p * 2) {
                if (board[pos] == 2) return pos;
                if (board[pos + 1] == 2) return pos + 1;
                if (board[pos + 2] == 2) return pos + 2;
            }
        }

        // Check columns for possible win
        for (int i = 1; i <= 3; i++) {
            pos = i;
            if (board[pos] * board[pos + 3] * board[pos + 6] == p * p * 2) {
                if (board[pos] == 2) return pos;
                if (board[pos + 3] == 2) return pos + 3;
                if (board[pos + 6] == 2) return pos + 6;
            }
        }

        // Check main diagonal for possible win
        pos = 1;
        if (board[pos] * board[pos + 4] * board[pos + 8] == p * p * 2) {
            if (board[pos] == 2) return pos;
            if (board[pos + 4] == 2) return pos + 4;
            if (board[pos + 8] == 2) return pos + 8;
        }

        // Check other diagonal for possible win
        pos = 3;
        if (board[pos] * board[pos + 2] * board[pos + 4] == p * p * 2) {
            if (board[pos] == 2) return pos;
            if (board[pos + 2] == 2) return pos + 2;
            if (board[pos + 4] == 2) return pos + 4;
        }

        // No possible win detected
        return 0;
    }

    // This function makes the move for the player (X or O)
    static void makemove(int player) {
        // Prefer the center if it is available
        if (board[5] == 2) {
            board[5] = player;
            return;
        }

        // Make a winning move if possible
        int winningMove = posswin(player);
        if (winningMove != 0 && board[winningMove] == 2) {
            board[winningMove] = player;
            return;
        }

        // Block the opponent's winning move if possible
        int blockingMove = posswin(player == 3 ? 5 : 3);
        if (blockingMove != 0 && board[blockingMove] == 2) {
            board[blockingMove] = player;
            return;
        }

        // Take one of the corners if available
        int[] corners = {1, 3, 7, 9};
        for (int i = 0; i < 4; ++i) {
            if (board[corners[i]] == 2) {
                board[corners[i]] = player;
                return;
            }
        }

        // Take any remaining empty spot
        for (int i = 1; i <= 9; ++i) {
            if (board[i] == 2) {
                board[i] = player;
                return;
            }
        }
    }

    // This function displays the current state of the board.
    static void displayBoard() {
        System.out.println();
        for (int i = 1; i <= 9; i++) {
            if (board[i] == 2) {
                System.out.print("_");
            } else if (board[i] == 3) {
                System.out.print("X");
            } else {
                System.out.print("O");
            }

            if (i % 3 == 0) {
                System.out.println();
            } else {
                System.out.print(" | ");
            }
        }
        System.out.println();
    }

    // This function checks if the game is over and announces the result.
    static int gameover() {
        if (checkWin(3)) {  // Check if player (X) has won
            displayBoard();
            System.out.println("Game Over. You win!");
            return 1;
        }

        if (checkWin(5)) {  // Check if computer (O) has won
            displayBoard();
            System.out.println("Game Over. You lose!");
            return 1;
        }

        // Check for draw
        for (int i = 1; i <= 9; i++) {
            if (board[i] == 2) {
                return 0;  // Game is not over yet
            }
        }

        displayBoard();
        System.out.println("Game Over. It's a draw!");
        return 1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Tic Tac Toe!");

        while (true) {
            displayBoard();

            int playerMove;
            System.out.print("Enter your move (1-9): ");
            playerMove = scanner.nextInt();
            if (playerMove < 1 || playerMove > 9 || board[playerMove] != 2) {
                System.out.println("Invalid move. Please choose a valid empty position.");
                continue;
            }
            board[playerMove] = 3;

            if (gameover() == 1) {
                break;
            }

            makemove(5);

            if (gameover() == 1) {
                break;
            }
        }

        scanner.close();
    }
}