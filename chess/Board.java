package chess;

import java.util.ArrayList;

public class Board extends ReturnPlay{
    public Piece[][] board; // Chess board, board[rank][file]
    public boolean whiteTurn = true;
    public static Board boardInstance;

    public Board(){
        board = createNewBoard();
    }

    public Board(Piece[][] board){
        this.board = board;
    }

    public Piece[][] copyBoard(Piece[][] boardToCopy){
        Piece[][] copiedBoard = new Piece[8][8];
        for (int rank = 0; rank < 8; rank++){
            for (int file = 0; file < 8; file++){
                if (board[rank][file] != null){
                    copiedBoard[rank][file] = board[rank][file];
                }
            }
        }

        return copiedBoard;
    }

    public Piece[][] createNewBoard(){
        Piece[][] newBoard = new Piece[8][8];
        
        // Placing white pieces
        newBoard[0][0] = new Rook(true, 0, 0);
        newBoard[0][1] = new Knight(true, 0, 1);
        newBoard[0][2] = new Bishop(true, 0, 2);
        newBoard[0][3] = new Queen(true, 0, 3);
        newBoard[0][4] = new King(true, 0, 4);
        newBoard[0][5] = new Bishop(true, 0, 5);
        newBoard[0][6] = new Knight(true, 0, 6);
        newBoard[0][7] = new Rook(true, 0, 7);
        for (int i = 0; i < 8; i++){
            newBoard[1][i] = new Pawn(true, 1, i);
        }

        // Placing black pieces
        newBoard[7][0] = new Rook(false, 7, 0);
        newBoard[7][1] = new Knight(false, 7, 1);
        newBoard[7][2] = new Bishop(false, 7, 2);
        newBoard[7][3] = new Queen(false, 7, 3);
        newBoard[7][4] = new King(false, 7, 4);
        newBoard[7][5] = new Bishop(false, 7, 5);
        newBoard[7][6] = new Knight(false, 7, 6);
        newBoard[7][7] = new Rook(false, 7, 7);
        for (int i = 0; i < 8; i++){
            newBoard[6][i] = new Pawn(false, 6, i);
        }

        return newBoard;
    }

    public void printBoard(){
        for (int rank = 0; rank < 0; rank++){
            for (int file = 0; file < 8; file++){
                if (board[rank][file] == null){
                    if ((rank + file) % 2 == 0) System.out.println("    "); // Printing light square
                    else System.out.println("## "); // Printing dark square
                }
                else System.out.println(board[rank][file] + " "); // Printing pieces
            }
            System.out.println((rank + 1) + ""); // Printing rank on right side
        }
        System.out.println("a  b  c  d  e  f  g  h"); // Printing file on the bottom
    }

    // Returns 0 if neither king is in check, returns 1 if white is in check, returns 2 if black is in check, returns 3 if both are in check
    public int isKingInCheck(){
        int check = 0;
        boolean blackChecked = false;
        boolean whiteChecked = false;

        // Finding the two kings
        Piece whiteKing = null;
        Piece blackKing = null;
        for (int rank = 0; rank < 8; rank++){
            for (int file = 0; file < 8; file++){
                Piece piece = board[rank][file];
                if (piece instanceof King){
                    if (piece.player) whiteKing = piece;
                    else blackKing = piece;
                }
            }
        }

        // Checking if any of the pieces can capture the king
        for (int rank = 0; rank < 8; rank++){
            for (int file = 0; file < 8; file++){
                Piece piece = board[rank][file];
                if (piece == null || piece instanceof King) continue;
                else if (piece.player && !blackChecked){
                    blackChecked = piece.makeMove(blackKing.rank, blackKing.file, this);
                }
                else if (!piece.player && !whiteChecked){
                    whiteChecked = piece.makeMove(whiteKing.rank, whiteKing.file, this);
                }
            }
        }

        // Updating if there was a check or not
        if (whiteChecked) check += 1;
        if (blackChecked) check += 2;
        return check;
    }

