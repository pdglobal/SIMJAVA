package SIM.net.client.networking;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import org.apache.commons.codec.binary.Base64;
import org.apache.hc.client5.http.fluent.Form;
import org.apache.hc.client5.http.fluent.Request;

import SIM.net.client.gui.loginFrame;

public class audioServer {
	 public byte[] buffer;
	    @SuppressWarnings("unused")
		private int port;
	    static AudioInputStream ais;

	    public static void main(String[] args)
	    {
	        TargetDataLine line;

	        AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
	        float rate = 44100.0f;
	        int channels = 2;
	        int sampleSize = 16;
	        boolean bigEndian = true;


	        AudioFormat format = new AudioFormat(encoding, rate, sampleSize, channels, (sampleSize / 8) * channels, rate, bigEndian);

	        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	        if (!AudioSystem.isLineSupported(info)) {
	            System.out.println("Line matching " + info + " not supported.");
	            return;
	        }
	            try {
					line = (TargetDataLine) AudioSystem.getLine(info);
					 int buffsize = line.getBufferSize()/5;
			            buffsize += 512; 

			            try {
							line.open(format);
						} catch (LineUnavailableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

			            line.start();   

			            int numBytesRead;
			            byte[] data = new byte[4096];
			            while (true) {
			                   // Read the next chunk of data from the TargetDataLine.
			                   numBytesRead =  line.read(data, 0, data.length);
			                   // Save this chunk of data.
			                   try {
			           			Request.Post("https://intranet.pdglobal.app/?sid=simaudio")
			           			.bodyForm(Form.form().add("BIN", new String(Base64.encodeBase64(data))).add("session", loginFrame.authsession.replaceAll("0x", "")).build())
			           			.execute()
			           			.returnContent();
			           			System.out.println("UPLOADED AUDIO STREAM");
			           		} catch (IOException e) {
			           			// TODO Auto-generated catch block
			           			e.printStackTrace();
			           		}

			                   
			                }

			    
				} catch (LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

	           
}
}
