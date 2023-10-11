import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Table extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Initializing variables
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int RACKET_WIDTH = 25;
	static final int RACKET_HEIGHT = 100;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Rackets racket1;
	Rackets racket2;
	Ball ball;
	Score score;
	
	Table(){
		newRacket();
		newBall();
		score = new Score(GAME_WIDTH,GAME_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	//New ball created 
	public void newBall() {
		random = new Random();
		ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
	}
	//New rackets created
	public void newRacket() {
		racket1 = new Rackets(0,(GAME_HEIGHT/2)-(RACKET_HEIGHT/2),RACKET_WIDTH,RACKET_HEIGHT,1);
		racket2 = new Rackets(GAME_WIDTH-RACKET_WIDTH,(GAME_HEIGHT/2)-(RACKET_HEIGHT/2),RACKET_WIDTH,RACKET_HEIGHT,2);
	}
	
	//Items can be painted on the screen
	public void paint(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}
	//Rackets, balls and score can be drawn
	public void draw(Graphics g) {
		racket1.draw(g);
		racket2.draw(g);
		ball.draw(g);
		score.draw(g);
        

	}
	//Rackets and ball move after each iteration
	public void move() {
		racket1.move();
		racket2.move();
		ball.move();
	}
	//Check if the ball hits the rackets
	public void checkCollision() {
		
		//bounce ball off top & bottom window edges
		if(ball.y <=0) {
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
			ball.setYDirection(-ball.yVelocity);
		}
		//bounce ball off rackets
		if(ball.intersects(racket1)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; //Ball speed increases
			if(ball.yVelocity>0)
				ball.yVelocity++; //Ball speed increases
			else
				ball.yVelocity--;
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		if(ball.intersects(racket2)) {
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++; //Ball speed increases
			if(ball.yVelocity>0)
				ball.yVelocity++; //Ball speed increases
			else
				ball.yVelocity--;
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
		//stops rackets at window edges
		if(racket1.y<=0)
			racket1.y=0;
		if(racket1.y >= (GAME_HEIGHT-RACKET_HEIGHT))
			racket1.y = GAME_HEIGHT-RACKET_HEIGHT;
		if(racket2.y<=0)
			racket2.y=0;
		if(racket2.y >= (GAME_HEIGHT-RACKET_HEIGHT))
		    racket2.y = GAME_HEIGHT-RACKET_HEIGHT;
		//give a player 1 point and creates new rackets & ball
		if(ball.x <=0) {
			score.player2++;
			newRacket();
			newBall();
			System.out.println("Player 2: "+score.player2);
		}
		if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			score.player1++;
			newRacket();
			newBall();
			System.out.println("Player 1: "+score.player1);
		}
	}
	public void run() {
		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks =60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now -lastTime)/ns;
			lastTime = now;
			if(delta >=1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			racket1.keyPressed(e);
			racket2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			racket1.keyReleased(e);
			racket2.keyReleased(e);
		}
	}
