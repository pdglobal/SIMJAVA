package SIM.net.client;


public class Client
{
  private int user_id;
  
  private String username;
  
  private String screen_name;
  private int status;
  private boolean online;
  private boolean playedSound;
  
  public Client(int user_id, String username, String screen_name, int status, boolean online, boolean playedSound)
  {
    this.user_id = user_id;
    this.username = username;
    this.screen_name = screen_name;
    this.status = status;
    this.online = online;
    this.playedSound = playedSound;
  }
  
  public int getUser_id() {
    return user_id;
  }
  
  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getScreen_name() {
    return screen_name;
  }
  
  public void setScreen_name(String screen_name) {
    this.screen_name = screen_name;
  }
  
  public int getStatus() {
    return status;
  }
  
  public void setStatus(int status) {
    this.status = status;
  }
  
  public boolean isOnline() {
    return online;
  }
  
  public void setOnline(boolean online) {
    this.online = online;
  }
  
  public boolean isPlayedSound() {
    return playedSound;
  }
  
  public void setPlayedSound(boolean playedSound) {
    this.playedSound = playedSound;
  }
}
