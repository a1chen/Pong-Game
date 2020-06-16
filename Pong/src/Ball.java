import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Ball extends Rectangle {
	// Smash ball speed increase
	static final int SMASH_BALL_SPEED_INCREASE_X = 15;
	static final int SMASH_BALL_SPEED_INCREASE_y = 25;

	double dx, dy, speed;
	Color c2 = Color.BLACK;
	boolean didBallCollide = false;
	static Random ranNum = new Random();
	Paddle left, right;
	boolean hitLeftPaddle = false, hitRightPaddle = false;

	public Ball(Paddle paddleLeft, Paddle paddleRight, int speed) {

		super(905, ranNum.nextInt(225), 20, 20);
		//Speed 1
		if (speed == 1) {
			//Serve in random direction
			if (ranNum.nextInt(2) == 0) {
				dy = 10;
				dx = 10;
			}
			if (ranNum.nextInt(2) == 1) {
				dy = 10;
				dx = -10;
			}
		}
		
		//Speed 2
		if (speed == 2) {
			//Serve in random direction
			if (ranNum.nextInt(2) == 0) {
				dy = 15;
				dx = 15;
			}

			if (ranNum.nextInt(2) == 1) {
				dy = 15;
				dx = -15;
			}
		}
		
		//Speed 3
		if (speed == 3) {
			//Serve in random direction
			if (ranNum.nextInt(2) == 0) {
				dy = 20;
				dx = 20;
			}
			if (ranNum.nextInt(2) == 1) {
				dy = 20;
				dx = -20;
			}
		}
		c2 = Color.WHITE;
		left = paddleLeft;
		right = paddleRight;
	}

	//Check if ball hits the paddle
	public boolean BallCollides(Paddle paddle) {
		boolean result = false;
		if (this.intersects(paddle)) {
			result = true;
		}
		return result;
	}

	public void moveAndDraw(Graphics2D win) {
		// Draw ball
		win.setColor(c2);
		win.fill(this);

		// Hitting edges
		if ((this.getY() + 40 + dy > 900) || (this.getY() + dy < 0)) {
			dy = -dy;
		}
		
		//Move the ball
		this.translate((int) dx, (int) dy);

		// Hitting paddles
		if (BallCollides(left)) {
			dx = -dx;
			hitLeftPaddle = true;
		}
		if (BallCollides(right)) {
			dx = -dx;
			hitRightPaddle = true;
		}

		// Smash Ball speed increase
		if (BallCollides(left)) {
			if (this.dy < 0 && left.dy > 0) {
				dx += SMASH_BALL_SPEED_INCREASE_X;
				dy += SMASH_BALL_SPEED_INCREASE_y;
			} else if (this.dy > 0 && left.dy < 0) {
				dx += SMASH_BALL_SPEED_INCREASE_X;
				dy -= SMASH_BALL_SPEED_INCREASE_y;
			}
		}
		if (BallCollides(right)) {
			if (this.dy < 0 && right.dy > 0) {
				dx -= SMASH_BALL_SPEED_INCREASE_X;
				dy += SMASH_BALL_SPEED_INCREASE_y;

			} else if (this.dy > 0 && right.dy < 0) {
				dx -= SMASH_BALL_SPEED_INCREASE_X;
				dy -= SMASH_BALL_SPEED_INCREASE_y;
			}

		}
	}
}
