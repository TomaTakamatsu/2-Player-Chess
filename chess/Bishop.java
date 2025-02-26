package chess;

public class Bishop extends Piece {
    public Bishop(boolean player, int rank, int file) {
        super(player, rank, file);
    }

    @Override
    public boolean isValidMove(int targetRank, int targetFile) {
        // Bishop moves diagonally
        return Math.abs(targetRank - rank) == Math.abs(targetFile - file);
    }

    @Override
    public boolean makeMove(int targetRank, int targetFile) {
        if (isValidMove(targetRank, targetFile)) {
            this.rank = targetRank;
            this.file = targetFile;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return (player ? "w" : "b") + "B";
    }
}