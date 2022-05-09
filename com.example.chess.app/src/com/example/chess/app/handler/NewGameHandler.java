package com.example.chess.app.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

import com.example.chess.app.parts.ChessBoardPart;

public class NewGameHandler {
	  @Execute
	   public void execute(Shell shell) {
		  ChessBoardPart.reset();
	}
}
