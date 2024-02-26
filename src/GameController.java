import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The GameController class manages the game logic and controls the gameplay.
 */
public class GameController extends GameScreen{
	protected static Scene scene;
	protected static Pane gamePane;
	protected static int level = 1;
	protected static int ammo;
	Duck blackDuck = new Duck("duck_black");
	Duck blueDuck = new Duck("duck_blue");
	Duck redDuck = new Duck("duck_red");
	int duckImageWidth = FRAME_WIDTH_VALUE/10;
	int duckImageHeight = FRAME_HEIGHT_VALUE/10;
	Text levelText = new Text("Level "+level+"/6");
	VBox levelVbox = new VBox(levelText);
	Text ammoText = new Text("Ammo Left: "+ ammo);
	VBox ammoVbox = new VBox(ammoText);
	int currentFrameIndex;
	AudioClip levelCompletedMusic = getMusic("LevelCompleted",false);
	AudioClip gameOverMusic = getMusic("GameOver",false);
	AudioClip duckFallMusic = getMusic("DuckFalls",false);
	AudioClip gunshotMusic = getMusic("Gunshot",false);
	AudioClip gameCompletedMusic = getMusic("GameCompleted",false);
	EventHandler<?  super MouseEvent> sceneClickHandler;
	public GameController() {
	}
	/**
	 * Constructor for GameController that initializes the scene and game pane.
	 *
	 * @param scene     the JavaFX scene
	 * @param gamePane  the JavaFX pane for the gameScene
	 */
	public GameController(Scene scene, Pane gamePane) {
		this.scene = scene;
		this.gamePane = gamePane;
		this.ammo = level==1 ? 3 : (level==2 ? 3 : (level==3 ? 6 : (level==4 ? 6 : (level==5 ? 9 : (level==6 ? 9 :0)))));
		startGame();

	}
	/**
	 * Stops all the game-related sound effects.
	 */
	public void stopMusics(){
		gunshotMusic.stop();
		duckFallMusic.stop();
		gameOverMusic.stop();
		levelCompletedMusic.stop();
		gameCompletedMusic.stop();
	}

	/**
	 * Sets the default cursor for the game pane.
	 */
	public void getDefaultCursor(){
		gamePane.getChildren().remove(crosshairImageView);
		gamePane.setCursor(Cursor.DEFAULT);
	}
	/**
	 * Starts the game based on the current level.
	 */
	protected void startGame(){
		switch (level){
			case 1:
				Level1 playLevel1 = new Level1();
				break;
			case 2:
				Level2 playLevel2 = new Level2();
				break;
			case 3:
				Level3 playLevel3 = new Level3();
				break;
			case 4:
				Level4 playLevel4 = new Level4();
				break;
			case 5:
				Level5 playLevel5 = new Level5();
				break;
			case 6:
				Level6 playLevel6 = new Level6();
				break;
		}
	}
	/**
	 * Creates the texts for ammo and level and adds them to the game pane.
	 *
	 * @param pane      the JavaFX pane
	 * @param ammoText  the JavaFX text for ammo
	 * @param levelText the JavaFX text for level
	 * @param vBoxAmmo  the JavaFX VBox for ammo
	 * @param vBoxLevel the JavaFX VBox for level
	 */
	public void createPaneTexts(Pane pane,Text ammoText,Text levelText,VBox vBoxAmmo,VBox vBoxLevel){
		ammoText.setFont(Font.font("Arial",FRAME_WIDTH_VALUE/40));
		ammoText.setFill(Color.ORANGE);

		levelText.setFont(Font.font("Arial",FRAME_WIDTH_VALUE/40));
		levelText.setFill(Color.ORANGE);

		pane.getChildren().add(vBoxAmmo);
		pane.getChildren().add(vBoxLevel);

		vBoxAmmo.widthProperty().addListener(e -> vBoxAmmo.setLayoutX((FRAME_WIDTH_VALUE)-((1.1*vBoxAmmo.getWidth()))));
		vBoxAmmo.heightProperty().addListener(e -> vBoxAmmo.setLayoutY(0.2*vBoxAmmo.getHeight()));

		vBoxLevel.widthProperty().addListener(e -> vBoxLevel.setLayoutX((FRAME_WIDTH_VALUE/2)-(vBoxLevel.getWidth()/2)));
		vBoxLevel.heightProperty().addListener(e -> vBoxLevel.setLayoutY(0.2*vBoxLevel.getHeight()));
	}


	/**
	 * Creates an animation for displaying flashing text.
	 *
	 * @param texts the texts to be displayed
	 * @return a timeline animation for the text
	 */
	public Timeline getAnimationText(String... texts){
		VBox vBox1= createVboxTexts(FRAME_WIDTH_VALUE/25,texts[0]);
		VBox vBox2;

		if(texts.length == 2){
			vBox2 = createVboxTexts(FRAME_WIDTH_VALUE/25,texts[1]);
		}
		else{
			vBox2 = createVboxTexts(FRAME_WIDTH_VALUE/25,texts[1],texts[2]);
		}
		VBox allTextVbox = new VBox();
		allTextVbox.getChildren().add(vBox1);
		allTextVbox.getChildren().add(vBox2);

		allTextVbox.widthProperty().addListener(e -> allTextVbox.setLayoutX((FRAME_WIDTH_VALUE/2)-(allTextVbox.getWidth()/2)));
		allTextVbox.heightProperty().addListener(e -> allTextVbox.setLayoutY((FRAME_HEIGHT_VALUE/2)-(allTextVbox.getHeight()/2)));

		gamePane.getChildren().add(allTextVbox);

		Timeline animationText = new Timeline();
		animationText.getKeyFrames().add(new KeyFrame(Duration.seconds(0.8), evt -> vBox2.setVisible(false)));
		animationText.getKeyFrames().add(new KeyFrame(Duration.seconds( 1), evt -> vBox2.setVisible(true)));
		animationText.setCycleCount(Animation.INDEFINITE);

		return animationText;
	}

	/**
	 * Creates an animation for shooting a duck.
	 *
	 * @param duck the duck to be animated
	 * @return a timeline animation for the shot
	 */
	public Timeline shotAnimation(Duck duck){
		duck.getDuckImageView().setRotate(0);
		duck.getDuckImageView().setScaleX(1);
		duck.getDuckImageView().setScaleY(1);

		duck.getDuckImageView().setImage(duck.getDuckFrames().get(6));

		Timeline timeline = new Timeline();

		KeyFrame fallFrame = new KeyFrame(Duration.millis(200), e -> {
			duck.getDuckImageView().setImage(duck.getDuckFrames().get(7));
			duck.getDuckImageView().setY(duck.getDuckImageView().getY()+(FRAME_HEIGHT_VALUE/20));
			duck.setPositionY(duck.getDuckImageView().getY());
			});

		KeyFrame control = new KeyFrame(Duration.millis(10), e -> {
			if(duck.getDuckImageView().getY()>=(FRAME_HEIGHT_VALUE+duckImageHeight)){
				timeline.stop();
			}});

		timeline.getKeyFrames().add(control);
		timeline.getKeyFrames().add(fallFrame);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		return  timeline;
	}

}
