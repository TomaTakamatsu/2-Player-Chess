package chess;

public class Rook extends Piece{
    public Rook(boolean player, int rank, int file){
        super(player, rank, file);
    }

    public boolean makeMove(int targetRank, int targetFile, Board board){
        return false;
    }

    public String toString(){
        if (player) return "wR ";
        else return "bR ";
    }
}
