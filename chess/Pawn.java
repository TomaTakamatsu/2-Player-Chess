package chess;

public class Pawn extends Piece{
    public Pawn(boolean player, int rank, int file){
        super(player, rank, file);
    }

    public boolean makeMove(int targetRank, int targetFile, Board board){
        return false;
    }

    public String toString(){
        if (player) return "wp ";
        else return "bp ";
    }
}
