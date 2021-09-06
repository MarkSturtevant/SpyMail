package markszhouc.spam.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GUIHandler {

	public static double stageW; // stage width
	public static double stageH; // stage height
	
	public static double mouseX; // decimal mouse X
	public static double mouseY; // decimal mouse Y
	
	private static boolean mousePressed;
	
	public static Pane root; // special use
	public static GraphicsContext gc; // Used for painting onto the canvas

	private static AnimationTimer loop;
	
	private static List<AudioClip> activeMusic, activeSE;
	private static boolean musicOn, soundEffectsOn;
	
	public static void init(double width, double height, double awidth, double aheight) {
		mouseX = 1;
		mouseY = 1;
		mousePressed = false;
		stageW = awidth;
		stageH = aheight;
		activeMusic = new ArrayList<>();
		activeSE = new ArrayList<>();
		musicOn = soundEffectsOn = true;
		CanvasUtils.setReferences(width, height);
	}
	
	protected static void initCanvasAndLoop(Stage stage) {
		root = new Pane();
		// init scene
		Scene sc = new Scene(root, stageW, stageH);
		sc.setOnMousePressed(e -> {
			mousePressed = true;
		});
		sc.setOnMouseReleased(e -> {
			mousePressed = false;
			GUIScreen.onMouseClick(mouseX, mouseY);
		});
		sc.setOnMouseMoved(e -> {
			mouseX = e.getSceneX() * CanvasUtils.getReferenceWidth() / stageW;
			mouseY = e.getSceneY() * CanvasUtils.getReferenceHeight() / stageH;
		});
		sc.setOnMouseDragged(e -> {
			mouseX = e.getSceneX() * CanvasUtils.getReferenceWidth() / stageW;
			mouseY = e.getSceneY() * CanvasUtils.getReferenceHeight() / stageH;
		});

		// init canvas
		ResizableCanvas canvas = new ResizableCanvas(stageW, stageH);
		gc = canvas.getGraphicsContext2D();
		gc.setFont(Font.font("Consolas", 12));
		sc.widthProperty().addListener((obs, oldVal, newVal) -> {
			stageW = sc.getWidth();
			canvas.setWidth(stage.getWidth());
		});
		sc.heightProperty().addListener((obs, oldVal, newVal) -> {
			stageH = sc.getHeight();
			canvas.setHeight(stage.getHeight());
		});
		root.getChildren().add(canvas);

		// setting canvasScene to open when game starts; then shows window
		GUIScreen.init();
		stage.setScene(sc);
		stage.show();
		// loop launch
		initTimerLoop();
	}

	public static String createTextInput(String message) {
		TextInputDialog td = new TextInputDialog("");
		td.setTitle("Enter Data");
		td.setHeaderText(message);
		td.showAndWait();
		return td.getEditor().getText();
	}

	public static void createAlert(String message) {
		Alert a = new Alert(Alert.AlertType.WARNING);
		a.setTitle("Error Processing Data");
		a.setContentText(message);
		a.showAndWait();
	}

	public static void createInfo(String message) {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setTitle("Information");
		a.setContentText(message);
		a.showAndWait();
	}
	
	// ends loop
	public static void stop() {
		loop.stop();
	}
	
	private static void initTimerLoop() {
		loop = new AnimationTimer() {
			long timein = System.currentTimeMillis();
			int frameSpeed = 1; // frameSpeed is in milliseconds!
			@Override
			public void handle(long now) {
				frameSpeed = (int) (System.currentTimeMillis() - timein);
				timein = System.currentTimeMillis();
				GUIScreen.render(mouseX, mouseY, frameSpeed);
			}
		};
		loop.start();
	}
	
	// Same as normal canvas but allows for resizeability
	private static class ResizableCanvas extends Canvas {
		
		private ResizableCanvas(double width, double height) {
			super(width, height);
		}
		
        @Override
        public boolean isResizable() {
            return true;
        }

        @Override
        public double prefWidth(double height) {
            return getWidth();
        }

        @Override
        public double prefHeight(double width) {
            return getHeight();
        }
	}
	
}
