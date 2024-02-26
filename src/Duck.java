import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

/**
 * The Duck class represents a duck in the game.
 */
public class Duck extends ScreenController{
	public double positionX;
	public double positionY;
	public ArrayList<Image> duckFrames;
	public ImageView duckImageView;

	/**
	 * Constructs a Duck object with the specified duck type.
	 *
	 * @param duckType the type of the duck
	 */
	public Duck(String duckType){
		loadDuckImages(duckType);
		duckImageView.setFitWidth(FRAME_WIDTH_VALUE/10);
		duckImageView.setFitHeight(FRAME_HEIGHT_VALUE/10);
	}
	/**
	 * Loads the duck images from the assets folder according to color.
	 *
	 * @param duckType the type of the duck
	 */
	public void loadDuckImages(String duckType){
		this.duckFrames = loadImagesFromFolder("assets/"+duckType);
		this.duckImageView = new ImageView(duckFrames.get(0));
	}

	/**
	 * Loads the each images path from the assets.
	 *
	 * @param folderPath the path of the folder containing the images
	 * @return an ArrayList of loaded images
	 */
	public ArrayList<Image> loadImagesFromFolder(String folderPath) {
		ArrayList<Image> fileNames = new ArrayList<>();
		for(int i=0;i<8;i++){
			fileNames.add(new Image(folderPath+"/"+(i+1)+".png"));
		}
		return fileNames;
	}

	/**
	 * Returns the ImageView of the duck.
	 *
	 * @return the ImageView of the duck
	 */
	public ImageView getDuckImageView() {
		return duckImageView;
	}

	/**
	 * Sets the X position of the duck on the scene.
	 *
	 * @param positionX the X position of the duck
	 */
	public void setPositionX(double positionX) {
		this.positionX = positionX;
	}

	/**
	 * Sets the Y position of the duck on the scene.
	 *
	 * @param positionY the Y position of the duck
	 */
	public void setPositionY(double positionY) {
		this.positionY =  positionY;
	}

	/**
	 * Returns the duck frames with pictures of ducks.
	 *
	 * @return an ArrayList of duck frames.
	 */
	public ArrayList<Image> getDuckFrames() {
		return duckFrames;
	}
}
