package markszhouc.spam.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUIWindow extends Application {

	// The actual GUIWindow
	private static Stage stg;
	private static String title;
	private static double width, height, awidth, aheight;

	public static void launchGUI(String stageTitle) {
		title = stageTitle;
		width = 900;
		height = 600;
		awidth = 900;
		aheight = 600;
		// calls start method
		launch();
	}
	
	public static void launchGUI(String stageTitle, double width_, double height_, double appWidth, double appHeight) {
		title = stageTitle;
		width = width_;
		height = height_;
		awidth = appWidth;
		aheight = appHeight;
		// calls start method
		launch();
	}

	@Override
	public void start(Stage arg0) throws Exception {
		stg = arg0;
		stg.setTitle(title);
		GUIHandler.init(width, height, awidth, aheight);
		GUIHandler.initCanvasAndLoop(stg);
		//stg.show();
	}

	@Override
	public void stop() {
		System.out.println("Shutting Down.");
		GUIHandler.stop();
	}
}
