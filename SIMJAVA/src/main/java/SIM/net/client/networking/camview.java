package SIM.net.client.networking;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.codec.binary.Base64;

import SIM.net.client.gui.URLSrc;
import SIM.net.client.gui.loginFrame;
import javax.swing.JSeparator;
import javax.swing.JButton;

public class camview {
	public static JLabel lblNewLabel;
	private JFrame frmWebcamViewer;
	public static String user = "";
	public static Map<String, camview> windows = new HashMap<String, camview>();
	public static Map<String, String[]> windowkeys = new HashMap<String, String[]>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				camwin("Zach");

			}
		});

	}

	/**
	 * Create the application.
	 */
	public static void camwin(String user) {

		if (!windows.containsKey(user)) {
			camview window = new camview(user);
			window.frmWebcamViewer.setVisible(true);
			windows.put(user, window);
			windowkeys.put(user, null);
			setFrame(camview.user, fetchFrame(camview.user));
			Thread thread = new Thread(camview.user) {
				public void run() {
					while (true) {
						setFrame(camview.user, camview.user);
					}
				}
			};

			thread.start();

		} else {
			windows.get(user).frmWebcamViewer.toFront();
		}
	}

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
		frmWebcamViewer.setBounds(100, 100, 450, 325);
		frmWebcamViewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmWebcamViewer.getContentPane().setLayout(null);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 263, 434, 2);
		frmWebcamViewer.getContentPane().add(separator);

		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 434, 261);
		frmWebcamViewer.getContentPane().add(lblNewLabel);

		JButton btnStart = new JButton("START");
		btnStart.setBounds(345, 263, 89, 23);
		frmWebcamViewer.getContentPane().add(btnStart);

		JButton btnKeyNone = new JButton("KEY: NONE");
		btnKeyNone.setBounds(0, 263, 184, 23);
		frmWebcamViewer.getContentPane().add(btnKeyNone);
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
