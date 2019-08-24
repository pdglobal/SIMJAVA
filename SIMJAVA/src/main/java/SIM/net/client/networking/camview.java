package SIM.net.client.networking;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.codec.binary.Base64;

import SIM.net.client.gui.URLSrc;
import SIM.net.client.gui.loginFrame;

public class camview {
	public static JLabel lblNewLabel;
	private JFrame frmWebcamViewer;
	public static String user = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					camview window = new camview("zach");
					window.frmWebcamViewer.setVisible(true);
					setFrame(camview.user, fetchFrame(camview.user));
					Thread thread = new Thread(camview.user) {
						public void run() {
							while (true) {
								setFrame(camview.user, camview.user);
							}
						}
					};

					thread.start();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * Create the application.
	 */

	public camview(String user) {
		camview.user = user;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWebcamViewer = new JFrame();
		frmWebcamViewer.setTitle("WEBCAM VIEWER - " + camview.user.toUpperCase());
		frmWebcamViewer.setBounds(100, 100, 450, 300);
		frmWebcamViewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmWebcamViewer.getContentPane().setLayout(null);

		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 434, 261);
		frmWebcamViewer.getContentPane().add(lblNewLabel);
	}

	public static String fetchFrame(String username) {
		String frame;
		try {
			frame = URLSrc.getURLSource("https://intranet.pdglobal.app/?sid=getframe&user=" + username + "&session="
					+ loginFrame.authsession.replaceAll("0x", ""));
			System.out.println(frame);
			return frame;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "0";

	}

	public static void setFrame(String username, String data) {
		lblNewLabel.setIcon(new ImageIcon(snapshot.resize(Base64img.decodeToImage(data), 434, 261)));
		lblNewLabel.repaint();
	}

}
