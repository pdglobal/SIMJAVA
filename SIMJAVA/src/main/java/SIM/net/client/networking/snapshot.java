package SIM.net.client.networking;

import com.github.sarxos.webcam.Webcam;

import DARTIS.construct;
import DARTIS.keys;
import SIM.net.client.networking.stream;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class snapshot {
	public static boolean encryptcam = false;
	public static String[] key = construct.load(keys.generate());
	public static BufferedImage webcamimg;
	public static int operations = 0;
	public static String hz = "0";
	public static void main(String[] args) throws IOException {
		
		// get default webcam and open it
		Webcam webcam = Webcam.getDefault();
		webcam.open();

		mirror.main(null);
		long startTime = System.currentTimeMillis();

		while (true) {
		// get image
		BufferedImage image = webcam.getImage();
		mirror.setLblNewLabel(new ImageIcon(resize(image, 434, 261)));
		// save image to memory
		webcamimg = image;
		if (encryptcam == true) {
			stream.uploadcam(stream.compressString(DARTIS.crypt.inject(stream.imgToBase64String(webcamimg, "jpg"), key)));
			operations += 1;
		} else {
			stream.uploadcam(stream.compressString(stream.imgToBase64String(webcamimg, "jpg")));
			operations += 1;
		}
		hz = String.valueOf((operations)/((System.currentTimeMillis()-startTime)/1000));
		System.out.println(hz+" FPS");
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