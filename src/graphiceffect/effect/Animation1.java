package graphiceffect.effect;

import java.awt.Color;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

//For more information on how this math works, check out:
//http://blog.marmakoide.org/?p=1

public class Animation1 extends Effect
{
  // The number of points
  double N = 200;

  // The initial angle change between succesive points
  // Roughly 360 * (1 - 1/phi) degrees
  double phi = 5;

  double angleChange = 360.0 * (1 - 1 / phi);

  // The radius of the circle
  int maxRadius;


  @Override
  public void drawImage(Image image)
  {
    maxRadius = (width > height) ? width : height;

    for (int i = 0; i < N; i += 1)
    {
      double radius = maxRadius * (i / N) / 2 +2;
      double theta = angleChange * i;

      double x = width / 2 + radius * Math.cos(theta * Math.PI / 180.0);
      double y = height / 2 + radius * Math.sin(theta * Math.PI / 180.0);
      image.setPixel((int) x, (int) y, Color.yellow);
    }

    angleChange += 0.02;
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
