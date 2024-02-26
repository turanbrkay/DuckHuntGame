import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
/**
 * Represents Level 4 of the game.
 */
public class Level4 extends GameController {
	boolean isBlueDuckLive = true;
	boolean isBlackDuckLive = true;

	public Level4(){
		createGameArea();
		startGame();
	}
	/**
	 * Creates the game area for Level 4.
	 */
	public void createGameArea(){
		createPaneTexts(gamePane,ammoText,levelText,ammoVbox,levelVbox);

		gamePane.getChildren().add(blueDuck.getDuckImageView());
		gamePane.getChildren().add(blackDuck.getDuckImageView());

		gamePane.getChildren().add(foregroundImage);
		gamePane.getChildren().add(crosshairImageView);

	}
	/**
	 * Starts Level 4.
	 */
	public void startGame() {
		ReflectorPath blueDuckFlyAnimation = new ReflectorPath(blueDuck,new int[]{duckImageWidth/2, FRAME_HEIGHT_VALUE/4},1,FRAME_WIDTH_VALUE/60,FRAME_HEIGHT_VALUE/90);
		blueDuckFlyAnimation.startTimeline();

		ReflectorPath blackDuckFlyAnimation = new ReflectorPath(blackDuck,new int[]{3*FRAME_WIDTH_VALUE/4, FRAME_WIDTH_VALUE/4},1,FRAME_WIDTH_VALUE/60,FRAME_HEIGHT_VALUE/90);
		blackDuckFlyAnimation.startTimeline();


		sceneClickHandler = event -> {
			if(event.getButton() == MouseButton.PRIMARY){

				gunshotMusic.play();

			double blackDuckImageCenterX = blackDuck.getDuckImageView().getBoundsInParent().getMinX() + blackDuck.getDuckImageView().getBoundsInParent().getWidth() / 2;
			double blackDuckImageCenterY = blackDuck.getDuckImageView().getBoundsInParent().getMinY() + blackDuck.getDuckImageView().getBoundsInParent().getHeight() / 2;

			double blueDuckImageCenterX = blueDuck.getDuckImageView().getBoundsInParent().getMinX() + blueDuck.getDuckImageView().getBoundsInParent().getWidth() / 2;
			double blueDuckImageCenterY = blueDuck.getDuckImageView().getBoundsInParent().getMinY() + blueDuck.getDuckImageView().getBoundsInParent().getHeight() / 2;


			if((blueDuckImageCenterX-(duckImageWidth/2) < event.getSceneX() && blueDuckImageCenterX+(duckImageWidth/2) > event.getSceneX()) &&
					(blueDuckImageCenterY-(duckImageHeight/2) < event.getSceneY() && blueDuckImageCenterY+(duckImageHeight/2) > event.getSceneY()) && ammo>0 && isBlueDuckLive == true){
				isBlueDuckLive = false;
				duckFallMusic.play();
				blueDuckFlyAnimation.stopTimeline();

				Timeline blackDuckShotAnimation = shotAnimation(blueDuck);
			}
			if((blackDuckImageCenterX-(duckImageWidth/2) < event.getSceneX() && blackDuckImageCenterX+(duckImageWidth/2) > event.getSceneX()) &&
					(blackDuckImageCenterY-(duckImageHeight/2) < event.getSceneY() && blackDuckImageCenterY+(duckImageHeight/2) > event.getSceneY()) && ammo>0 && isBlackDuckLive == true){
				isBlackDuckLive = false;
				duckFallMusic.play();
				blackDuckFlyAnimation.stopTimeline();

				Timeline blackDuckShotAnimation = shotAnimation(blackDuck);
			}
			if(ammo>0){
				ammo--;
				ammoText.setText("Ammo Left: " + ammo);
			}
			if(isBlackDuckLive == false && isBlueDuckLive == false){
				getDefaultCursor();
				scene.setOnMouseClicked(null);
				levelCompletedMusic.play();
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
			} else if (ammo == 0) {
				getDefaultCursor();
				scene.setOnMouseClicked(null);
				gameOverMusic.play();
				level=1;
				Timeline gameOverAnimationText = getAnimationText("GAME OVER!","Press ENTER to play again","Press ESC to exit");
				gameOverAnimationText.play();
				scene.setOnKeyPressed(e -> {
					if (e.getCode() == KeyCode.ENTER) {
						stopMusics();
						blueDuckFlyAnimation.stopTimeline();
						blackDuckFlyAnimation.stopTimeline();
						gameOverAnimationText.stop();
						sceneManager.showScene(new GameScreen()); // yeni bir gameScreen başlatılır
					}
					else if (e.getCode() == KeyCode.ESCAPE) {
						stopMusics();
						gameOverAnimationText.stop();
						blueDuckFlyAnimation.stopTimeline();
						blackDuckFlyAnimation.stopTimeline();
						gamePane.getChildren().clear();
						sceneManager.showScene(new TitleScreen());
					}
				});
			}


		}};

		scene.setOnMouseClicked(sceneClickHandler);
	}
}
