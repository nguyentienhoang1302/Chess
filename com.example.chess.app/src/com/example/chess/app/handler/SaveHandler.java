package com.example.chess.app.handler;

import java.io.*;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import com.example.chess.app.parts.ChessBoardPart;
import com.example.chess.app.room.ChessRoom;
import com.example.chess.core.model.Board;

public class SaveHandler {
	  @Execute
	  public void execute(Shell shell)
	  {
			ChessRoom chessRoom = ChessBoardPart.getChessRoom();
			
			Board board = chessRoom.getBoard();
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Tien Hoang\\Documents\\GitHub\\Chess\\save\\save.txt"));
				for(int r = Board.LENGTH - 1; r >= 0; r--)
				{
					for(int c = 0; c < Board.LENGTH; c++)
					{
						if(board.getSquare(r, c).getPiece() != null)
						{
							bw.write(board.getSquare(r, c).getPiece().toString());
						}
						else
						{
							bw.write(".");
						}
					}
				}
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
}
