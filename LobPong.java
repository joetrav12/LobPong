import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.*;
import javax.sound.sampled.*;

public class LobPong extends JPanel implements ActionListener, KeyListener, LineListener, MouseListener {

	protected Random rando = new Random();
	protected static int ballCount = 0;
	protected static boolean userPaddleHit = false;
	protected Ball ball;
	protected Paddle paddle;
	protected boolean ballAbovePaddle = true;
	protected int size = 36;
	protected int brickScore = 0;
	//protected Bricks [] bricksArray = new Bricks[size];
	protected List<Bricks> bricksList = new ArrayList<Bricks>();
	protected int brickXCount = 0;
	protected int brickYCount = 0;
	protected boolean [] brickHitArray = new boolean[size];
	protected int x, y;
	protected int [] xArray = new int[50];
	protected int [] yArray = new int[50];
	protected int randomPowerUp;
	protected boolean powerUpAbovePaddle = true;
	protected boolean powerUpErase = false;
	// protected AudioInputStream audio;
	// protected Clip clip;
	// protected String [] tracksArray = {};
	protected int trackNumber = 0;
	//different states of the game:
	protected static enum STATE {
		MENU,
		GAME,
		WON,
		LOST
	};
	protected static STATE State = STATE.MENU;
	protected Menu menu = new Menu();
	protected Won won = new Won();
	protected Lost lost = new Lost();
	protected Timer t;

	public LobPong() {
		setLayout(new FlowLayout());
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		setBackground(Color.BLACK);

		//To shuffle songs:
		// Collections.shuffle(Arrays.asList(tracksArray));
		// //To play first song:
		// try {
		// 	audio = AudioSystem.getAudioInputStream(new File(tracksArray[trackNumber]).getAbsoluteFile());
		// 	clip = AudioSystem.getClip();
		// 	clip.open(audio);
		// 	clip.start();
		// 	clip.addLineListener(this);		
		// }
		// catch (UnsupportedAudioFileException e) {
		// 	e.printStackTrace();
		// }
		// catch (IOException e) {
		// 	e.printStackTrace();
		// }
		// catch (LineUnavailableException e) {
		// 	e.printStackTrace();
		// }

		ball = new Ball(300, 300);
		paddle = new Paddle();

		for (int i = 0; i <= size - 1; i++) {
			bricksList.add(new Bricks(80 + brickXCount, 100 + brickYCount, 25, 10));
			//bricksArray[i] = new Bricks(80 + brickXCount, 100 + brickYCount, 25, 10);
			brickXCount += 55;
			brickHitArray[i] = false;

			if (brickXCount % 495 == 0) {
				brickYCount += 25;
				brickXCount = 0;
			}
		}

		t = new Timer(5, this);
		t.start();
		setVisible(true);

		for (int i = 0; i <= 49; i++) {
			xArray[i] = rando.nextInt(600);
			yArray[i] = rando.nextInt(600);
		}

		randomPowerUp = rando.nextInt(size);
		// System.out.println(randomPowerUp);
	}

	public void update(LineEvent event) {
		//To check if first song is over:
		// if (event.getType().equals(LineEvent.Type.STOP)) {
		// 	clip.stop();
		// 	//To play next song:
		// 	trackNumber++;

		// 	if (trackNumber == 9) {
		// 		Collections.shuffle(Arrays.asList(tracksArray));
		// 		trackNumber = 0;
		// 	}
		// }

		// try {
		// 	audio = AudioSystem.getAudioInputStream(new File(tracksArray[trackNumber]).getAbsoluteFile());
		// 	clip = AudioSystem.getClip();
		// 	clip.open(audio);
		// 	clip.start();
		// }
		// catch (UnsupportedAudioFileException e) {
		// 	e.printStackTrace();
		// }
		// catch (IOException e) {
		// 	e.printStackTrace();
		// }
		// catch (LineUnavailableException e) {
		// 	e.printStackTrace();
		// }
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		stars(g);

		if (State == STATE.GAME) {
			ball.draw(g);
			paddle.draw(g);
			ball.lives(g);

			//added this code so that if the bricks have NOT been hit, they are drawn.
			for (int i = 0; i <= size - 1; i++) {
				if (brickHitArray[i] == false) {
					bricksList.get(i).draw(g);
					//bricksArray[i].draw(g);
				}

				//added this code to draw powerUps if the ball hits a certain brick:
				if (brickHitArray[i] == true) {	
					if (randomPowerUp == i) {
						if (bricksList.get(i).brickY + bricksList.get(i).powerUpY < LobPongTest.HEIGHT - 50 - paddle.pH && powerUpAbovePaddle == true && powerUpErase == false) {
							bricksList.get(i).drawPowerUps(g);
						}
						if (bricksList.get(i).brickY + bricksList.get(i).powerUpY > LobPongTest.HEIGHT - 50 - paddle.pH && powerUpErase == false) {
							if (bricksList.get(i).brickX > paddle.pX - paddle.pW && bricksList.get(i).brickX < paddle.pX + paddle.pW) {
								paddle.pW = 75;
							}

							bricksList.get(i).powerUpDifY *= -1;
							powerUpAbovePaddle = false;
						}
					}
				}
			}
		}
		else if (State == STATE.MENU){
			menu.draw(g);
		}
		else if (State == STATE.WON) {
			won.draw(g);
		}
		else if (State == STATE.LOST) {
			lost.draw(g);
		}
	}

