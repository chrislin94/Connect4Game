/**
 * Created by chrislin on 4/16/16.
 */
package ConnectFourGame;

import java.util.Scanner;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Connect4Game {
    private int width;
    private int height;
    private char[][] board;
    private int currentColumn = -1, currentHeight = -1;

    //Creating players with 'x' and 'o' for player 1 and player 2
    private static final char[] players = new char[] {'x', 'o'};

    public Connect4Game(int w, int h) {
        this.width = w;
        this.height = h;
        this.board = new char[height][];
        fillBoard();
    }

    //Fills the entire board with '*' characters
    public void fillBoard(){
        for (int i = 0; i < this.height; i++) {
            this.board[i] = new char[this.width];
            Arrays.fill(this.board[i], '*');
        }
    }

    //Inputs characterInput into location specified by user
    //@params: characterInput - this either 'o' or 'x'
    //@params: systemIn - input number from user
    public void addToBoard(char characterInput, Scanner systemIn) {
        while(true){
            // Prompts user to provide input
            System.out.print("\nPlayer " + characterInput + " please input column number between range 0 to " + (this.width - 1) + ":");

            int columnSize = systemIn.nextInt();
            // Checks for input out of range
            if (!(columnSize < this.width && 0 <= columnSize)) {
                System.out.println("Wrong input: Please provide input between range 0 to " + (this.width - 1));
                continue;
            }
            for (int i = this.height - 1; i >= 0; i--) {
                if (this.board[i][columnSize] == '*') {
                    this.currentHeight = i;
                    this.currentColumn = columnSize;
                    this.board[this.currentHeight][this.currentColumn] = characterInput;
                    return;
                }
            }
            System.out.println("This column is has reached capacity: " + columnSize);
        }
    }

    //Checks to see if move has won
    //@return: boolean
    public boolean isWinningMove() {
        char playersLastInput = this.board[this.currentHeight][this.currentColumn];
        String s = new StringBuilder().append(playersLastInput).append(playersLastInput).append(playersLastInput).append(playersLastInput).toString();
        String compareCon4 = s.toString();
        return isMatch(this.horizontal(), compareCon4) || isMatch(this.vertical(), compareCon4) || isMatch(this.diagonalOne(), compareCon4) || isMatch(this.diagonalTwo(), compareCon4);
    }

    //Checks if match is within line and input line
    //@param: line - line in board
    //@param: inputLine - line generated from user input
    //@return: boolean
    private static boolean isMatch(String line, String inputLine) {
        return line.indexOf(inputLine) >= 0;
    }

    //Grab whole column of where user last input piece
    //@return: string
    private String vertical() {
        StringBuilder sb = new StringBuilder(this.height);
        for (int i = 0; i < this.height; i++) {
            sb.append(this.board[i][this.currentColumn]);
        }
        return sb.toString();
    }

    //Grab whole row of where user last input piece
    //@return: string
    private String horizontal() {
        char[] horizontalPart = this.board[this.currentHeight];
        return new String(horizontalPart);
    }

    //Grabs whole diagonal facing front
    //@return: string
    private String diagonalOne() {
        StringBuilder sb = new StringBuilder(this.height);
        for (int i = 0; i < this.height; i++) {
            int width = this.currentColumn + this.currentHeight - i;
            if (0 <= width && width < this.width) {

                sb.append(this.board[i][width]);
            }
        }
        return sb.toString();
    }

    //Grabs whole diagonal facing back
    //@return string
    private String diagonalTwo() {
        StringBuilder sb = new StringBuilder(this.height);
        for (int i = 0; i < this.height; i++) {
            int width = this.currentColumn - this.currentHeight + i;
            if (0 <= width && width < this.width) {
                sb.append(this.board[i][width]);
            }
        }
        return sb.toString();
    }

    //Board to string format
    //@return: string
    public String toString() {
        return Arrays.stream(this.board).map(String::new).collect(Collectors.joining("\n"));
    }

    public static void main(String[] args) {
        try (Scanner systemIn = new Scanner(System.in)) {
            //Default is set to 6x6 board. Feel free to change dimensions.
            int height = 6;
            int width = 6;
            int moves = height * width;
            Connect4Game board = new Connect4Game(width, height);
            System.out.println(board);
            for (int player = 0; moves > 0; player = 1 - player, moves--) {
                board.addToBoard(players[player], systemIn);
                System.out.println(board);
                if (board.isWinningMove()) {
                    System.out.println("Congratulations!!! player " + players[player] + " wins");
                    return;
                }
            }
            System.out.println("No Winners :(");
        }
    }
}
