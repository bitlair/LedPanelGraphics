package graphiceffect.effect;

import java.awt.Color;
import java.util.Calendar;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

public class AnalogClock1 extends Effect
{
  @Override
  public void drawImage(Image image)
  {
    Calendar cal = Calendar.getInstance();
    int min = cal.get(Calendar.MINUTE);
    int hour = cal.get(Calendar.HOUR_OF_DAY);
    int sec = cal.get(Calendar.SECOND);

    int xm = width / 2;
    int ym = height / 2;
    int n = 12;

    for (int i = 0; i < n; i++)
    {
      int x1 = (int) (0.5 + 20 * Math.sin(2.0 * Math.PI * i / n));
      int y1 = (int) (0.5 + 20 * Math.cos(2.0 * Math.PI * i / n));
      int x2 = (int) (0.5 + 22 * Math.sin(2.0 * Math.PI * i / n));
      int y2 = (int) (0.5 + 22 * Math.cos(2.0 * Math.PI * i / n));
      image.drawLine(xm + x1, ym + y1, xm + x2, ym + y2, Color.red);
      x1 = 0;
      y1 = 0;
      x2 =
        (int) (0.5 + 21 * Math.sin(2.0 * Math.PI * (hour * 60.0 + min)
          / 720.0));
      y2 =
        (int) (0.5 + 21 * Math.cos(2.0 * Math.PI * (hour * 60.0 + min)
          / 720.0));
      image.drawLine(xm + x1, ym + y1, xm + x2, ym + y2, Color.yellow);
      x2 = (int) (0.5 + 18 * Math.sin(2.0 * Math.PI * min / 60.0));
      y2 = (int) (0.5 + 18 * Math.cos(2.0 * Math.PI * min / 60.0));
      image.drawLine(xm + x1, ym + y1, xm + x2, ym + y2, Color.green);
      // x1 = (int) (0.5 + 18 * Math.sin(2.0 * Math.PI * sec / 60.0));
      // y1 = (int) (0.5 + 18 * Math.cos(2.0 * Math.PI * sec / 60.0));
      x2 = (int) (0.5 + 15 * Math.sin(2.0 * Math.PI * sec / 60.0));
      y2 = (int) (0.5 + 15 * Math.cos(2.0 * Math.PI * sec / 60.0));
      image.drawLine(xm + x1, ym + y1, xm + x2, ym + y2, Color.red);
    }
  }


  @Override
  protected void init()
  {
  }


  @Override
  protected void setXml(effect xml) throws Exception
  {
  }
}
