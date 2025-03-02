package chess;

public class Knight extends Piece{
    public Knight(boolean player, int rank, int file){
        super(player, rank, file);
        if (player) pieceType = PieceType.WN;
        else pieceType = PieceType.BN;
    }

    public boolean makeMove(int targetRank, int targetFile, Board board){
        if (targetRank == rank || targetFile == file || Math.abs(targetRank - rank) + Math.abs(targetFile - file) != 3) return false; // Checking if the movement is L shape
        
        // Checking if the piece is able to move on to the target
        Piece target = board.board[targetRank][targetFile];
        if ((target != null && target.player != player)||(target==null)) return true; // Target is empty or an enemy piece

        return false;
    }
}
