
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
/**
 * The SceneManager class manages the navigation between scenes in the application.
 */
public class SceneManager{

	private Stage primaryStage;
	private Scene currentScene;
	private Scene previousScene;

	/**
	 * Constructs a new SceneManager with the specified primary stage.
	 *
	 * @param primaryStage the primary stage of the application
	 */
	public SceneManager(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.getIcons().add(new Image(("assets/favicon/1.png")));

	}
	/**
	 * Shows the specified scene.
	 *
	 * @param scene the scene to show
	 */
	public void showScene(SceneBase scene) {
		previousScene = currentScene;
		currentScene = scene.getScene(this);
		primaryStage.setScene(currentScene);

	}
	/**
	 * Navigates back to the previous scene.
	 */
	public void goBack() {
		if (previousScene != null) {
			Scene myscene = currentScene;
			currentScene = previousScene;
			previousScene = myscene;
			primaryStage.setScene(currentScene);
		}
	}
}
