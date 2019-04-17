package SIM.net.client.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import SIM.net.client.Constants;
import SIM.net.client.FileWriter;






public class PreferenceDialog
  extends JDialog
  implements ChangeListener, ActionListener, MouseListener
{
  private static final long serialVersionUID = -1817090973423261099L;
  private JCheckBox fontCheckBox = new JCheckBox("View Custom Fonts");
  private JCheckBox logCheckBox = new JCheckBox("Save Log Files");
  private JCheckBox trimCheckBox = new JCheckBox("Trim Chat Log");
  private JCheckBox soundCheckBox = new JCheckBox("Play Sounds");
  private JCheckBox showFileBox = new JCheckBox("Show File Transfer");
  private JCheckBox timeFormatBox = new JCheckBox("24 Hour Time");
  private JCheckBox playNudgeBox = new JCheckBox("Play Nudges");
  
  private JLabel showLabel = new JLabel("After ");
  private JLabel showLabel2 = new JLabel(" lines.");
  
  private JTextField lineField = new JTextField("1000");
  
  private JLabel logLabel = new JLabel("Log location:");
  private JLabel downloadLabel = new JLabel("Download location:");
  
  private JTextField logField = new JTextField();
  private JTextField downloadField = new JTextField();
  
  private JButton saveButton = new JButton("Save");
  private JButton cancelButton = new JButton("Cancel");
  
  private ArrayList<String> settings = new ArrayList();
  
  private JFileChooser fileChooser = new JFileChooser();
  


  public PreferenceDialog()
  {
    checkSetup();
    
    setTitle("Preferences");
    setSize(353, 227);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(2);
    setLayout(null);
    setResizable(false);
    
    fontCheckBox.setBounds(10, 10, 140, 20);
    logCheckBox.setBounds(10, 35, 120, 20);
    showFileBox.setBounds(10, 60, 140, 20);
    timeFormatBox.setBounds(10, 85, 120, 20);
    
    trimCheckBox.setBounds(200, 10, 120, 20);
    showLabel.setBounds(200, 35, 40, 20);
    lineField.setBounds(240, 35, 40, 20);
    showLabel2.setBounds(285, 35, 40, 20);
    
    trimCheckBox.addChangeListener(this);
    
    soundCheckBox.setBounds(200, 60, 120, 20);
    playNudgeBox.setBounds(200, 85, 120, 20);
    
    showLabel.setEnabled(false);
    lineField.setEnabled(false);
    showLabel2.setEnabled(false);
    
    logLabel.setBounds(10, 110, 100, 20);
    downloadLabel.setBounds(10, 135, 120, 20);
    
    logField.setBounds(125, 110, 210, 20);
    downloadField.setBounds(125, 135, 210, 20);
    
    logField.addMouseListener(this);
    downloadField.addMouseListener(this);
    
    saveButton.setBounds(235, 165, 100, 25);
    cancelButton.setBounds(10, 165, 100, 25);
    
    saveButton.addActionListener(this);
    cancelButton.addActionListener(this);
    

    fileChooser.setFileSelectionMode(1);
    fileChooser.setDialogTitle("Select Location");
    
    add(fontCheckBox);
    add(logCheckBox);
    add(showFileBox);
    add(timeFormatBox);
    add(trimCheckBox);
    add(soundCheckBox);
    add(playNudgeBox);
    add(showLabel);
    add(lineField);
    add(showLabel2);
    add(logLabel);
    add(downloadLabel);
    add(logField);
    add(downloadField);
    add(saveButton);
    add(cancelButton);
    
    readSettings();
  }
  
  public void stateChanged(ChangeEvent e)
  {
    if (e.getSource() == trimCheckBox) {
      if (trimCheckBox.isSelected()) {
        showLabel.setEnabled(true);
        lineField.setEnabled(true);
        showLabel2.setEnabled(true);
      } else {
        showLabel.setEnabled(false);
        lineField.setEnabled(false);
        showLabel2.setEnabled(false);
      }
    }
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == saveButton) {
      save();
    } else if (e.getSource() == cancelButton) {
      dispose();
    }
  }
  


  public void save()
  {
    update();
    dispose();
  }
  


  public void update()
  {
    Constants.setFontEnabled(fontCheckBox.isSelected());
    Constants.setSaveLogFiles(logCheckBox.isSelected());
    Constants.setTrimChatLog(trimCheckBox.isSelected());
    Constants.setLineCount(Integer.parseInt(lineField.getText()));
    Constants.setPlaySounds(soundCheckBox.isSelected());
    Constants.setPlayNudges(playNudgeBox.isSelected());
    Constants.setShowFileTransfer(showFileBox.isSelected());
    Constants.setTimeFormat(timeFormatBox.isSelected());
    Constants.setLineCount(Integer.parseInt(lineField.getText()));
    Constants.setLogFileLocation(logField.getText());
    Constants.setDownloadFileLocation(downloadField.getText());
  }
  

  public void readSettings()
  {
	  
  }
  



  public static void checkSetup()
  {
    String[] dirs = { "Underground IM", "Underground IM\\Logs", "Underground IM\\Downloads" };
    try
    {
      String[] arrayOfString1 = dirs;int j = dirs.length; for (int i = 0; i < j; i++) { String s = arrayOfString1[i];
        File f = new File(System.getProperty("user.home") + "\\" + s);
        if (!f.exists()) {
          f.mkdir();
        }
      }
      if (!Constants.getPrefFile().exists()) {
        Constants.getPrefFile().createNewFile();
        

        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Font Enabled;\t\ttrue", false, false);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Save Log Files;\t\tfalse", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Trim Log;\t\ttrue", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Trim After;\t\t1000", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Play Sounds;\t\ttrue", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Play Nudges;\t\ttrue", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Show File Transfer;\ttrue", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "24 Hour Time;\t\tfalse", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Log Location;\t\t" + Constants.getUserHome() + "\\Logs", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Download Location;\t" + Constants.getUserHome() + "\\Downloads", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Server;\t\t64.94.101.216:5632", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Username;\t\t", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font Start;\t", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font End;\t\t", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font Face;\t\tDialog", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font Size;\t\t3", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font Modifiers;\t0", true, true);
        FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font Colour;\t333333", true, true);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void mouseClicked(MouseEvent e)
  {
    if (e.getSource() == logField) {
      int option = fileChooser.showOpenDialog(this);
      
      if (option == 0) {
        logField.setText(fileChooser.getSelectedFile().getAbsolutePath());
      }
      
    }
    else if (e.getSource() == downloadField) {
      int option = fileChooser.showOpenDialog(this);
      
      if (option == 0) {
        downloadField.setText(fileChooser.getSelectedFile().getAbsolutePath());
      }
    }
  }
  
  public void mouseEntered(MouseEvent arg0) {}
  
  public void mouseExited(MouseEvent arg0) {}
  
  public void mousePressed(MouseEvent arg0) {}
  
  public void mouseReleased(MouseEvent arg0) {}
}
