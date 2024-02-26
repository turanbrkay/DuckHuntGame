import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
/**
 * Represents Level 2 of the game.
 */
public class Level2 extends GameController {
	public Level2(){
		createGameArea();
		startGame();
	}

	/**
	 * Creates the game area for Level 2.
	 */
	public void createGameArea(){
		createPaneTexts(gamePane,ammoText,levelText,ammoVbox,levelVbox);

		gamePane.getChildren().add(blueDuck.getDuckImageView());

		gamePane.getChildren().add(foregroundImage);
		gamePane.getChildren().add(crosshairImageView);

	}
	/**
	 * Starts Level 2.
	 */
	public void startGame() {

		ReflectorPath blueDuckFlyAnimation = new ReflectorPath(blueDuck,new int[]{FRAME_WIDTH_VALUE-duckImageWidth, FRAME_WIDTH_VALUE/4},1,FRAME_WIDTH_VALUE/70,FRAME_HEIGHT_VALUE/90);
		blueDuckFlyAnimation.startTimeline();

		sceneClickHandler = event -> {

			gunshotMusic.play();

			if (event.getButton() == MouseButton.PRIMARY) {

				if (ammo > 0) {
					ammo--;
					ammoText.setText("Ammo Left: " + ammo);
				}
				double blueDuckImageCenterX = blueDuck.getDuckImageView().getBoundsInParent().getMinX() + blueDuck.getDuckImageView().getBoundsInParent().getWidth() / 2;
				double blueDuckImageCenterY = blueDuck.getDuckImageView().getBoundsInParent().getMinY() + blueDuck.getDuckImageView().getBoundsInParent().getHeight() / 2;

				if (((blueDuckImageCenterX - (duckImageWidth / 2)) < event.getSceneX() && (blueDuckImageCenterX + (duckImageWidth / 2)) > event.getSceneX()) &&
						(blueDuckImageCenterY - (duckImageHeight / 2) < event.getSceneY() && blueDuckImageCenterY + (duckImageHeight / 2) > event.getSceneY())) {
					getDefaultCursor();
					scene.setOnMouseClicked(null);


					duckFallMusic.play();
					levelCompletedMusic.play();

					blueDuckFlyAnimation.stopTimeline();

					Timeline blackDuckShotAnimation = shotAnimation(blueDuck);

					Timeline nextLevelAnimationText = getAnimationText("YOU WIN!", "Press ENTER to next level");
					nextLevelAnimationText.play();

					scene.setOnKeyPressed(e -> {
						if (e.getCode() == KeyCode.ENTER) {
							stopMusics();
							nextLevelAnimationText.stop();
							blackDuckShotAnimation.stop();
							level++;
							gamePane.getChildren().clear();
							sceneManager.showScene(new GameScreen());
						}
					});
				} else if (ammo == 0) {
					getDefaultCursor();
					scene.setOnMouseClicked(null);
					gameOverMusic.play();

					Timeline gameOverAnimationText = getAnimationText("GAME OVER!", "Press ENTER to play again", "Press ESC to exit");
					gameOverAnimationText.play();
					level = 1;
					scene.setOnKeyPressed(e -> {
						if (e.getCode() == KeyCode.ENTER) {
							stopMusics();
							blueDuckFlyAnimation.stopTimeline();
							gamePane.getChildren().clear();
							sceneManager.showScene(new GameScreen());
						} else if (e.getCode() == KeyCode.ESCAPE) {
							stopMusics();
							gameOverAnimationText.stop();
							sceneManager.showScene(new TitleScreen());
						}
					});
				}

			}};

			scene.setOnMouseClicked(sceneClickHandler);

	}
}
