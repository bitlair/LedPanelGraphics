package graphiceffect.main;

import graphiceffect.xml.palette;

import java.awt.Color;

import utils.Utils;

public abstract class Palette
{
  protected Color[] colors;

  protected int alpha = 255;


  public int length()
  {
    return colors.length;
  }


  public Color getColor(int index)
  {
    return colors[Utils.mod(index, colors.length)];
  }


  public Color[] getColors()
  {
    return colors;
  }


  protected void setColors(int[] ints)
  {
    colors = new Color[ints.length];
    for (int i = 0; i < ints.length; i++)
    {
      colors[i] = new Color(ints[i] + (alpha << 24), true);
    }
  }


  protected void setColors(Color[] colors)
  {
    this.colors = new Color[colors.length];
    for (int i = 0; i < colors.length; i++)
    {
      this.colors[i] = new Color((colors[i].getRGB() << 8) + alpha, true);
    }
  }


  public int rgb(int r, int g, int b)
  {
    return ((r & 0xff) << 16) + ((g & 0xff) << 8) + (b & 0xff);
  }


  public static Palette getPalette(palette xml) throws Exception
  {
    if (xml == null)
    {
      return null;
    }
    
    String name = xml.getClassName();
    Palette p =
      (Palette) ClassLoader.getSystemClassLoader().loadClass(name)
        .newInstance();
    if ((xml.getParam() != null) && (xml.getParam().length > 0))
    {
      p.setXml(xml);
    }
    return p;
  }


  abstract protected void setXml(palette xml) throws Exception;
}
