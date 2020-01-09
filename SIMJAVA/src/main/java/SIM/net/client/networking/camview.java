package SIM.net.client.networking;

import java.awt.EventQueue;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.commons.codec.binary.Base64;

import DARTIS.construct;
import DARTIS.crypt;
import SIM.net.client.Constants;
import SIM.net.client.gui.URLSrc;
import SIM.net.client.gui.loginFrame;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class camview {
	public static String noimg;
	public static Map<String, camviewer> windows = new HashMap<String, camviewer>();
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
		try {
			noimg = URLSrc.getURLSource("https://static.pdglobal.app/?sid=simdataerr");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!windows.containsKey(user)) {
			camviewer window = new camviewer();
			window.camview(user);
			window.frmWebcamViewer.setVisible(true);
			windows.put(user, window);
			windowkeys.put(user, null);
			window.setFrame(window.user, window.fetchFrame());
			Thread thread = new Thread(window.user) {
				public void run() {
					while (true) {

						if (window.isactive() && window.frmWebcamViewer.isVisible()) {
							window.setFrame(window.user, window.fetchFrame());
						} else {
							try {
								Thread.sleep(666);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			};

			thread.start();

		} else {

			camviewer window = windows.get(user);
			window.frmWebcamViewer.show();
			window.frmWebcamViewer.toFront();
		}
	}

	public static class camviewer {
		public JFrame frmWebcamViewer;
		public JButton btnStart;
		public JLabel lblNewLabel;
		public String user = "";
		private boolean active = false;

		public boolean isactive() {
			return active;
		}

		private void initialize() {
			frmWebcamViewer = new JFrame();
			frmWebcamViewer.setTitle("WEBCAM VIEWER - " + user.toUpperCase());
			frmWebcamViewer.setBounds(100, 100, 450, 325);
			frmWebcamViewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frmWebcamViewer.getContentPane().setLayout(null);

			JSeparator separator = new JSeparator();
			separator.setBounds(0, 263, 434, 2);
			frmWebcamViewer.getContentPane().add(separator);

			lblNewLabel = new JLabel("");
			lblNewLabel.setBounds(0, 0, 434, 261);
			frmWebcamViewer.getContentPane().add(lblNewLabel);

			btnStart = new JButton("START");
			btnStart.setBounds(345, 263, 89, 23);
			frmWebcamViewer.getContentPane().add(btnStart);
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(user);
					if (active) {
						active = false;
						btnStart.setText("START");
					} else {
						active = true;
						btnStart.setText("STOP");
					}
				}
			});
			; // this is the line

			JButton btnKeyNone = new JButton("KEY: NONE");
			btnKeyNone.setBounds(0, 263, 184, 23);
			frmWebcamViewer.getContentPane().add(btnKeyNone);
			btnKeyNone.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(user);
					if (btnKeyNone.getText() == "KEY: NONE") {
					      JFileChooser fileChooser = new JFileChooser();
					      if (fileChooser.showOpenDialog(null) == 0) {
					        File file = fileChooser.getSelectedFile();
					        FileReader fr = null;
					        try {
					          fr = new FileReader(file.getAbsolutePath());
					        }
					        catch (FileNotFoundException e1) {
					          e1.printStackTrace();
					        }
					        BufferedReader br = new BufferedReader(fr);
					        String line = "";
					        String kfbftext = "";
					        try {
					          while ((line = br.readLine()) != null)
					          {
					            kfbftext = kfbftext + line;
					          }
					        }
					        catch (IOException e1)
					        {
					          e1.printStackTrace();
					        }
					        btnKeyNone.setText(file.getName());
					        windowkeys.replace(user, construct.load(kfbftext));
					      }
					} else {
						btnKeyNone.setText("KEY: NONE");
						windowkeys.replace(user, null);
					}
				}
			});
			; 
		}

		public void camview(String user) {
			this.user = user;
			initialize();

		}

		public String fetchFrame() {
			String username = this.user;
			String frame;
			try {
				frame = URLSrc.getURLSource("https://intranet.pdglobal.app/?sid=getframe&user=" + username + "&session="
						+ loginFrame.authsession.replaceAll("0x", ""));
				System.out.println(frame);
				if (frame.length() > 2) {
				if (frame.substring(0, 2).toString().equals("0x")) {
					if (windowkeys.get(username) != null) {
					String encrypted = loginFrame.trim(frame, "0x");
					byte[] byteframe = crypt.extract(encrypted, windowkeys.get(username));
					
					frame = Base64img.encodeToString(createImageFromBytes(byteframe), "jpeg");
					} else {
						lblNewLabel.setToolTipText("ERROR: STREAM IS ENCRYPTED BUT NO SUITABLE DECRYPTION KEY WAS FOUND");
						return noimg;
					}
					}
				} else {
					lblNewLabel.setToolTipText("ERROR: STREAM DATA IS CORRUPTED OR INCOMPLETE");
					return noimg;
				}
				BufferedImage frametest = Base64img.decodeToImage(frame);
				if (frametest == null) {
					lblNewLabel.setToolTipText("ERROR: STREAM IS ENCRYPTED BUT NO SUITABLE DECRYPTION KEY WAS FOUND");
					return noimg;
				} else {
				lblNewLabel.setToolTipText("VIDEO STREAM FROM: ".concat(username.toUpperCase()));
				return frame;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lblNewLabel.setToolTipText("ERROR: UNSPECIFIED ERROR RETRIEVING DATA STREAM");
			return noimg;

		}

		public void setFrame(String username, String data) {
			lblNewLabel.setIcon(new ImageIcon(snapshot.resize(Base64img.decodeToImage(data), 434, 261)));
			lblNewLabel.repaint();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static BufferedImage createImageFromBytes(byte[] imageData) {
	    ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
	    try {
	        return ImageIO.read(bais);
	    } catch (IOException e) {
	        throw new RuntimeException(e);
	    }
	}
}
