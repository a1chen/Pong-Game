import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

public class powerUp extends Rectangle {
	boolean powerUpOn = false;
	Random ran = new Random();
	
	
	public powerUp(Ball ball, Paddle left, Paddle right, int numHitLeft, int numHitRight) {
		
	}
	
	public void shrinkPaddle(Ball ball,Paddle left, Paddle right, int numHitLeft, int numHitRight,Graphics2D win) {
		//Rectangle rect1 = new Rectangle(1000,1000,500,500);
		if (powerUpOn == false) {
			//win.setColor(Color.WHITE);
			//win.fill(rect1);
			//if (ball.intersects(rect1)) {
				//leftTemp.moveAndDraw(win);
				
				
			//}
		}
			
		
	}
}
