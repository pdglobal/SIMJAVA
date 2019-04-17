package SIM.net.client.gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import SIM.net.client.AudioPlayer;
import SIM.net.client.Client;
import SIM.net.client.Constants;

















public class FriendList
  extends JFrame
{
  private static final long serialVersionUID = -2834540331055941876L;
  public JTable friendList;
  private JScrollPane friendListContainer;
  public DefaultTableModel friendListData;
  public static JList list;
  public PopupMenu popupMenu;
  public MenuBar menuBar = new MenuBar();
 
  public FriendList()
  {
    setTitle("SIM - Secure Instant Messenger");
    setSize(300, 400);
    setLocationRelativeTo(null);
    setResizable(true);
    setJMenuBar(menuBar.getMenuBar());
    setDefaultCloseOperation(3);
    
    list = new JList(loginFrame.listModel);
    DefaultListCellRenderer renderer =  (DefaultListCellRenderer)list.getCellRenderer();  
    renderer.setHorizontalAlignment(JLabel.CENTER);  
    list.setSelectionMode(0);
    list.setFont(new Font("Tahoma", 1, 30));
    list.setBounds(0, 0, 300, 400);
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(list);
    getContentPane().add(scrollPane);
    

    MouseListener mouseListener = new MouseAdapter() {
      public void mouseClicked(MouseEvent mouseEvent) {
        JList<String> theList = (JList)mouseEvent.getSource();
        if (mouseEvent.getClickCount() == 1) {
          String usrid = "";
          
          try {
            usrid = URLSrc.getURLSource("https://intranet.pdglobal.net/?sid=getusrid&usr=" + FriendList.list.getSelectedValue().toString());
          }
          catch (IOException e) {
            e.printStackTrace();
          }
          try {
            Constants.getPM(Integer.parseInt(usrid)).requestFocus();
            Constants.getPM(Integer.parseInt(usrid)).show();
          } catch (Exception e) {
            Constants.addPmWindow(new PersonalMessage(new Client(Integer.parseInt(usrid), loginFrame.authsession, FriendList.list.getSelectedValue().toString(), 1, true, true)), false);
            System.out.println(FriendList.list.getSelectedValue().toString());
          }
          
        }
      }
    };
    list.addMouseListener(mouseListener);
  }
  



  public void updateFriends()
  {
    int count = friendListData.getRowCount();
    boolean online = false;
    
    for (int i = 0; i < count; i++) {
      friendListData.removeRow(0);
    }
    
    for (Client c : Constants.getFriends()) {
      if (c.isOnline()) {
        switch (c.getStatus()) {
        case 0: 
          if (!c.isPlayedSound()) {
            online = true;
            c.setPlayedSound(true);
          }
          friendListData.addRow(new Object[] {c.getUsername() });
          break;
        case 1: 
          friendListData.addRow(new Object[] {c.getUsername() });
          break;
        case 2: 
          friendListData.addRow(new Object[] {c.getUsername() });
        }
        
      } else {
        
      }
    }
    
    repaint();
    
    if ((Constants.isPlaySounds()) && (online)) {
      Constants.getAudioPlayer().play(AudioPlayer.ONLINE);
    }
  }
}
