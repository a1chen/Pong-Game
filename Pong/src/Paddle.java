import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Paddle extends Rectangle implements KeyListener {
	
	//Height of paddle
	static final int PADDLE_HEIGHT = 200;
	//Height of frame
	static final int FRAME_HEIGHT = 900;
	
	double dy, speed = 25;
	Color cRed;
	boolean isUpPressed, isDownPressed;
	int keyUp, keyDown;

	public Paddle(int x, int y, int akeyUp, int akeyDown) {
		//Create rectangle at position x, y with width 25 length 200
		super(x, y, 25, 200);
		dy = 0;
		cRed = Color.RED;
		isUpPressed = false;
		keyUp = akeyUp;
		keyDown = akeyDown;

	}

	public void moveAndDraw(Graphics2D win) {
		//Stationary
		dy = 0;
		
		//Move paddle up or down
		if (this.isDownPressed) {
			dy = speed;
		}
		if (this.isUpPressed) {
			dy = -speed;
		}
		
		//Prevent paddle from moving out of bounds
		if (this.getY() + dy + PADDLE_HEIGHT > FRAME_HEIGHT || this.getY() + dy < 0) {
			dy = 0;
		}
		
		//Moves paddles by dy up and down
		this.translate(0, (int) dy);

		//Draw the paddles
		win.setColor(cRed);
		win.fill(this);
	}

	//Method to move computer paddle in single player mode
	public void computer(Graphics2D win, Ball ball, Paddle paddle) {
		//Start stationary
		dy = 0;
		
		//Follow the ball
		if (ball.getY() > paddle.getY()) {
			dy = speed;
		}
		if (ball.getY() < paddle.getY()) {
			dy = -speed;
		}
		
		//Make sure paddle stays in bound
		if (this.getY() + PADDLE_HEIGHT + dy > FRAME_HEIGHT || this.getY() + dy < 0) {
			dy = 0;
		}
		this.translate(0, (int) dy);

		//Draw the paddle
		win.setColor(cRed);
		win.fill(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//If up is pressed go up
		//If down is pressed go down
		if (e.getKeyCode() == this.keyUp) {
			this.isUpPressed = true;
		} else if (e.getKeyCode() == this.keyDown) {
			this.isDownPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		//When arrows are released stop moving
		if (e.getKeyCode() == this.keyUp) {
			this.isUpPressed = false;
		} else if (e.getKeyCode() == this.keyDown) {
			this.isDownPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
