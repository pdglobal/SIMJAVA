package SIM.net.client.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import SIM.net.client.AudioPlayer;
import SIM.net.client.Client;
import SIM.net.client.Constants;
import SIM.net.client.networking.PacketHeaders;
import SIM.net.client.networking.PacketManager;






public class FileTransfer
  extends JFrame
  implements ActionListener, KeyListener, MouseListener, ComponentListener
{
  private static final long serialVersionUID = -7022069724226966364L;
  public Client client;
  private JPopupMenu menu = new JPopupMenu();
  private JMenuItem deleteMenu = new JMenuItem("Delete");
  private ImageIcon deleteIcon = new ImageIcon(PopupMenu.class.getResource("/icons/delete.png"));
  
  private JList<String> transferList = new JList();
  private JScrollPane transferContainer;
  private Border transferBorder = BorderFactory.createTitledBorder("Files To Transfer");
  
  private JList<String> transferLog = new JList();
  private JScrollPane transferLogContainer;
  private Border transferLogBorder = BorderFactory.createTitledBorder("Transfer Log");
  
  private JLabel statusLabel = new JLabel("Status:");
  private JLabel statusLabel2 = new JLabel("Select file(s) to transfer.");
  private JProgressBar progressBar = new JProgressBar();
  
  public JButton pickFile = new JButton("Pick File(s)");
  public JButton sendFile = new JButton("Send File(s)");
  
  private JFileChooser fileChooser = new JFileChooser();
  
  private ArrayList<File> transferFiles = new ArrayList();
  private Vector<String> fileNames = new Vector();
  private Vector<String> transLog = new Vector();
  public boolean transfer;
  public boolean host; public boolean cancelled = false;
  
  private FileOutputStream fileOut;
  private String fileName;
  private long fileLength;
  private long startTime;
  private long endTime;
  private long total;
  private byte[] bytes;
  
  public FileTransfer(Client client)
  {
    this.client = client;
    
    setTitle(client.getUsername() + " - File Transfer");
    setSize(500, 390);
    setLocationRelativeTo(null);
    setLayout(null);
    setResizable(true);
    setDefaultCloseOperation(2);
    addComponentListener(this);
    setMinimumSize(new Dimension(500, 250));
    

    deleteMenu.setIcon(deleteIcon);
    menu.add(deleteMenu);
    deleteMenu.addActionListener(this);
    

    transferList.setBounds(0, 0, 150, 150);
    transferList.setBorder(transferBorder);
    transferList.addKeyListener(this);
    transferList.addMouseListener(this);
    
    transferContainer = new JScrollPane(transferList, 
      22, 
      30);
    transferContainer.setBounds(5, 5, 235, 250);
    

    transferLog.setBounds(0, 0, 150, 150);
    transferLog.setBorder(transferLogBorder);
    
    transferLogContainer = new JScrollPane(transferLog, 
      22, 
      30);
    transferLogContainer.setBounds(245, 5, 235, 250);
    

    statusLabel.setBounds(5, 260, 60, 20);
    statusLabel2.setBounds(50, 260, 470, 20);
    statusLabel2.setFont(new Font("Dialog", 0, 12));
    progressBar.setBounds(5, 285, 473, 25);
    progressBar.setStringPainted(true);
    

    pickFile.setBounds(5, 320, 235, 25);
    sendFile.setBounds(245, 320, 235, 25);
    
    pickFile.addActionListener(this);
    sendFile.addActionListener(this);
    

    fileChooser.setMultiSelectionEnabled(true);
    fileChooser.setDialogTitle("Select File(s)");
    
    add(transferContainer);
    add(transferLogContainer);
    add(statusLabel);
    add(statusLabel2);
    add(progressBar);
    add(pickFile);
    add(sendFile);
  }
  

  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == pickFile) {
      int option = fileChooser.showOpenDialog(this);
      
      if (option == 0) {
        addFiles(fileChooser.getSelectedFiles());
      }
      
    }
    else if (e.getSource() == sendFile) {
      if (!transfer) {
        fileTransfer();
      } else {
        transfer = false;
       
      }
      
    }
    else if (e.getSource() == deleteMenu) {
      removeFiles();
    }
  }
  




  public void addFiles(File[] files)
  {
    for (File f : files) {
      transferFiles.add(f);
      fileNames.add(f.getName());
    }
    
    transferList.setListData(fileNames);
    transferList.repaint();
  }
  


  public void fileTransfer()
  {
    sendFile.setEnabled(false);
    String fileList = "";
    
    for (File f : transferFiles) {
      fileList = fileList + f.getName() + " - " + Constants.getMod(f.length()) + "\n";
    }
    
    
    Constants.getPM(client.getUser_id()).log("Server", "Waiting for user to accept or decline file transfer", null, null);
  }
  


  public void removeFiles()
  {
    if (transferList.getSelectedValue() != null) {
      List<String> files = transferList.getSelectedValuesList();
      for (String s : files) {
        fileNames.remove(s);
        removeFile(s.split("-")[0].trim());
      }
      
      transferList.setListData(fileNames);
      transferList.repaint();
    }
  }
  




  public void removeFile(String name)
  {
    for (File f : transferFiles) {
      if (f.getName().equals(name)) {
        transferFiles.remove(f);
        break;
      }
    }
  }
  


  public void keyPressed(KeyEvent e)
  {
    switch (e.getKeyCode()) {
    case 127: 
      removeFiles();
    }
    
  }
  
  public void mouseReleased(MouseEvent e)
  {
    if ((e.getSource() == transferList) && (e.isPopupTrigger())) {
      menu.show(e.getComponent(), e.getX(), e.getY());
    }
  }
  


  public void sendFiles()
  {
    transfer = true;
    host = true;
    cancelled = false;
    sendFile.setEnabled(true);
    pickFile.setEnabled(false);
    sendFile.setText("Cancel");
    
    Thread thread = new Thread()
    {
      public void run() {
        ArrayList<String> filesToRemove = new ArrayList();
        for (File f : transferFiles) {
          if (transfer) {
            try {
              fileName = f.getName();
              FileTransfer.this.fileLength = f.length();
              statusLabel2.setText("Uploading " + f.getName());
              transLog.add("Uploading " + f.getName() + " (" + Constants.getMod(f.length()) + ")");
              transferLog.setListData(transLog);
              transferLog.repaint();
              Constants.getPM(client.getUser_id()).log("Server", "Uploading " + f.getName() + " (" + Constants.getMod(f.length()) + ")", null, null);
              progressBar.setValue(0);
              
              FileInputStream in = null;
              try {
                in = new FileInputStream(f.getAbsoluteFile());
              } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(null, 
                  "Please read the below error message:\n\n" + 
                  Constants.getStack(e1), 
                  "Transfer Error", 
                  0);
              }
            

              int read = 0;
              long total = 0L;
              byte[] tmp = new byte[10000];
              int fileLength = (int)f.length();
              String fileL = Constants.getMod(fileLength);
              
              long starttime = System.currentTimeMillis();
              try {
                while (((read = in.read(tmp)) != -1) && (transfer)) {
                  total += read;
                  
                 
                  progressBar.setValue((int)(total * 100L / fileLength));
                  
                  long elapsedTime = System.currentTimeMillis() - starttime;
                  }
                
                if (!transfer) {
                  progressBar.setValue(0);
                  transLog.add("Cancelled " + f.getName());
                  transferLog.setListData(transLog);
                  transferLog.repaint();
                  Constants.getPM(client.getUser_id()).log("Server", "Cancelled " + f.getName(), null, null);
                  statusLabel2.setText("Cancelled " + f.getName());
                }
              } catch (IOException e) {
                JOptionPane.showMessageDialog(null, 
                  "Please read the below error message:\n\n" + 
                  Constants.getStack(e), 
                  "Transfer Error", 
                  0);
              }
              
              in.close();
              if (transfer) {
                long endtime = (System.currentTimeMillis() - starttime) / 1000L;
                statusLabel2.setText("Complete");
                progressBar.setValue(0);
                
                transLog.add("Uploaded " + f.getName() + " (" + Constants.getMod(f.length()) + ")" + " in " + endtime + " seconds");
                Constants.getPM(client.getUser_id()).log("Server", "Uploaded " + f.getName() + " (" + Constants.getMod(f.length()) + ")" + " in " + endtime + " seconds", null, null);
                transferLog.setListData(transLog);
                transferLog.repaint();
                
                
                filesToRemove.add(f.getName());
                
                if (Constants.isPlaySounds())
                  Constants.getAudioPlayer().play(AudioPlayer.ALERT);
              }
            } catch (Exception es) {
              JOptionPane.showMessageDialog(null, 
                "Please read the below error message:\n\n" + 
                Constants.getStack(es), 
                "Transfer Error", 
                0);
              transfer = false;
              pickFile.setEnabled(true);
              sendFile.setText("Send File(s)");
            }
          }
        }
        

        for (String s : filesToRemove) {
          fileNames.remove(s);
          removeFile(s);
        }
        
        transferList.setListData(fileNames);
        transferList.repaint();
        
        transferLog.setListData(transLog);
        transferLog.repaint();
        
        transfer = false;
        host = false;
        pickFile.setEnabled(true);
        sendFile.setText("Send File(s)");
      }
      
    };
    thread.start();
    thread = null;
  }
  



  public void fileStart(String fileName, long fileLength)
  {
    try
    {
      if (!transfer) {
        total = 0L;
        progressBar.setValue(0);
        
        transfer = true;
        cancelled = false;
        pickFile.setEnabled(false);
        sendFile.setText("Cancel");
        
        this.fileName = fileName;
        this.fileLength = fileLength;
        fileOut = new FileOutputStream(Constants.getDownloadFileLocation() + "\\" + fileName);
        
        transLog.add("Downloading " + fileName + " (" + Constants.getMod(fileLength) + ")");
        transferLog.setListData(transLog);
        transferLog.repaint();
        Constants.getPM(client.getUser_id()).log("Server", "Downloading " + fileName + " (" + Constants.getMod(fileLength) + ")", null, null);
        statusLabel2.setText("Downloading " + fileName);
        
        startTime = System.currentTimeMillis();
      }
    } catch (FileNotFoundException e) {
      JOptionPane.showMessageDialog(null, 
        "Please read the below error message:\n\n" + 
        Constants.getStack(e), 
        "Transfer Error", 
        0);
    }
  }
  



  public void fileSend(String filePart)
  {
    try
    {
      if (transfer) {
        bytes = Constants.stringToBytes(filePart);
        total += bytes.length;
        
        fileOut.write(bytes);
        
        progressBar.setValue((int)(total * 100L / fileLength));
        long elapsedTime = System.currentTimeMillis() - startTime;
        } else {
        fileCancel();
      }
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, 
        "Please read the below error message:\n\n" + 
        Constants.getStack(e), 
        "Transfer Error", 
        0);
    }
  }
  

  public void fileEnd()
  {
    try
    {
      fileOut.close();
      endTime = ((System.currentTimeMillis() - startTime) / 1000L);
      
      transfer = false;
      pickFile.setEnabled(true);
      sendFile.setText("Send File(s)");
      
      statusLabel2.setText("Complete");
      progressBar.setValue(0);
      transLog.add("Downloaded " + fileName + " (" + Constants.getMod(fileLength) + ")" + " in " + endTime + " seconds");
      transferLog.setListData(transLog);
      transferLog.repaint();
      Constants.getPM(client.getUser_id()).log("Server", "Downloaded <font color=\"blue\"><a href=\"file://" + Constants.getDownloadFileLocation() + "\\" + fileName + "\">" + fileName + " (" + Constants.getMod(fileLength) + ")</a></font>" + " in " + endTime + " seconds", null, null);
      
      if (Constants.isPlaySounds())
        Constants.getAudioPlayer().play(AudioPlayer.ALERT);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, 
        "Please read the below error message:\n\n" + 
        Constants.getStack(e), 
        "Transfer Error", 
        0);
    }
  }
  


  public void fileCancel()
  {
    if (!cancelled) {
      endTime = ((System.currentTimeMillis() - startTime) / 1000L);
      
      transfer = false;
      cancelled = true;
      try
      {
        if (fileOut != null)
          fileOut.close();
      } catch (IOException e) {
        JOptionPane.showMessageDialog(null, 
          "Please read the below error message:\n\n" + 
          Constants.getStack(e), 
          "Transfer Error", 
          0);
      }
      
      total = 0L;
      progressBar.setValue(0);
      
      pickFile.setEnabled(true);
      sendFile.setText("Send File(s)");
      
      if (!host) {
        transLog.add("Cancelled " + fileName);
        transferLog.setListData(transLog);
        transferLog.repaint();
        Constants.getPM(client.getUser_id()).log("Server", fileName + " cancelled", null, null);
        statusLabel2.setText("Cancelled " + fileName);
      }
      
      JOptionPane.showMessageDialog(null, 
        "The file transfer of " + fileName + " has been cancelled.", 
        "Transfer Cancelled", 
        0);
    }
  }
  
  public void componentResized(ComponentEvent e)
  {
    transferContainer.setSize(getWidth() / 2 - 15, getHeight() - 140);
    transferLogContainer.setBounds(transferContainer.getWidth() + 10, 5, getWidth() / 2 - 15, getHeight() - 140);
    
    statusLabel.setBounds(5, transferLogContainer.getHeight() + 10, 60, 20);
    statusLabel2.setBounds(50, transferLogContainer.getHeight() + 10, getWidth() - 30, 20);
    progressBar.setBounds(5, transferLogContainer.getHeight() + 35, getWidth() - 27, 25);
    
    pickFile.setBounds(5, transferLogContainer.getHeight() + 70, 235, 25);
    sendFile.setBounds(getWidth() - 255, transferLogContainer.getHeight() + 70, 235, 25);
    
    repaint();
  }
  
  public void keyTyped(KeyEvent e) {}
  
  public void keyReleased(KeyEvent arg0) {}
  
  public void mouseClicked(MouseEvent arg0) {}
  
  public void mouseEntered(MouseEvent arg0) {}
  
  public void mouseExited(MouseEvent arg0) {}
  
  public void mousePressed(MouseEvent arg0) {}
  
  public void componentHidden(ComponentEvent arg0) {}
  
  public void componentMoved(ComponentEvent arg0) {}
  
  public void componentShown(ComponentEvent arg0) {}
}
