// Toma Takamatsu, Amy Margolina

package chess;

import java.util.ArrayList;

public class Chess {

        enum Player { white, black }
    
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	public static ReturnPlay play(String move) {
		move = move.trim();
		String[] parts = move.split(" ");
		if (parts.length != 2) {
			ReturnPlay result = new ReturnPlay();
			result.piecesOnBoard = Board.getPiecesOnBoard();
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return result;
		}
	
		int startFile = parts[0].charAt(0) - 'a';
		int startRank = 8 - Character.getNumericValue(parts[0].charAt(1));
		int targetFile = parts[1].charAt(0) - 'a';
		int targetRank = 8 - Character.getNumericValue(parts[1].charAt(1));
	
		if (startRank < 0 || startRank > 7 || startFile < 0 || startFile > 7 ||
			targetRank < 0 || targetRank > 7 || targetFile < 0 || targetFile > 7) {
			ReturnPlay result = new ReturnPlay();
			result.piecesOnBoard = Board.getPiecesOnBoard();
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return result;
		}
	
		Piece piece = Board.boardInstance.board[startRank][startFile];
	
		if (piece == null) {
			ReturnPlay result = new ReturnPlay();
			result.piecesOnBoard = Board.getPiecesOnBoard();
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return result;
		}
	
		// Ensure it's the correct player's turn
		if ((Board.whiteTurn && !piece.player) || (!Board.whiteTurn && piece.player)) {
			ReturnPlay result = new ReturnPlay();
			result.piecesOnBoard = Board.getPiecesOnBoard();
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return result;
		}
	
		// Check if move is valid according to the piece's logic
		if (!piece.makeMove(targetRank, targetFile, Board.boardInstance)) {
			ReturnPlay result = new ReturnPlay();
			result.piecesOnBoard = Board.getPiecesOnBoard();
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return result;
		}

		//check if move puts the player's own king in check
		if (!piece.validMove(targetRank, targetFile, Board.boardInstance)) {
			ReturnPlay result = new ReturnPlay();
			result.piecesOnBoard = Board.getPiecesOnBoard();
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			return result;
		}
	
		// Backup for possible rollback
		Piece capturedPiece = Board.boardInstance.board[targetRank][targetFile];
	
		// Move piece
		Board.boardInstance.board[startRank][startFile] = null;
		Board.boardInstance.board[targetRank][targetFile] = piece;
		piece.updatePosition(targetRank, targetFile);
	
		// Check if move puts own king in check
		int checkStatus = Board.boardInstance.isKingInCheck();
		ReturnPlay result = new ReturnPlay();
		result.piecesOnBoard = Board.getPiecesOnBoard();
	
		if (checkStatus == 1 || checkStatus == 2) {
			result.message = ReturnPlay.Message.CHECK;
		} else {
			result.message = null;
		}
	
		// Switch turn
		Board.whiteTurn = !Board.whiteTurn;
	
		return result;
	}	

	public static void start() {
		Board.boardInstance = new Board();
		Board.whiteTurn = true;
	}
	
}