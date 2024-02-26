import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
/**
 * Represents Level 6 of the game.
 */
public class Level6 extends GameController {
	boolean isBlueDuckLive = true;
	boolean isBlackDuckLive = true;
	boolean isRedDuckLive = true;

	public Level6(){
		createGameArea();
		startGame();
	}

	/**
	 * Creates the game area for Level 6.
	 */
	public void createGameArea(){
		createPaneTexts(gamePane,ammoText,levelText,ammoVbox,levelVbox);

		gamePane.getChildren().add(blueDuck.getDuckImageView());
		gamePane.getChildren().add(blackDuck.getDuckImageView());
		gamePane.getChildren().add(redDuck.getDuckImageView());

		gamePane.getChildren().add(foregroundImage);
		gamePane.getChildren().add(crosshairImageView);

	}
	/**
	 * Starts Level 6.
	 */
	public void startGame() {

		ReflectorPath redDuckFlyAnimation = new ReflectorPath(redDuck,new int[]{(duckImageWidth), FRAME_WIDTH_VALUE/8},-1,FRAME_WIDTH_VALUE/60,FRAME_HEIGHT_VALUE/90);
		redDuckFlyAnimation.startTimeline();

		ReflectorPath blackDuckFlyAnimation = new ReflectorPath(blackDuck,new int[]{(duckImageWidth), FRAME_WIDTH_VALUE/6},1,FRAME_WIDTH_VALUE/60,FRAME_HEIGHT_VALUE/90);
		blackDuckFlyAnimation.startTimeline();

		ReflectorPath blueDuckFlyAnimation = new ReflectorPath(blueDuck,new int[]{FRAME_WIDTH_VALUE-(duckImageWidth),FRAME_HEIGHT_VALUE/5},1,FRAME_WIDTH_VALUE/60,FRAME_HEIGHT_VALUE/90);
		blueDuckFlyAnimation.startTimeline();


		sceneClickHandler = event -> {
			if(event.getButton() == MouseButton.PRIMARY){
				gunshotMusic.play();

			double blackDuckImageCenterX = blackDuck.getDuckImageView().getBoundsInParent().getMinX() + blackDuck.getDuckImageView().getBoundsInParent().getWidth() / 2;
			double blackDuckImageCenterY = blackDuck.getDuckImageView().getBoundsInParent().getMinY() + blackDuck.getDuckImageView().getBoundsInParent().getHeight() / 2;

			double blueDuckImageCenterX = blueDuck.getDuckImageView().getBoundsInParent().getMinX() + blueDuck.getDuckImageView().getBoundsInParent().getWidth() / 2;
			double blueDuckImageCenterY = blueDuck.getDuckImageView().getBoundsInParent().getMinY() + blueDuck.getDuckImageView().getBoundsInParent().getHeight() / 2;

			double redDuckImageCenterX = redDuck.getDuckImageView().getBoundsInParent().getMinX() + redDuck.getDuckImageView().getBoundsInParent().getWidth() / 2;
			double redDuckImageCenterY = redDuck.getDuckImageView().getBoundsInParent().getMinY() + redDuck.getDuckImageView().getBoundsInParent().getHeight() / 2;

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
			if((redDuckImageCenterX-(duckImageWidth/2) < event.getSceneX() && redDuckImageCenterX+(duckImageWidth/2) > event.getSceneX()) &&
					(redDuckImageCenterY-(duckImageHeight/2) < event.getSceneY() && redDuckImageCenterY+(duckImageHeight/2) > event.getSceneY()) && ammo>0 && isRedDuckLive == true){
				isRedDuckLive = false;
				duckFallMusic.play();
				redDuckFlyAnimation.stopTimeline();

				Timeline redDuckShotAnimation = shotAnimation(redDuck);
			}
			if(isBlackDuckLive == false && isBlueDuckLive == false && isRedDuckLive == false){
				getDefaultCursor();
				scene.setOnMouseClicked(null);
				gameCompletedMusic.play();
				Timeline nextLevelAnimationText = getAnimationText("You have completed the game!","Press ENTER to play again","Press ESC to exit");
				nextLevelAnimationText.play();
				level=1;
				scene.setOnKeyPressed(e -> {
					if (e.getCode() == KeyCode.ENTER) {
						stopMusics();
						nextLevelAnimationText.stop();
						blueDuckFlyAnimation.stopTimeline();
						blackDuckFlyAnimation.stopTimeline();
						redDuckFlyAnimation.stopTimeline();
						gamePane.getChildren().clear();
						gamePane.getChildren().clear();
						sceneManager.showScene(new GameScreen());
					} else if (e.getCode() == KeyCode.ESCAPE) {
						stopMusics();
						nextLevelAnimationText.stop();
						blueDuckFlyAnimation.stopTimeline();
						blackDuckFlyAnimation.stopTimeline();
						redDuckFlyAnimation.stopTimeline();
						gamePane.getChildren().clear();
						sceneManager.showScene(new TitleScreen());
					}

				});
			}

			if(ammo>0){
				ammo--;
				ammoText.setText("Ammo Left: " + ammo);
			}
			if (ammo == 0) {
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
						redDuckFlyAnimation.stopTimeline();
						gameOverAnimationText.stop();
						sceneManager.showScene(new GameScreen()); // yeni bir gameScreen başlatılır
					}
					else if (e.getCode() == KeyCode.ESCAPE) {
						stopMusics();
						gameOverAnimationText.stop();
						blueDuckFlyAnimation.stopTimeline();
						blackDuckFlyAnimation.stopTimeline();
						redDuckFlyAnimation.stopTimeline();
						gamePane.getChildren().clear();
						sceneManager.showScene(new TitleScreen());
					}
				});
			};


		}};

		scene.setOnMouseClicked(sceneClickHandler);
	}
}


