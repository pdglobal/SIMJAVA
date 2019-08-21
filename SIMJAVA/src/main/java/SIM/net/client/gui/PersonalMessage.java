package SIM.net.client.gui;
import DARTIS.crypt;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import SIM.net.client.AudioPlayer;
import SIM.net.client.BrowserLaunch;
import SIM.net.client.Client;
import SIM.net.client.Constants;
import SIM.net.client.Emoticon;
import SIM.net.client.FileWriter;
import SIM.net.client.Timer;
import SIM.net.client.networking.PacketHeaders;
import SIM.net.client.networking.PacketManager;
public class PersonalMessage
extends JFrame
implements ComponentListener, KeyListener, FocusListener, HyperlinkListener, WindowFocusListener, DropTargetListener, DragSourceListener, DragGestureListener, ActionListener, WindowListener
{
private static final long serialVersionUID = -7212578453796141050L;
public ArrayList<Client> clients = new ArrayList();
private Vector<String> clientsList = new Vector();
public FileTransfer fileTransfer;
public String baseTitle;
public JTextPane log = new JTextPane();
public JTextArea chatBox = new JTextArea();
public JList<String> clientList = new JList();
private JScrollPane logContainer;
private JScrollPane chatBoxContainer;
private JScrollPane clientContainer;
private JButton fontButton = new JButton();
private JButton emoticonButton = new JButton();
private JButton nudgeButton = new JButton();
private JButton addButton = new JButton();
private JButton transferButton = new JButton();
private JLabel statusLabel = new JLabel();
private HTMLEditorKit kit = new HTMLEditorKit();
private HTMLDocument doc = new HTMLDocument();
private StyleSheet styleSheet = this.kit.getStyleSheet();
private boolean shiftDown = false;
public boolean showing = true;
public boolean groupChat = false;
private String URL_PATTERN = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
private String URL_PATTERN_2 = "([^\"|\\'])\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
private Pattern p;
private Matcher regexMatcher;
public PopupMenu popupMenu;
private boolean dc;
private boolean typing = false;
private DropTarget dropTarget = new DropTarget(this, this);
private DragSource dragSource = DragSource.getDefaultDragSource();
private Timer nudgeTimer = new Timer(10000);
private String clientSend;
public String[] kfbp;

public PersonalMessage(Client client)
{
  this.kfbp = new String[10001];
  for (int i = 0; i < 10001; i++) {
    this.kfbp[i] = "0";
  }
  this.clients.add(client);
  setIconImage(Constants.mailIcon);
  this.baseTitle = (client.getScreen_name() + " | " + client.getUsername());
  setTitle(this.baseTitle + " - PM");
  setSize(600, 450);
  setLocationRelativeTo(null);
  setLayout(null);
  setResizable(true);
  setDefaultCloseOperation(2);
  setJMenuBar(new MenuBar().getMenuBar());
  
  setMinimumSize(new Dimension(400, 300));
  setPreferredSize(new Dimension(700, 500));
  addComponentListener(this);
  addFocusListener(this);
  addWindowFocusListener(this);
  addWindowListener(this);
  show(true);
  
  this.log.setBounds(0, 0, 585, 300);
  this.log.setEditable(false);
  this.log.setFont(new Font("Arial", 0, 12));
  
  this.styleSheet.addRule("a:link {color:blue}");
  
  this.log.setEditorKit(this.kit);
  this.log.setDocument(this.doc);
  this.log.addHyperlinkListener(this);
  this.log.setAutoscrolls(false);
  
  this.logContainer = new JScrollPane(this.log, 
    20, 
    30);
  this.logContainer.setBounds(0, 0, 585, 300);
  this.logContainer.setAutoscrolls(false);
  this.logContainer.setViewportView(this.log);
  
  this.chatBox.setBounds(0, 0, 300, 130);
  this.chatBox.setFont(new Font("Dialog", 0, 12));
  this.chatBox.addKeyListener(this);
  this.chatBox.setLineWrap(true);
  this.chatBox.setWrapStyleWord(true);
  this.chatBox.setToolTipText("Enter to submit, Shift+Enter for new line");
  this.chatBox.setDropTarget(this.dropTarget);
  
  this.dragSource.createDefaultDragGestureRecognizer(this, 1, this);
  
  this.chatBoxContainer = new JScrollPane(this.chatBox, 
    20, 
    31);
  this.chatBoxContainer.setBounds(0, 300, 450, 90);
  
  this.fontButton.setBounds(0, 279, 50, 20);
  this.fontButton.setIcon(Constants.fontIcon2);
  
  this.emoticonButton.setBounds(51, 279, 25, 20);
  this.emoticonButton.setIcon(Constants.emoticonIcon);
  
  this.nudgeButton.setBounds(77, 279, 30, 20);
  this.nudgeButton.setIcon(Constants.nudgeIcon);
  
  this.addButton.setBounds(getWidth() - 57, 279, 20, 20);
  this.addButton.setIcon(Constants.addIcon);
  this.addButton.setToolTipText("Add Buddy to conversation");
  
  this.transferButton.setBounds(getWidth() - 36, 279, 20, 20);
  this.transferButton.setIcon(Constants.transferIcon);
  this.transferButton.setToolTipText("File Transfer");
  
  this.fontButton.addActionListener(this);
  this.emoticonButton.addActionListener(this);
  this.nudgeButton.addActionListener(this);
  this.addButton.addActionListener(this);
  this.transferButton.addActionListener(this);
  
  this.statusLabel.setBounds(115, 279, 200, 20);
  
  this.clientList.setBounds(450, 0, 135, getHeight() - 60);
  this.clientContainer = new JScrollPane(this.clientList, 
    20, 
    31);
  this.clientContainer.setBounds(450, 0, 135, getHeight() - 60);
  
  add(this.logContainer);
  add(this.chatBoxContainer);
  add(this.fontButton);
  add(this.emoticonButton);
  add(this.nudgeButton);
  add(this.addButton);
  add(this.transferButton);
  add(this.statusLabel);
  
  this.chatBox.addFocusListener(this);
  this.chatBox.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "");
  
  this.chatBox.setFont(new Font(Constants.getFontDialog().lastFontFace, 
    Constants.getFontDialog().lastFontModifiers, 
    Constants.convertSize(Constants.getFontDialog().lastFontSize)));
  
  this.chatBox.setForeground(Constants.getFontDialog().colourPanel.getBackground());
  
  this.popupMenu = new PopupMenu();
  this.fileTransfer = new FileTransfer((Client)this.clients.get(0));
}

