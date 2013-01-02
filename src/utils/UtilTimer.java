package utils;

import java.util.Timer;
import java.util.TimerTask;

public class UtilTimer extends TimerTask
{
  final Runnable timerTask;

  Timer timer = new Timer();


  public UtilTimer(int msec, Runnable timerTask)
  {
    this.timerTask = timerTask;
    timer.schedule(this, 1000, msec);
  }


  @Override
  public void run()
  {
    timerTask.run();
  }


  public void stop()
  {
    if (timer != null)
    {
      timer.cancel();
    }
    timer = null;
  }
}


