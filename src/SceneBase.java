
import javafx.scene.Scene;
/**
 * The base class for all scenes in the Duck Hunt game.
 */
public abstract class SceneBase extends DuckHunt {


	protected static SceneManager sceneManager;

	/**
	 * Retrieves the scene manager associated with the scene.
	 *
	 * @return the scene manager
	 */
	public SceneManager getSceneManager() {
		return sceneManager;
	}
	/**
	 * Sets the scene manager for the scene.
	 *
	 * @param sceneManager the scene manager to set
	 */
	public void setSceneManager(SceneManager sceneManager) {
		this.sceneManager = sceneManager;
	}
	/**
	 * Retrieves the JavaFX scene for the scene.
	 *
	 * @param sceneManager the scene manager associated with the scene
	 * @return the JavaFX scene
	 */
	public abstract Scene getScene(SceneManager sceneManager);


}
