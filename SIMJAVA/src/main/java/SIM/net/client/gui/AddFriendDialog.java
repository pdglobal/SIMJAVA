package SIM.net.client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import SIM.net.client.Client;
import SIM.net.client.Constants;
import SIM.net.client.ImageRenderer;
import SIM.net.client.networking.PacketHeaders;
import SIM.net.client.networking.PacketManager;




public class AddFriendDialog
  extends JDialog
  implements ActionListener, WindowFocusListener
{
  private static final long serialVersionUID = 9018855409823926066L;
  private PersonalMessage pm;
  public JTable friendList;
  private JScrollPane friendListContainer;
  private DefaultTableModel friendListData;
  private JButton addFriendButton = new JButton("Add Friend(s)");
  private String clientSend = "";
  private String clientList = "";
  


  public AddFriendDialog()
  {
    setTitle("Online Friends");
    setSize(250, 300);
    setDefaultCloseOperation(2);
    setLayout(null);
    setResizable(false);
    setLocationRelativeTo(null);
    addWindowFocusListener(this);
    

    friendListData = new DefaultTableModel(new Object[0][0], new String[] { "Status", "Friends" })
    {
      private static final long serialVersionUID = -3666163903937562582L;
      
      public boolean isCellEditable(int a, int b) {
        return false;
      }
      
    };
    friendList = new JTable(friendListData);
    friendList.setBounds(0, 0, getWidth() - 5, getHeight() - 60);
    friendList.setFont(new Font("Arial", 0, 12));
    friendList.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
    
    friendListContainer = new JScrollPane(friendList, 
      20, 
      30);
    friendListContainer.setBounds(0, 0, getWidth() - 5, getHeight() - 68);
    
    addFriendButton.setBounds(5, 237, 234, 30);
    addFriendButton.addActionListener(this);
    

    add(friendListContainer);
    add(addFriendButton);
  }
  




  public void setTarget(PersonalMessage pm)
  {
    this.pm = pm;
  }
  


  public void updateFriends()
    throws IOException
  {
    System.out.print("https://intranet.pdglobal.net/?sid=contactslist&session=" + loginFrame.authsession);
    String contacts = "";
    contacts = URLSrc.getURLSource("https://intranet.pdglobal.net/?sid=contactslist&session=" + loginFrame.authsession.replaceAll("0x", ""));
    
    String[] contacts_array = contacts.split("\\|");
    
    int count = contacts_array.length;
    
    for (int i = 0; i < count; i++) {
      friendListData.addRow(new Object[] { contacts_array[i] });
    }
    
    repaint();
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == addFriendButton) {

    }
  }
  
  public void windowLostFocus(WindowEvent e)
  {
    dispose();
  }
  
  public void windowGainedFocus(WindowEvent e) {}
}
