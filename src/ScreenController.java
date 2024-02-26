import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
/**
 * The ScreenController class provides utility methods for creating UI elements and loading resources.
 * It also extends the SceneBase class to implement the getScene method.
 */
public class ScreenController extends SceneBase{

	protected int FRAME_WIDTH_VALUE = (int) (400*SCALE);
	protected int FRAME_HEIGHT_VALUE = (int) (300*SCALE);

	@Override
	public Scene getScene(SceneManager sceneManager) {
		return null;
	}

	/**
	 * Creates a VBox container with the specified text properties.
	 *
	 * @param textSize the size of the text
	 * @param texts    the texts to display in the VBox
	 * @return the created VBox container
	 */
	public VBox createVboxTexts(int textSize, String... texts){

		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);

		for (String text : texts) {
			Text myText = new Text(text);
			myText.setFont(Font.font("Arial",textSize));
			myText.setFill(Color.ORANGE);
			vBox.getChildren().add(myText);
		}
		return vBox;
	}
	/**
	 * Loads the file names from the specified folder path and returns them as an ArrayList.
	 *
	 * @param folderPath the path of the folder containing the files
	 * @return the list of file names
	 */
	public ArrayList<String> loadFilesFromFolder(String folderPath) {
		ArrayList<String> fileNames = new ArrayList<>();
		for(int i=0;i<8;i++){
			fileNames.add(("assets/"+folderPath+"/"+(i+1)+".png"));
		}
		return fileNames;
	}

	/**
	 * Retrieves the AudioClip for the specified music name.
	 *
	 * @param musicName the name of the music file
	 * @param isRepeat  determines whether the music should be repeated
	 * @return the AudioClip for the music
	 */
	public AudioClip getMusic(String musicName,boolean isRepeat){
		AudioClip audioClip = new AudioClip(getClass().getResource("assets/effects/"+musicName+".mp3").toString());
		if (isRepeat) {
			audioClip.setCycleCount(AudioClip.INDEFINITE);
		}
		audioClip.setVolume(VOLUME);
		return audioClip;


	}
	/**
	 * Retrieves the Background with the specified image path.
	 *
	 * @param imagePath the path of the background image
	 * @return the Background with the image
	 */
	public Background getBackgroundImage (String imagePath){
		BackgroundImage newBackgroundImage = new BackgroundImage(new Image(imagePath),
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				null,
				new BackgroundSize(FRAME_WIDTH_VALUE, FRAME_HEIGHT_VALUE, false, false, false, false));
		return new Background(newBackgroundImage);
	}
}