public void log(String username, String msg, String string, String stringEnd)
{
  if ((string == null) || (stringEnd == null))
  {
    string = "<font face='Dialog' size='3' color='gray'>";
    stringEnd = "</font>";
  }
  else if (!Constants.isFontEnabled())
  {
    string = "<font face='Dialog' size='3' color='#333333'>";
    stringEnd = "</font>";
  }
  if (Constants.isTrimChatLog())
  {
    String[] count = this.log.getText().split("\n");
    if (count.length > Constants.getLineCount()) {
      this.log.setText("");
    }
  }
  try
  {
    for (Emoticon e : Constants.getEmotions()) {
      msg = msg.replaceAll(e.getKey(), e.getValue());
    }
    if ((msg.startsWith("http")) || (msg.startsWith("www")) || (msg.startsWith("ftp"))) {
      this.p = Pattern.compile(this.URL_PATTERN);
    } else {
      this.p = Pattern.compile(this.URL_PATTERN_2);
    }
    this.regexMatcher = this.p.matcher(msg);
    while (this.regexMatcher.find()) {
      msg = msg.replace(this.regexMatcher.group(), 
        "<font color=\"blue\"><a href=\"" + 
        this.regexMatcher.group() + 
        "\">" + this.regexMatcher.group() + 
        "</a></font>");
    }
    this.kit.insertHTML(this.doc, this.doc.getLength(), "<font color='gray' face='Dialog' size='3pt'><b>" + 
      Constants.getDate() + " - " + 
      username + ": </b></font>" + 
      msg.replace("\n", "<br>"), 0, 0, null);
    
    this.log.setCaretPosition(this.doc.getLength());
  }
  catch (Exception e)
  {
    e.printStackTrace();
  }
  if (Constants.isSaveLogFiles()) {
    FileWriter.writeToFile(Constants.getLogFileName(), Constants.getDate() + " - " + username + ": " + msg, true, true);
  }
}

