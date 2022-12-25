package SIM.net.client.networking;

import com.github.sarxos.webcam.Webcam;

import DARTIS.construct;
import DARTIS.keys;
import SIM.net.client.networking.stream;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class snapshot {
	public static boolean encryptcam = false;
	public static String[] kfb;
	public static BufferedImage webcamimg;
	public static int operations = 0;
	public static String hz = "0";
	public static boolean open = false;
	public static Webcam webcam = Webcam.getDefault();
	public static void main(String[] args) throws IOException {
		
		// get default webcam and open it
		if (!open) {
			open = true;
		
		webcam.open();

		mirror.main(null);
		long startTime = System.currentTimeMillis();

		while (snapshot.open) {
		// get image
		BufferedImage image = webcam.getImage();
		mirror.setLblNewLabel(new ImageIcon(resize(image, 434, 261)));
		// save image to memory
		if (mirror.broadcast) {
		webcamimg = image;
		if (encryptcam == true) {				 
			String encimg = DARTIS.crypt.inject(Base64img.encodeToString(webcamimg, "jpeg").getBytes(), kfb);
			stream.uploadcam("0x"+encimg);
			operations += 1;
		} else {
			stream.uploadcam(Base64img.encodeToString(webcamimg, "jpeg"));
			operations += 1;
		}
		hz = String.valueOf((operations)/((System.currentTimeMillis()-startTime)/1000));
		mirror.getLblFps().setText("FPS: "+ hz);
		mirror.getLblNewLabel().repaint();
		//System.out.println(hz+" FPS");
		}
	}
}
	}
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}

}