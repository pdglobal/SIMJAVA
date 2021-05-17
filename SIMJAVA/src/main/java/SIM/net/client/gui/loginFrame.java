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
import javax.swing.SwingConstants;




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
  private JTextField textField_1;
  private JTextField textField_2;
  private JTextField textField_3;
  private JLabel lblNewLabel_1;
  private JLabel lblNewLabel_2;
  private JLabel lblNewLabel_3;
  private JLabel lblNewLabel_4;
  private JLabel lblNewLabel_5;
  
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
  

  /**
	 * @wbp.parser.entryPoint
	 */

  private Window initialize()
  {
	frmSimSignIn = new JFrame();
    frmSimSignIn.setTitle("SECURE INSTANT MESSENGER - SIGN IN");
    frmSimSignIn.getContentPane().setBackground(Color.WHITE);
    frmSimSignIn.setBackground(Color.WHITE);
    frmSimSignIn.setBounds(100, 100, 725, 278);
    frmSimSignIn.setDefaultCloseOperation(3);
    frmSimSignIn.getContentPane().setLayout(null);
    textField = new JTextField();
    textField.setHorizontalAlignment(SwingConstants.CENTER);
    textField.setToolTipText("USERNAME");
    textField.setBounds(285, 70, 384, 26);
    frmSimSignIn.getContentPane().add(textField);
    textField.setColumns(10);
    
    passwordField = new JPasswordField();
    passwordField.setHorizontalAlignment(SwingConstants.CENTER);
    passwordField.setToolTipText("PASSWORD");
    passwordField.setBounds(285, 121, 384, 26);
    frmSimSignIn.getContentPane().add(passwordField);
    
    JLabel lblCopyrightProgressive = new JLabel("COPYRIGHT 2013-2019 PROGRESSIVE DYNAMICS GLOBAL LIMITED COMPANY");
    lblCopyrightProgressive.setHorizontalAlignment(0);
    lblCopyrightProgressive.setFont(new Font("Neo Sans", 0, 12));
    lblCopyrightProgressive.setBounds(0, 219, 709, 20);
    frmSimSignIn.getContentPane().add(lblCopyrightProgressive);
    
    Image image = null;
    try {
        URL url = new URL("https://intranet.pdglobal.app/?sid=files_go&ID=logopng");
        image = ImageIO.read(url);
    } catch (IOException e) {
    	e.printStackTrace();
    }
    
    lblEnterYourPdglobal = new JLabel("");
    lblEnterYourPdglobal.setFont(new Font("Times New Roman", Font.PLAIN, 18));
    lblEnterYourPdglobal.setHorizontalAlignment(0);
    lblEnterYourPdglobal.setBounds(0, 0, 709, 34);
    frmSimSignIn.getContentPane().add(lblEnterYourPdglobal);
    
    JButton btnSignIn = new JButton("SIGN IN");
    btnSignIn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        loginFrame.username = textField.getText();
        String password = passwordField.getText();
        String[] mfapassword = {"", "", ""};
        mfapassword[0] = textField_2.getText();
		 mfapassword[1] = textField_3.getText();
		 mfapassword[2] = textField_1.getText();

		 try {
	          ret = URLSrc.getURLSource("https://intranet.pdglobal.app/?sid=login&google2fa=" + mfapassword[2] + "&phone2fa=" + mfapassword[0] + "&email2fa=" + mfapassword[1] + "&usr=" + URLEncoder.encode(loginFrame.username, "UTF-8").trim() + "&pwd=" + URLEncoder.encode(password, "UTF-8").trim());
	        }
	        catch (IOException ev) {
	          ev.printStackTrace();
	        }
        
        if (ret == "") {
          lblEnterYourPdglobal.setText("ERROR CONNECTING TO AUTHENTICATION SERVER...");
        }
        System.out.println(ret);
        if (ret.contains("invalid") || ret.contains("code")) {
          lblEnterYourPdglobal.setText(ret);
          lblEnterYourPdglobal.setForeground(Color.red);
        } else {
        
        		
        		 
        	
          loginFrame.go = 1;
          lblEnterYourPdglobal.setText("LOG IN AUTHORIZED, PLEASE WAIT...");
          lblEnterYourPdglobal.setForeground(Color.green);
          loginFrame.authsession = ret;
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
         // Executors.newSingleThreadExecutor().execute(new Runnable() {
        	//    @Override
        	  //  public void run() {
        	    //	try {
				//		SIM.net.client.networking.audioServer.main(new String[3]);
				//	} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
				//	}
        	   // }
        //	});
          
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
    btnSignIn.setBounds(285, 179, 384, 29);
    frmSimSignIn.getContentPane().add(btnSignIn);
    
    textField_1 = new JTextField();
    textField_1.setToolTipText("        \t\t mfapassword[0] = \"\";        \t\t mfapassword[0] = \"\";");
    textField_1.setColumns(10);
    textField_1.setBounds(10, 124, 86, 20);
    frmSimSignIn.getContentPane().add(textField_1);
    
    textField_2 = new JTextField();
    textField_2.setToolTipText("        \t\t mfapassword[0] = \"\";        \t\t mfapassword[0] = \"\";");
    textField_2.setColumns(10);
    textField_2.setBounds(10, 167, 86, 20);
    frmSimSignIn.getContentPane().add(textField_2);
    
    textField_3 = new JTextField();
    textField_3.setToolTipText("        \t\t mfapassword[0] = \"\";        \t\t mfapassword[0] = \"\";");
    textField_3.setColumns(10);
    textField_3.setBounds(10, 208, 86, 20);
    frmSimSignIn.getContentPane().add(textField_3);
            
            lblNewLabel_1 = new JLabel("AUTH CODE");
            lblNewLabel_1.setBounds(10, 107, 86, 14);
            frmSimSignIn.getContentPane().add(lblNewLabel_1);
            
                JLabel lblNewLabel = new JLabel(new ImageIcon(image), JLabel.CENTER);
                
                  
                    lblNewLabel.setBounds(-21, 11, 384, 169);
                    frmSimSignIn.getContentPane().add(lblNewLabel);
                    
                    lblNewLabel_2 = new JLabel("SMS CODE");
                    lblNewLabel_2.setBounds(10, 152, 86, 14);
                    frmSimSignIn.getContentPane().add(lblNewLabel_2);
                    
                    lblNewLabel_3 = new JLabel("EMAIL CODE");
                    lblNewLabel_3.setBounds(10, 194, 86, 14);
                    frmSimSignIn.getContentPane().add(lblNewLabel_3);
                    
                    lblNewLabel_4 = new JLabel("PDGLOBAL.APP USERNAME");
                    lblNewLabel_4.setHorizontalAlignment(SwingConstants.TRAILING);
                    lblNewLabel_4.setBounds(488, 55, 181, 14);
                    frmSimSignIn.getContentPane().add(lblNewLabel_4);
                    
                    lblNewLabel_5 = new JLabel("PASSWORD");
                    lblNewLabel_5.setHorizontalAlignment(SwingConstants.TRAILING);
                    lblNewLabel_5.setBounds(583, 107, 86, 14);
                    frmSimSignIn.getContentPane().add(lblNewLabel_5);
   return frmSimSignIn;
  }
  
  public static String trim(String str, String s)
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
	          String encrypted = trim(msgtext, "0x");
	          System.out.println(msgtext);
	          System.out.println(encrypted);
	          msgtext = new String(crypt.extract(encrypted, Constants.getPM(Integer.parseInt(usrid)).kfbp));
	        }
	        final PersonalMessage PM = Constants.getPM(Integer.parseInt(usrid));
	        if (msgtext.equals("*NUDGE*")) {
	          PM.toFront();
	          PM.setSize(450, 450);
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
