import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
/**
 *
 * The TitleScreen class represents the screen displayed at the start of the game.
 * It allows the user to open the theme page by pressing Enter or exit the game by pressing Esc.
 */
public class TitleScreen extends ScreenController {


	@Override
	public Scene getScene(SceneManager sceneManager) {
		setSceneManager(sceneManager);

		Pane loginPane = new Pane();
		VBox vBox = createVboxTexts(FRAME_WIDTH_VALUE/20,"PRESS ENTER TO START","PRESS ESC TO EXIT");


		Timeline flashTextTimeline = new Timeline();
		flashTextTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.8), evt -> vBox.setVisible(false)));
		flashTextTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds( 1), evt -> vBox.setVisible(true)));
		flashTextTimeline.setCycleCount(Animation.INDEFINITE);
		AudioClip titleMusic = getMusic("Title",true);
		titleMusic.play();

		flashTextTimeline.playFromStart();

		loginPane.getChildren().add(vBox);

		loginPane.setBackground(getBackgroundImage("assets/favicon/1.png"));

		Scene scene = new Scene(loginPane,FRAME_WIDTH_VALUE,FRAME_HEIGHT_VALUE);

		vBox.widthProperty().addListener(e -> vBox.setLayoutX((FRAME_WIDTH_VALUE/2)-(vBox.getWidth()/2)));
		vBox.heightProperty().addListener(e -> vBox.setLayoutY((3*FRAME_HEIGHT_VALUE/4)-(3*vBox.getHeight()/4)));

		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				sceneManager.showScene(new ThemeSelectionScreen(titleMusic));
			}
			else if (event.getCode() == KeyCode.ESCAPE) {
				System.exit(0);
			}
		});

		return scene;

	}

}