package graphiceffect.effect;

import graphiceffect.font.DosHercules;
import graphiceffect.main.Effect;
import graphiceffect.main.Font;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;
import graphiceffect.xml.param;

import java.awt.Color;
import java.util.ArrayList;

public class Text3 extends Effect
{
  int sw = 0;

  int sh = 0;

  int dir = 1;

  int delay = 0;

  Font font;

  int letterCount = 0;

  int letterCountSub = 0;

  ArrayList<int[]> letters = new ArrayList<int[]>();

  int length = 0;

  // spaceLength must be 0 or larger; normally 1
  int spaceLength = 3;

  int count = 0;


  public synchronized void addLetter(char c)
  {
  }


  @Override
  public void drawImage(Image image)
  {
    if (delay >= 1)
    {
      letterCountSub += dir;
      if (dir > 0)
      {
        if (letterCountSub >= getLetter(letterCount).length)
        {
          letterCount++;
          letterCountSub = -spaceLength;
        }
      }
      else if (dir < 0)
      {
        letterCount--;
        letterCountSub = getLetter(letterCount).length - 1;
      }

      delay = 0;
    }
    delay++;

    drawImage2(image);
  }


  public synchronized int[] getLetter(int index)
  {
    index %= letters.size();
    if (index < 0)
    {
      index += letters.size();
    }
    return letters.get(index);
  }


  public void drawImage2(Image image)
  {
    int[] letter = getLetter(letterCount);
    int l = letterCount;
    int z = letterCountSub;
    int a = height / 3;
    int dy = (int) (a + a * Math.sin(2 * 1.5 * Math.PI * count / width));

    for (int x = 0; x < width; x++)
    {
      if (z < 0)
      {
        z++;
        continue;
      }
      for (int y = 0; y < 16; y++)
      {
        int yy = y + dy;
        if ((letter[z] & (0x1 << y)) > 0)
        {
          image.setPixel(x - 1, yy, Color.black);
          image.setPixel(x + 1, yy, Color.black);
          image.setPixel(x, yy - 1, Color.black);
          image.setPixel(x, yy + 1, Color.black);
        }
      }
      z++;
      if (z >= letter.length)
      {
        l++;
        z = -spaceLength;
        letter = getLetter(l);
        dy =
          (int) (a + a * Math.sin(2 * 1.5 * Math.PI * (x + count) / width));
      }
    }

    letter = getLetter(letterCount);
    l = letterCount;
    z = letterCountSub;
    dy = (int) (a + a * Math.sin(2 * 1.5 * Math.PI * count / width));

    for (int x = 0; x < width; x++)
    {
      if (z < 0)
      {
        z++;
        continue;
      }
      for (int y = 0; y < 16; y++)
      {
        int yy = y + dy;
        if ((letter[z] & (0x1 << y)) > 0)
        {
          image.setPixel(x, yy, palette.getColor(0));
        }
      }
      z++;
      if (z >= letter.length)
      {
        l++;
        z = -spaceLength;
        letter = getLetter(l);
        dy =
          (int) (a + a * Math.sin(2 * 1.5 * Math.PI * (x + count) / width));
      }
    }
    count += 5;
  }


  @Override
  protected void init()
  {
  }


  protected void setXml(effect xml)
  {
    font = new DosHercules();

    String s = "aZbYcXdWeVfUgThSiRjQkPlOmNnMoLpKqJrIsHtGuFvEwDxCyBzA";
    // String s =
    // "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,./<>?;':\"[]\\{}|`-=~!@#$%^&*()_+";

    for (param p : xml.getParam())
    {
      String name = p.getName();
      String value = p.getValue();

      if ((name == null) || (name.length() == 0))
      {
        continue;
      }

      if (name.equals("text"))
      {
        s = value;
      }
    }

    for (int i = 0; i < s.length(); i++)
    {
      int[] letter = font.getLetter(s.charAt(i));
      letters.add(letter);
      length += letter.length;
    }
  }
}
