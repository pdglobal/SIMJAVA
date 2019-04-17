package SIM.net.client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import SIM.net.client.Constants;
import SIM.net.client.Profile;
import SIM.net.client.networking.PacketHeaders;
import SIM.net.client.networking.PacketManager;





public class ProfileEdit
  extends JDialog
  implements ActionListener
{
  private static final long serialVersionUID = 3541155790509935847L;
  private JLabel usernameLabel = new JLabel("Username:");
  private JLabel screennameLabel = new JLabel("Screen Name:");
  private JLabel emailLabel = new JLabel("Email Address:");
  private JLabel ageLabel = new JLabel("Age:");
  private JLabel sexLabel = new JLabel("Sex:");
  private JLabel locationLabel = new JLabel("Location:");
  private JLabel aboutLabel = new JLabel("About:");
  
  private String[] gender = { "N\\A", "Male", "Female" };
  
  private JTextField username = new JTextField();
  private JTextField screenname = new JTextField();
  private JTextField email = new JTextField();
  private JTextField age = new JTextField();
  private JComboBox<String> sex = new JComboBox(gender);
  private JTextField location = new JTextField();
  private JTextArea about = new JTextArea();
  private JScrollPane aboutContainer = new JScrollPane();
  
  private JButton saveButton = new JButton("Save");
  private JButton cancelButton = new JButton("Cancel");
  


  public ProfileEdit()
  {
    
    setTitle("Edit Profile");
    setSize(437, 313);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(2);
    setLayout(null);
    setResizable(false);
    
    usernameLabel.setBounds(10, 10, 100, 20);
    screennameLabel.setBounds(10, 35, 100, 20);
    emailLabel.setBounds(10, 60, 100, 20);
    ageLabel.setBounds(10, 85, 100, 20);
    sexLabel.setBounds(10, 110, 100, 20);
    locationLabel.setBounds(10, 135, 100, 20);
    aboutLabel.setBounds(10, 160, 100, 20);
    
    username.setBounds(120, 10, 300, 20);
    username.setEditable(false);
    screenname.setBounds(120, 35, 300, 20);
    email.setBounds(120, 60, 300, 20);
    age.setBounds(120, 85, 300, 20);
    sex.setBounds(120, 110, 300, 20);
    location.setBounds(120, 135, 300, 20);
    
    about.setBounds(0, 0, 300, 130);
    about.setFont(new Font("Dialog", 0, 12));
    about.setLineWrap(true);
    about.setWrapStyleWord(true);
    aboutContainer = new JScrollPane(about, 
      22, 
      31);
    aboutContainer.setBounds(120, 160, 300, 80);
    
    cancelButton.setBounds(10, 250, 100, 25);
    saveButton.setBounds(320, 250, 100, 25);
    
    cancelButton.addActionListener(this);
    saveButton.addActionListener(this);
    
    add(usernameLabel);
    add(screennameLabel);
    add(emailLabel);
    add(ageLabel);
    add(sexLabel);
    add(locationLabel);
    add(aboutLabel);
    add(username);
    add(screenname);
    add(email);
    add(age);
    add(sex);
    add(location);
    add(aboutContainer);
    add(cancelButton);
    add(saveButton);
  }
  
  public void update() {
   
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == cancelButton) {
      update();
      dispose();

    }
    else if (e.getSource() == saveButton) {
      Constants.getUserProfile().setScreen_name(screenname.getText());
      Constants.getUserProfile().setEmail(email.getText());
      
      if (!age.getText().contains("N\\A")) {
        Constants.getUserProfile().setAge(Byte.valueOf(age.getText()).byteValue());
      } else {
        Constants.getUserProfile().setAge((byte)-1);
      }
      Constants.getUserProfile().setSex((byte)sex.getSelectedIndex());
      Constants.getUserProfile().setLocation(location.getText());
      Constants.getUserProfile().setAbout(about.getText());
      
      dispose();
    }
  }
}
