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