package graphiceffect.palette;

import graphiceffect.main.Palette;
import graphiceffect.xml.palette;
import graphiceffect.xml.param;

import java.awt.Color;

import utils.Utils;

/**
 * XML parameters:
 *   hasBlack - The index in the palette for the color used.
 *   alpha    -
 *   distance -  
 *
 */
public class RedGreenYellow extends Palette
{
  private int[] ints =
  {
    0xff0000, 0x00ff00, 0xffff00, 
  };

  int dist = 1;


  public int length()
  {
    return colors.length * dist;
  }


  public Color getColor(int index)
  {
    return colors[Utils.mod(index / dist, colors.length)];
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

      if (name.equals("hasBlack"))
      {
        if (Boolean.parseBoolean(value))
        {
          int[] intsNew = new int[ints.length + 1];
          intsNew [0] = 0;
          for (int i = 0; i < ints.length; i++)
          {
            intsNew[i + 1] = ints[i];
          }
          ints = intsNew;
        }
      }
      else if (name.equals("alpha"))
      {
        alpha = Integer.parseInt(value);
      }
      else if (name.equals("distance"))
      {
        dist = Integer.parseInt(value);
      }
    }
    setColors(ints);
  }
}
