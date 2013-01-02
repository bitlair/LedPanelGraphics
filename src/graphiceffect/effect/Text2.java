package graphiceffect.effect;

import graphiceffect.font.DosHercules;
import graphiceffect.main.Effect;
import graphiceffect.main.Font;
import graphiceffect.main.Image;
import graphiceffect.xml.effect;

import java.util.ArrayList;

public class Text2 extends Effect
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
  int spaceLength = 2;


  public synchronized void addLetter(char c)
  {

  }


  @Override
  public void drawImage(Image image)
  {
    if (delay >= 2)
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
    for (int x = 0; x < width; x++)
    {
      if (z < 0)
      {
        z++;
        continue;
      }
      for (int y = 0; y < 16; y++)
      {
        if ((letter[z] & (0x1 << y)) > 0)
        {
          // image.setPixel(x + 1, y - 1, Color.black);
          // image.setPixel(x, y, Color.red);

          image.setPixel(x, y, palette.getColor(l));
        }
      }
      z++;
      if (z >= letter.length)
      {
        l++;
        z = -spaceLength;
        letter = getLetter(l);
      }
    }
  }


  @Override
  protected void init()
  {
  }


  protected void setXml(effect effect)
  {
    font = new DosHercules();
    try
    {
      // font = new FontArial10();
    }
    catch (Exception e)
    {
      System.out.println("ERROR: " + e.getMessage());
      e.printStackTrace();
    }

    String s = font.testString();
    for (int i = 0; i < s.length(); i++)
    {
      int[] letter = font.getLetter(s.charAt(i));
      letters.add(letter);
      length += letter.length;
    }
  }
}
