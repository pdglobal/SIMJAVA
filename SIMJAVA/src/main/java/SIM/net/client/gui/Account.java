package SIM.net.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import SIM.net.client.Constants;
import SIM.net.client.networking.PacketHeaders;
import SIM.net.client.networking.PacketManager;






public class Account
  extends JDialog
  implements ActionListener
{
  private static final long serialVersionUID = 2563111246123391750L;
  private JPanel passwordPanel = new JPanel();
  private Border passwordBorder = BorderFactory.createTitledBorder("Change Password");
  
  private JLabel currentPasswordLabel = new JLabel("Current Password:");
  private JLabel newPasswordLabel = new JLabel("New Password:");
  private JLabel newPasswordLabelConfrim = new JLabel("Confirm Password:");
  
  private JPasswordField currentPassword = new JPasswordField();
  private JPasswordField newPassword = new JPasswordField();
  private JPasswordField newPasswordConfirm = new JPasswordField();
  
  private JButton saveButton = new JButton("Save");
  private JButton cancelButton = new JButton("Cancel");
  
  public Account() {
    setTitle("Account Settings");
    setSize(357, 170);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(2);
    setLayout(null);
    setResizable(false);
    
    passwordPanel.setLayout(null);
    passwordPanel.setBounds(5, 5, 340, 100);
    passwordPanel.setBorder(passwordBorder);
    
    currentPasswordLabel.setBounds(10, 20, 120, 20);
    newPasswordLabel.setBounds(10, 45, 120, 20);
    newPasswordLabelConfrim.setBounds(10, 70, 120, 20);
    
    currentPassword.setBounds(130, 20, 200, 20);
    newPassword.setBounds(130, 45, 200, 20);
    newPasswordConfirm.setBounds(130, 70, 200, 20);
    
    passwordPanel.add(currentPasswordLabel);
    passwordPanel.add(newPasswordLabel);
    passwordPanel.add(newPasswordLabelConfrim);
    passwordPanel.add(currentPassword);
    passwordPanel.add(newPassword);
    passwordPanel.add(newPasswordConfirm);
    
    cancelButton.setBounds(7, 110, 100, 25);
    saveButton.setBounds(243, 110, 100, 25);
    
    cancelButton.addActionListener(this);
    saveButton.addActionListener(this);
    
    add(passwordPanel);
    add(cancelButton);
    add(saveButton);
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == cancelButton) {
      currentPassword.setText("");
      newPassword.setText("");
      newPasswordConfirm.setText("");
      dispose();

    }
    else if (e.getSource() == saveButton) {
     
        JOptionPane.showMessageDialog(null, 
          "Your passwords do not match.", 
          "Password Error", 
          0);
        
        currentPassword.setText("");
        newPassword.setText("");
        newPasswordConfirm.setText("");
      }
  }
}
