import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongComp extends GameDriverV3 implements KeyListener{
	
	//initializing variables 
	Paddle left, right;
	Rectangle background = new Rectangle ( 0,0, 6000, 4000);
	int gameState = 0, speedLevel = 1;
	int scoreRight = 0, scoreLeft = 0;
	int numHitLeft = 0, numHitRight = 0;
	Ball ball;
	powerUp powerUp1;
	Font f1 = new Font("ComicSansMS", 100,100);
	Font f2 = new Font("SansSerif Bold", 200, 200);
	Font f3 = new Font("ComicSansMS", 100, 100);
	Font f4 = new Font("ComicSansMS", 50,50);
	Font f5 = new Font("ComicSansMS", 25,25);

	Color c1 = Color.BLACK;
	Color c2 = Color.RED;
	
	boolean hasStartedGame = false; // prevent from player needing to keep choosing speed
	
	//starts the game
	public void start() {
		if (hasStartedGame == false) { // prevent from player needing to keep choosing speed
			
			left = new Paddle(5, this.getHeight() / 2, KeyEvent.VK_W, KeyEvent.VK_S);
			right = new Paddle(3760, this.getHeight() / 2, KeyEvent.VK_UP, KeyEvent.VK_DOWN);		
			ball = new Ball(left, right, speedLevel);
			powerUp1 = new powerUp (ball, left, right, numHitLeft, numHitRight);
			this.addKeyListener(left);
			this.addKeyListener(right);
			//gameState = 1;
			gameState = 7;
			}
		else {
			left = new Paddle(5, this.getHeight() / 2, KeyEvent.VK_W, KeyEvent.VK_S);
			right = new Paddle(3760, this.getHeight() / 2, KeyEvent.VK_UP, KeyEvent.VK_DOWN);		
			ball = new Ball(left, right, speedLevel);
			powerUp1 = new powerUp (ball, left, right, numHitLeft, numHitRight);
			this.addKeyListener(left);
			this.addKeyListener(right);
			gameState = 1;
		}
	}
	
	public void compRestart() { //restarts the score for computer mode
		scoreLeft = 0;
		scoreRight = 0;
		test();
	}
	
	public void restart() { // restarts the score for two player mode
		scoreLeft = 0;
		scoreRight = 0;
		start();
	}
	
	public void test() { //computer mode
		left = new Paddle(5, this.getHeight() / 2, KeyEvent.VK_W, KeyEvent.VK_S);
		right = new Paddle(3760, this.getHeight() / 2, KeyEvent.VK_UP, KeyEvent.VK_DOWN);		
		ball = new Ball(left, right, speedLevel);
		this.addKeyListener(right);
		gameState = 4;
	}
	
	//score counter
	public void CountScore(Ball x){
	
		if (x.getX() < 0) { //counts score if the ball goes past the left side
			if (gameState == 4) {
			scoreRight += 1;
			gameState = 5;
			}
			else {
				scoreRight +=1;
				gameState = 2;
			}
		}
		
		if (x.getX() > 3770 ) { //counts score if the ball goes past the right side
			if (gameState == 4) {
			scoreLeft += 1;
			gameState = 5;
			}
			else {
				scoreLeft += 1;
				gameState = 2;
			}
		}
		
		
		if(scoreRight == 3 || scoreLeft == 3) { //ends game if score reaches 3
			if (gameState == 5) { //restarts in computer mode
				gameState = 6;
			}
			else {
			gameState = 3; //restarts in two player mode
			}
		}
		
	}
	
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		if (gameState == 0) { //splash screen
			win.setColor(Color.BLACK);
			win.fill(background);
			win.setFont(f2);
			win.setColor(c2);
			win.drawString("PONG", 1650, 400);
			win.setFont(f3);
			win.setColor(Color.WHITE);
			win.drawString("By Aaron Chen", 1600, 500);
			win.setFont(f2);
			win.setColor(Color.RED);
			
			win.drawString("LEFT" , 100, 1300);
			win.drawString("W = UP",100 , 1600);
			win.drawString("S = DOWN",100 , 1800);
			win.drawString("RIGHT", 3150, 1250);
			win.drawString("UP ARROW = UP", 2700, 1550);
			win.drawString("DOWN ARROW = DOWN", 2350, 1750);

			win.setFont(f1);
			win.setColor(Color.WHITE);
			win.drawString("Press ENTER for two player mode", 1200, 1000);
			win.drawString("Press \"T\" for single player mode", 1200, 800);
			win.drawString("To perform a smash ball, ", 1380, 1200);
			win.drawString("hit the ball in the opposite direction", 1200, 1300);
			this.addKeyListener(this);
		}
		
		
		if (gameState == 1) {//game start in single player mode
		win.setColor(Color.BLACK);
		win.fill(background);
		win.setColor(Color.WHITE);
		win.drawLine(1900, 0, 1900, 3500);
		win.drawOval(1650, 775, 500, 500);
		
		//draw them
		left.moveAndDraw(win);
		right.moveAndDraw(win);
		ball.moveAndDraw(win);
		
		
		CountScore(ball);
		win.setFont(f1);
		win.drawString("Score" + " " +scoreLeft + " - "+ scoreRight , 1600, 400);
		
		}
		
		if(gameState == 2) { //state if player scores in two player mode
			
			win.setFont(f1);
			win.setColor(Color.WHITE);
			win.drawString("Press ENTER to serve", 1400, 1000);
			this.addKeyListener(this);
			
		}
		
		if(gameState == 3) { //state if player wins in two player mode
			
			if (scoreLeft == 3) {
				win.setFont(f1);
				win.setColor(Color.WHITE);
				win.drawString("Left player is the winner!"+" Press \"i\" to restart", 1100, 1000);
				this.addKeyListener(this);
			}
			
			if (scoreRight == 3) {
				win.setFont(f1);
				win.setColor(Color.WHITE);

				win.drawString("Right player is the winner! Press \"i\" to restart", 1000, 1000);
				this.addKeyListener(this);
			}
		}
		
		if (gameState == 4) { //state to start single player mode
		
			win.setColor(Color.BLACK);
			win.fill(background);
			//draw them
			left.computer(win,ball,left);
			right.moveAndDraw(win);
			ball.moveAndDraw(win);
			CountScore(ball);
			win.setFont(f1);
			win.drawString("Score" + " " +scoreLeft + " - "+ scoreRight , 1600, 400);
			
		}
		
		if (gameState == 5) { //state to serve ball in single player mode
			win.setFont(f1);
			win.setColor(Color.WHITE);
			win.drawString("Press \"T\" to serve", 1400, 1000);
			this.addKeyListener(this);
		}
		if (gameState == 6) { //state to restart game in single player mode
			if (scoreLeft == 3) {
				win.setFont(f1);
				win.setColor(Color.WHITE);
				win.drawString("Left player is the winner!"+" Press \"g \"to restart", 1100, 1000);
				this.addKeyListener(this);
			}
			
			if (scoreRight == 3) {
				win.setFont(f1);
				win.setColor(Color.WHITE);
				win.drawString("Right player is the winner! Press \"g\" to restart", 1000, 1000);
				this.addKeyListener(this);
		
			}
		
		}
		if (gameState == 7) { //state to choose speed of ball
			hasStartedGame = true;

			win.setColor(Color.BLACK);
			win.fill(background);
			
			win.setColor(Color.RED);
			win.setFont(f2);
			win.drawString("SPEED LEVEL", 1300, 300);
			
			win.setColor(Color.WHITE);
			win.setFont(f1);
			win.drawString("Press 1 for Normal Speed", 1400, 700);
			win.drawString("Press 2 for Fast Speed", 1450, 850);
			win.drawString("Press 3 for Super Speed", 1400, 1000);
			win.setFont(f4);
			win.drawString("If ball does not serve press \"ENTER\" again", 1450, 1500);
			win.setFont(f5);
			win.drawString("(Dont ask me why)", 1800, 1600);
			this.addKeyListener(this);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER) { //starts in two player mode
			start();
					
			
		}
		
		if (e.getKeyCode() == KeyEvent.VK_I) { //resets score
			restart();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_T) { //single player mode
			test();
		}
		if (e.getKeyCode() == KeyEvent.VK_G) { //single player mode restart
			compRestart();
		}
		if (e.getKeyCode() == KeyEvent.VK_1) {//speed level 1
			speedLevel = 1;
			gameState = 1;
		}
		if (e.getKeyCode() == KeyEvent.VK_2) { //speed level 2
			speedLevel = 2;
			gameState = 1;
		}
		if (e.getKeyCode() == KeyEvent.VK_3) { //speed level 3
			speedLevel = 3;
			gameState = 1;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}
