import javafx.application.Application;
import javafx.stage.Stage;

public class DuckHunt extends Application {
	private Stage primaryStage;
	private SceneManager sceneManager;
	protected static double SCALE = 2;
	protected static double VOLUME = 1;

	/**
	 * Starts the game by displaying the Title screen.
	 *
	 * @param primaryStage the primary stage for the application
	 * @throws Exception if an exception occurs during the start process
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		sceneManager = new SceneManager(primaryStage);
		sceneManager.showScene(new TitleScreen());
		primaryStage.setTitle("HUBBM Duck Hunt");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);}
}
