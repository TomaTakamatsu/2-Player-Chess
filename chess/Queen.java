package chess;

public class Queen extends Piece {
    public Queen(boolean player, int rank, int file) {
        super(player, rank, file);
    }

    @Override
    public boolean makeMove(int targetRank, int targetFile, Board board) {
        // ensure valid Queen movement (straight or diagonal)
        if (!(targetRank == rank || targetFile == file || 
              Math.abs(targetRank - rank) == Math.abs(targetFile - file))) {
            return false;
        }
    
        // check if path is blocked
        int rankStep = Integer.compare(targetRank, rank);
        int fileStep = Integer.compare(targetFile, file);
        
        int currentRank = rank + rankStep;
        int currentFile = file + fileStep;
    
        while (currentRank != targetRank || currentFile != targetFile) {
            if (board.board[currentRank][currentFile] != null) {
                return false;
            }
            currentRank += rankStep;
            currentFile += fileStep;
        }
    
        // check if the destination has a piece
        Piece targetPiece = board.board[targetRank][targetFile];
        if (targetPiece != null) {
            // if the piece is same color, can't move
            if (targetPiece.player == this.player) {
                return false;
            }
            // if other color, capture opponent's piece (remove it)
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
        return (player ? "w" : "b") + "Q";
    }
}