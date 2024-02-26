import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
/**
 * Represents Level 3 of the game.
 */
public class Level3 extends GameController {

	boolean isBlueDuckLive = true;
    boolean isRedDuckLive = true;
	public Level3(){
		createGameArea();
		startGame();
	}
	/**
	 * Creates the game area for Level 3.
	 */
	public void createGameArea(){
		createPaneTexts(gamePane,ammoText,levelText,ammoVbox,levelVbox);

		gamePane.getChildren().add(blueDuck.getDuckImageView());
		gamePane.getChildren().add(redDuck.getDuckImageView());

		gamePane.getChildren().add(foregroundImage);
		gamePane.getChildren().add(crosshairImageView);

	}
	/**
	 * Starts Level 3.
	 */
	public void startGame() {

		StraightPath blueDuckFlyAnimation = new StraightPath(blueDuck,new int[]{duckImageWidth/2,FRAME_HEIGHT_VALUE/13},1,FRAME_WIDTH_VALUE/20,FRAME_HEIGHT_VALUE/90);
		blueDuckFlyAnimation.startTimeline();

		StraightPath redDuckFlyAnimation = new StraightPath(redDuck,new int[]{FRAME_WIDTH_VALUE-(duckImageWidth),FRAME_HEIGHT_VALUE/6},-1,FRAME_WIDTH_VALUE/20,FRAME_HEIGHT_VALUE/90);
		redDuckFlyAnimation.startTimeline();

		sceneClickHandler = event -> {
			if(event.getButton() == MouseButton.PRIMARY){
				gunshotMusic.play();

				double redDuckImageCenterX = redDuck.getDuckImageView().getBoundsInParent().getMinX() + redDuck.getDuckImageView().getBoundsInParent().getWidth() / 2;
				double redDuckImageCenterY = redDuck.getDuckImageView().getBoundsInParent().getMinY() + redDuck.getDuckImageView().getBoundsInParent().getHeight() / 2;

				double blueDuckImageCenterX = blueDuck.getDuckImageView().getBoundsInParent().getMinX() + blueDuck.getDuckImageView().getBoundsInParent().getWidth() / 2;
				double blueDuckImageCenterY = blueDuck.getDuckImageView().getBoundsInParent().getMinY() + blueDuck.getDuckImageView().getBoundsInParent().getHeight() / 2;

				if((blueDuckImageCenterX-(duckImageWidth/2) < event.getSceneX() && blueDuckImageCenterX+(duckImageWidth/2) > event.getSceneX()) &&
						(blueDuckImageCenterY-(duckImageHeight/2) < event.getSceneY() && blueDuckImageCenterY+(duckImageHeight/2) > event.getSceneY()) && ammo>0 && isBlueDuckLive == true){


					duckFallMusic.play();


					isBlueDuckLive = false;
					blueDuckFlyAnimation.stopTimeline();

					Timeline blueDuckShotAnimation = shotAnimation(blueDuck);
				}
				if((redDuckImageCenterX-(duckImageWidth/2) < event.getSceneX() && redDuckImageCenterX+(duckImageWidth/2) > event.getSceneX()) &&
						(redDuckImageCenterY-(duckImageHeight/2) < event.getSceneY() && redDuckImageCenterY+(duckImageHeight/2) > event.getSceneY()) && ammo>0 && isRedDuckLive == true){

					duckFallMusic.play();

					isRedDuckLive = false;
					redDuckFlyAnimation.stopTimeline();

					Timeline redDuckShotAnimation = shotAnimation(redDuck);
				}
				if(ammo>0){
					ammo--;
					ammoText.setText("Ammo Left: " + ammo);}

				if(isRedDuckLive == false && isBlueDuckLive == false){
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
				}else if (ammo == 0) {
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
							redDuckFlyAnimation.stopTimeline();
							gameOverAnimationText.stop();
							sceneManager.showScene(new GameScreen()); // yeni bir gameScreen başlatılır
						}
						else if (e.getCode() == KeyCode.ESCAPE) {
							stopMusics();
							gameOverAnimationText.stop();
							blueDuckFlyAnimation.stopTimeline();
							redDuckFlyAnimation.stopTimeline();
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
