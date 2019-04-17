package SIM.net.client.gui;

import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import SIM.net.client.Constants;
import SIM.net.client.Profile;









public class ProfileView
  extends JDialog
{
  private static final long serialVersionUID = 3541155790509935847L;
  private Profile profile;
  private JLabel usernameLabel = new JLabel("Username:");
  private JLabel screennameLabel = new JLabel("Screen Name:");
  private JLabel emailLabel = new JLabel("Email Address:");
  private JLabel ageLabel = new JLabel("Age:");
  private JLabel sexLabel = new JLabel("Sex:");
  private JLabel locationLabel = new JLabel("Location:");
  private JLabel aboutLabel = new JLabel("About:");
  private JLabel madeLabel = new JLabel("Made Date:");
  
  private JLabel username = new JLabel();
  private JLabel screenname = new JLabel();
  private JLabel email = new JLabel();
  private JLabel age = new JLabel();
  private JLabel sex = new JLabel();
  private JLabel location = new JLabel();
  private JTextArea about = new JTextArea();
  private JScrollPane aboutContainer = new JScrollPane();
  private JLabel made = new JLabel();
  


  public ProfileView()
  {
    
    setTitle("Username's Profile");
    setSize(437, 297);
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
    madeLabel.setBounds(10, 240, 100, 20);
    
    username.setBounds(120, 10, 300, 20);
    screenname.setBounds(120, 35, 300, 20);
    email.setBounds(120, 60, 300, 20);
    age.setBounds(120, 85, 300, 20);
    sex.setBounds(120, 110, 300, 20);
    location.setBounds(120, 135, 300, 20);
    made.setBounds(120, 240, 300, 20);
    
    about.setBounds(0, 0, 300, 130);
    about.setFont(new Font("Dialog", 0, 12));
    about.setEditable(false);
    about.setLineWrap(true);
    about.setWrapStyleWord(true);
    aboutContainer = new JScrollPane(about, 
      22, 
      31);
    aboutContainer.setBounds(120, 160, 300, 80);
    
    add(usernameLabel);
    add(screennameLabel);
    add(emailLabel);
    add(ageLabel);
    add(sexLabel);
    add(locationLabel);
    add(aboutLabel);
    add(madeLabel);
    add(username);
    add(screenname);
    add(email);
    add(age);
    add(sex);
    add(location);
    add(aboutContainer);
    add(made);
  }
  
  public static void main(String[] args) {
    new ProfileView();
  }
  
  public Profile getProfile() {
    return profile;
  }
  
  public void setProfile(Profile profile) {
}

}