public static String escapeHTML(String s)
{
  StringBuilder out = new StringBuilder(Math.max(16, s.length()));
  for (int i = 0; i < s.length(); i++)
  {
    char c = s.charAt(i);
    if ((c > '') || (c == '"') || (c == '<') || (c == '>') || (c == '&'))
    {
      out.append("&#");
      out.append(c);
      out.append(';');
    }
    else
    {
      out.append(c);
    }
  }
  return out.toString();
}

public void sendMessage()
  throws IOException
{
  this.chatBox.setEnabled(false);
  String msgtext = this.chatBox.getText().trim();
  String text = msgtext.substring(0, msgtext.length());
  if (text.isEmpty())
  {
    JOptionPane.showMessageDialog(null, 
      "You must enter in some text before trying to send a message.", 
      "Message Error", 
      0);
  }
  else
  {
    log(loginFrame.username, 
      text, 
      Constants.getFontDialog().getFontString(), 
      Constants.getFontDialog().getFontStringEnd());
    if (!this.kfbp[0].equals("0")) {
      text = "0x" + crypt.inject(text, this.kfbp);
    }
    String send = URLSrc.getURLSource("https://intranet.pdglobal.app/?sid=sendmsg&to=" + this.baseTitle.split("\\|")[0].toString().trim() + "&msg=" + URLEncoder.encode(text, "UTF-8").trim() + "&session=" + loginFrame.authsession.replaceAll("0x", ""));
    System.out.println(send);
    
    this.chatBox.setText("");
  }
  this.chatBox.setEnabled(true);
  this.chatBox.requestFocus();
}

public void userDC(boolean dc)
{
  this.dc = dc;
  if (dc)
  {
    setTitle("(Offline) - " + this.baseTitle);
    this.chatBox.setEnabled(false);
    this.fontButton.setEnabled(false);
    this.emoticonButton.setEnabled(false);
    this.nudgeButton.setEnabled(false);
    this.transferButton.setEnabled(false);
    this.fileTransfer.pickFile.setEnabled(false);
    this.fileTransfer.sendFile.setEnabled(false);
    this.addButton.setEnabled(false);
    this.chatBox.setText("User is offline.");
    updateStatus(false);
  }
  else
  {
    setTitle(this.baseTitle + " - PM");
    this.chatBox.setEnabled(true);
    this.fontButton.setEnabled(true);
    this.emoticonButton.setEnabled(true);
    this.nudgeButton.setEnabled(true);
    this.transferButton.setEnabled(true);
    this.fileTransfer.pickFile.setEnabled(true);
    this.fileTransfer.sendFile.setEnabled(true);
    this.addButton.setEnabled(true);
    this.chatBox.setText("");
  }
}

public void hyperlinkUpdate(HyperlinkEvent e)
{
  if ((e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) && (e.getURL() != null)) {
    BrowserLaunch.openURL(e.getURL().toString());
  }
}

public void focusGained(FocusEvent e)
{
  if (!this.showing)
  {
    this.showing = true;
    if ((getTitle() != this.baseTitle + " - PM") && (!this.dc)) {
      setTitle(this.baseTitle + " - PM");
    }
    this.log.setCaretPosition(this.doc.getLength());
  }
}

public void focusLost(FocusEvent e)
{
  this.showing = false;
}

public void keyPressed(KeyEvent e)
{
  switch (e.getKeyCode())
  {
  case 10: 
    if (this.shiftDown) {
      this.chatBox.setText(this.chatBox.getText() + "\n");
    } else {
      try
      {
        sendMessage();
      }
      catch (IOException e1)
      {
        e1.printStackTrace();
      }
    }
    break;
  case 16: 
    this.shiftDown = true;
  }
}

