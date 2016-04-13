/* Joseph Traversy
 * Projec #4
 * Lab TR 11:05-12:40
 * Becky Everson
 * Jeremiah Bill
 * Carter Letsky
 * I affirm that I have not given or received any unauthorized help on this assignment and that this work is my own.
 */ 

import javax.swing.JFrame;

public class LobPongTest {

	public static final int HEIGHT = 600;
	public static final int WIDTH = 600;

	public static void main(String[] args) {
		JFrame frame = new JFrame("Lob Pong!");
		LobPong lp = new LobPong();
		frame.add(lp);
		lp.setFocusable(true);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}