package chess;

public class Queen extends Piece {
    public Queen(boolean player, int rank, int file) {
        super(player, rank, file);
    }

    @Override
    public boolean isValidMove(int targetRank, int targetFile) {
        // Queen moves like both a bishop (diagonal) and a rook (straight)
        return targetRank == rank || targetFile == file || 
               Math.abs(targetRank - rank) == Math.abs(targetFile - file);
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
        return (player ? "w" : "b") + "Q";
    }
}