public void keyReleased(KeyEvent e)
{
  switch (e.getKeyCode())
  {
  case 16: 
    this.shiftDown = false;
  }
}

public void keyTyped(KeyEvent e) {}

public void componentResized(ComponentEvent e)
{
  if (!this.groupChat)
  {
    this.log.setSize(getWidth() - 15, getHeight() - 171);
    this.logContainer.setSize(getWidth() - 15, getHeight() - 171);
  }
  else
  {
    this.log.setSize(getWidth() - 150, getHeight() - 171);
    this.logContainer.setSize(getWidth() - 150, getHeight() - 171);
    
    this.clientContainer.setBounds(getWidth() - 150, 0, 135, getHeight() - 171);
  }
  this.chatBox.setSize(getWidth() - 15, this.chatBox.getHeight());
  this.chatBoxContainer.setBounds(0, this.logContainer.getHeight() + 21, getWidth() - 15, this.chatBoxContainer.getHeight());
  
  this.fontButton.setBounds(0, getHeight() - 171, 50, 20);
  this.emoticonButton.setBounds(51, getHeight() - 171, 25, 20);
  this.nudgeButton.setBounds(77, getHeight() - 171, 30, 20);
  this.addButton.setBounds(getWidth() - 57, getHeight() - 171, 20, 20);
  this.transferButton.setBounds(getWidth() - 36, getHeight() - 171, 20, 20);
  this.statusLabel.setBounds(115, getHeight() - 171, this.statusLabel.getWidth(), this.statusLabel.getHeight());
  
  repaint();
  
  this.log.setCaretPosition(this.doc.getLength());
}

public void componentShown(ComponentEvent e)
{
  this.chatBox.requestFocus();
  this.log.setCaretPosition(this.doc.getLength());
}

public void windowGainedFocus(WindowEvent e)
{
  SwingUtilities.invokeLater(new Runnable()
  {
    public void run()
    {
      PersonalMessage.this.logContainer.getVerticalScrollBar().setValue(PersonalMessage.this.logContainer.getVerticalScrollBar().getMaximum());
      PersonalMessage.this.chatBox.requestFocus();
    }
  });
}

public void drop(DropTargetDropEvent e)
{
  try
  {
    Transferable tr = e.getTransferable();
    if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
    {
      e.acceptDrop(1);
      List<?> fileList = (List)tr.getTransferData(DataFlavor.javaFileListFlavor);
      this.fileTransfer.addFiles((File[])fileList.toArray());
      this.fileTransfer.fileTransfer();
    }
  }
  catch (Exception es)
  {
    es.printStackTrace();
  }
}

public void dragEnter(DropTargetDragEvent e)
{
  e.acceptDrag(3);
}

public void actionPerformed(ActionEvent e)
{
  if (e.getSource() == this.fontButton)
  {
    Constants.getFontDialog().setVisible(true);
  }
  else if (e.getSource() == this.emoticonButton)
  {
    Constants.getEmoticonDialog().setTarget(this);
    Constants.getEmoticonDialog().setLocation(getX() + 58, getY() + this.logContainer.getHeight() - 112);
    Constants.getEmoticonDialog().setVisible(true);
  }
  else if ((e.getSource() == this.nudgeButton) && (this.clients.size() < 2))
  {
    if (this.nudgeTimer.isUp())
    {
      log("Server", "You just sent a Nudge!", null, null);
      
      this.nudgeTimer.reset();
      if (Constants.isPlaySounds()) {
        Constants.getAudioPlayer().play(AudioPlayer.NUDGE);
      }
    }
    else
    {
      log("Server", "You can only send one nudge every 10 seconds!", null, null);
    }
  }
  else if (e.getSource() != this.addButton)
  {
    if (e.getSource() == this.transferButton) {
      this.fileTransfer.setVisible(true);
    }
  }
}

public void receiveNudge()
{
  log("Server", ((Client)this.clients.get(0)).getScreen_name() + " sent you a Nudge!", null, null);
  if (Constants.isPlaySounds()) {
    Constants.getAudioPlayer().play(AudioPlayer.NUDGE);
  }
}

