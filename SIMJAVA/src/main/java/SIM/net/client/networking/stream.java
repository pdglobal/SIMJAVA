package SIM.net.client.networking;

import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.fluent.Form;
import org.apache.hc.client5.http.fluent.Request;

public class stream {

	
	public static void uploadcam(String ss) {
		try {
			Request.Post("https://intranet.pdglobal.app/?sid=simwebcam")
			.bodyForm(Form.form().add("BIN", ss).add("user", "ZACH").add("pass", "123456").add("sid",  "simwebcam").build())
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
	    return Base64.getEncoder().encodeToString(os.toByteArray());
	  }
	  catch (final IOException ioe)
	  {
	    throw new UncheckedIOException(ioe);
	  }
	}
	@SuppressWarnings("deprecation")
	public static String compressString(String srcTxt)
		      throws IOException {
		    ByteArrayOutputStream rstBao = new ByteArrayOutputStream();
		    GZIPOutputStream zos = new GZIPOutputStream(rstBao);
		    zos.write(srcTxt.getBytes());
		    IOUtils.closeQuietly(zos);

		    byte[] bytes = rstBao.toByteArray();
		    // In my solr project, I use org.apache.solr.co mmon.util.Base64.
		    // return = org.apache.solr.common.util.Base64.byteArrayToBase64(bytes, 0,
		    // bytes.length);
		    return Base64.getEncoder().encodeToString((bytes));
		  }
	
}
