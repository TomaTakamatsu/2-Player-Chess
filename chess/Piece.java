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

    public abstract boolean isValidMove(int targetRank, int targetFile);
    public abstract boolean makeMove(int targetRank, int targetFile);
    public abstract String toString();
}
