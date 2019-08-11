package SIM.net.client.networking;

import com.github.sarxos.webcam.Webcam;

import SIM.net.client.networking.stream;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class snapshot {

	public static BufferedImage webcamimg;
	
	public static void main(String[] args) throws IOException {
		
		// get default webcam and open it
		Webcam webcam = Webcam.getDefault();
		webcam.open();

		mirror.main(null);
		while (true) {
		// get image
		BufferedImage image = webcam.getImage();
		mirror.setLblNewLabel(new ImageIcon(resize(image, 434, 261)));
		// save image to memory
		webcamimg = image;
		stream.uploadcam(stream.compressString(stream.imgToBase64String(webcamimg, "jpg")));
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