package SIM.net.client.networking;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class mirror {

	private static JFrame frame;
	private static JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		mirror window = new mirror();
		window.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public mirror() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 434, 261);
		frame.getContentPane().add(lblNewLabel);

	}

	public static JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public static void setLblNewLabel(ImageIcon icon) {
		lblNewLabel.setIcon(icon);
		frame.repaint();
	}

	public static JFrame getFrame() {
		return frame;
	}
}
