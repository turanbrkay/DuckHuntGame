import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.ArrayList;

/**
 * The ThemeSelectionScreen class represents the screen for selecting themes in the game.
 * It allows the user to navigate between different backgrounds and crosshairs using arrow keys,
 * start the game by pressing Enter, and return the title page by pressing Esc.
 */
public class ThemeSelectionScreen extends ScreenController {
	private int currentBackgroundIndex = 0;
	private int currentCrosshairIndex = 0;
	AudioClip titleMusic;

	/**
	 * Creates a ThemeSelectionScreen object with the specified title music.
	 *
	 * @param titleMusic the audio clip for the title music
	 */
	public ThemeSelectionScreen(AudioClip titleMusic) {
		this.titleMusic = titleMusic;

	}

	@Override
	public Scene getScene(SceneManager sceneManager) {
		setSceneManager(sceneManager);
		Pane pane = new Pane();

		VBox vBox = createVboxTexts(FRAME_WIDTH_VALUE/35,"USE ARROW KEYS TO NAVIGATE","PRESS ENTER TO START","PRESS ESC TO EXIT");
		pane.getChildren().add(vBox);
		ArrayList<String> crosshairs = loadFilesFromFolder("crosshair");
		ArrayList<String> backgrounds = loadFilesFromFolder("background");
		ArrayList<String> foregrounds = loadFilesFromFolder("foreground");

		pane.setBackground(getBackgroundImage(backgrounds.get(currentBackgroundIndex)));

		ImageView crosshairImageView = new ImageView(new Image(crosshairs.get(currentCrosshairIndex)));
		crosshairImageView.setPreserveRatio(true);
		crosshairImageView.setFitWidth((FRAME_WIDTH_VALUE/20));
		crosshairImageView.setFitHeight((FRAME_HEIGHT_VALUE/20));
		pane.getChildren().add(crosshairImageView);

		Scene scene = new Scene(pane,FRAME_WIDTH_VALUE,FRAME_HEIGHT_VALUE);

		crosshairImageView.setX((FRAME_WIDTH_VALUE/2)-(crosshairImageView.getFitWidth()/2));
		crosshairImageView.setY((FRAME_HEIGHT_VALUE/2)-(crosshairImageView.getFitHeight()/2));

		vBox.widthProperty().addListener(e -> vBox.setLayoutX((FRAME_WIDTH_VALUE/2)-(vBox.getWidth()/2)));
		vBox.heightProperty().addListener(e -> vBox.setLayoutY((vBox.getHeight()/4)));


		scene.setOnKeyPressed(event -> {
			KeyCode keyCode = event.getCode();
			if (keyCode == KeyCode.LEFT) {
				currentBackgroundIndex = (currentBackgroundIndex == 0) ? (5) : currentBackgroundIndex-1;
			} else if (keyCode == KeyCode.RIGHT) {
				currentBackgroundIndex = (currentBackgroundIndex == 5) ? 0 : (currentBackgroundIndex+1);
			}else if (keyCode == KeyCode.UP) {
				currentCrosshairIndex = (currentCrosshairIndex == 6) ? 0 : (currentCrosshairIndex+1);
			}else if (keyCode == KeyCode.DOWN) {
				currentCrosshairIndex = (currentCrosshairIndex == 0) ? (6) : currentCrosshairIndex-1;
			} else if (event.getCode() == KeyCode.ENTER) {
				scene.addEventFilter(KeyEvent.KEY_PRESSED, KeyEvent::consume); // enter tuşuna basmayı devre dışı bıraktım
				titleMusic.stop();

				Media sound = new Media(getClass().getClassLoader().getResource("assets/effects/Intro.mp3").toExternalForm());
				MediaPlayer introMusic = new MediaPlayer(sound);
				introMusic.setVolume(VOLUME);
				introMusic.play();
				introMusic.setOnEndOfMedia(() -> {
					sceneManager.showScene(new GameScreen(pane.getBackground(),(new Image(crosshairs.get(currentCrosshairIndex))),new ImageView(new Image(foregrounds.get(currentBackgroundIndex)))));
					scene.removeEventFilter(KeyEvent.KEY_PRESSED, KeyEvent::consume);
				});
			}
			else if (event.getCode() == KeyCode.ESCAPE) {
				sceneManager.goBack();}

			crosshairImageView.setImage(new Image(crosshairs.get(currentCrosshairIndex)));
			pane.setBackground(getBackgroundImage(backgrounds.get(currentBackgroundIndex)));

		});




		return scene;

	}

}
