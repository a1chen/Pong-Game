import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongComp extends GameDriverV3 implements KeyListener {

	// Different states
	static final int STATE_SPLASH_SCREEN = 0;
	static final int STATE_TWO_PLAYER_START = 1;
	static final int STATE_TWO_PLAYER_SCORED = 2;
	static final int STATE_TWO_PLAYER_WON = 3;
	static final int STATE_SINGLE_PLAYER_START = 4;
	static final int STATE_SINGLE_PLAYER_SERVE = 5;
	static final int STATE_SINGLE_PLAYER_WON = 6;
	static final int STATE_CHOOSE_SPEED = 7;

	// Score to win
	static final int POINTS_TO_WIN = 3;

	// initializing variables
	Paddle left, right;
	Rectangle background = new Rectangle(0, 0, 6000, 4000);
	int gameState = STATE_SPLASH_SCREEN, speedLevel = 1, scoreRight = 0, scoreLeft = 0, numHitLeft = 0, numHitRight = 0;
	Ball ball;
	Font impactSmall = new Font("Impact", 100, 100);
	Font impactSmaller = new Font("Impact", 25, 25);

	Color cBlack = Color.BLACK;
	Color cRed = Color.RED;

	boolean hasStartedGame = false; 

	// Starts the game creating paddles and balls
	public void start() {
		if (!hasStartedGame) { // prevent from player needing to keep choosing speed
			left = new Paddle(5, this.getHeight() / 2, KeyEvent.VK_W, KeyEvent.VK_S);
			right = new Paddle(1785, this.getHeight() / 2, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
			ball = new Ball(left, right, speedLevel);
			this.addKeyListener(left);
			this.addKeyListener(right);
			
			//Change screen to choosing states
			gameState = STATE_CHOOSE_SPEED;
		} else {
			//Serves the ball
			ball = new Ball(left, right, speedLevel);
			gameState = STATE_TWO_PLAYER_START;
		}
	}
	
	// restarts the score for computer mode
	public void compRestart() { 
		scoreLeft = 0;
		scoreRight = 0;
		computerStart();
	}
	
	// restarts the score for two player mode
	public void restart() { 
		scoreLeft = 0;
		scoreRight = 0;
		start();
	}
	
	// Single player mode
	public void computerStart() { 
		if (!hasStartedGame) {
			left = new Paddle(5, this.getHeight() / 2, KeyEvent.VK_W, KeyEvent.VK_S);
			right = new Paddle(1785, this.getHeight() / 2, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
			ball = new Ball(left, right, speedLevel);
			this.addKeyListener(right);
			gameState = STATE_SINGLE_PLAYER_START;
			hasStartedGame = true;
		} else {
			ball = new Ball(left, right, speedLevel);
			gameState = STATE_SINGLE_PLAYER_START;
		}

	}

	//Score counter
	public void CountScore(Ball ball) {
		// Counts score if the ball goes past the left side
		if (ball.getX() < 0) { 
			//Single player
			if (gameState == STATE_SINGLE_PLAYER_START) {
				scoreRight += 1;
				gameState = STATE_SINGLE_PLAYER_SERVE;
			//Two player
			} else {
				scoreRight += 1;
				gameState = STATE_TWO_PLAYER_SCORED;
			}
		}
		//Counts score if the ball goes past the right side
		if (ball.getX() + ball.width > 1830) { 
			//Single player
			if (gameState == STATE_SINGLE_PLAYER_START) {
				scoreLeft += 1;
				gameState = STATE_SINGLE_PLAYER_SERVE;
				
			//Two player
			} else {
				scoreLeft += 1;
				gameState = STATE_TWO_PLAYER_SCORED;
			}
		}
		//Ends game if score reaches POINTS_TO_WIN
		if (scoreRight == POINTS_TO_WIN || scoreLeft == POINTS_TO_WIN) { 
			//Change to won state
			if (gameState == STATE_SINGLE_PLAYER_SERVE) { 
				gameState = STATE_SINGLE_PLAYER_WON;
			} else {
				gameState = STATE_TWO_PLAYER_WON;
			}
		}

	}

	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		// Splash Screen
		if (gameState == STATE_SPLASH_SCREEN) { 
			//Title
			win.setColor(Color.BLACK);
			win.fill(background);
			win.setFont(impactSmall);
			win.setColor(cRed);
			win.drawString("PONG", 750, 100);
			win.setColor(Color.WHITE);
			win.drawString("By Aaron Chen", 550, 250);
			win.setFont(impactSmall);
			win.setColor(Color.RED);
			
			//Instructions
			win.drawString("LEFT PLAYER", 100, 400);
			win.drawString("W = UP", 100, 600);
			win.drawString("S = DOWN", 100, 800);
			win.drawString("RIGHT PLAYER", 1200, 400);
			win.drawString("UP ARROW = UP", 1150, 600);
			win.drawString("DOWN ARROW = DOWN", 900, 800);
			
			//Gamemodes
			win.setFont(impactSmaller);
			win.setColor(Color.WHITE);
			win.drawString("Press ENTER for two player mode", 100, 50);
			win.drawString("Press \"T\" for single player mode", 100, 100);
			win.drawString("To perform a smash ball, ", 100, 150);
			win.drawString("hit the ball in the opposite direction", 100, 200);
			this.addKeyListener(this);
		}
		
		//Game start in single player mode
		if (gameState == STATE_TWO_PLAYER_START) {
			//Draw lines
			win.setColor(Color.BLACK);
			win.fill(background);
			win.setColor(Color.WHITE);
			win.drawLine(915, 0, 915, 900);
			win.drawOval(665, 200, 500, 500);

			//Draw paddles and ball
			left.moveAndDraw(win);
			right.moveAndDraw(win);
			ball.moveAndDraw(win);
			
			//Keep score
			CountScore(ball);
			win.setFont(impactSmall);
			win.drawString("Score" + " " + scoreLeft + " - " + scoreRight, 675, 100);

		}
		
		//State if player scores in two player mode
		if (gameState == STATE_TWO_PLAYER_SCORED) { 
			win.setFont(impactSmall);
			win.setColor(Color.WHITE);
			win.drawString("Press ENTER to Serve", 600, 300);
			this.addKeyListener(this);

		}
		
		//State if a player wins in two player mode
		if (gameState == STATE_TWO_PLAYER_WON) { 
			//Left player won
			if (scoreLeft == POINTS_TO_WIN) {
				win.setFont(impactSmall);
				win.setColor(Color.WHITE);
				win.drawString("Left player is the winner!", 350, 450);
				win.drawString(" Press \"i\" to restart", 550, 550);
				this.addKeyListener(this);
			}
			
			//Right player won
			if (scoreRight == POINTS_TO_WIN) {
				win.setFont(impactSmall);
				win.setColor(Color.WHITE);
				win.drawString("Right player is the winner!", 350, 450);
				win.drawString("Press \"i\" to restart", 550, 550);
				this.addKeyListener(this);
			}
		}
		
		//State to start single player mode
		if (gameState == STATE_SINGLE_PLAYER_START) { 
			//Draw background
			win.setColor(Color.BLACK);
			win.fill(background);
			win.drawLine(915, 0, 915, 900);
			win.drawOval(665, 200, 500, 500);
			
			//Draw paddles and ball
			left.computer(win, ball, left);
			right.moveAndDraw(win);
			ball.moveAndDraw(win);
			
			//Keep score
			CountScore(ball);
			win.setFont(impactSmall);
			win.drawString("Score" + " " + scoreLeft + " - " + scoreRight, 675, 100);
			win.setColor(Color.WHITE);
		}
		
		//State to serve ball in single player mode
		if (gameState == STATE_SINGLE_PLAYER_SERVE) { 
			win.setFont(impactSmall);
			win.setColor(Color.WHITE);
			win.drawString("Press \"T\" to serve", 550, 450);
			this.addKeyListener(this);
		}
		//State to restart game in single player mode
		if (gameState == STATE_SINGLE_PLAYER_WON) {
			//Left player has won
			if (scoreLeft == POINTS_TO_WIN) {
				win.setFont(impactSmall);
				win.setColor(Color.WHITE);
				win.drawString("Left player is the winner!", 350, 450);
				win.drawString(" Press \"g\" to restart", 550, 550);
				this.addKeyListener(this);
			}
			
			//Right player has won
			if (scoreRight == POINTS_TO_WIN) {
				win.setFont(impactSmall);
				win.setColor(Color.WHITE);
				win.drawString("Right player is the winner!", 350, 450);
				win.drawString("Press \"g\" to restart", 550, 550);
				this.addKeyListener(this);
			}
		}
		//State to choose speed of ball
		if (gameState == STATE_CHOOSE_SPEED) { 
			hasStartedGame = true;
			
			//Draw background
			win.setColor(Color.BLACK);
			win.fill(background);
			win.setColor(Color.RED);
			win.setFont(impactSmall);
			win.drawString("SPEED LEVEL", 650, 100);
			
			//Speed options
			win.setColor(Color.WHITE);
			win.drawString("Press 1 for Normal Speed", 400, 300);
			win.drawString("Press 2 for Fast Speed", 450, 500);
			win.drawString("Press 3 for Super Speed", 425, 700);
			this.addKeyListener(this);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//Starts in two player mode
		if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
			start();
		}
		//Resets score
		if (e.getKeyCode() == KeyEvent.VK_I) { 
			restart();
		}
		//Single player mode
		if (e.getKeyCode() == KeyEvent.VK_T) { 
			computerStart();
		}
		//Single player mode restart
		if (e.getKeyCode() == KeyEvent.VK_G) { 
			compRestart();
		}
		//Speed level 1
		if (e.getKeyCode() == KeyEvent.VK_1) {
			speedLevel = 1;
			gameState = STATE_TWO_PLAYER_START;
		}
		//Speed level 2
		if (e.getKeyCode() == KeyEvent.VK_2) { 
			speedLevel = 2;
			gameState = STATE_TWO_PLAYER_START;
		}
		//Speed level 3
		if (e.getKeyCode() == KeyEvent.VK_3) {
			speedLevel = 3;
			gameState = STATE_TWO_PLAYER_START;
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
