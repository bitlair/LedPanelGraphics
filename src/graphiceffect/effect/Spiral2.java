package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

public class Spiral2 extends Effect
{
  double a = 1;


  @Override
  public void drawImage(Image image)
  {
    int rmax = ((width > height ? width : height) + 1) / 2;

    double r = 0;
    double t = 0;
    double b = 1;

    for (r = 0; r < rmax; r += 0.05)
    {
      t = (r - a) / b;
      int x = (int) ((r * Math.cos(t)) + 0.5) + width / 2;
      int y = (int) ((r * Math.sin(t)) + 0.5) + height / 2;
      image.setPixel(x, y, palette.getColor((int) r));
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
