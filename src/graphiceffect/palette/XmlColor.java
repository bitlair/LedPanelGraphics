package graphiceffect.palette;

import graphiceffect.main.Palette;
import graphiceffect.xml.palette;
import graphiceffect.xml.param;

import java.awt.Color;
import java.util.ArrayList;

import utils.Utils;

/**
 * XML parameters:
 *   hasBlack - The index in the palette for the color used.
 *   alpha    -
 *   distance -  
 *
 */
public class XmlColor extends Palette
{
  private int[] ints;

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
    ArrayList<Integer> color = new ArrayList<Integer>();
    
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
      else if (name.equals("distance"))
      {
        dist = Integer.parseInt(value);
      }
      else if (name.equals("color"))
      {
        color.add(Integer.parseInt(value, 16));
      }
    }
    ints = new int[color.size()];
    for (int i = 0; i < ints.length; i++)
    {
      ints[i] = color.get(i);
    }
    setColors(ints);
  }
}
