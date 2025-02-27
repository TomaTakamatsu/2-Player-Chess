package chess;

import java.util.ArrayList;

public class Board {
    public Piece[][] board; // Chess board, board[rank][file]
    public static boolean whiteTurn = true;
    public static Board boardInstance;

    public Board(){
        board = createNewBoard();
    }

    public Board(Piece[][] board){
        this.board = board;
    }

    public Piece[][] copyBoard(Piece[][] boardToCopy){
        Piece[][] copiedBoard = new Piece[8][8];
        for (int rank = 0; rank < 8; rank++){
            for (int file = 0; file < 8; file++){
                if (board[rank][file] != null){
                    copiedBoard[rank][file] = board[rank][file];
                }
            }
        }

        return copiedBoard;
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

    // Returns 0 if neither king is in check, returns 1 if white is in check, returns 2 if black is in check, returns 3 if both are in check
    public int isKingInCheck(){
        int check = 0;
        boolean blackChecked = false;
        boolean whiteChecked = false;

        // Finding the two kings
        Piece whiteKing = null;
        Piece blackKing = null;
        for (int rank = 0; rank < 8; rank++){
            for (int file = 0; file < 8; file++){
                Piece piece = board[rank][file];
                if (piece instanceof King){
                    if (piece.player) whiteKing = piece;
                    else blackKing = piece;
                }
            }
        }

        // Checking if any of the pieces can capture the king
        for (int rank = 0; rank < 8; rank++){
            for (int file = 0; file < 8; file++){
                Piece piece = board[rank][file];
                if (piece == null || piece instanceof King) continue;
                else if (piece.player && !blackChecked){
                    blackChecked = piece.makeMove(blackKing.rank, blackKing.file, this);
                }
                else if (!piece.player && !whiteChecked){
                    whiteChecked = piece.makeMove(whiteKing.rank, whiteKing.file, this);
                }
            }
        }

        // Updating if there was a check or not
        if (whiteChecked) check += 1;
        if (blackChecked) check += 2;
        return check;
    }

    public static ArrayList<ReturnPiece> getPiecesOnBoard() {
        ArrayList<ReturnPiece> pieces = new ArrayList<>();
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece piece = boardInstance.board[rank][file];
                if (piece != null) {
                    pieces.add(convertToReturnPiece(piece, rank, file));
                }
            }
        }
        return pieces;
    }

    private static ReturnPiece convertToReturnPiece(Piece piece, int rank, int file) {
        ReturnPiece rp = new ReturnPiece();
        rp.pieceRank = 8 - rank; // Convert from array index to chess notation
        rp.pieceFile = ReturnPiece.PieceFile.values()[file];

        if (piece instanceof Pawn) rp.pieceType = piece.player ? ReturnPiece.PieceType.WP : ReturnPiece.PieceType.BP;
        else if (piece instanceof Rook) rp.pieceType = piece.player ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR;
        else if (piece instanceof Knight) rp.pieceType = piece.player ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN;
        else if (piece instanceof Bishop) rp.pieceType = piece.player ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB;
        else if (piece instanceof Queen) rp.pieceType = piece.player ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
        else if (piece instanceof King) rp.pieceType = piece.player ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK;

        return rp;
    }
}
