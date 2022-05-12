package com.example.chess.app.handler;

import java.io.*;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import com.example.chess.app.parts.ChessBoardPart;
import com.example.chess.app.room.ChessRoom;
import com.example.chess.core.model.Board;
import com.example.chess.core.model.Side;
import com.example.chess.core.model.piece.Bishop;
import com.example.chess.core.model.piece.BlackPawn;
import com.example.chess.core.model.piece.King;
import com.example.chess.core.model.piece.Knight;
import com.example.chess.core.model.piece.Queen;
import com.example.chess.core.model.piece.Rook;
import com.example.chess.core.model.piece.WhitePawn;

public class LoadHandler {
	  @Execute
	   public void execute(Shell shell) {
		ChessRoom chessRoom = ChessBoardPart.getChessRoom();
			
		  try {
			  BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Tien Hoang\\Documents\\GitHub\\Chess\\save\\save.txt"));
			  int c1;
			  StringBuilder response= new StringBuilder();
			  while ((c1 = br.read()) != -1) {
				    response.append( (char)c1 ) ;  
				}
				String s = response.toString();
				
				for(int r = Board.LENGTH - 1; r >= 0; r--)
				{
					for(int c = 0; c < Board.LENGTH; c++)
					{
						chessRoom.getBoard().getSquare(r, c).setPiece(null);
					}
				}
				
				chessRoom.getBoard().getArmy(Side.WHITE).clearArmy();
				chessRoom.getBoard().getArmy(Side.BLACK).clearArmy();
			    
				for(int r = Board.LENGTH - 1; r >= 0; r--)
				{
					for(int c = 0; c < Board.LENGTH; c++)
					{
						char flag = s.charAt(0);
						
						switch(flag)
						{
						case 'p':
							chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new BlackPawn(chessRoom.getBoard().getSquare(r, c), Side.BLACK));
							break;
						case 'r':
							chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new Rook(chessRoom.getBoard().getSquare(r, c), Side.BLACK));
							break;
						case 'k':
							chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new Knight(chessRoom.getBoard().getSquare(r, c), Side.BLACK));
							break;
						case 'b':
							chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new Bishop(chessRoom.getBoard().getSquare(r, c), Side.BLACK));
							break;
						case 'q':
							chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new Queen(chessRoom.getBoard().getSquare(r, c), Side.BLACK));
							break;
						case 'g':
							chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new King(chessRoom.getBoard().getSquare(r, c), Side.BLACK));
							break;
						case 'P':
							chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new WhitePawn(chessRoom.getBoard().getSquare(r, c), Side.WHITE));
							break;
						case 'R':
							chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Rook(chessRoom.getBoard().getSquare(r, c), Side.WHITE));
							break;
						case 'K':
							chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Knight(chessRoom.getBoard().getSquare(r, c), Side.WHITE));
							break;
						case 'B':
							chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Bishop(chessRoom.getBoard().getSquare(r, c), Side.WHITE));
							break;
						case 'Q':
							chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Queen(chessRoom.getBoard().getSquare(r, c), Side.WHITE));
							break;
						case 'G':
							chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new King(chessRoom.getBoard().getSquare(r, c), Side.WHITE));
							break;							
						}
						
						s = s.substring(1);
					}
				}    
			    br.close();
		  }
		  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
}
