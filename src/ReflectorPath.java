import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
/**
 * Represents a reflector path for a duck in the game.
 */
public class ReflectorPath extends GameController {
	private double speedX;
	private double speedY;
	private double positionX;
	private double positionY;
	private double newPositionX;
	private double newPositionY;
	private Timeline changeFrameTimeline ;
	private Timeline changePositionTimeline;

	/**
	 * Constructs a new ReflectorPath object.
	 *
	 * @param duck        the duck object
	 * @param startLocation the starting location of the duck
	 * @param direction   the direction of the duck's movement
	 * @param speedXAxis  the speed on the X-axis
	 * @param speedYAxis  the speed on the Y-axis
	 */
	public ReflectorPath(Duck duck, int[] startLocation, int direction, int speedXAxis, int speedYAxis){
		this.speedX = speedXAxis;
		this.speedY = speedYAxis;
		duck.getDuckImageView().setTranslateX(startLocation[0]);
		duck.getDuckImageView().setTranslateY(startLocation[1]);

		changeFrameTimeline = new Timeline();
		changePositionTimeline = new Timeline();

		if(direction == 1){
			speedX = speedX; // X-axis speed
			speedY = -speedY; // Y-axis speed
		}else {
			duck.getDuckImageView().setRotate(90);
			speedX = speedX; // X-axis speed
			speedY = speedY;  // Y-axis speed
		}


		positionX = duck.getDuckImageView().getTranslateX();
		positionY = duck.getDuckImageView().getTranslateY();

		changeFrameTimeline = new Timeline(
				new KeyFrame(Duration.millis(200), e -> {
					currentFrameIndex = (currentFrameIndex + 1) % (duck.getDuckFrames().size() - 2);
					duck.getDuckImageView().setImage(duck.getDuckFrames().get(currentFrameIndex));})
		);

		changePositionTimeline = new Timeline(
				new KeyFrame(Duration.millis(60), e -> {
					// Calculate new position
					newPositionX = positionX + speedX;
					newPositionY = positionY + speedY;
					// Check collision with screen edges
					if (newPositionX < 0) {
						if (speedY > 0 ) {
							duck.getDuckImageView().setScaleX(-duck.getDuckImageView().getScaleX());
							duck.getDuckImageView().setRotate(duck.getDuckImageView().getRotate()-180);
						} else {
							duck.getDuckImageView().setScaleX(-duck.getDuckImageView().getScaleX());
						}
						speedX = -speedX;
					}
					if(newPositionX + duckImageWidth > FRAME_WIDTH_VALUE){
						if (speedY > 0 ) {
							duck.getDuckImageView().setScaleX(-duck.getDuckImageView().getScaleX());
							duck.getDuckImageView().setRotate(duck.getDuckImageView().getRotate()-180);
						} else {
							duck.getDuckImageView().setScaleX(-duck.getDuckImageView().getScaleX());
						}
						speedX = -speedX;
					}
					if(newPositionY + duckImageHeight > FRAME_HEIGHT_VALUE){
						if (speedX > 0) {
							duck.getDuckImageView().setRotate(duck.getDuckImageView().getRotate()-90); // Yukarı doğru hareket ederken rotasyonu 0 derece yap
						} else {
							duck.getDuckImageView().setScaleX(-duck.getDuckImageView().getScaleX());
							duck.getDuckImageView().setScaleY(-duck.getDuckImageView().getScaleY());
							duck.getDuckImageView().setRotate(duck.getDuckImageView().getRotate()-90);
						}
						speedY = -speedY;
					}
					if (newPositionY < 0 ) {
						if (speedX > 0) {
							duck.getDuckImageView().setRotate(duck.getDuckImageView().getRotate()+90); // Yukarı doğru hareket ederken rotasyonu 0 derece yap
						} else {
							duck.getDuckImageView().setRotate(duck.getDuckImageView().getRotate()-90);
						}

						// Change the Y-axis direction
						speedY = -speedY;
					}
					// Update the new position
					positionX += speedX;
					positionY += speedY;

					duck.setPositionX(duck.getDuckImageView().getTranslateX()+(duckImageWidth/2));
					duck.setPositionY(duck.getDuckImageView().getTranslateY()+(duckImageHeight/2));

					duck.getDuckImageView().setTranslateX(positionX);
					duck.getDuckImageView().setTranslateY(positionY);
				}));

		changeFrameTimeline.setCycleCount(Timeline.INDEFINITE);
		changePositionTimeline.setCycleCount(Timeline.INDEFINITE);

	}
	/**
	 * Starts the timeline for the reflector path animation.
	 */
	protected void startTimeline(){
		changeFrameTimeline.play();
		changePositionTimeline.play();
	}
	/**
	 * Stops the timeline for the reflector path animation.
	 */
	protected void stopTimeline(){
		changeFrameTimeline.stop();
		changePositionTimeline.stop();
	}
}
