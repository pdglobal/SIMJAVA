package SIM.net.client.networking;

import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.fluent.Form;
import org.apache.hc.client5.http.fluent.Request;

import SIM.net.client.gui.loginFrame;

public class stream {

	
	public static void uploadcam(String ss) {
		try {
			Request.Post("https://intranet.pdglobal.app/?sid=simwebcam")
			.bodyForm(Form.form().add("BIN", ss).add("session", loginFrame.authsession.replaceAll("0x", "")).build())
			.execute()
			.returnContent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String imgToBase64String(final RenderedImage img, final String formatName)
	{
	  final ByteArrayOutputStream os = new ByteArrayOutputStream();

	  try
	  {
	    ImageIO.write(img, formatName, os);
	    return new String(Base64.encodeBase64(os.toByteArray()));
	  }
	  catch (final IOException ioe)
	  {
	    throw new UncheckedIOException(ioe);
	  }
	}
	
}
