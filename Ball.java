import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Ball {

	Random rando = new Random();
	protected int ballR = 5;
	protected boolean begin = false;
	protected double ballX, ballY, difBallX, difBallY;
	//setting initial velocity and angle of ball:
	protected double v = 2.5;
	protected double a = 90;

	public Ball(double ballX, double ballY) {
		this.ballX = ballX;
		this.ballY = ballY;
		//equations for projectile motion:
		this.difBallX = v*Math.cos(Math.toRadians(a));
		this.difBallY = v*Math.sin(Math.toRadians(a));
	}

	//to move ball:
	public void move() {
		if (begin == true) {
			ballX += difBallX;
			ballY += difBallY;

			//Gravity:
			difBallY += .0098;

			if (ballX - ballR < 0 && difBallX < 0) {
				difBallX *= -1;
			}

			if (ballX + ballR > LobPongTest.WIDTH && difBallX > 0) {
				difBallX *= -1;
			}

			if (ballY - ballR < 0 && difBallY < 0) {
				difBallY *= -1;
			}
		}
	}

	//to draw ball:
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		Ellipse2D ball = new Ellipse2D.Double(ballX - ballR, ballY - ballR, ballR*2, ballR*2);
		g.setColor(Color.YELLOW);
		g2d.draw(ball);
		g.fillOval((int)ballX - ballR, (int)ballY - ballR, ballR*2, ballR*2);
	}

	//to draw lives in upper right hand corner:
	public void lives(Graphics g) {
		g.setColor(Color.YELLOW);

		if (LobPong.ballCount == 0) {
			g.fillOval(10, 10, 5, 5);
			g.fillOval(20, 10, 5, 5);
			g.fillOval(30, 10, 5, 5);
		}

		if (LobPong.ballCount == 1) {
			g.fillOval(10, 10, 5, 5);
			g.fillOval(20, 10, 5, 5);
		}
		if (LobPong.ballCount == 2) {
			g.fillOval(10, 10, 5, 5);
		}
	}

}