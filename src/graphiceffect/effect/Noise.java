package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

public class Noise extends Effect
{

  @Override
  public void drawImage(Image image)
  {
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        int n = (int) (palette.length() * Math.random());
        image.setPixel(x, y, palette.getColor(n));
      }
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
