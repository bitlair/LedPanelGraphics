package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

public class Spiral1 extends Effect
{
  double a = 1;


  @Override
  public void drawImage(Image image)
  {
    int hw = width / 2;
    int hh = height / 2;

    double r;
    double t;
    double b;

    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        double xx = (x - hw);
        double yy = (y - hh);
        r = Math.sqrt(xx * xx + yy * yy);
        t = Math.atan2(yy, xx);
        b = (r - a) / t;
        image.setPixel(x, y, palette.getColor((int) b));
      }
    }
    a += 0.5;
  }


  @Override
  protected void init()
  {
  }


  protected void setXml(effect effect)
  {
  }
}
