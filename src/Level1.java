import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
/**
 * Represents Level 1 of the game.
 */
public class Level1 extends GameController {

	 public Level1(){
		 createGameArea();
		 startGame();
	 }


	/**
	 * Creates the game area for Level 1.
	 */
	public void createGameArea(){
		 createPaneTexts(gamePane,ammoText,levelText,ammoVbox,levelVbox);

		gamePane.getChildren().add(blackDuck.getDuckImageView());

		gamePane.getChildren().add(foregroundImage);
		gamePane.getChildren().add(crosshairImageView);

	}

	/**
	 * Starts Level 1.
	 */
	public void startGame() {
		StraightPath blackDuckFlyAnimation = new StraightPath(blackDuck,
				new int[]{duckImageWidth/2,FRAME_HEIGHT_VALUE/8},
				1,
				FRAME_WIDTH_VALUE/20,
				FRAME_HEIGHT_VALUE/90);
		blackDuckFlyAnimation.startTimeline();

		sceneClickHandler = event -> {
			if(event.getButton() == MouseButton.PRIMARY){
				gunshotMusic.play();

				if(ammo>0){
					ammo--;
					ammoText.setText("Ammo Left: " + ammo);
				}

				double imageViewCenterX = blackDuck.getDuckImageView().getBoundsInParent().getMinX() + blackDuck.getDuckImageView().getBoundsInParent().getWidth() / 2;
				double imageViewCenterY = blackDuck.getDuckImageView().getBoundsInParent().getMinY() + blackDuck.getDuckImageView().getBoundsInParent().getHeight() / 2;

				if(((imageViewCenterX-(duckImageWidth/2)) < event.getSceneX() && (imageViewCenterX+(duckImageWidth/2)) > event.getSceneX()) &&
						((imageViewCenterY - (duckImageHeight/2)) < event.getSceneY() && (imageViewCenterY+(duckImageHeight/2)) > event.getSceneY())){

					getDefaultCursor();
					scene.setOnMouseClicked(null);


					duckFallMusic.play();

					levelCompletedMusic.play();

					blackDuckFlyAnimation.stopTimeline();

					Timeline blackDuckShotAnimation = shotAnimation(blackDuck);


					Timeline nextLevelAnimationText = getAnimationText("YOU WIN!","Press ENTER to next level");
					nextLevelAnimationText.play();

					scene.setOnKeyPressed(e -> {
						if (e.getCode() == KeyCode.ENTER) {
							stopMusics();
							level++;
							gamePane.getChildren().clear();
							sceneManager.showScene(new GameScreen());
						}
					});
				}else if (ammo == 0) {

					getDefaultCursor();
					scene.setOnMouseClicked(null);

					gameOverMusic.play();

					Timeline gameOverAnimationText = getAnimationText("GAME OVER!","Press ENTER to play again","Press ESC to exit");
					gameOverAnimationText.play();
					level=1;
					scene.setOnKeyPressed(e -> {
						if (e.getCode() == KeyCode.ENTER) {
							stopMusics();
							blackDuckFlyAnimation.startTimeline();
							gameOverAnimationText.stop();
							sceneManager.showScene(new GameScreen()); // yeni bir gameScreen başlatılır
						}
						else if (e.getCode() == KeyCode.ESCAPE) {
							stopMusics();
							gameOverAnimationText.stop();
							blackDuckFlyAnimation.startTimeline();
							scene.setOnMouseClicked(sceneClickHandler);
							gamePane.getChildren().clear();
							sceneManager.showScene(new TitleScreen());
						}
					});
				}


		}};

		scene.setOnMouseClicked(sceneClickHandler);

	}
}
