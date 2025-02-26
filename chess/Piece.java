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
        

        return true; // Move is valid
    }
}
