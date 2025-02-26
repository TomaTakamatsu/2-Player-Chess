package chess;

public abstract class Piece {
    boolean player; // true = white, false = black
    int rank;
    int file; // 0 = a, 7 = h

    public Piece(boolean player, int rank, int file){
        this.player = player;
        this.rank = rank;
        this.file = file;
    }

    public abstract boolean makeMove(int targetRank, int targetFile, Board board);
    public abstract String toString();
    public void updatePosition(int rank, int file){
        this.rank = rank;
        this.file = file;
    }

    public boolean validMove(int targetRank, int targetFile, Board board){
        if (targetRank == rank && targetFile == file) return false; // Checking if move changes nothing
        if (targetRank > 7 || targetRank < 0 || targetFile > 7 || targetFile < 0) return false; // Checking if move is within the board

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
