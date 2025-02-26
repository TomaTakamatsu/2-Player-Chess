package chess;

public class Board {
    public Piece[][] board; // Chess board, board[rank][file]
    public Board(){
        board = createNewBoard();
    }

    public Piece[][] createNewBoard(){
        Piece[][] newBoard = new Piece[8][8];
        
        // Placing white pieces
        newBoard[0][0] = new Rook(true, 0, 0);
        newBoard[0][1] = new Knight(true, 0, 1);
        newBoard[0][2] = new Bishop(true, 0, 2);
        newBoard[0][3] = new Queen(true, 0, 3);
        newBoard[0][4] = new King(true, 0, 4);
        newBoard[0][5] = new Bishop(true, 0, 5);
        newBoard[0][6] = new Knight(true, 0, 6);
        newBoard[0][7] = new Rook(true, 0, 7);
        for (int i = 0; i < 8; i++){
            newBoard[1][i] = new Pawn(true, 1, i);
        }

        // Placing black pieces
        newBoard[7][0] = new Rook(false, 7, 0);
        newBoard[7][1] = new Knight(false, 7, 1);
        newBoard[7][2] = new Bishop(false, 7, 2);
        newBoard[7][3] = new Queen(false, 7, 3);
        newBoard[7][4] = new King(false, 7, 4);
        newBoard[7][5] = new Bishop(false, 7, 5);
        newBoard[7][6] = new Knight(false, 7, 6);
        newBoard[7][7] = new Rook(false, 7, 7);
        for (int i = 0; i < 8; i++){
            newBoard[6][i] = new Pawn(false, 6, i);
        }

        return newBoard;
    }

    public void printBoard(){
        for (int rank = 0; rank < 8; rank++){
            for (int file = 0; file < 8; file++){
                if (board[rank][file] == null){
                    if ((rank + file) % 2 == 0) System.out.println("    "); // Printing light square
                    else System.out.println("## "); // Printing dark square
                }
                else System.out.println(board[rank][file] + " "); // Printing pieces
            }
            System.out.println((8 - rank) + ""); // Printing rank on right side
        }
        System.out.println("a  b  c  d  e  f  g  h"); // Printing file on the bottom
    }
}
