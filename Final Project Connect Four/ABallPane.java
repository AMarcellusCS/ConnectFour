/**
 * @author Adrian Marcellus
 * @version 12/3/19
 * BALL ANIMATION for Final Project
 * Plays the ball animation for connect four.
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class ABallPane extends Pane {
	public final double radius = 40;
	private double y = 128;
	private double stopY;
	private double dy = 10;
	private Circle circle = new Circle(0, y, radius);
	private Timeline animation;

	public ABallPane(char turn, double x, double stop) {
		this.stopY = stop;
		if(turn == 'b') {
			circle.setFill(Color.RED);
		}
		else {
			circle.setFill(Color.BLACK);
		}
		circle.setStroke(Color.BLACK);
		circle.setCenterX(x);
		getChildren().add(circle); // Place a ball into this pane

		// Create an animation for moving the ball
		animation = new Timeline(
				new KeyFrame(Duration.millis(3), e -> moveBall()));
		animation.setRate(1);
		animation.setCycleCount(1000);
		animation.play(); // Start animation
	}

	public void play() {
		animation.play();
	}
	public void stop() {
		animation.stop();
	}
	protected void moveBall() {
		if(y > stopY + 20) {
			animation.stop();
			this.getChildren().removeAll();
		}
		else {
			y += dy;
			circle.setCenterY(y);
		}
	}
}
