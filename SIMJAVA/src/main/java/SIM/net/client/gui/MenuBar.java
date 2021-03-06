package SIM.net.client.gui;

import DARTIS.construct;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import SIM.net.client.BrowserLaunch;
import SIM.net.client.Constants;
import SIM.net.client.networking.PacketHeaders;
import SIM.net.client.networking.PacketManager;
import SIM.net.client.networking.snapshot;

public class MenuBar implements java.awt.event.ActionListener
{
  private JMenuBar menuBar = new JMenuBar();
  
  private JMenu mnFile = new JMenu("File");
  private JMenu mnFormat = new JMenu("Settings");
  private JMenu mnHelp = new JMenu("Webcam");
  public JMenu mntmStatus = new JMenu("Encryption");
  
  private JMenuItem mntmOnline = new JMenuItem("Select KFB for this conversation");
  private JMenuItem mntmAddFriend = new JMenuItem("Add Friend");
  private JMenuItem mntmCheckRequests = new JMenuItem("Check F/R");
  private JMenuItem mntmAway = new JMenuItem("Reset KFB for this conversation");
  private JMenuItem mntmDND = new JMenuItem("Generate new KFB");
  private JMenuItem mntmProfile = new JMenuItem("Profile");
  private JMenuItem mntmAccount = new JMenuItem("Account");
  private JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
  private JMenuItem mntmExit = new JMenuItem("Exit");
  private JMenuItem mntmPreferences = new JMenuItem("Preferences");
  public JMenuItem mntmFont = new JMenuItem("Format Font");
  private JMenuItem mntmWebsite = new JMenuItem("Start Webcam Server");



  public MenuBar()
  {
    menuBar.add(mnFile);
    menuBar.add(mnFormat);
    menuBar.add(mntmStatus);
    menuBar.add(mnHelp);
    
    mnFile.add(mntmAddFriend);
    mnFile.add(mntmCheckRequests);
    mnFile.addSeparator();
    mnFile.add(mntmProfile);
    mnFile.add(mntmAccount);
    mnFile.addSeparator();
    mnFile.add(mntmDisconnect);
    mnFile.add(mntmExit);
    

    mnFormat.add(mntmPreferences);
    mnFormat.add(mntmFont);
    
    mnHelp.add(mntmWebsite);
    

    mntmStatus.add(mntmOnline);
    mntmStatus.add(mntmAway);
    mntmStatus.add(mntmDND);

    mntmAddFriend.setToolTipText("Add a friend");
    mntmCheckRequests.setToolTipText("Check friend requests");
    mntmProfile.setToolTipText("Update your profile");
    mntmAccount.setToolTipText("Update your account settings");
    mntmDisconnect.setToolTipText("Disconnect from this server");
    mntmExit.setToolTipText("Exit the program");
    

    mntmOnline.addActionListener(this);
    mntmAway.addActionListener(this);
    mntmDND.addActionListener(this);
    mntmAddFriend.addActionListener(this);
    mntmCheckRequests.addActionListener(this);
    mntmProfile.addActionListener(this);
    mntmAccount.addActionListener(this);
    mntmDisconnect.addActionListener(this);
    mntmExit.addActionListener(this);
    mntmPreferences.addActionListener(this);
    mntmFont.addActionListener(this);
    mntmWebsite.addActionListener(this);
  }
  
  @SuppressWarnings("deprecation")
public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == mntmOnline) {
      PersonalMessage topFrame = (PersonalMessage)SwingUtilities.getWindowAncestor(menuBar);
      JFileChooser fileChooser = new JFileChooser();
      if (fileChooser.showOpenDialog(null) == 0) {
        File file = fileChooser.getSelectedFile();
        FileReader fr = null;
        try {
          fr = new FileReader(file.getAbsolutePath());
        }
        catch (FileNotFoundException e1) {
          e1.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String kfbftext = "";
        try {
          while ((line = br.readLine()) != null)
          {
            kfbftext = kfbftext + line;
          }
        }
        catch (IOException e1)
        {
          e1.printStackTrace();
        }
        topFrame.kfbp = construct.load(kfbftext);
      }
      
    }
    else if (e.getSource() == mntmAway) {

      String[] kfbp = new String[100001];
    } else {
      JFileChooser fileChooser;
      if (e.getSource() == mntmDND)
      {
        String kfb = DARTIS.keys.generate();
        fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select where to save your Key File Blueprint file");
        if (fileChooser.showSaveDialog(null) == 0) {
          File file = fileChooser.getSelectedFile();
          try
          {
            FileWriter fileWriter = new FileWriter(file + ".kfb");
            fileWriter.write(kfb);
            fileWriter.close();
          }
          catch (IOException e1)
          {
            e1.printStackTrace();
          }
          
        }
        

      }
      else if (e.getSource() == mntmAddFriend) {
        String username = JOptionPane.showInputDialog("Enter username:");
        
        if (username != null) {
          
        }
        
      }
      else if (e.getSource() == mntmCheckRequests) {
        

      }
      else if (e.getSource() == mntmProfile) {
        Constants.getProfileEdit().update();
        Constants.getProfileEdit().setVisible(true);

      }
      else if (e.getSource() == mntmAccount) {
        Constants.getAccount().setVisible(true);

      }
      else if (e.getSource() == mntmDisconnect) {
        for (PersonalMessage pm : Constants.getPmWindows()) {
          pm.dispose();
        }
        
        Constants.getFriendList().dispose();
        FriendList.list.removeAll();
        DefaultListModel listModel = (DefaultListModel) FriendList.list.getModel();
        listModel.removeAllElements();
        FriendList.list.repaint();
        loginFrame.username = "";
        loginFrame.lblEnterYourPdglobal.setText("ENTER YOUR PDGLOBAL USERNAME AND PASSWORD BELOW TO SIGN IN");
        loginFrame.lblEnterYourPdglobal.setForeground(Color.black);
        loginFrame.passwordField.setText("");
        loginFrame.textField.setText("");
        loginFrame.frmSimSignIn.show();
      }
      else if (e.getSource() == mntmExit) {
        System.exit(0);

      }
      else if (e.getSource() == mntmFont) {
        Constants.getFontDialog().setVisible(true);

      }
      else if (e.getSource() == mntmPreferences) {
        Constants.getPreferencesGUI().setVisible(true);

      }
      else if (e.getSource() == mntmWebsite) {
			if (!snapshot.open) {
				Thread thread = new Thread() {
					public void run() {
						try {
							snapshot.main(new String[3]);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};

				thread.start();
			}
      }
    }
  }
  
  public JMenuBar getMenuBar() { return menuBar; }
}
