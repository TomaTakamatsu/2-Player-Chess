package chess;

import java.util.HashMap;

public abstract class Piece extends ReturnPiece{
    boolean player; // true = white, false = black
    int rank;
    int file; // 0 = a, 7 = h

    public static HashMap<Integer, PieceFile> intToFile = new HashMap<>();
    static{
        intToFile.put(0, PieceFile.a);
        intToFile.put(1, PieceFile.b);
        intToFile.put(2, PieceFile.c);
        intToFile.put(3, PieceFile.d);
        intToFile.put(4, PieceFile.e);
        intToFile.put(5, PieceFile.f);
        intToFile.put(6, PieceFile.g);
        intToFile.put(7, PieceFile.h);
    }

    public Piece(boolean player, int rank, int file){
        this.player = player;
        this.rank = rank;
        this.file = file;
        this.pieceFile = intToFile.get(file);
        this.pieceRank = rank + 1;
    }

    public abstract boolean makeMove(int targetRank, int targetFile, Board board);
    public void updatePosition(int rank, int file){
        this.rank = rank;
        this.file = file;
        this.pieceFile = intToFile.get(file);
        this.pieceRank = rank + 1;
    }

    public boolean validMove(int targetRank, int targetFile, Board board){
        if (targetRank == rank && targetFile == file) return false; // Checking if move changes nothing
        if (targetRank > 7 || targetRank < 0 || targetFile > 7 || targetFile < 0) return false; // Checking if move is within the board
        if (board.board[targetRank][targetFile] instanceof King) return false; // If target is a king, return false
        System.out.println("saw if it was king");

        // Checking if move will cause a check on their own king

        // Making a copy of the piece
        Piece copyPiece = null;
        if (this instanceof Pawn) copyPiece = new Pawn(player, targetRank, targetFile);
        else if (this instanceof Rook) copyPiece = new Rook(player, targetRank, targetFile);
        else if (this instanceof Bishop) copyPiece = new Bishop(player, targetRank, targetFile);
        else if (this instanceof Knight) copyPiece = new Knight(player, targetRank, targetFile);
        else if (this instanceof King) copyPiece = new King(player, targetRank, targetFile);
        else if (this instanceof Queen) copyPiece = new Queen(player, targetRank, targetFile);

        // Making a copy of the board
        Piece[][] copiedBoard = board.copyBoard(board.board);
        copiedBoard[rank][file] = null;
        copiedBoard[targetRank][targetFile] = copyPiece;
        Board copiedBoardObj = new Board(copiedBoard);

        // Checking if king is in check
        int check = copiedBoardObj.isKingInCheck();
        if (check == 3) return false;
        else if (check == 2 && !player) return false;
        else if (check == 1 && player) return false;

        return true; // Move is valid
    }
}