public void updateStatus(boolean typing)
{
  if (typing) {
    this.statusLabel.setText(((Client)this.clients.get(0)).getScreen_name() + " is writing a message...");
  } else {
    this.statusLabel.setText("");
  }
}

public void setGroupChat()
{
  this.log.setSize(getWidth() - 150, getHeight() - 171);
  this.logContainer.setSize(getWidth() - 150, getHeight() - 171);
  
  this.chatBox.setSize(getWidth() - 15, this.chatBox.getHeight());
  this.chatBoxContainer.setBounds(0, this.logContainer.getHeight() + 21, getWidth() - 15, this.chatBoxContainer.getHeight());
  
  this.fontButton.setBounds(0, getHeight() - 171, 50, 20);
  this.emoticonButton.setBounds(51, getHeight() - 171, 25, 20);
  this.nudgeButton.setBounds(77, getHeight() - 171, 30, 20);
  this.addButton.setBounds(getWidth() - 57, getHeight() - 171, 20, 20);
  this.transferButton.setBounds(getWidth() - 36, getHeight() - 171, 20, 20);
  this.statusLabel.setBounds(115, getHeight() - 171, this.statusLabel.getWidth(), this.statusLabel.getHeight());
  
  this.clientContainer.setBounds(getWidth() - 150, 0, 135, getHeight() - 171);
  
  this.transferButton.setEnabled(false);
  this.nudgeButton.setEnabled(false);
  
  add(this.clientContainer);
}

public void updateClientList()
{
  this.clientsList.removeAllElements();
  for (Client c : this.clients) {
    this.clientsList.add(c.getScreen_name());
  }
  this.clientList.setListData(this.clientsList);
  this.clientList.repaint();
}

public void addClient(Client client)
{
  boolean add = true;
  for (Client c : this.clients) {
    if (c.getUser_id() == client.getUser_id()) {
      add = false;
    }
  }
  if (add) {
    this.clients.add(client);
  }
  if ((this.clients.size() > 1) && (!this.groupChat))
  {
    this.groupChat = true;
    setGroupChat();
  }
  updateClientList();
}

public void removeClient(int user_id)
{
  Client toRemove = null;
  for (Client c : this.clients) {
    if (c.getUser_id() == user_id) {
      toRemove = c;
    }
  }
  this.clients.remove(toRemove);
  toRemove = null;
  if ((this.clients.size() < 2) && (this.groupChat))
  {
    this.groupChat = false;
    remove(this.clientContainer);
    this.log.setSize(getWidth() - 15, getHeight() - 171);
    this.logContainer.setSize(getWidth() - 15, getHeight() - 171);
    
    this.transferButton.setEnabled(true);
    this.nudgeButton.setEnabled(true);
  }
  updateClientList();
  repaint();
}

public void windowClosing(WindowEvent e)
{
  this.clientSend = "";
  if (this.groupChat)
  {
    for (Client c : this.clients) {
      this.clientSend = (this.clientSend + c.getUser_id() + ",");
    }
    Constants.removePMWindow(this);
  }
}

public void componentHidden(ComponentEvent e) {}

public void componentMoved(ComponentEvent e) {}

public void windowLostFocus(WindowEvent e) {}

public void dragExit(DropTargetEvent arg0) {}

public void dragOver(DropTargetDragEvent arg0) {}

public void dropActionChanged(DropTargetDragEvent arg0) {}

public void dragGestureRecognized(DragGestureEvent arg0) {}

public void dragDropEnd(DragSourceDropEvent arg0) {}

public void dragEnter(DragSourceDragEvent e) {}

public void dragExit(DragSourceEvent arg0) {}

public void dragOver(DragSourceDragEvent arg0) {}

public void dropActionChanged(DragSourceDragEvent arg0) {}

public void windowActivated(WindowEvent arg0) {}

public void windowClosed(WindowEvent arg0) {}

public void windowDeactivated(WindowEvent arg0) {}

public void windowDeiconified(WindowEvent arg0) {}

public void windowIconified(WindowEvent arg0) {}

public void windowOpened(WindowEvent arg0) {}
}
