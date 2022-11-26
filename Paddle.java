import java.awt.Color;
import java.awt.Graphics;

public class Paddle {

	protected int pX = LobPongTest.WIDTH/2;
	protected int pY = LobPongTest.HEIGHT - 50;
	protected int dpX = 0;
	protected int pW = 50;
	protected int pH = 5;
	protected boolean left, right;

	public Paddle() {
	}

	//to draw paddle:
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(pX - pW, pY - pH, pW*2, pH*2);
	}

	//to move paddle:
	public void move() {
		if (left) {
			dpX = -1;
			pX += dpX;
		}
		else if (right) {
			dpX = 1;
			pX += dpX;
		}

		if (pX - pW < 0) {
			setLeft(false);
			pX = pW;
		}
		if (pX + pW > LobPongTest.WIDTH) {
			setRight(false);
			pX = LobPongTest.WIDTH - pW;
		}
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

}