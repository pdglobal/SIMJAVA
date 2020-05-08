package SIM.net.client.networking;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class nodeP2P {

		public Map<String, peer> peers = new HashMap<String, peer>();
		public Map<String, String> bcastStore = new HashMap<String, String>();
		public server server;
		public client client;
		public peer me;
		
		public nodeP2P(String username, int port, String IP) {
			this.server = new server(username, IP, port);
			this.client = new client();
			client.connect(username, "127.0.0.1", port);
			this.me = client.getPeer(username);
		}
		
		public class peer {
			public String username;
			public String ip;
			public int port;
			public Socket socket;
			BufferedReader br=null;
		    BufferedReader is=null;
		    PrintWriter os=null;

		    
		    public void ident() {
		    	this.senddata("HANDSHAKE;".concat(me.username).concat(";").concat(me.ip));
		    }

		    public void sendmsg(String message) {
		    	if (client.getPeer(username)== null) {
		    		server.broadcast(message);
		    	} else {
		    		client.getPeer(username).senddata("DIRECT;".concat(me.username).concat(";").concat(message));
		    	}
		    }
		    
			public peer(String username, String ip, int port, Socket socket) {
				this.username = username;
				this.ip = ip;
				this.port = port;
				if (peers.get(username) == null) {
				try {
					this.socket =new Socket(ip, port);
					new P2PThread(socket, peers).start();
					System.out.println();
					System.out.println("SERVER STARTED");
			        br= new BufferedReader(new InputStreamReader(System.in));
			        is=new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			        os= new PrintWriter(this.socket.getOutputStream());
			        }
				catch (IOException e){
			       this.socket = null;
			       System.out.println("ERROR CONNECTING TO PEER: ALREADY CONNECTED");
			    }
			}
			}
			
			public boolean senddata(String rawdata) {
                os.println(rawdata);
                os.flush();
                return true;
			}
		}

		public class server {
			int port;
			String bindIP;
			String username;
			ServerSocket serversocket;
	        Socket socket;
	        
	        public void broadcast(String message) {
	        for (Map.Entry<String,peer> entry : peers.entrySet())  {
	    		entry.getValue().senddata("BROADCAST;".concat(me.username).concat(";").concat(message));
	    	} 
	        }
	        
			public server(String username, String bindIP, int port) {
				this.port = port;
				this.bindIP = bindIP;
				this.username = username;
				try {
					this.serversocket = new ServerSocket(port);
					 Thread thread = new Thread(){
						    public void run(){
						        while (true) {
						            try {
						                socket = serversocket.accept();
						            } catch (IOException e) {
						                System.out.println("I/O error: " + e);
						            }
						            // new thread for a client
						            new P2PThread(socket, peers).start();
						            
						        }
						    }
						  };

						  thread.start();
				} catch (IOException e) {
					 System.out.println("I/O error: " + e);
					this.socket = null;
				}
				
			}
		}
		
		public class P2PThread extends Thread {
		    protected Socket socket;

		    public P2PThread(Socket clientSocket, Map<String, peer> peers) {
		        this.socket = clientSocket;
		    }

		    public void run() {
		        InputStream inp = null;
		        BufferedReader brinp = null;
		        DataOutputStream out = null;
		        try {
		            inp = socket.getInputStream();
		            brinp = new BufferedReader(new InputStreamReader(inp));
		            out = new DataOutputStream(socket.getOutputStream());
		        } catch (IOException e) {
		            return;
		        }
		        String line;
		        System.out.println("Connected to peer: ".concat(socket.getInetAddress().getHostAddress()));
		        while (true) {
		            try {
		                line = brinp.readLine();
		                if ((line == null)) {
		                	//do nothing
		                } else {
		                	String[] lineData = line.split(";");
		                    if (lineData[0].equals("HANDSHAKE")) {
		                    	peers.put(lineData[1], new peer(lineData[1], socket.getInetAddress().getHostAddress(), socket.getPort(), socket));
		                    	System.out.println("PEER @ ".concat(socket.getInetAddress().getHostAddress()).concat(":").concat(String.valueOf(socket.getPort())).concat(" IDENTIFIED AS USER ").concat(lineData[1]));
		                    }
		                    if (lineData[0].equals("QUIT")) {
		                    	peers.remove(lineData[1]);
		                    	socket.close();
		                    	return;
		                    }
		                    if (lineData[0].equals("BROADCAST")) {
		                    	String bhash = getMd5(lineData[1].concat(lineData[2]));
		                    	if (!bcastStore.containsKey(bhash)) {
		                    		//we have received a new broadcast
		                    		//check if for me, if not, check peers
		                    		//check peers, if peer found, send DIRECT
		                    		//if peer not found re-broadcast
		                    		bcastStore.put(bhash, lineData[1].concat(lineData[2]));
		                    	}
		                    	
		                    }
		                    
		                    
		                }
		            } catch (IOException e) {
		            System.out.println("No data from ".concat(socket.getInetAddress().getHostAddress()));
		            
		            return;
		            }
		        }
		    }
		}

		public class client {
			public String username;

			public boolean connect(String username, String ip, int port) {
				Socket socket = null;
				try {
					socket = new Socket(ip, port);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (socket.isConnected()) {
					peers.put(username, new peer(username, ip, port, socket));
					return true;
				} else {
					return false;
				}
			}

			public peer getPeer(String username) {
				if (peers.containsKey(username)) {
					return peers.get(username);
				} else {
					return null;
				}
			}
			
			
		}
		
		public static String getMd5(String input) 
	    { 
	        try { 
	  
	            // Static getInstance method is called with hashing MD5 
	            MessageDigest md = MessageDigest.getInstance("MD5"); 
	  
	            // digest() method is called to calculate message digest 
	            //  of an input digest() return array of byte 
	            byte[] messageDigest = md.digest(input.getBytes()); 
	  
	            // Convert byte array into signum representation 
	            BigInteger no = new BigInteger(1, messageDigest); 
	  
	            // Convert message digest into hex value 
	            String hashtext = no.toString(16); 
	            while (hashtext.length() < 32) { 
	                hashtext = "0" + hashtext; 
	            } 
	            return hashtext; 
	        }  
	  
	        // For specifying wrong message digest algorithms 
	        catch (NoSuchAlgorithmException e) { 
	            throw new RuntimeException(e); 
	        } 
	    } 

}
