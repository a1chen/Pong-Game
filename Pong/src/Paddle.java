import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Paddle extends Rectangle implements KeyListener {
	//Height of paddle
	static final int PADDLE_HEIGHT = 200;
	static final int FRAME_HEIGHT = 900;
	
	double dy, speed = 25;
	Color cRed;
	boolean isUpPressed, isDownPressed;
	int keyUp, keyDown;

	public Paddle(int x, int y, int akeyUp, int akeyDown) {

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

		this.translate(0, (int) dy);

		// draw
		win.setColor(cRed);
		win.fill(this);
	}

	public void computer(Graphics2D win, Ball ball, Paddle paddle) {
		dy = 0;
		if (ball.getY() > paddle.getY()) {
			dy = speed;
		}
		if (ball.getY() < paddle.getY()) {
			dy = -speed;
		}
		if (this.getY() + 200 + dy > 900 || this.getY() + dy < 0) {
			dy = 0;
		}

		this.translate(0, (int) dy);

		// draw
		win.setColor(cRed);
		win.fill(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		if (e.getKeyCode() == this.keyUp) {
			this.isUpPressed = true;
		} else if (e.getKeyCode() == this.keyDown) {
			this.isDownPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
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
