package chess;

public class Knight extends Piece{
    public Knight(boolean player, int rank, int file){
        super(player, rank, file);
    }

    public boolean makeMove(int targetRank, int targetFile, Board board){
        return false;
    }

    public String toString(){
        if (player) return "wN ";
        else return "bN ";
    }
}