    public boolean isCheckmate(boolean isWhite) {   
        isWhite=!isWhite; //?
        System.out.println("🔍 Checking for checkmate for " + (isWhite ? "White" : "Black"));
    
        int checkStatus = isKingInCheck();
        boolean kingInCheck = (isWhite && (checkStatus == 1 || checkStatus == 3)) || 
                              (!isWhite && (checkStatus == 2 || checkStatus == 3));
    
        if (!kingInCheck) {
            System.out.println("❌ ERROR: Called isCheckmate() but king is NOT in check.");
            return false;
        }
    
        System.out.println("🛠️ Debugging Board Before Checking Moves:");
        for (int r = 0; r < 8; r++) {
            for (int f = 0; f < 8; f++) {
                if (board[r][f] instanceof King) {
                    System.out.println((board[r][f].player ? "✅ White King" : "✅ Black King") + " at " + r + "," + f);
                }
            }
        }
    
        // Try all possible moves for the player in check
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                Piece piece = board[rank][file];
    
                if (piece != null && piece.player == isWhite) {
                    System.out.println("🛠️ Testing moves for: " + piece + " at (" + rank + "," + file + ")");
    
                    for (int targetRank = 0; targetRank < 8; targetRank++) {
                        for (int targetFile = 0; targetFile < 8; targetFile++) {
                            if (piece.validMove(targetRank, targetFile, this)) {  // ✅ Only valid moves
                                // Simulate the move
                                Piece temp = board[targetRank][targetFile];
                                board[targetRank][targetFile] = piece;
                                board[rank][file] = null;
    
                                System.out.println("🔍 Checking if this move removes check...");
                                System.out.println("🛠️ Debugging Board After Moving:");
                                for (int r = 0; r < 8; r++) {
                                    for (int f = 0; f < 8; f++) {
                                        if (board[r][f] instanceof King) {
                                            System.out.println((board[r][f].player ? "✅ White King" : "✅ Black King") + " at " + r + "," + f);
                                        }
                                    }
                                }
    
                                boolean stillInCheck = (isKingInCheck() == (isWhite ? 1 : 2));
    
                                // Undo the move
                                board[rank][file] = piece;
                                board[targetRank][targetFile] = temp;
    
                                if (!stillInCheck) {
                                    System.out.println("✅ Found an escape move! No checkmate.");
                                    return false; // At least one move escapes check
                                }
                            }
                        }
                    }
                }
            }
        }
    
        System.out.println("🚨 No valid escape moves found! This is CHECKMATE.");
        return true; // No valid moves → Checkmate
    }      

        // public boolean isKingInCheck(boolean whiteKing) {
        //     int kingRank = -1, kingFile = -1;
            
        //     // Locate the king on the board
        //     for (int rank = 0; rank < 8; rank++) {
        //         for (int file = 0; file < 8; file++) {
        //             Piece piece = board[rank][file];
        //             if (piece instanceof King && piece.player == whiteKing) {
        //                 kingRank = rank;
        //                 kingFile = file;
        //                 break;
        //             }
        //         }
        //     }
            
        //     // If the king is not found, return false (should not happen in a normal game)
        //     if (kingRank == -1 || kingFile == -1) {
        //         return false;
        //     }
            
        //     // Check if any opposing piece can attack the king
        //     for (int rank = 0; rank < 8; rank++) {
        //         for (int file = 0; file < 8; file++) {
        //             Piece attacker = board[rank][file];
        //             if (attacker != null && attacker.player != whiteKing) {
        //                 if (attacker.makeMove(kingRank, kingFile, this)) {
        //                     System.out.println("King in check! Attacker: " + attacker + " from (" + rank + "," + file + ")");
        //                     return true;
        //                 }
        //             }
        //         }
        //     }
        //     return false;
        // }
    
        // /**
        //  * Checks if the given player's king is in checkmate.
        //  * @param whiteKing true if checking for white's king, false for black's king
        //  * @return true if the player is in checkmate, false otherwise
        //  */
        // public boolean isCheckmate(boolean whiteKing) {
        //     if (!isKingInCheck(whiteKing)) {
        //         return false; // If the king is not in check, it's not checkmate
        //     }
        
        //     // Locate the king
        //     int kingRank = -1, kingFile = -1;
        //     for (int rank = 0; rank < 8; rank++) {
        //         for (int file = 0; file < 8; file++) {
        //             Piece piece = board[rank][file];
        //             if (piece instanceof King && piece.player == whiteKing) {
        //                 kingRank = rank;
        //                 kingFile = file;
        //                 break;
        //             }
        //         }
        //     }
        
        //     // If the king was not found (should not happen in a valid game), return false
        //     if (kingRank == -1 || kingFile == -1) {
        //         System.out.println("ERROR: King not found on board!");
        //         return false;
        //     }
        
        //     // Check if the king can move out of check
        //     int[] dRank = {-1, -1, -1, 0, 0, 1, 1, 1};
        //     int[] dFile = {-1, 0, 1, -1, 1, -1, 0, 1};
        
        //     for (int i = 0; i < 8; i++) {
        //         int newRank = kingRank + dRank[i];
        //         int newFile = kingFile + dFile[i];
        
        //         // Ensure new position is within bounds
        //         if (newRank >= 0 && newRank < 8 && newFile >= 0 && newFile < 8) {
        //             if (board[newRank][newFile] != null && board[newRank][newFile].player == whiteKing) {
        //                 continue; // Skip move if occupied by own piece
        //             }
        
        //             // Temporarily move the king
        //             Piece original = board[newRank][newFile];
        //             board[kingRank][kingFile] = null;
        //             board[newRank][newFile] = new King(whiteKing, newRank, newFile);
        //             boolean stillInCheck = isKingInCheck(whiteKing);
        
        //             // Undo the move
        //             board[newRank][newFile] = original;
        //             board[kingRank][kingFile] = new King(whiteKing, kingRank, kingFile);
        
        //             if (!stillInCheck) {
        //                 return false; // King can escape check
        //             }
        //         }
        //     }
        
        //     // Check if any move by other pieces can block the check or capture the attacker
        //     for (int rank = 0; rank < 8; rank++) {
        //         for (int file = 0; file < 8; file++) {
        //             Piece piece = board[rank][file];
        //             if (piece != null && piece.player == whiteKing) {
        //                 for (int tr = 0; tr < 8; tr++) {
        //                     for (int tf = 0; tf < 8; tf++) {
        //                         // Ensure target position is within bounds before calling makeMove()
        //                         if (tr >= 0 && tr < 8 && tf >= 0 && tf < 8) {
        //                             if (piece.makeMove(tr, tf, this)) {
        //                                 // Simulate the move
        //                                 Piece captured = board[tr][tf];
        //                                 board[rank][file] = null;
        //                                 board[tr][tf] = piece;
        //                                 boolean stillInCheck = isKingInCheck(whiteKing);
        
        //                                 // Undo the move
        //                                 board[tr][tf] = captured;
        //                                 board[rank][file] = piece;
        
        //                                 if (!stillInCheck) {
        //                                     System.out.println("Found escape move: " + piece + " from (" + rank + "," + file + ") to (" + tr + "," + tf + ")");
        //                                     return false; // Found a move that gets out of check
        //                                 }
        //                             }
        //                         }
        //                     }
        //                 }
        //             }
        //         }
        //     }
        
        //     // If no valid moves are found, it's checkmate
        //     System.out.println("🚨 No valid escape moves found! This is CHECKMATE.");
        //     return true;
        // }        

    public ArrayList<ReturnPiece> getBoardAsList(){
        ArrayList<ReturnPiece> pieces = new ArrayList<>();
        for (int rank = 0; rank < 8; rank++){
            for (int file = 0; file < 8; file++){
                if (board[rank][file] != null) pieces.add(board[rank][file]);
            }
        }

        return pieces;
    }

    public ReturnPlay.Message Move(int startFile, int startRank, int endFile, int endRank, String special){
        Piece targetPiece = board[startRank][startFile];
        
        // Checking for null and if correct player is making the move
        if(targetPiece == null || (!whiteTurn && targetPiece.player) || (whiteTurn && !targetPiece.player)){
            System.out.println("Move: " + startRank + " " + startFile + " " + endRank + " " + endFile);
            System.out.println("Correct player not making move s");
            return ReturnPlay.Message.ILLEGAL_MOVE;
        }

        // Checking if move will cause a check
        if (!targetPiece.validMove(endRank, endFile, this)){
            System.out.println("Move: " + startRank + " " + startFile + " " + endRank + " " + endFile);
            System.out.println("Invalid move");
            return ReturnPlay.Message.ILLEGAL_MOVE;
        }

        // Checking if move is valid
        if (!targetPiece.makeMove(endRank, endFile, this)){
            System.out.println("Move: " + startRank + " " + startFile + " " + endRank + " " + endFile);
            System.out.println("Couldn't make move ss");
            return ReturnPlay.Message.ILLEGAL_MOVE;
        }

        // Updating positions
        targetPiece.updatePosition(endRank, endFile);
        board[startRank][startFile] = null;
        board[endRank][endFile] = targetPiece;

        // //check for check and checkmate
        // if (isKingInCheck(!whiteTurn)) {
        //     return isCheckmate(!whiteTurn) 
        //         ? (whiteTurn ? ReturnPlay.Message.CHECKMATE_WHITE_WINS : ReturnPlay.Message.CHECKMATE_BLACK_WINS) 
        //         : ReturnPlay.Message.CHECK;
        // }

        int checkStatus = isKingInCheck();
        boolean opponentIsWhite = !whiteTurn;
        if ((checkStatus == 1 && opponentIsWhite) || (checkStatus == 2 && !opponentIsWhite)) {
            if (isCheckmate(whiteTurn)) {
                return whiteTurn ? ReturnPlay.Message.CHECKMATE_WHITE_WINS : ReturnPlay.Message.CHECKMATE_BLACK_WINS;
            }
            return ReturnPlay.Message.CHECK;
        }        
        
        // Checking for promotion and promoting
        if ((targetPiece.pieceType == ReturnPiece.PieceType.WP && endRank == 7)){
            Piece piece = null;
            if (special == null || special.equals("Q") || special.equals("draw?")){
                piece = new Queen(true, endRank, endFile);
            }
            else if (special.equals("B")){
                piece = new Bishop(true, endRank, endFile);
            }
            else if (special.equals("R")){
                piece = new Rook(true, endRank, endFile);
            }
            else if (special.equals("N")){
                piece = new Knight(true, endRank, endFile);
            }
            board[endRank][endFile] = piece;
        }
        else if (targetPiece.pieceType == ReturnPiece.PieceType.BP && endRank == 0){
            Piece piece = null;
            if (special == null || special.equals("Q") || special.equals("draw?")){
                piece = new Queen(false, endRank, endFile);
            }
            else if (special.equals("B")){
                piece = new Bishop(false, endRank, endFile);
            }
            else if (special.equals("R")){
                piece = new Rook(false, endRank, endFile);
            }
            else if (special.equals("N")){
                piece = new Knight(false, endRank, endFile);
            }
            board[endRank][endFile] = piece;
        }

        // Check for draw
        if (special != null && special.equals("draw?")) return ReturnPlay.Message.DRAW;
        
        // Switch turns
        if (whiteTurn) whiteTurn = false;
        else whiteTurn = true;

        return null;
    }
}