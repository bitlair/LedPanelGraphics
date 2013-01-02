package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;
import graphiceffect.xml.param;

public class Spiral extends Effect
{
  double a = 1;


  @Override
  public void drawImage(Image image)
  {
    int rmax = ((width > height ? width : height) + 1) / 2;

    double r = 0;
    double t = 0;
    double b = 1;

    for (b = 1; b < 6; b++)
    {
      for (r = 0; r < rmax; r += 0.05)
      {
        t = (r - a) / b;
        int x = (int) ((r * Math.cos(t)) + 0.5) + width / 2;
        int y = (int) ((r * Math.sin(t)) + 0.5) + height / 2;
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
    for (param p : effect.getParam())
    {
      String name = p.getName();
      String value = p.getValue();
      
      if ((name == null) || (name.length() == 0))
      {
        continue;
      }

      if (name.equals("color"))
      {
//        colorId = Integer.parseInt(value);
      }
    }
  }
}
