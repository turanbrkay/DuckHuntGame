import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
/**
 * The StraightPath class represents a straight path for a duck in the game.
 * The duck moves horizontally in a straight line, changing its Y direction periodically.
 */

public class StraightPath extends GameController {
	private double speedX;
	private double speedY;
	private double positionX;
	private double positionY;
	private double newPositionX;
	private double newPositionY;
	private Timeline changeFrameTimeline;
	private Timeline changePositionTimeline;
	private int counter;

	/**
	 * Creates a StraightPath object with the specified parameters.
	 *
	 * @param duck          the duck to follow the straight path
	 * @param startLocation the starting location of the duck
	 * @param direction     the direction of the path (1 for right, 2 for left)
	 * @param speedXAxis    the speed of the duck on the X-axis
	 * @param speedYAxis    the speed of the duck on the Y-axis
	 */
	public StraightPath(Duck duck, int[] startLocation,int direction,int speedXAxis,int speedYAxis){
		this.speedX = speedXAxis;
		this.speedY = speedYAxis;
		duck.getDuckImageView().setTranslateX(startLocation[0]);
		duck.getDuckImageView().setTranslateY(startLocation[1]);

		changeFrameTimeline = new Timeline();
		changePositionTimeline = new Timeline();

		if(direction == 1){
			speedX = speedX; // X-axis speed
			speedY = +speedY; // Y-axis speed
		}
		else {
			duck.getDuckImageView().setScaleX(-1);
			speedX = -speedX; // X-axis speed
			speedY = +speedY; // Y-axis speed
		}


		positionX = duck.getDuckImageView().getTranslateX();
		positionY = duck.getDuckImageView().getTranslateY();

		changeFrameTimeline = new Timeline(
				new KeyFrame(Duration.millis(200), e -> {
					currentFrameIndex = (currentFrameIndex + 1) % (duck.getDuckFrames().size() - 2);
					duck.getDuckImageView().setImage(duck.getDuckFrames().get(currentFrameIndex));})
		);

		changePositionTimeline = new Timeline(
				new KeyFrame(Duration.millis(200), e -> {
					// Calculate the new position
					newPositionX = positionX + speedX;
					newPositionY = positionY + speedY;
					// Check collision with screen edges
					if (newPositionX < 0 || newPositionX + duckImageWidth > FRAME_WIDTH_VALUE) {
						duck.getDuckImageView().setScaleX(-duck.getDuckImageView().getScaleX());
						speedX = -speedX;
					}
					if(counter % 3 == 1){
						speedY = -speedY;
					}
					else if(counter % 3 == 2){
						speedY = +speedY;
					}
					counter++;


					// Update the position
					positionX += speedX;
					positionY += speedY;


					duck.getDuckImageView().setTranslateX(positionX);
					duck.getDuckImageView().setTranslateY(positionY);

					duck.setPositionX(duck.getDuckImageView().getTranslateX()+(duckImageWidth/2));
					duck.setPositionY(duck.getDuckImageView().getTranslateY()+(duckImageHeight/2));
				}));

		changeFrameTimeline.setCycleCount(Timeline.INDEFINITE);
		changePositionTimeline.setCycleCount(Timeline.INDEFINITE);

	}
	/**
	 * Starts the timelines for frame change and position update.
	 */
	protected void startTimeline(){
		changeFrameTimeline.play();
		changePositionTimeline.play();
	}
	/**
	 * Stops the timelines for frame change and position update.
	 */
	protected void stopTimeline(){
		changeFrameTimeline.stop();
		changePositionTimeline.stop();
	}


}
