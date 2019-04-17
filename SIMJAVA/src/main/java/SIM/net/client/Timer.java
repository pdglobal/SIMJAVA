package SIM.net.client;


public class Timer
{
  public long startTime;
  
  public long endTime;
  
  public long timeLimit;
  
  public Timer(int timeLimit)
  {
    this.timeLimit = timeLimit;
    startTime = System.currentTimeMillis();
    reset();
  }
  
  public void reset() {
    endTime = ((this.startTime = System.currentTimeMillis()) + timeLimit);
  }
  
  public long getTimeElapsed() {
    return System.currentTimeMillis() - startTime;
  }
  
  public long getTimeLeft() {
    return endTime - System.currentTimeMillis();
  }
  
  public boolean isUp() {
    return System.currentTimeMillis() > endTime;
  }
  
  public boolean isNotUp() {
    return System.currentTimeMillis() < endTime;
  }
  
  public long getTimeRemaining() {
    return endTime - System.currentTimeMillis();
  }
  
  public long setTimerToEndIn(long endTime) {
    return this.endTime = System.currentTimeMillis() + endTime;
  }
  
  public String toStringTimeElapsed() {
    return toStringTime(getTimeElapsed());
  }
  
  public String toStringTimeLeft() {
    return toStringTime(getTimeLeft());
  }
  
  public long getTotalMins() {
    long TotalSecs = getTimeElapsed() / 1000L;
    return TotalSecs / 60L;
  }
  
  public static String toStringTime(long time) {
    StringBuilder b = new StringBuilder();
    long runtime = time;
    long TotalSecs = runtime / 1000L;
    long TotalMins = TotalSecs / 60L;
    long TotalHours = TotalMins / 60L;
    int seconds = (int)TotalSecs % 60;
    int minutes = (int)TotalMins % 60;
    int hours = (int)TotalHours % 60;
    if (hours < 10)
      b.append("0");
    b.append(hours);
    b.append(" : ");
    if (minutes < 10)
      b.append("0");
    b.append(minutes);
    b.append(" : ");
    if (seconds < 10)
      b.append("0");
    b.append(seconds);
    
    return String.valueOf(b.toString());
  }
}
