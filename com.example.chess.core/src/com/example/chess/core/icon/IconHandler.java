package com.example.chess.core.icon;

//import java.net.URISyntaxException;
//import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import com.example.chess.core.model.Side;

public class IconHandler {
	private static Path path;
	
	static
	{
		path = Paths.get("C:/Users/Tien Hoang/eclipse-workspace/com.example.chess.core/image");
		/*try {
			//URL location = IconHandler.class.getProtectionDomain().getCodeSource().getLocation();
			//path = Paths.get(location.toURI()).resolve("../com.example.chess.core/image");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	public static Image getIcon(String pieceName, Side side)
	{
		String imageName = (side == Side.WHITE ? "W_" : "B_") + pieceName + ".png";
		return new Image(Display.getDefault(), path.resolve(imageName).toString());
	}
	
	public static Image getBlankIcon()
	{
		return new Image(Display.getDefault(), path.resolve("blank.png").toString());
	}
}
