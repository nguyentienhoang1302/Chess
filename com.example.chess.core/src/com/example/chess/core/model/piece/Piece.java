package com.example.chess.core.model.piece;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;

import com.example.chess.core.model.Side;
import com.example.chess.core.model.Square;

public abstract class Piece {
	private final int score;
	protected Square square;
	protected final Side side;
	protected final Image icon;
	
	public abstract List<Square> computeLegalMoves();
	
	protected Piece(Square square, Side side, int score, Image icon)
	{
		this.square = square;
		this.square.setPiece(this);
		this.score = score;
		this.side = side;
		this.icon = icon;
	}

	public void setSquare(Square square) {
		// TODO Auto-generated method stub
		this.square = square;
	}

	public Square getSquare() {
		// TODO Auto-generated method stub
		return square;
	}
	
	
	protected void checkSquare(List<Square> legalMoves, Square targetSquare)
	{
		if(targetSquare != null && (targetSquare.getPiece() == null || targetSquare.getPiece().side != side))
		{
			legalMoves.add(targetSquare);
		}
	}
	
	protected List<Square> computeLinearMoves(int horizontal, int vertical)
	{
		List<Square> legalMoves = new ArrayList<Square>();
		Square targetSquare = square.getAdjacentSquare(horizontal, vertical);
		
		while(targetSquare != null)
		{
			if(targetSquare.getPiece() == null)
			{
				legalMoves.add(targetSquare);
			}
			else if(targetSquare.getPiece().side == side)
			{
				break;
			}
			else
			{
				legalMoves.add(targetSquare);
				break;
			}
			targetSquare = targetSquare.getAdjacentSquare(horizontal, vertical);
		}
		
		return legalMoves;
	}

	public Image getIcon() {
		// TODO Auto-generated method stub
		return icon;
	}

	public Side getSide() {
		// TODO Auto-generated method stub
		return side;
	}

	public int getScore() {
		// TODO Auto-generated method stub
		return score;
	}
}
