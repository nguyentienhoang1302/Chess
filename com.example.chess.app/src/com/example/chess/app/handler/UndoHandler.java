package com.example.chess.app.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import com.example.chess.app.listener.ChessMoveListener;

import com.example.chess.core.model.Army;
import com.example.chess.core.model.Board;
import com.example.chess.core.model.ChessMove;
import com.example.chess.core.model.Side;
import com.example.chess.core.model.Square;
import com.example.chess.core.model.piece.Piece;


public class UndoHandler {
	protected Board board;
	protected Side side;
	  @Execute
	   public void execute(Shell shell) {
		  ChessMove whiteMove = ChessMoveListener.getLastWhiteMove();
		  ChessMove blackMove = ChessMoveListener.getLastBlackMove();
		  undoMove(blackMove);
		  undoMove(whiteMove);
		}

	private void undoMove(ChessMove move) {
		Square initialSquare = move.getInitialSquare();
		Square targetSquare = move.getTargetSquare();
		Piece targetPiece = move.getTargetPiece();
		Piece movingPiece = targetSquare.getPiece();
		
		movingPiece.setSquare(initialSquare);
		initialSquare.setPiece(movingPiece);
		targetSquare.setPiece(null);
		
		if(targetPiece != null)
		{
			targetPiece.setSquare(targetSquare);
			targetSquare.setPiece(targetPiece);
			Army opponentArmy = board.getArmy(side.opposite());
			opponentArmy.revivePiece(targetPiece);
		}
	}
}
