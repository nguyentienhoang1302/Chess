package com.example.chess.app.parts;


import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

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
		checkWhitePawnPromotion();
		checkBlackPawnPromotion();
		checkEndGame();
	}
	
	private void checkWhitePawnPromotion() {
		// TODO Auto-generated method stub
		for(int c = 0; c < Board.LENGTH; c++)
		{
			if(chessRoom.getBoard().getSquare(7, c).getPiece() != null && chessRoom.getBoard().getSquare(7, c).getPiece().toString() == "P")
			{
		        CustomDialog dialog = new CustomDialog(new Shell());
		        dialog.setText("Title");
		        dialog.setMessage("Message");
	
		        dialog.open(c);
			}
		}	
	}
	
	private void checkBlackPawnPromotion() {
		// TODO Auto-generated method stub
		for(int c = 0; c < Board.LENGTH; c++)
		{
			if(chessRoom.getBoard().getSquare(0, c).getPiece() != null && chessRoom.getBoard().getSquare(0, c).getPiece().toString() == "p")
			{
				chessRoom.getBoard().getArmy(Side.BLACK).buryPiece(chessRoom.getBoard().getSquare(0, c).getPiece());
				chessRoom.getBoard().getArmy(Side.BLACK).addPiece(new Queen(chessRoom.getBoard().getSquare(0, c), Side.BLACK));
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
	
	public void checkEndGame()
	{
		if(chessRoom.getBoard().computeRating(Side.BLACK) >= 99)
		{
			MessageBox messageBox = new MessageBox(new Shell(), SWT.ICON_WARNING | SWT.OK);
			messageBox.setText("End Game");
			messageBox.setMessage("Black Win");
			messageBox.open();
			reset();
		}
		
		if(chessRoom.getBoard().computeRating(Side.WHITE) >= 99)
		{
			MessageBox messageBox = new MessageBox(new Shell(), SWT.ICON_WARNING | SWT.OK);
			messageBox.setText("End Game");
			messageBox.setMessage("White Win");
			messageBox.open();
			reset();
		}
	}
	public class CustomDialog extends Dialog
	{
	    private String message = "Promotion";
	    private Shell shell;

	    public CustomDialog(Shell parent)
	    {
	        // Pass the default styles here
	        this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	        shell = parent;
	    }

	    public CustomDialog(Shell parent, int style)
	    {
	        // Let users override the default styles
	        super(parent, style);
	        shell = parent;
	    }

	    public String getMessage()
	    {
	        return message;
	    }

	    public void setMessage(String message)
	    {
	        this.message = message;
	    }

	    public void open(int c)
	    {
	        shell.setText(getText());
	        createContents(shell, c);
	        shell.pack();
	        shell.open();
	        Display display = getParent().getDisplay();
	        while (!shell.isDisposed())
	        {
	            if (!display.readAndDispatch())
	            {
	                display.sleep();
	            }
	        }
	    }

	    /**
	     * Creates the dialog's contents
	     * 
	     * @param shell
	     *            the dialog window
	     */
	    private void createContents(final Shell shell, int c)
	    {
	        shell.setLayout(new GridLayout(4, true));

	        // Show the message
	        Label label = new Label(shell, SWT.NONE);
	        label.setText("Promotion");
	        GridData data = new GridData();
	        data.horizontalSpan = 4;
	        label.setLayoutData(data);

	        // Display the input box
	        Label image = new Label(shell, SWT.NONE);
	        image.setImage(shell.getDisplay().getSystemImage(SWT.ICON_QUESTION));
	        data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
	        data.horizontalSpan = 4;
	        image.setLayoutData(data);

	        Button queen = new Button(shell, SWT.PUSH);
	        queen.setText("Queen");
	        data = new GridData(SWT.FILL, SWT.END, true, true);
	        queen.setLayoutData(data);
	        queen.addSelectionListener(new SelectionAdapter()
	        {
	            public void widgetSelected(SelectionEvent event)
	            {
	        		chessRoom.getBoard().getArmy(Side.WHITE).buryPiece(chessRoom.getBoard().getSquare(7, c).getPiece());
					chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Queen(chessRoom.getBoard().getSquare(7, c), Side.WHITE));
					shell.close();
	            }
	        });

	        Button knight = new Button(shell, SWT.PUSH);
	        knight.setText("Knight");
	        data = new GridData(SWT.FILL, SWT.END, true, true);
	        knight.setLayoutData(data);
	        knight.addSelectionListener(new SelectionAdapter()
	        {
	            public void widgetSelected(SelectionEvent event)
	            {
	        		chessRoom.getBoard().getArmy(Side.WHITE).buryPiece(chessRoom.getBoard().getSquare(7, c).getPiece());
					chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Knight(chessRoom.getBoard().getSquare(7, c), Side.WHITE));
					shell.close();
	            }
	        });

	        Button rook = new Button(shell, SWT.PUSH);
	        rook.setText("Rook");
	        data = new GridData(SWT.FILL, SWT.END, true, true);
	        rook.setLayoutData(data);
	        rook.addSelectionListener(new SelectionAdapter()
	        {
	            public void widgetSelected(SelectionEvent event)
	            {
	        		chessRoom.getBoard().getArmy(Side.WHITE).buryPiece(chessRoom.getBoard().getSquare(7, c).getPiece());
					chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Rook(chessRoom.getBoard().getSquare(7, c), Side.WHITE));
					shell.close();
	            }
	        });
	        
	        Button bishop = new Button(shell, SWT.PUSH);
	        bishop.setText("Bishop");
	        data = new GridData(SWT.FILL, SWT.END, true, true);
	        bishop.setLayoutData(data);
	        bishop.addSelectionListener(new SelectionAdapter()
	        {
	            public void widgetSelected(SelectionEvent event)
	            {
	        		chessRoom.getBoard().getArmy(Side.WHITE).buryPiece(chessRoom.getBoard().getSquare(7, c).getPiece());
					chessRoom.getBoard().getArmy(Side.WHITE).addPiece(new Bishop(chessRoom.getBoard().getSquare(7, c), Side.WHITE));
					shell.close();
	            }
	        });


	        shell.setDefaultButton(queen);
	    }
	}
}



