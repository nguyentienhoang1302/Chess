package com.example.chess.core.model;

import com.example.chess.core.model.piece.Piece;

public class Square {
	private final Board board;
	private final int row;
	private final int col;
	private Piece piece;
	private boolean legal;
	
	public Square(Board board, int row, int col)
	{
		this.board = board;
		this.row = row;
		this.col = col;
	}
	
	public void setPiece(Piece piece)
	{
		this.piece = piece;
	}

	public Piece getPiece() {
		// TODO Auto-generated method stub
		return piece;
	}
	
	public Square getAdjacentSquare(int horizontal, int vertical)
	{
		return board.getSquare(vertical + row, horizontal + col);
	}

	public int getRow() {
		// TODO Auto-generated method stub
		return row;
	}

	public void setLegal(boolean legal) {
		// TODO Auto-generated method stub
		this.legal = legal;
	}
	
	public boolean isLegal()
	{
		return legal;
	}
}
