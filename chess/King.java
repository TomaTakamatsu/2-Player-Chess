package chess;

public class King extends Piece {
    public boolean hasMoved = false;

    public King(boolean player, int rank, int file) {
        super(player, rank, file);
    }

    @Override
    public boolean makeMove(int targetRank, int targetFile, Board board) {
        // ensure the move is one square in any direction
        if (Math.abs(targetRank - rank) > 1 || Math.abs(targetFile - file) > 1) {
            return false;
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

        hasMoved = true;
        
        return true;
    }
    
    @Override
    public String toString() {
        return (player ? "w" : "b") + "K";
    }
}
