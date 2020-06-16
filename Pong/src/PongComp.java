import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongComp extends GameDriverV3 implements KeyListener{
	
	//Constants
	//Different states
	static final int STATE_TWO_PLAYER_START = 1;
	static final int STATE_TWO_PLAYER_SCORED = 2;
	static final int STATE_TWO_PLAYER_WON = 3;
	static final int STATE_SINGLE_PLAYER_START = 4;
	static final int STATE_SINGLE_PLAYER_SERVE = 5;
	static final int STATE_SINGLE_PLAYER_WON = 6;
	static final int STATE_CHOOSE_SPEED = 7;
	
	//Score to win
	static final int POINTS_TO_WIN = 3;
	
	
	//initializing variables 
	Paddle left, right;
	Rectangle background = new Rectangle ( 0,0, 6000, 4000);
	int gameState = 0, speedLevel = 1;
	int scoreRight = 0, scoreLeft = 0;
	int numHitLeft = 0, numHitRight = 0;
	Ball ball;
	Font impactSmall = new Font("Impact", 100,100);
	Font impactSmaller = new Font("Impact", 25, 25);

	Color cBlack = Color.BLACK;
	Color cRed = Color.RED;
	
	boolean hasStartedGame = false; // prevent from player needing to keep choosing speed
	
	//starts the game
	public void start() {
		if (hasStartedGame == false) { // prevent from player needing to keep choosing speed
			
			left = new Paddle(5, this.getHeight() / 2, KeyEvent.VK_W, KeyEvent.VK_S);
			right = new Paddle(1785, this.getHeight() / 2, KeyEvent.VK_UP, KeyEvent.VK_DOWN);		
			ball = new Ball(left, right, speedLevel);
			powerUp1 = new powerUp (ball, left, right, numHitLeft, numHitRight);
			this.addKeyListener(left);
			this.addKeyListener(right);
			gameState = STATE_CHOOSE_SPEED;
			
		} else {
			left = new Paddle(5, this.getHeight() / 2, KeyEvent.VK_W, KeyEvent.VK_S);
			right = new Paddle(1785, this.getHeight() / 2, KeyEvent.VK_UP, KeyEvent.VK_DOWN);		
			ball = new Ball(left, right, speedLevel);
			powerUp1 = new powerUp (ball, left, right, numHitLeft, numHitRight);
			this.addKeyListener(left);
			this.addKeyListener(right);
			gameState = STATE_TWO_PLAYER_START;
		}
	}
	
	public void compRestart() { //restarts the score for computer mode
		scoreLeft = 0;
		scoreRight = 0;
		computerStart();
	}
	
	public void restart() { // restarts the score for two player mode
		scoreLeft = 0;
		scoreRight = 0;
		start();
	}
	
	public void computerStart() { //computer mode
		left = new Paddle(5, this.getHeight() / 2, KeyEvent.VK_W, KeyEvent.VK_S);
		right = new Paddle(1785, this.getHeight() / 2, KeyEvent.VK_UP, KeyEvent.VK_DOWN);		
		ball = new Ball(left, right, speedLevel);
		this.addKeyListener(right);
		gameState = STATE_SINGLE_PLAYER_START;
	}
	
	//score counter
	public void CountScore(Ball ball){
	
		if (ball.getX() < 0) { //counts score if the ball goes past the left side
			if (gameState == STATE_SINGLE_PLAYER_START) {
				scoreRight += 1;
				gameState = STATE_SINGLE_PLAYER_SERVE;
			}
			else {
				scoreRight +=1;
				gameState = STATE_TWO_PLAYER_SCORED;
			}
		}
		
		if (ball.getX() + ball.width > 1830 ) { //counts score if the ball goes past the right side
			if (gameState == STATE_SINGLE_PLAYER_START) {
				scoreLeft += 1;
				gameState = STATE_SINGLE_PLAYER_SERVE;
			}
			else {
				scoreLeft += 1;
				gameState = STATE_TWO_PLAYER_SCORED;
			}
		}
		
		
		if(scoreRight == POINTS_TO_WIN || scoreLeft == POINTS_TO_WIN) { //ends game if score reaches 3
			if (gameState == STATE_SINGLE_PLAYER_SERVE) { //restarts in computer mode
				gameState = STATE_SINGLE_PLAYER_WON;
			}
			else {
				gameState = STATE_TWO_PLAYER_WON; //restarts in two player mode
			}
		}
		
	}
	
	public void draw(Graphics2D win) {
		// TODO Auto-generated method stub
		if (gameState == 0) { //splash screen
			win.setColor(Color.BLACK);
			win.fill(background);
			win.setFont(impactSmall);
			win.setColor(cRed);
			win.drawString("PONG", 750, 100);
			win.setColor(Color.WHITE);
			win.drawString("By Aaron Chen", 550, 250);
			win.setFont(impactSmall);
			win.setColor(Color.RED);
			
			win.drawString("LEFT PLAYER" , 100, 400);
			win.drawString("W = UP",100 , 600);
			win.drawString("S = DOWN",100 , 800);
			win.drawString("RIGHT PLAYER", 1200, 400);
			win.drawString("UP ARROW = UP", 1150, 600);
			win.drawString("DOWN ARROW = DOWN", 900, 800);

			win.setFont(impactSmaller);
			win.setColor(Color.WHITE);
			win.drawString("Press ENTER for two player mode", 100, 50);
			win.drawString("Press \"T\" for single player mode", 100, 100);
			win.drawString("To perform a smash ball, ", 100, 150);
			win.drawString("hit the ball in the opposite direction", 100, 200);
			this.addKeyListener(this);
		}
		
		
		if (gameState == STATE_TWO_PLAYER_START) {//game start in single player mode
			win.setColor(Color.BLACK);
			win.fill(background);
			win.setColor(Color.WHITE);
			win.drawLine(915, 0, 915, 900);
			win.drawOval(665, 200, 500, 500);
			
			//draw them
			left.moveAndDraw(win);
			right.moveAndDraw(win);
			ball.moveAndDraw(win);
			
			
			CountScore(ball);
			win.setFont(impactSmall);
			win.drawString("Score" + " " +scoreLeft + " - "+ scoreRight , 675, 100);
		
		}
		
		if(gameState == STATE_TWO_PLAYER_SCORED) { //state if player scores in two player mode
			
			win.setFont(impactSmall);
			win.setColor(Color.WHITE);
			win.drawString("Press ENTER to Serve", 600, 300);
			this.addKeyListener(this);
			
		}
		
		if(gameState == STATE_TWO_PLAYER_WON) { //state if player wins in two player mode
			
			if (scoreLeft == POINTS_TO_WIN) {
				win.setFont(impactSmall);
				win.setColor(Color.WHITE);
				win.drawString("Left player is the winner!", 350, 450);
				win.drawString(" Press \"i\" to restart", 550, 550);
				this.addKeyListener(this);
			}
			
			if (scoreRight == STATE_SINGLE_PLAYER_SERVE) {
				win.setFont(impactSmall);
				win.setColor(Color.WHITE);

				win.drawString("Right player is the winner!", 350, 450);
				win.drawString("Press \"i\" to restart", 550, 550);
				this.addKeyListener(this);
			}
		}
		
		if (gameState == STATE_SINGLE_PLAYER_START) { //state to start single player mode
		
			win.setColor(Color.BLACK);
			win.fill(background);
			//draw them
			left.computer(win,ball,left);
			right.moveAndDraw(win);
			ball.moveAndDraw(win);
			CountScore(ball);
			win.setFont(impactSmall);
			win.drawString("Score" + " " +scoreLeft + " - "+ scoreRight , 675, 100);
			win.setColor(Color.WHITE);
			win.drawLine(915, 0, 915, 900);
			win.drawOval(665, 200, 500, 500);
			
		}
		
		if (gameState == STATE_SINGLE_PLAYER_SERVE) { //state to serve ball in single player mode
			win.setFont(impactSmall);
			win.setColor(Color.WHITE);
			win.drawString("Press \"T\" to serve", 550, 450);
			this.addKeyListener(this);
		}
		if (gameState == STATE_SINGLE_PLAYER_WON) { //state to restart game in single player mode
			if (scoreLeft == POINTS_TO_WIN) {
				win.setFont(impactSmall);
				win.setColor(Color.WHITE);
				win.drawString("Left player is the winner!", 350, 450);
				win.drawString(" Press \"g\" to restart", 550, 550);
				this.addKeyListener(this);
			}
			
			if (scoreRight == POINTS_TO_WIN) {
				win.setFont(impactSmall);
				win.setColor(Color.WHITE);
				win.drawString("Right player is the winner!", 350, 450);
				win.drawString("Press \"g\" to restart", 550, 550);
				this.addKeyListener(this);
		
			}
		
		}
		if (gameState == STATE_CHOOSE_SPEED) { //state to choose speed of ball
			hasStartedGame = true;

			win.setColor(Color.BLACK);
			win.fill(background);
			
			win.setColor(Color.RED);
			win.setFont(impactSmall);
			win.drawString("SPEED LEVEL", 650, 100);
			
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
		if (e.getKeyCode() == KeyEvent.VK_ENTER) { //starts in two player mode
			start();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_I) { //resets score
			restart();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_T) { //single player mode
			computerStart();
		}
		if (e.getKeyCode() == KeyEvent.VK_G) { //single player mode restart
			compRestart();
		}
		if (e.getKeyCode() == KeyEvent.VK_1) {//speed level 1
			speedLevel = 1;
			gameState = STATE_TWO_PLAYER_START;
		}
		if (e.getKeyCode() == KeyEvent.VK_2) { //speed level 2
			speedLevel = 2;
			gameState = STATE_TWO_PLAYER_START;
		}
		if (e.getKeyCode() == KeyEvent.VK_3) { //speed level 3
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
