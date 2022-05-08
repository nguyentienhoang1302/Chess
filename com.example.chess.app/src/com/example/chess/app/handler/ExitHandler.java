package com.example.chess.app.handler;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.swt.widgets.Shell;

public class ExitHandler {
	  @Execute
	   public void execute(Shell shell) {
	       System.exit(0);
	  }
}
