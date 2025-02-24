package chess;

abstract class Piece {
    char player;
    int rank;
    char file;

    Piece(char player, int rank, char file){
        this.player = player;
        this.rank = rank;
        this.file = file;
    }

    abstract boolean isValidMove(int targetRank, char targetFile);
    abstract boolean makeMove(int targetRank, char targetFile);
}
