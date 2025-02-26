package chess;

public class King extends Piece {
    public King(boolean player, int rank, int file) {
        super(player, rank, file);
    }

    @Override
    public boolean isValidMove(int targetRank, int targetFile) {
        // King moves one step in any direction
        return Math.abs(targetRank - rank) <= 1 && Math.abs(targetFile - file) <= 1;
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
        return (player ? "w" : "b") + "K";
    }
}
