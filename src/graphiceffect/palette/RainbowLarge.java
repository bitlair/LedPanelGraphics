package graphiceffect.palette;

import graphiceffect.main.Palette;
import graphiceffect.xml.palette;
import graphiceffect.xml.param;

public class RainbowLarge extends Palette
{
  private void fillColors()
  {
    int[] result = new int[6 * 256];
    int j = 0;
    for (int i = 0; i < 256; i++)
    {
      result[j + i] = rgb(255, i, 0);
    }
    j += 256;
    for (int i = 0; i < 256; i++)
    {
      result[j + i] = rgb(255 - i, 255, 0);
    }
    j += 256;
    for (int i = 0; i < 256; i++)
    {
      result[j + i] = rgb(0, 255, i);
    }
    j += 256;
    for (int i = 0; i < 256; i++)
    {
      result[j + i] = rgb(0, 255 - i, 255);
    }
    j += 256;
    for (int i = 0; i < 256; i++)
    {
      result[j + i] = rgb(i, 0, 255);
    }
    j += 256;
    for (int i = 0; i < 256; i++)
    {
      result[j + i] = rgb(255, 0, 255 - i);
    }
    j += 256;
    setColors(result);
  }


  @Override
  protected void setXml(palette palette) throws Exception
  {
    for (param p : palette.getParam())
    {
      String name = p.getName();
      String value = p.getValue();

      if ((name == null) || (name.length() == 0))
      {
        continue;
      }

      if (name.equals("alpha"))
      {
        alpha = Integer.parseInt(value);
      }
    }
    fillColors();
  }
}
