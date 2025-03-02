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

	public static Board chessBoard;

	public static ReturnPlay play(String move) {

		/* FILL IN THIS METHOD */
		
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		ReturnPlay result = new ReturnPlay();
		move = move.trim();
		String[] parts = move.split(" ");

		// Checking if resign
		if (parts[0].equals("resign")){
			if (chessBoard.whiteTurn){
				result.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
			}
			else result.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
			result.piecesOnBoard = chessBoard.getBoardAsList();
			return result;
		}

		// Getting starting file/rank and ending file/rank
		int startFile = ((int)parts[0].charAt(0)) - 97;
		int startRank = parts[0].charAt(1) - '0' - 1;
		int endFile = ((int)parts[1].charAt(0)) - 97;
		int endRank = parts[1].charAt(1) - '0' - 1;

		// Checking for draw or pawn promotion piece request, special could be "draw?", "resign", "Q", etc.
		String special = null;
		if (parts.length == 3) special = parts[2];

		// Trying to make the move
		result.message = chessBoard.Move(startRank, startFile, endFile, endRank, special);

		// Getting updated board
		result.piecesOnBoard = chessBoard.getBoardAsList();

		return result;
	}
	
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		/* FILL IN THIS METHOD */
		chessBoard = new Board();
	}

	// public static ReturnPlay play(String move) {
	// 	move = move.trim();
	// 	String[] parts = move.split(" ");
	// 	if (parts.length != 2) {
	// 		ReturnPlay result = new ReturnPlay();
	// 		result.piecesOnBoard = Board.getPiecesOnBoard();
	// 		result.message = ReturnPlay.Message.ILLEGAL_MOVE;
	// 		return result;
	// 	}
	
	// 	int startFile = parts[0].charAt(0) - 'a';
	// 	int startRank = 8 - Character.getNumericValue(parts[0].charAt(1));
	// 	int targetFile = parts[1].charAt(0) - 'a';
	// 	int targetRank = 8 - Character.getNumericValue(parts[1].charAt(1));
	
	// 	if (startRank < 0 || startRank > 7 || startFile < 0 || startFile > 7 ||
	// 		targetRank < 0 || targetRank > 7 || targetFile < 0 || targetFile > 7) {
	// 		ReturnPlay result = new ReturnPlay();
	// 		result.piecesOnBoard = Board.getPiecesOnBoard();
	// 		result.message = ReturnPlay.Message.ILLEGAL_MOVE;
	// 		return result;
	// 	}
	
	// 	Piece piece = Board.boardInstance.board[startRank][startFile];
	
	// 	if (piece == null) {
	// 		ReturnPlay result = new ReturnPlay();
	// 		result.piecesOnBoard = Board.getPiecesOnBoard();
	// 		result.message = ReturnPlay.Message.ILLEGAL_MOVE;
	// 		return result;
	// 	}
	
	// 	// Ensure it's the correct player's turn
	// 	if ((Board.whiteTurn && !piece.player) || (!Board.whiteTurn && piece.player)) {
	// 		ReturnPlay result = new ReturnPlay();
	// 		result.piecesOnBoard = Board.getPiecesOnBoard();
	// 		result.message = ReturnPlay.Message.ILLEGAL_MOVE;
	// 		return result;
	// 	}
	
	// 	// Check if move is valid according to the piece's logic
	// 	if (!piece.makeMove(targetRank, targetFile, Board.boardInstance)) {
	// 		ReturnPlay result = new ReturnPlay();
	// 		result.piecesOnBoard = Board.getPiecesOnBoard();
	// 		result.message = ReturnPlay.Message.ILLEGAL_MOVE;
	// 		return result;
	// 	}

	// 	//check if move puts the player's own king in check
	// 	if (!piece.validMove(targetRank, targetFile, Board.boardInstance)) {
	// 		ReturnPlay result = new ReturnPlay();
	// 		result.piecesOnBoard = Board.getPiecesOnBoard();
	// 		result.message = ReturnPlay.Message.ILLEGAL_MOVE;
	// 		return result;
	// 	}
	
	// 	// Backup for possible rollback
	// 	Piece capturedPiece = Board.boardInstance.board[targetRank][targetFile];
	
	// 	// Move piece
	// 	Board.boardInstance.board[startRank][startFile] = null;
	// 	Board.boardInstance.board[targetRank][targetFile] = piece;
	// 	piece.updatePosition(targetRank, targetFile);
	
	// 	// Check if move puts own king in check
	// 	int checkStatus = Board.boardInstance.isKingInCheck();
	// 	ReturnPlay result = new ReturnPlay();
	// 	result.piecesOnBoard = Board.getPiecesOnBoard();
	
	// 	if (checkStatus == 1 || checkStatus == 2) {
	// 		result.message = ReturnPlay.Message.CHECK;
	// 	} else {
	// 		result.message = null;
	// 	}
	
	// 	// Switch turn
	// 	Board.whiteTurn = !Board.whiteTurn;
	
	// 	return result;
	// }	

	// public static void start() {
	// 	Board.boardInstance = new Board();
	// 	Board.whiteTurn = true;
	// }
	
}