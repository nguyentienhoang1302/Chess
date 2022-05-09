package com.example.chess.app.parts;


import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import com.example.chess.app.listener.ChessMoveListener;
import com.example.chess.app.room.ChessRoom;
import com.example.chess.core.icon.IconHandler;
import com.example.chess.core.model.Board;
import com.example.chess.core.model.Side;
import com.example.chess.core.model.Square;
import com.example.chess.core.model.piece.Bishop;
import com.example.chess.core.model.piece.BlackPawn;
import com.example.chess.core.model.piece.King;
import com.example.chess.core.model.piece.Knight;
import com.example.chess.core.model.piece.Piece;
import com.example.chess.core.model.piece.Queen;
import com.example.chess.core.model.piece.Rook;
import com.example.chess.core.model.piece.WhitePawn;


public class ChessBoardPart {
	private static final String[] COL_LETTERS = {"A", "B", "C", "D", "E", "F", "G", "H"};
	private static ChessRoom chessRoom;
	private Label[][] squares;

	public ChessBoardPart()
	{
		chessRoom = new ChessRoom();
		squares = new Label[Board.LENGTH + 1][Board.LENGTH + 1];
	}
	
	@PostConstruct
	public void createComposite(Composite parent) {	
		//parent.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		parent.setLayout(new GridLayout(Board.LENGTH + 1, false));
		GridData tagGridData = new GridData(SWT.CENTER, SWT.CENTER, true, false);
		GridData squareGridData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		squareGridData.widthHint = 100;
		squareGridData.heightHint = 100;
		
		for(int r = Board.LENGTH - 1; r >= 0; r--)
		{
			squares[r][0] = new Label(parent, SWT.NONE);
			squares[r][0].setLayoutData(tagGridData);
			squares[r][0].setText(String.format("%s", r+1));
			
			for(int c = 0; c < Board.LENGTH; c++)
			{
				squares[r][c+1] = new Label(parent, SWT.BORDER);
				squares[r][c+1].setLayoutData(squareGridData);
				squares[r][c+1].setData(chessRoom.getBoard().getSquare(r, c));
				squares[r][c+1].addMouseListener(new ChessMoveListener(squares[r][c+1]));
			}
		}
		
		new Label(parent, SWT.NONE);
		
		for(int c = 0; c < Board.LENGTH; c++)
		{
			squares[Board.LENGTH][c+1] = new Label(parent, SWT.NONE);
			squares[Board.LENGTH][c+1].setText(COL_LETTERS[c]);
			squares[Board.LENGTH][c+1].setLayoutData(tagGridData);
		}
		setFocus();
		PartRefresher.setChessBoardPart(this);
	}

	@Focus
	public void setFocus() {
		for(int r = Board.LENGTH - 1; r >= 0; r--)
		{
			for(int c = 0; c < Board.LENGTH; c++)
			{
				if((r + c) % 2 == 0)
				{
					squares[r][c+1].setBackground(Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY));
				}
				else
				{
					squares[r][c+1].setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
				}
				
				if(((Square) squares[r][c+1].getData()).isLegal())
				{
					squares[r][c+1].setBackground(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
				}
				
				Piece piece = ((Square) squares[r][c+1].getData()).getPiece();
				
				if(piece == null)
				{
					squares[r][c+1].setImage(IconHandler.getBlankIcon());
				}
				else
				{
					squares[r][c+1].setImage(piece.getIcon());
				}
			}
		}
	}
	
	public static ChessRoom getChessRoom()
	{
		return chessRoom;
	}
	
	
	public static void reset()
	{
		for(int r = Board.LENGTH - 1; r >= 0; r--)
		{
			for(int c = 0; c < Board.LENGTH; c++)
			{
				chessRoom.getBoard().getSquare(r, c).setPiece(null);
			}
		}
		
		chessRoom.getBoard().getArmy(Side.WHITE).clearArmy();
		chessRoom.getBoard().getArmy(Side.BLACK).clearArmy();
		
		for(int c = 0; c < Board.LENGTH; c++)
		{
			chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new WhitePawn(chessRoom.getBoard().getSquare(1, c), Side.WHITE));
		}
		chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Rook(chessRoom.getBoard().getSquare(0, 0), Side.WHITE));
		chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Rook(chessRoom.getBoard().getSquare(0, 7), Side.WHITE));
		chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Knight(chessRoom.getBoard().getSquare(0, 1), Side.WHITE));
		chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Knight(chessRoom.getBoard().getSquare(0, 6), Side.WHITE));
		chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Bishop(chessRoom.getBoard().getSquare(0, 2), Side.WHITE));
		chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Bishop(chessRoom.getBoard().getSquare(0, 5), Side.WHITE));
		chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new King(chessRoom.getBoard().getSquare(0, 4), Side.WHITE));
		chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Queen(chessRoom.getBoard().getSquare(0, 3), Side.WHITE));
		
		for(int c = 0; c < Board.LENGTH; c++)
		{
			chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new BlackPawn(chessRoom.getBoard().getSquare(6, c), Side.BLACK));
		}
		chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new Rook(chessRoom.getBoard().getSquare(7, 0), Side.BLACK));
		chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new Rook(chessRoom.getBoard().getSquare(7, 7), Side.BLACK));
		chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new Knight(chessRoom.getBoard().getSquare(7, 1), Side.BLACK));
		chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new Knight(chessRoom.getBoard().getSquare(7, 6), Side.BLACK));
		chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new Bishop(chessRoom.getBoard().getSquare(7, 2), Side.BLACK));
		chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new Bishop(chessRoom.getBoard().getSquare(7, 5), Side.BLACK));
		chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new King(chessRoom.getBoard().getSquare(7, 4), Side.BLACK));
		chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new Queen(chessRoom.getBoard().getSquare(7, 3), Side.BLACK));		
	}
}