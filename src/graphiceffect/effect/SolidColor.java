package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;
import graphiceffect.xml.param;

import java.awt.Color;

/**
 * XML parameters:
 *   color - The index in the palette for the color used.  
 *
 */
public class SolidColor extends Effect
{
  int colorId = 0;


  @Override
  public void drawImage(Image image)
  {
    Color color = palette.getColor(colorId);
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        image.setPixel(x, y, color);
      }
    }
  }


  @Override
  protected void init()
  {
  }


  protected void setXml(effect effect) throws Exception
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
        colorId = Integer.parseInt(value);
      }
    }
  }
}
