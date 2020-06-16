import javax.swing.JFrame;

public class PongFrame {

	public static void main(String[] args) {
		JFrame j1 = new JFrame();
		j1.setTitle("Pong");
		j1.setSize(1830, 900);
		j1.setLocationRelativeTo(null);
		j1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j1.add(new PongComp());
		j1.setVisible(true);
	}
}