	public void keyPressed(KeyEvent e) {
		//paddle controls:
		if (State == STATE.GAME) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				paddle.setLeft(true);
				paddle.setRight(false);
				break;
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				paddle.setRight(true);
				paddle.setLeft(false);
				break;
			}

			//pressing space to begin the game:
			if (e.getKeyChar() == ' ') {
				if (ball.begin == false) {
					ball.begin = true;
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		paddle.setRight(false);
		paddle.setLeft(false);
	}

	public void actionPerformed(ActionEvent e) {
		if (State == STATE.GAME) {
			paddle.move();
			ball.move();
		}
		else if (State == STATE.MENU) {
		}
		else if (State == STATE.MENU) {
			brickScore = 0;
		}

		//if the ball hits the paddle:
		if ((ball.ballY + ball.ballR > LobPongTest.HEIGHT - 50 && ball.difBallY > 0) && (ball.ballX - ball.ballR > paddle.pX - paddle.pW) && (ball.ballX + ball.ballR < paddle.pX + paddle.pW) && (ballAbovePaddle == true)) {
			ball.difBallY *= -1;
			userPaddleHit = true;
			//I had to add this bit of code so that depending on where the ball hits the paddle, the direction of the ball in the x direction is altered slightly. So if the ball hits the left side of the paddle, the dX of the ball will decrease by 1, and if the ball hits the right side of the paddle, the dX of the ball will increase by 1.
			double ballHit = ball.ballX - (paddle.pX - paddle.pW);

			if (ballHit == paddle.pW) {
				ball.difBallX *= 1;
			}
			else if (ballHit < paddle.pW) {
				ball.difBallX += -((paddle.pW - ballHit)/(paddle.pW/2));
			}
			else if (ballHit > paddle.pW) {
				ball.difBallX += (ballHit - paddle.pW)/(paddle.pW/2);
			}
		}

		//if the ball is no longer above the paddle:
		if (ball.ballY + ball.ballR > LobPongTest.HEIGHT - 50 + paddle.pH) {
			ballAbovePaddle = false;
		}

		//if the ball goes through bottom of the screen: 
		if (ball.ballY + ball.ballR > LobPongTest.HEIGHT && ball.difBallY > 0) {
			//reset ball/paddle:
			repaint();
			ball.difBallY = 2.5;
			ball.difBallX = 0;
			paddle.pX = LobPongTest.WIDTH/2;
			paddle.pY = LobPongTest.HEIGHT - 50;
			paddle.pW = 50;
			ball.ballX = 300;
			ball.ballY = 300;
			ball.begin = false;
			ballAbovePaddle = true;

			if (brickHitArray[randomPowerUp] == true) {
				powerUpErase = true;
			}
			ballCount++;

			//if the user uses up all of his/her balls:
			if (ballCount == 4) {
				State = STATE.LOST;
			}
		}

		//So the ball bounces off bricks:
		for (int i = 0; i <= size - 1; i++) {
			if ((ball.ballY - ball.ballR < bricksList.get(i).brickY + bricksList.get(i).brickH && ball.ballY + ball.ballR > bricksList.get(i).brickY + bricksList.get(i).brickH + 1) && (ball.ballX - ball.ballR > bricksList.get(i).brickX - bricksList.get(i).brickW && ball.ballX + ball.ballR < bricksList.get(i).brickX + bricksList.get(i).brickW) && (ball.difBallY != 0) && (brickHitArray[i] == false)) {	
				ball.difBallY *= -1;
				brickScore += 1;
				brickHitArray[i] = true;
				if (brickScore == size && ballCount < 4) {
					State = STATE.WON;
				}
			}
			if ((ball.ballY + ball.ballR > bricksList.get(i).brickY - bricksList.get(i).brickH && ball.ballY - ball.ballR < bricksList.get(i).brickY - bricksList.get(i).brickH + 1) && (ball.ballX - ball.ballR > bricksList.get(i).brickX - bricksList.get(i).brickW && ball.ballX + ball.ballR < bricksList.get(i).brickX + bricksList.get(i).brickW) && (ball.difBallY != 0) && (brickHitArray[i] == false)) {
				ball.difBallY *= -1;
				brickScore += 1;
				brickHitArray[i] = true;
				if (brickScore == size && ballCount < 4) {
					State = STATE.WON;
				}
			}
			if ((ball.ballX - ball.ballR < bricksList.get(i).brickX + bricksList.get(i).brickW && ball.ballX + ball.ballR > bricksList.get(i).brickX + bricksList.get(i).brickW + 1) && (ball.ballY - ball.ballR > bricksList.get(i).brickY - bricksList.get(i).brickH && ball.ballY + ball.ballR < bricksList.get(i).brickY + bricksList.get(i).brickH) && (brickHitArray[i] == false)) {
				ball.difBallX *= -1;
				brickScore += 1;
				brickHitArray[i] = true;
				if (brickScore == size && ballCount < 4) {
					State = STATE.WON;
				}
			}
			if ((ball.ballX + ball.ballR > bricksList.get(i).brickX - bricksList.get(i).brickW && ball.ballX - ball.ballR < bricksList.get(i).brickX - bricksList.get(i).brickW + 1) && (ball.ballY - ball.ballR > bricksList.get(i).brickY - bricksList.get(i).brickH && ball.ballY + ball.ballR < bricksList.get(i).brickY + bricksList.get(i).brickH) && (brickHitArray[i] == false)) {
				ball.difBallX *= -1;
				brickScore += 1;
				brickHitArray[i] = true;
				if (brickScore == size && ballCount < 4) {
					State = STATE.WON;
				}
			}
		}

		for (int i = 0; i <= size - 1; i++) {
			if (brickHitArray[i] == true && randomPowerUp == i) {
				bricksList.get(i).movePowerUps();
			}
		}

		repaint();
	}

	//to draw 50 random stars:
	public void stars(Graphics g) {
		for (int i = 0; i <= 49; i++) {
			g.setColor(Color.WHITE);
			g.fillRect(xArray[i], yArray[i], 1, 1);
		}
	}

	public void mousePressed(MouseEvent arg0) {
		int mX = arg0.getX();
		int mY = arg0.getY();

		if (State == STATE.MENU) {
			if (mX >= 284 && mX <= 317 && mY >= 265 && mY <= 277) {
				State = STATE.GAME;
			}
		}
		else if (State == STATE.WON || State == STATE.LOST) {
			//to reset lives/bricks:
			ballCount = 0;
			brickScore = 0;

			if (mX >= 256 && mX <= 344 && mY >= 334 && mY <= 353) {
				//draw new bricks:
				brickYCount += 25;
				for (int i = 0; i <= size - 1; i++) {
					bricksList.add(new Bricks(80 + brickXCount, 100 + brickYCount, 25, 10));
					//bricksArray[i] = new Bricks(80 + brickXCount, 100 + brickYCount, 25, 10);
					brickXCount += 55;
					brickHitArray[i] = false;

					if (brickXCount % 495 == 0) {
						brickYCount += 25;
						brickXCount = 0;
					}
				}
								
				//to reset position of ball:
				paddle.pX = LobPongTest.WIDTH/2;
				paddle.pY = LobPongTest.HEIGHT - 50;
				paddle.pW = 50;
				ball.difBallX = 0;
				ball.difBallY = 2.5;
				ball.ballX = 300;
				ball.ballY = 300;
				ball.begin = false;
				ballAbovePaddle = true;
				randomPowerUp = rando.nextInt(size);

				for (int i = 0; i <= size - 1; i++) {
					brickHitArray[i] = false;
				}
				State = STATE.GAME;
			}
			else if (mX >= 279 && mX <= 317 && mY >= 388 && mY <= 407) {
				getTopLevelAncestor().dispatchEvent(new WindowEvent((Window) getTopLevelAncestor(), WindowEvent.WINDOW_CLOSING));
			}
		}
	}

	//Auto-generated, unused method.
	public void keyTyped(KeyEvent e) {
	}

	//Auto-generated, unused method.
	public void mouseClicked(MouseEvent e) {
	}


	//Auto-generated, unused method.
	public void mouseReleased(MouseEvent e) {
	}

	//Auto-generated, unused method.
	public void mouseEntered(MouseEvent e) {	
	}

	//Auto-generated, unused method.
	public void mouseExited(MouseEvent e) {	
	}

}