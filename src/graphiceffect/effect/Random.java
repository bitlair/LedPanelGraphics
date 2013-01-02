package graphiceffect.effect;

import graphiceffect.main.Effect;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

import java.awt.Color;


public class Random extends Effect
{
  int count = 0;

  Color[] colors;

  int[][] pixels;


  @Override
  public void drawImage(Image image)
  {
    int speed = 10;

    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        int c = (count + pixels[x][y]) % colors.length;
        image.setPixel(x, y, colors[c]);
      }
    }
    count += speed;
  }


  private void fillColors()
  {
    Color[] result = new Color[6 * 256];
    int j = 0;
    for (int i = 0; i < 256; i++)
    {
      result[j + i] = new Color(255, i, 0);
    }
    j += 256;
    for (int i = 0; i < 256; i++)
    {
      result[j + i] = new Color(255 - i, 255, 0);
    }
    j += 256;
    for (int i = 0; i < 256; i++)
    {
      result[j + i] = new Color(0, 255, i);
    }
    j += 256;
    for (int i = 0; i < 256; i++)
    {
      result[j + i] = new Color(0, 255 - i, 255);
    }
    j += 256;
    for (int i = 0; i < 256; i++)
    {
      result[j + i] = new Color(i, 0, 255);
    }
    j += 256;
    for (int i = 0; i < 256; i++)
    {
      result[j + i] = new Color(255, 0, 255 - i);
    }
    j += 256;
    colors = result;
  }


  @Override
  protected void init()
  {
  }


  protected void setXml(effect effect)
  {
    fillColors();

    pixels = new int[width][height];
    for (int x = 0; x < width; x++)
    {
      for (int y = 0; y < height; y++)
      {
        pixels[x][y] = (int) (colors.length * Math.random());
      }
    }
  }
}
