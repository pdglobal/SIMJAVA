package SIM.net.client.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;


import SIM.net.client.AudioPlayer;
import SIM.net.client.Client;
import SIM.net.client.Constants;
import SIM.net.client.Profile;
import SIM.net.client.encryption.AESEncoder;
import SIM.net.client.encryption.Encoder;
import SIM.net.client.gui.FriendList;
import SIM.net.client.gui.PersonalMessage;

/**
 * 
 * @author Troy
 *
 */
public class PacketManager {
	
}