import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Won extends JPanel {

	public Won() {
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;

		g.setColor(Color.GREEN);
		Font font = new Font("Bank Gothic", Font.PLAIN, 50);
		g.setFont(font);
		g.drawString("You Win!", 196, 250);

		g.setColor(Color.WHITE);
		Font font2 = new Font("Braggadocio", Font.PLAIN, 18);
		g.setFont(font2);
		g.drawString("Play Again", 255, 345);
		g.drawString("Quit", 281, 400);
	}

}