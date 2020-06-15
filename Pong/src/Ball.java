import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Ball extends Rectangle{
	double dx, dy, speed;
	Color c2 = Color.BLACK;
	boolean didBallCollide = false;
	static Random ranNum = new Random();
	Paddle left, right;
	boolean hitLeftPaddle = false, hitRightPaddle = false;
	 
	public Ball(Paddle paddleLeft, Paddle paddleRight, int speed) {
		
		super(1900,ranNum.nextInt(2000), 50,50);
		if (speed == 1) {
			if(ranNum.nextInt(2) == 0) {
			dy = 30;
			dx = 30;
			}
		
			if(ranNum.nextInt(2) == 1) {
			dy = 30;
			dx = -30;
			}
		}
		
		if (speed == 2) {
			if(ranNum.nextInt(2) == 0) {
				dy = 40;
				dx = 40;
				}
			
				if(ranNum.nextInt(2) == 1) {
				dy = 40;
				dx = -40;
				}
		}
		
		if (speed == 3) {
			if(ranNum.nextInt(2) == 0) {
				dy = 45;
				dx = 45;
				}
			
				if(ranNum.nextInt(2) == 1) {
				dy = 45;
				dx = -45;
				}
		}
		c2 = Color.WHITE;
		left = paddleLeft;
		right = paddleRight;
	}
	
	public boolean BallCollides (Paddle p1) {
		boolean result = false;
		if (this.intersects(p1) ) {
			result = true;
		}
		return result;
	}
	
	public void moveAndDraw(Graphics2D win) {
		if((this.getY() + dy > 2000) || (this.getY() + dy < 0 )) {
			dy = -dy;
		}
		this.translate((int)dx, (int) dy);
		win.setColor(c2);
		win.fill(this);
		
		
		if (BallCollides(left) ) {
			dx = -dx;
			hitLeftPaddle = true;
			
			
		}
		
		if(BallCollides (right)) {
			dx = -dx;
			hitRightPaddle = true;
			
		}
		
		if(hitLeftPaddle == true && this.getX() > 1750 && this.dx >= 40 && this.dy <0) {
			dx = dx- (dx * .3);
			dy += -dy*.3;
			hitLeftPaddle = false;
			
		}
		
		if(hitLeftPaddle == true && this.getX() > 1750 && this.dx >= 40 && this.dy > 0 ) {
			dx -= dx * .3;
			dy -= dy *.3;
			hitLeftPaddle = false;
			

		}
		
		if (hitRightPaddle ==true && this.getX() < 1750 && this.dx <= -40 && this.dy <0) {
			dx += -dx * .3;
			dy += -dy*.3;
			hitRightPaddle = false;
			

		}
		
		if (hitRightPaddle ==true && this.getX() < 1750 && this.dx <= -40 && this.dy > 0) {
			dx += -dx *.3;
			dy -= dy*.3;
			hitRightPaddle = false;
			

		}
		
		if (BallCollides(left)) {//smashBall 
			if (this.dy < 0 && left.dy > 0  ) {
				
				dx += 30;
				dy += 60;	

			}
			else if(this.dy > 0 && left.dy < 0) {
				
				dx += 30;
				dy -= 60;

				
			}
		}
		
		if (BallCollides (right)) {
			if (this.dy < 0 && right.dy> 0) {
				
				dx -= 30;
				dy += 60;
				
			}
			else if (this.dy > 0 && right.dy < 0) {
				
				dx -= 30;
				dy -= 60;
				
				
			}
			
		}
	}
	
	
}
