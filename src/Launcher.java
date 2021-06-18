import java.awt.EventQueue;

import javax.swing.JFrame;

public class Launcher extends JFrame {

	private static final long serialVersionUID = -4796392158436621989L;

	public Launcher() {

		setContentPane(new Address());
		setResizable(false);
		setTitle("Address Book");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new Launcher();
				frame.setSize(500, 500);
				frame.setVisible(true);
				frame.pack();
			}
		});

	}
}
