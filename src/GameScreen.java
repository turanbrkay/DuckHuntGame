
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents the game screen.
 */
public class GameScreen extends ScreenController{
	protected static ImageView foregroundImage;
	protected static Background backgroundImage;
	protected static ImageView crosshairImageView;
	protected static Image crosshairImage;

	public GameScreen(){

	}

	/**
	 * Constructs a new GameScreen with the specified background image, crosshair image, and foreground image.
	 *
	 * @param backgroundImage the background image
	 * @param crosshairImage   the crosshair image
	 * @param foregroundImage  the foreground image
	 */
	public GameScreen(Background backgroundImage, Image crosshairImage,ImageView foregroundImage){
		this.backgroundImage = backgroundImage;
		this.crosshairImage = crosshairImage;
		this.foregroundImage = foregroundImage;
		this.crosshairImageView = new ImageView(crosshairImage);
		foregroundImage.setFitHeight(FRAME_HEIGHT_VALUE);
		foregroundImage.setFitWidth(FRAME_WIDTH_VALUE);
		crosshairImageView.setFitWidth((FRAME_WIDTH_VALUE/28));
		crosshairImageView.setFitHeight((FRAME_WIDTH_VALUE/28));
		crosshairImageView.setX((FRAME_WIDTH_VALUE/2)-(crosshairImageView.getFitWidth()/2));
		crosshairImageView.setY((FRAME_HEIGHT_VALUE/2)-(crosshairImageView.getFitHeight()/2));
	}


	/**
	 * Returns the scene for the game screen.
	 *
	 * @param sceneManager the scene manager
	 * @return the scene for the game screen
	 */
	@Override
	public Scene getScene(SceneManager sceneManager) {
		setSceneManager(sceneManager);
		Pane gamePane = new Pane();
		gamePane.setPrefSize(FRAME_WIDTH_VALUE, FRAME_HEIGHT_VALUE);
		gamePane.setBackground(backgroundImage);

		Rectangle border = new Rectangle(FRAME_WIDTH_VALUE, FRAME_HEIGHT_VALUE);
		border.setStroke(Color.RED);
		border.setStrokeWidth(5);
		border.setFill(null);


		Scene scene = new Scene(gamePane, FRAME_WIDTH_VALUE, FRAME_HEIGHT_VALUE);

		scene.setCursor(Cursor.NONE);
		gamePane.setCursor(Cursor.NONE);

		GameController duckGame = new GameController(scene,gamePane);


		scene.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
			crosshairImageView.setX(event.getX());
			crosshairImageView.setY(event.getY());
		});



		return scene;
	}

}
