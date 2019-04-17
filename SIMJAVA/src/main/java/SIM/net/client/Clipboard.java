package SIM.net.client;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.PrintStream;

public class Clipboard
  implements ClipboardOwner
{
  public Clipboard() {}
  
  private static Clipboard instance = new Clipboard();
  
  public static void setClipboardContents(String clip) {
    StringSelection stringSelection = new StringSelection(clip);
    java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, instance);
  }
  





  public static String getClipboardContents()
  {
    String result = "";
    java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    
    Transferable contents = clipboard.getContents(null);
    boolean hasTransferableText = (contents != null) && (contents.isDataFlavorSupported(DataFlavor.stringFlavor));
    
    if (hasTransferableText) {
      try {
        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
      } catch (UnsupportedFlavorException ex) {
        System.out.println(ex);
        ex.printStackTrace();
      } catch (IOException ex) {
        System.out.println(ex);
        ex.printStackTrace();
      }
    }
    
    return result;
  }
  
  public void lostOwnership(java.awt.datatransfer.Clipboard arg0, Transferable arg1) {}
}
