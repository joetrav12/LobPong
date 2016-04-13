import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel {

	public Menu() {
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;

		g.setColor(Color.GREEN);
		Font font = new Font("Thonburi", Font.BOLD + Font.ITALIC, 50);
		g.setFont(font);
		g.drawString("SPACE PONG", LobPongTest.WIDTH/2 - 166, 100);

		g.setColor(Color.WHITE);
		Font font2 = new Font("Verdana", Font.PLAIN, 8);
		g.setFont(font2);
		g.drawString("Music by Dibiase", 520, 15);
		g.drawString("& Jonwayne", 520, 22);

		Font font3 = new Font("Braggadocio", Font.PLAIN, 18);
		g.setFont(font3);
		g.drawString("Play", 282, 275);
	}

}