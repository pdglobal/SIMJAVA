package SIM.net.client.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import SIM.net.client.Constants;









public class FontDialog
  extends JDialog
  implements ListSelectionListener, MouseListener, ActionListener
{
  private static final long serialVersionUID = 1L;
  private JLabel fontLabel = new JLabel("Font:");
  private JLabel fontStyleLabel = new JLabel("Font Style:");
  private JLabel fontSizeLabel = new JLabel("Font Size:");
  private JLabel fontColourLabel = new JLabel("Font Colour:");
  
  private JList<String> fontBox = new JList();
  private JList<String> fontStyleBox = new JList(new String[] { "Regular", "Bold", "Italic", "Bold Italic" });
  private JList<String> fontSizeBox = new JList(new String[] { "1", "2", "3", "4", "5", "6", "7" });
  
  private JScrollPane fontContainer;
  
  private JScrollPane fontStyleContainer;
  private JScrollPane fontSizeContainer;
  private JPanel previewPanel = new JPanel();
  public JPanel colourPanel = new JPanel();
  private TitledBorder previewBorder = new TitledBorder("Preview Font:");
  private TitledBorder colourBorder = new TitledBorder("");
  private String previewText = "This is how your font will look.";
  private JLabel previewLabel = new JLabel("This is how your font will look.");
  
  private JButton confirm = new JButton("Save");
  private JButton cancel = new JButton("Cancel");
  
  private Font beforeFont;
  
  private Color beforeColour;
  private String fontString = "<html><font face='Dialog' size='3' color='#333333'>";
  private String fontStringEnd = "</font></html>";
  public static String lastFontFace = "Dialog";
  public int lastFontSize = 3;
  public int lastFontModifiers = 0;
  


  public FontDialog()
  {
    
    setTitle("Font");
    setSize(358, 423);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(2);
    setLayout(null);
    setResizable(false);
    
    fontLabel.setBounds(10, 5, 80, 20);
    fontStyleLabel.setBounds(170, 5, 80, 20);
    fontSizeLabel.setBounds(280, 5, 80, 20);
    fontColourLabel.setBounds(10, 280, 80, 20);
    
    fontBox = new JList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    fontBox.addListSelectionListener(this);
    fontContainer = new JScrollPane(fontBox, 
      22, 
      31);
    fontContainer.setBounds(10, 25, 150, 150);
    
    fontStyleBox.addListSelectionListener(this);
    fontStyleContainer = new JScrollPane(fontStyleBox, 
      22, 
      31);
    fontStyleContainer.setBounds(170, 25, 100, 150);
    
    fontSizeBox.addListSelectionListener(this);
    fontSizeContainer = new JScrollPane(fontSizeBox, 
      22, 
      31);
    fontSizeContainer.setBounds(280, 25, 60, 150);
    
    previewPanel.setBounds(10, 180, 330, 100);
    previewPanel.setBorder(previewBorder);
    previewLabel.setFont(new Font("Dialog", 0, 12));
    previewLabel.setText(fontString + previewText + fontStringEnd);
    
    colourPanel.setBounds(10, 300, 330, 50);
    colourPanel.setBorder(colourBorder);
    colourPanel.setBackground(previewLabel.getForeground());
    colourPanel.setToolTipText("Click to select font colour.");
    colourPanel.addMouseListener(this);
    
    confirm.setBounds(240, 360, 100, 25);
    cancel.setBounds(10, 360, 100, 25);
    
    confirm.addActionListener(this);
    cancel.addActionListener(this);
    
    beforeFont = previewLabel.getFont();
    beforeColour = previewLabel.getForeground();
    
    add(fontLabel);
    add(fontStyleLabel);
    add(fontSizeLabel);
    add(fontContainer);
    add(fontStyleContainer);
    add(fontSizeContainer);
    add(previewPanel);
    previewPanel.add(previewLabel);
    add(fontColourLabel);
    add(colourPanel);
    add(confirm);
    add(cancel);
  }
  










  public void valueChanged(ListSelectionEvent e)
  {
    if ((fontBox.getSelectedIndex() != -1) && (fontStyleBox.getSelectedIndex() != -1) && (fontSizeBox.getSelectedIndex() != -1))
    {
      lastFontFace = ((String)fontBox.getSelectedValue());
      lastFontSize = Integer.parseInt((String)fontSizeBox.getSelectedValue());
      lastFontModifiers = fontStyleBox.getSelectedIndex();
    }
    else if ((fontBox.getSelectedIndex() != -1) && (fontStyleBox.getSelectedIndex() != -1) && (fontSizeBox.getSelectedIndex() == -1))
    {
      lastFontFace = ((String)fontBox.getSelectedValue());
      lastFontModifiers = fontStyleBox.getSelectedIndex();
    }
    else if ((fontBox.getSelectedIndex() != -1) && (fontStyleBox.getSelectedIndex() == -1) && (fontSizeBox.getSelectedIndex() == -1))
    {
      lastFontFace = ((String)fontBox.getSelectedValue());
    }
    else if ((fontBox.getSelectedIndex() != -1) && (fontStyleBox.getSelectedIndex() == -1) && (fontSizeBox.getSelectedIndex() != -1))
    {
      lastFontFace = ((String)fontBox.getSelectedValue());
      lastFontSize = Integer.parseInt((String)fontSizeBox.getSelectedValue());
    }
    else if ((fontBox.getSelectedIndex() == -1) && (fontStyleBox.getSelectedIndex() != -1) && (fontSizeBox.getSelectedIndex() != -1))
    {
      lastFontSize = Integer.parseInt((String)fontSizeBox.getSelectedValue());
      lastFontModifiers = fontStyleBox.getSelectedIndex();
    }
    else if ((fontBox.getSelectedIndex() == -1) && (fontStyleBox.getSelectedIndex() != -1) && (fontSizeBox.getSelectedIndex() == -1))
    {
      lastFontModifiers = fontStyleBox.getSelectedIndex();
    }
    else if ((fontBox.getSelectedIndex() == -1) && (fontStyleBox.getSelectedIndex() == -1) && (fontSizeBox.getSelectedIndex() != -1))
    {
      lastFontSize = Integer.parseInt((String)fontSizeBox.getSelectedValue());
    }
    

    updatePreview();
  }
  


  public void updatePreview()
  {
    fontString = "<html>";
    fontStringEnd = "</font>";
    
    if (lastFontModifiers == 1) {
      fontString += "<b>";
      fontStringEnd += "</b>";
    }
    
    if (lastFontModifiers == 2) {
      fontString += "<i>";
      fontStringEnd += "</i>";
    }
    
    if (lastFontModifiers == 3) {
      fontString += "<b><i>";
      fontStringEnd += "</i></b>";
    }
    
    fontString += "<font ";
    fontString = (fontString + "face='" + lastFontFace + "' ");
    fontString = (fontString + "size='" + lastFontSize + "' ");
    fontString = (fontString + "color='" + toHexString(colourPanel.getBackground()) + "'>");
    
    fontStringEnd += "</html>";
    
    previewLabel.setText(fontString + previewText + fontStringEnd);
  }
  





  public static String toHexString(Color c)
  {
    StringBuilder sb = new StringBuilder(35);
    
    if (c.getRed() < 16) {
      sb.append('0');
    }
    sb.append(Integer.toHexString(c.getRed()));
    
    if (c.getGreen() < 16) {
      sb.append('0');
    }
    sb.append(Integer.toHexString(c.getGreen()));
    
    if (c.getBlue() < 16) {
      sb.append('0');
    }
    sb.append(Integer.toHexString(c.getBlue()));
    
    return sb.toString();
  }
  




  public String getFontString()
  {
    return fontString.replace("<html>", "");
  }
  




  public String getFontStringEnd()
  {
    return fontStringEnd.replace("</html>", "");
  }
  
  public void setFontString(String fontString) {
    this.fontString = fontString;
  }
  
  public void setFontStringEnd(String fontStringEnd) {
    this.fontStringEnd = fontStringEnd;
  }
  
  public void mouseClicked(MouseEvent e)
  {
    if (e.getSource() == colourPanel) {
      colourPanel.setBackground(JColorChooser.showDialog(this, "Colour Picker", previewLabel.getForeground()));
      updatePreview();
    }
  }
  
  public void mouseEntered(MouseEvent e)
  {
    if (e.getSource() == colourPanel) {
      colourPanel.setCursor(Cursor.getPredefinedCursor(12));
    }
  }
  





  public void actionPerformed(ActionEvent e)
  {
      dispose();
  }
  
  public void mouseExited(MouseEvent arg0) {}
  
  public void mousePressed(MouseEvent arg0) {}
  
  public void mouseReleased(MouseEvent arg0) {}
  
  public void componentHidden(ComponentEvent arg0) {}
  
  public void componentMoved(ComponentEvent arg0) {}
  
  public void componentResized(ComponentEvent arg0) {}
}
