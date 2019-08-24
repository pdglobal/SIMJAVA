package SIM.net.client.gui;

import DARTIS.crypt;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import SIM.net.client.AudioPlayer;
import SIM.net.client.Client;
import SIM.net.client.Constants;




public class loginFrame
{
	public static JFrame frmSimSignIn;
  public static JTextField textField;
  public String ret = "";
  public static String authsession = "";
  public static int go = 0;
  public static DefaultListModel<String> listModel = new DefaultListModel<String>();
  public String contacts = "";
  public static String username = "";
  public static loginFrame window = new loginFrame();
  public static JPasswordField passwordField;
  public static JLabel lblEnterYourPdglobal;
  
  public static void main(String[] args)
  {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
    		   frmSimSignIn.setVisible(true);
      }
    });
  }
  


  private loginFrame()
  {
	Constants.setEmotions();
    initialize();
  }
  


  private Window initialize()
  {
	frmSimSignIn = new JFrame();
    frmSimSignIn.setTitle("SIM SIGN IN WINDOW");
    frmSimSignIn.getContentPane().setBackground(Color.WHITE);
    frmSimSignIn.setBackground(Color.WHITE);
    frmSimSignIn.setBounds(100, 100, 436, 675);
    frmSimSignIn.setDefaultCloseOperation(3);
    frmSimSignIn.getContentPane().setLayout(null);
    textField = new JTextField();
    textField.setBounds(15, 325, 384, 26);
    frmSimSignIn.getContentPane().add(textField);
    textField.setColumns(10);
    
    passwordField = new JPasswordField();
    passwordField.setBounds(15, 426, 384, 26);
    frmSimSignIn.getContentPane().add(passwordField);
    
    JLabel lblPassword = new JLabel("PASSWORD");
    lblPassword.setBounds(166, 390, 85, 20);
    frmSimSignIn.getContentPane().add(lblPassword);
    
    JLabel lblUsername = new JLabel("USERNAME");
    lblUsername.setBounds(166, 275, 85, 20);
    frmSimSignIn.getContentPane().add(lblUsername);
    
    JLabel lblCopyrightProgressive = new JLabel("COPYRIGHT 2013-2019 PROGRESSIVE DYNAMICS GLOBAL LIMITED COMPANY");
    lblCopyrightProgressive.setHorizontalAlignment(0);
    lblCopyrightProgressive.setFont(new Font("Neo Sans", 0, 12));
    lblCopyrightProgressive.setBounds(0, 599, 414, 20);
    frmSimSignIn.getContentPane().add(lblCopyrightProgressive);
    
    Image image = null;
    try {
        URL url = new URL("https://intranet.pdglobal.app/?sid=files_go&ID=logopng");
        image = ImageIO.read(url);
    } catch (IOException e) {
    	e.printStackTrace();
    }

    JLabel lblNewLabel = new JLabel(new ImageIcon(image), JLabel.CENTER);

  
    lblNewLabel.setBounds(15, 90, 384, 169);
    frmSimSignIn.getContentPane().add(lblNewLabel);
    
    lblEnterYourPdglobal = new JLabel("ENTER YOUR PDGLOBAL USERNAME AND PASSWORD BELOW TO SIGN IN");
    lblEnterYourPdglobal.setFont(new Font("Times New Roman", 0, 10));
    lblEnterYourPdglobal.setHorizontalAlignment(0);
    lblEnterYourPdglobal.setBounds(15, 16, 384, 46);
    frmSimSignIn.getContentPane().add(lblEnterYourPdglobal);
    
    JButton btnSignIn = new JButton("SIGN IN");
    btnSignIn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loginFrame.username = textField.getText();
        String password = passwordField.getText();
        try {
          ret = URLSrc.getURLSource("https://intranet.pdglobal.app/?sid=login&usr=" + URLEncoder.encode(loginFrame.username, "UTF-8").trim() + "&pwd=" + URLEncoder.encode(password, "UTF-8").trim());
        }
        catch (IOException ev) {
          ev.printStackTrace();
        }
        while ((ret == "") && (loginFrame.go < 10)) {
          try {
            Thread.sleep(1000L);
          }
          catch (InterruptedException ex) {
            ex.printStackTrace();
          }
          loginFrame.go += 1;
        }
        
        if (ret == "") {
          lblEnterYourPdglobal.setText("Error connecting to authentication server...");
        }
        
        if (ret.contains("invalid")) {
          lblEnterYourPdglobal.setText(ret);
          lblEnterYourPdglobal.setForeground(Color.red);
        } else {
          loginFrame.go = 1;
          lblEnterYourPdglobal.setText("Success loggin in, please wait...");
          lblEnterYourPdglobal.setForeground(Color.green);
          loginFrame.authsession = ret;
          System.out.print(ret);
          Constants.addFriend(new Client(0, password, password, 0, false, false));
          Constants.setFriendList(new FriendList());
          Constants.getFriendList().setVisible(true);
          
          SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
          Constants.setLogFileName(
            Constants.getLogFileLocation() + "\\" + loginFrame.username + "_" + sdf.format(new Date()) + ".log");
          
          Constants.getPreferencesGUI().save();
          
          frmSimSignIn.hide();
        }
        
        if (loginFrame.go == 1) {
          System.out.print("https://intranet.pdglobal.app/?sid=contactslist&session=" + loginFrame.authsession);
          try {
            contacts = URLSrc.getURLSource("https://intranet.pdglobal.app/?sid=contactslist&session=" + 
              loginFrame.authsession.replaceAll("0x", ""));
          }
          catch (IOException ec) {
            ec.printStackTrace();
          }
          
          String[] contacts_array = contacts.split("\\|");
          
          for (String contactData : contacts_array) {
            loginFrame.listModel.addElement(contactData);
            
          }      
          Executors.newSingleThreadExecutor().execute(new Runnable() {
        	    @Override
        	    public void run() {
        	    	try {
						//SIM.net.client.networking.audioHandler.main(new String[3]);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        	    }
        	});
          
          Executors.newSingleThreadExecutor().execute(new Runnable()
          {
            public void run() {
              for (;;) {
                try {
                  checkmsgs();
                }
                catch (Exception localException) {}
                try
                {
                  Thread.sleep(10L);
                }
                catch (InterruptedException e) {
                  e.printStackTrace();
                }
                
              }
            }
          });
        }
      }
    });
    btnSignIn.setBounds(140, 501, 156, 29);
    frmSimSignIn.getContentPane().add(btnSignIn);
   return frmSimSignIn;
  }
  
  String trim(String str, String s)
  {
    String res = s.length() >= str.length() ? str : str == null ? null : s == null ? str : str.replaceFirst(s, "");
    if ((res != null) && (s != null) && (res.length() >= s.length())) {
      return res.substring(res.length() - s.length(), res.length()).equals(s) ? res.substring(0, res.length() - s.length()) : res;
    }
    return res;
  }
  
  public void checkmsgs() throws IOException {
	    String send = URLSrc.getURLSource("https://intranet.pdglobal.app/?sid=getmsgs&session=" + authsession.replaceAll("0x", ""));
	    String[] msgs = send.split("!");
	    for (String msg : msgs) {
	      if (msg.length() > 2) {
	        System.out.println(msg);
	        String[] array = msg.split("`");
	        String usrid = "";
	        try {
	          usrid = URLSrc.getURLSource("https://intranet.pdglobal.app/?sid=getusrid&usr=" + array[0]);
	        }
	        catch (IOException e) {
	          e.printStackTrace();
	        }
	        String msgtext = array[1];
	        if (msgtext.substring(0, 2).toString().equals("0x")) {
	          System.out.println("Encrypted message inbound");
	          String encrypted = trim(msgtext, "0x").replace("E ", "E+");
	          System.out.println(msgtext);
	          System.out.println(encrypted);
	          msgtext = crypt.extract(encrypted, Constants.getPM(Integer.parseInt(usrid)).kfbp);
	        }
	        final PersonalMessage PM = Constants.getPM(Integer.parseInt(usrid));
	        if (msgtext.equals("{NUDGE}")) {
	          PM.toFront();
	          Executors.newSingleThreadExecutor().execute(new Runnable()
	          {
	            public void run() {
	              int rumble = 0;
	              

	              Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	              double width = screenSize.getWidth() / 2.0D;
	              double height = screenSize.getHeight() / 2.0D;
	              while (rumble < 99)
	              {
	                int x = 0;
	                int y = 0;
	                x = (int)(width + (Math.random() * 10.0D + Math.random() * (Math.random() * 2.5D * 3.0D) / 2.0D) * 10.0D) - PM.getWidth();
	                y = (int)(height + (Math.random() * 10.0D + Math.random() * (Math.random() * 2.5D * 3.0D) / 2.0D) * 10.0D) - PM.getHeight();
	                PM.setLocation(x, y);
	                PM.move(x, y);
	                
	                System.out.println(x + " " + y);
	                try
	                {
	                  Thread.sleep(23 * (int)(Math.random() + 1.0D));
	                }
	                catch (InterruptedException e) {
	                  e.printStackTrace();
	                }
	                rumble++;
	              }
	              
	            }
	            
	          });
	          PM.log(array[0].toUpperCase(), "sent you a Nudge!", null, null);
	          if (Constants.isPlaySounds()) {
	            Constants.getAudioPlayer().play(AudioPlayer.NUDGE);
	          }
	        } else {
	          try {
	            Constants.getPM(Integer.parseInt(usrid)).log(array[0].toUpperCase(), msgtext, "", "");
	            if ((Constants.isPlaySounds()) && (!Constants.getPM(Integer.parseInt(usrid)).isActive())) Constants.getAudioPlayer().play(AudioPlayer.MESSAGE);
	            Constants.getPM(Integer.parseInt(usrid)).setVisible(true);
	          } catch (Exception e) {
	            Constants.addPmWindow(new PersonalMessage(new Client(Integer.parseInt(usrid), authsession, array[0].toString(), 1, true, true)), false);
	            Constants.getPM(Integer.parseInt(usrid)).log(array[0].toUpperCase(), msgtext, "", "");
	            if ((Constants.isPlaySounds()) && (!Constants.getPM(Integer.parseInt(usrid)).isActive())) Constants.getAudioPlayer().play(AudioPlayer.MESSAGE);
	            System.out.println(array[1].toString());
	            if (Constants.isPlaySounds()) Constants.getAudioPlayer().play(AudioPlayer.MESSAGE);
	          }
	        }
	      }
	    }
	  }
}
