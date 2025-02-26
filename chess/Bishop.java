package chess;

public class Bishop extends Piece {
    public Bishop(boolean player, int rank, int file) {
        super(player, rank, file);
    }

    @Override
    public boolean makeMove(int targetRank, int targetFile, Board board) {
        // ensure move is diagonal
        if (Math.abs(targetRank - rank) != Math.abs(targetFile - file)) {
            return false;
        }

        // determine direction of movement
        int rankStep = (targetRank > rank) ? 1 : -1; // up or down
        int fileStep = (targetFile > file) ? 1 : -1; // right or left

        int currentRank = rank + rankStep;
        int currentFile = file + fileStep;

        // check if path is blocked
        while (currentRank != targetRank && currentFile != targetFile) {
            if (board.board[currentRank][currentFile] != null) {
                return false;
            }
            currentRank += rankStep;
            currentFile += fileStep;
        }

        // check if the destination has a piece
        Piece targetPiece = board.board[targetRank][targetFile];
        if (targetPiece != null) {
            // if it's the same color, move not valid
            if (targetPiece.player == this.player) {
                return false;
            }
            // if other color, remove piece
            board.board[targetRank][targetFile] = null;
        }

        // update position
        board.board[rank][file] = null;
        this.rank = targetRank;
        this.file = targetFile;
        board.board[targetRank][targetFile] = this;
        
        return true;
    }

    @Override
    public String toString() {
        return (player ? "w" : "b") + "B";
    }
}