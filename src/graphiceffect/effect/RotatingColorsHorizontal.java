package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

import java.awt.Color;

public class RotatingColorsHorizontal extends Effect
{
  private int count = 0;

  /**
   * Value: int != 0; neg = inwards, pos = outwards
   */
  public int speed = 1;


  @Override
  public void drawImage(Image image)
  {
    for (int x = 0; x < width; x++)
    {
      int c = Math.abs(width / 2 - x);
      for (int y = 0; y < height; y++)
      {
        c += (int) (10 * Math.sin(1.0 + 2 * Math.PI * x  * y / height / 1));
        Color color = palette.getColor(c + count);
        image.setPixel(x, y, color);
      }
    }
    count -= speed;
  }


  @Override
  protected void init()
  {
  }


  protected void setXml(effect effect)
  {
  }
}
