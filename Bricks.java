import java.awt.Color;
import java.awt.Graphics;

public class Bricks {

	protected int brickX;
	protected int brickY;
	protected int brickW;
	protected int brickH;

	protected int powerUpY = 0;
	protected int powerUpDifY = 1;

	public Bricks(int brickX, int brickY, int brickW, int brickH) {
		this.brickX = brickX;
		this.brickY = brickY;
		this.brickW = brickW;
		this.brickH = brickH;
	}

	//draw bricks on screen:
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(brickX - brickW, brickY - brickH, brickW*2, brickH*2);
	}

	//to draw powerUps:
	public void drawPowerUps(Graphics g) {
		//Bigger Paddle:
		g.setColor(Color.BLUE);
		g.fillOval(brickX - 8, brickY - 8  + powerUpY, 16, 16);
	}

	//to move powerUps:
	public void movePowerUps() {
		powerUpY += powerUpDifY;
	}

}