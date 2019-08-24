package SIM.net.client.networking;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import DARTIS.construct;

import javax.swing.JToggleButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JSeparator;

public class mirror {
	public static boolean broadcast = false;
	private static JFrame frmWebcamBroadcast;
	private static JLabel lblNewLabel;
	private static JLabel lblFps;
	private JButton tglbtnNewToggleButton;
	public static boolean open = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		mirror window = new mirror();
		window.frmWebcamBroadcast.setVisible(true);
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
		frmWebcamBroadcast = new JFrame();
		frmWebcamBroadcast.setTitle("WEBCAM BROADCAST");
		frmWebcamBroadcast.setBounds(100, 100, 450, 327);
		frmWebcamBroadcast.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				snapshot.open = false;
				frmWebcamBroadcast.dispose();
				snapshot.webcam.close();
			}
		});
		frmWebcamBroadcast.getContentPane().setLayout(null);

		lblFps = new JLabel("FPS: 0");
		lblFps.setBounds(0, 0, 46, 14);
		frmWebcamBroadcast.getContentPane().add(lblFps);

		JButton btnNewButton = new JButton("START");
		btnNewButton.setBounds(345, 264, 89, 23);
		frmWebcamBroadcast.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(e -> {
			if (broadcast) {
				btnNewButton.setText("START");
				mirror.broadcast = false;
				mirror.getLblFps().setText("FPS: 0");
				mirror.getLblNewLabel().repaint();
			} else {
				btnNewButton.setText("STOP");
				mirror.broadcast = true;
				mirror.getLblFps().setText("FPS: 0");
				mirror.getLblNewLabel().repaint();
			}
		});

		lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 434, 261);
		frmWebcamBroadcast.getContentPane().add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 263, 434, 2);
		frmWebcamBroadcast.getContentPane().add(separator);

		tglbtnNewToggleButton = new JButton("ENCRYPTION: DISABLED");
		tglbtnNewToggleButton.setBounds(0, 264, 181, 23);
		frmWebcamBroadcast.getContentPane().add(tglbtnNewToggleButton);
		tglbtnNewToggleButton.addActionListener(e -> {
			if (tglbtnNewToggleButton.getText().equals("ENCRYPTION: DISABLED")) {
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(null) == 0) {
					File file = fileChooser.getSelectedFile();
					FileReader fr = null;
					try {
						fr = new FileReader(file.getAbsolutePath());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					BufferedReader br = new BufferedReader(fr);
					String line = "";
					String kfbftext = "";
					try {
						while ((line = br.readLine()) != null) {
							kfbftext = kfbftext + line;
						}
					} catch (IOException e1) {
						return;
					}
					snapshot.kfb = construct.load(kfbftext);
					if (snapshot.kfb.length > 9999) {
						snapshot.encryptcam = true;
						tglbtnNewToggleButton.setText("ENCRYPTION: ENABLED");
					}
				}
			} else {
				tglbtnNewToggleButton.setText("ENCRYPTION: DISABLED");
				snapshot.encryptcam = false;
			}
		});

	}

	public static JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public static void setLblNewLabel(ImageIcon icon) {
		lblNewLabel.setIcon(icon);
		frmWebcamBroadcast.repaint();
	}

	public static JFrame getFrame() {
		return frmWebcamBroadcast;
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
		}
	}

	public static JLabel getLblFps() {
		return lblFps;
	}

	public JButton getTglbtnNewToggleButton() {
		return tglbtnNewToggleButton;
	}
}
