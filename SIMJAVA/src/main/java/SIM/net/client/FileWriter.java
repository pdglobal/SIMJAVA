package SIM.net.client;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;











public class FileWriter
{
  private static DataOutputStream dos;
  
  public FileWriter() {}
  
  public static boolean writeToFile(String fileName, String dataLine, boolean isAppendMode, boolean isNewLine)
  {
    if (isNewLine) {
      dataLine = "\r\n" + dataLine;
    }
    try {
      File outFile = new File(fileName);
      if (!outFile.exists()) {
        outFile.createNewFile();
      }
      
      if (isAppendMode) {
        dos = new DataOutputStream(new FileOutputStream(fileName, true));
      } else {
        dos = new DataOutputStream(new FileOutputStream(outFile));
      }
      dos.writeBytes(dataLine);
      dos.close();
    } catch (Exception ex) { ex.printStackTrace();return false;
    }
    return true;
  }
}
